package com.dbq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dbq.common.export.account.AccountFeignService;
import com.dbq.common.model.Account;
import com.dbq.common.model.Order;
import com.dbq.common.redis.RedisUtils;
import com.dbq.common.result.PojoResult;
import com.dbq.util.HttpUtil;
import com.dbq.mapper.OrderMapper;
import com.dbq.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author dbq
 * @create 2018-10-23 9:48
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private AccountFeignService accountFeignService;

    @Override
    public Account getBankAccount(Integer userId) {
        boolean exists = baseMapper.selectByUserId(userId) != null;
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
        PojoResult<Account> result = accountFeignService.getBankAccountByUserId(userId);
        if (!result.isSuccess()) {
            LOGGER.debug("查询用户Id:[{}]失败,ErrorCode:[{}]", userId, result.getErrorCode());
            return null;
        }

        return result.getContent();
    }

    @Override
    public void add(Integer userId) {
        //新增一条订单记录
        Order order = new Order();
        order.setUserId(1);
        order.setProductId(1);
        order.setPayAmount(1D);
        order.setStatus("1");
        order.setAddTime(new Date());
        order.setLastUpdateTime(new Date());
        this.save(order);

        //更新账户信息，模拟分布式事务
        accountFeignService.updateAccount(userId);

    }
}
