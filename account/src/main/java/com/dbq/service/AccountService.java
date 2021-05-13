package com.dbq.service;


import com.dbq.common.model.Account;

/**
 * @author dbq
 * @create 2018-10-19 16:56
 */
public interface AccountService {

    /**
     * 查询用户账号
     *
     * @param id
     * @return
     */
    Account getById(Integer id);

}