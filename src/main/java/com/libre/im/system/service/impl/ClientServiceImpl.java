package com.libre.im.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.im.system.mapper.ClientMapper;
import com.libre.im.system.pojo.entity.Client;
import com.libre.im.system.service.ClientService;
import org.springframework.stereotype.Service;

/**
 * @author zhao.cheng
 */
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> implements ClientService {

}
