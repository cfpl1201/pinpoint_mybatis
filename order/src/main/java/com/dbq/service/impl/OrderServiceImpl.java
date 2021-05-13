package com.dbq.service.impl;

import com.dbq.common.export.account.BankAccountFeignService;
import com.dbq.common.model.Account;
import com.dbq.common.redis.RedisUtils;
import com.dbq.common.result.PojoResult;
import com.dbq.util.HttpUtil;
import com.dbq.mapper.OrderMapper;
import com.dbq.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dbq
 * @create 2018-10-23 9:48
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private BankAccountFeignService bankAccountFeignService;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Account getBankAccount(Integer userId) {
        boolean exists = orderMapper.selectById(userId) != null;
        if (!exists) {
            LOGGER.debug("用户Id:[{}]不存在", userId);
            return null;
        }

        //测试与外部系统的连接
        try {
            HttpUtil.doPost("https://www.huining.info/");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //测试与redis的连接
        try {
            RedisUtils.get("test");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //测试Feign调用
        PojoResult<Account> result = bankAccountFeignService.getBankAccountByUserId(userId);
        if (!result.isSuccess()) {
            LOGGER.debug("查询用户Id:[{}]失败,ErrorCode:[{}]", userId, result.getErrorCode());
            return null;
        }

        return result.getContent();
    }
}
