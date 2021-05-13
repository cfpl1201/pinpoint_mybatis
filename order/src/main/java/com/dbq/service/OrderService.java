package com.dbq.service;


import com.dbq.common.model.Account;

/**
 * @author dbq
 * @create 2018-10-23 9:47
 */
public interface OrderService {
    /**
     * @param userId
     * @return
     */
    Account getBankAccount(Integer userId);

}