package com.dbq.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dbq.common.model.Account;
import com.dbq.common.model.Order;

/**
 * @author dbq
 * @create 2018-10-23 9:47
 */
public interface OrderService extends IService<Order> {
    /**
     * @param userId
     * @return
     */
    Account getBankAccount(Integer userId);

    void add(Integer userId);
}