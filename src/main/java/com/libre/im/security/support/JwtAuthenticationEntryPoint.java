package com.libre.im.security.support;

import com.libre.boot.toolkit.RequestUtils;
import com.libre.toolkit.result.R;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhao.cheng
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 当用户尝试访问安全的REST资源而不提供任何凭据时，将调用此方法发送401 响应
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        RequestUtils.renderJson(response, R.fail(HttpStatus.UNAUTHORIZED.value(),"没有权限访问"));
    }
}
