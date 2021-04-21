package com.dbq.provider.service;


import com.dbq.common.model.BankAccount;

/**
 * @author dbq
 * @create 2018-10-19 16:56
 */
public interface BankAccountService {

    /**
     * 查询用户账号
     *
     * @param id
     * @return
     */
    BankAccount getById(Integer id);

}