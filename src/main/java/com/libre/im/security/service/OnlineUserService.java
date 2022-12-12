package com.libre.im.security.service;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.libre.boot.toolkit.RequestUtils;
import com.libre.im.config.LibreSecurityProperties;
import com.libre.im.security.pojo.dto.AuthUser;
import com.libre.im.security.pojo.dto.OnlineUserDTO;
import com.libre.ip2region.core.Ip2regionSearcher;
import com.libre.ip2region.core.IpInfo;
import com.libre.redis.cache.RedisUtils;
import com.libre.toolkit.core.CharPool;
import com.libre.toolkit.core.DesensitizationUtil;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Zheng Jie
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OnlineUserService {

    private final LibreSecurityProperties properties;
    private final Ip2regionSearcher regionSearcher;
    private final RedisUtils redisUtils;

    /**
     * 保存在线用户信息
     *
     * @param authUser {@link AuthUser}
     * @param token     jwt token
     * @param request   {@link HttpServletRequest}
     */
    public void save(AuthUser authUser, String token, HttpServletRequest request) {

        String storePrefix = properties.getJwtToken().getStorePrefix();
        String ip = RequestUtils.getIp();
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader(HttpHeaders.USER_AGENT));
        String browser = userAgent.getBrowser().getName();
        IpInfo ipInfo = regionSearcher.memorySearch(ip);
        String key = DigestUtils.md5Hex(token);
        OnlineUserDTO onlineUserDto = new OnlineUserDTO();
        if (ipInfo != null) {
            onlineUserDto.setAddress(ipInfo.getAddress());
        }
        onlineUserDto.setUserName(authUser.getUsername());
        onlineUserDto.setNickName(authUser.getNickName());
        onlineUserDto.setKey(key);
        // token 摘要，前6后8，中间4位占位符
        onlineUserDto.setSummary(DesensitizationUtil.sensitive(token, 8, 8, CharPool.DOT, 4));
        onlineUserDto.setLoginTime(LocalDateTime.now());
        onlineUserDto.setBrowser(browser);
        onlineUserDto.setIp(ip);
        redisUtils.setEx(storePrefix + key, onlineUserDto, properties.getJwtToken().getExpireTime());
    }


    /**
     * 查询全部数据，不分页
     *
     * @param filter /
     * @return /
     */
    public List<OnlineUserDTO> getAll(String filter) {
        Set<String> keys = redisUtils.scan(properties.getJwtToken().getStorePrefix() + StringPool.ASTERISK);
        List<OnlineUserDTO> onlineUsers = new ArrayList<>();
        for (String key : keys) {
            OnlineUserDTO onlineUserDto = redisUtils.get(key);
            if (StringUtils.isNotBlank(filter) && null != onlineUserDto) {
                if (onlineUserDto.toString().contains(filter)) {
                    onlineUsers.add(onlineUserDto);
                }
            } else {
                onlineUsers.add(onlineUserDto);
            }
        }
        onlineUsers.sort((o1, o2) -> o2.getLoginTime().compareTo(o1.getLoginTime()));
        return onlineUsers;
    }

    @Async
    public void removeByToken(String token) {
        redisUtils.del(getCacheKeyByToken(token));
    }

    /**
     * 踢出用户
     *
     * @param key /
     */
    public void kickOut(String key) {
        key = properties.getJwtToken().getStorePrefix() + key;
        redisUtils.del(key);
    }


    /**
     * 查询用户
     *
     * @param token /
     * @return /
     */
    public OnlineUserDTO getOne(String token) {
        return redisUtils.get(getCacheKeyByToken(token));
    }

    /**
     * 检测用户是否在之前已经登录，已经登录踢下线
     * @param userName 用户名
     */
    public void checkLoginOnUser(String userName, String ignoreToken) {
        List<OnlineUserDTO> onlineUsers = getAll(userName);
        if (CollectionUtils.isEmpty(onlineUsers)) {
            return;
        }
        for (OnlineUserDTO onlineUserDto : onlineUsers) {
            if (!Objects.equals(onlineUserDto.getUserName(), userName)) {
                continue;
            }
            String key = onlineUserDto.getKey();
            if (StringUtils.isNotBlank(ignoreToken) ) {
                String ignoreKey = DigestUtils.md5Hex(ignoreToken);
                if (!ignoreKey.equals(key)) {
                    this.kickOut(key);
                }
            } else  {
                this.kickOut(key);
            }
        }
    }

    /**
     * 根据用户名强退用户
     * @param username username
     */
    @Async
    public void kickOutForUsername(String username) throws Exception {
        List<OnlineUserDTO> onlineUsers = getAll(username);
        for (OnlineUserDTO onlineUser : onlineUsers) {
            if (onlineUser.getUserName().equals(username)) {
                kickOut(onlineUser.getKey());
            }
        }
    }

    private String getCacheKeyByToken(String token) {
        String storePrefix = properties.getJwtToken().getStorePrefix();
        String key = DigestUtils.md5Hex(token);
        return storePrefix + key;
    }
}
