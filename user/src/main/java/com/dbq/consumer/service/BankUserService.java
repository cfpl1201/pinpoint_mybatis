package com.dbq.consumer.service;


import com.dbq.common.model.BankAccount;

/**
 * @author dbq
 * @create 2018-10-23 9:47
 */
public interface BankUserService {
    /**
     * @param userId
     * @return
     */
    BankAccount getBankAccount(Integer userId);

}