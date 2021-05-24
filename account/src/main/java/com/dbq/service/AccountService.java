package com.dbq.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dbq.common.model.Account;

/**
 * @author admin
 */
public interface AccountService extends IService<Account> {

    /**
     * 查询用户账号
     *
     * @param id
     * @return
     */
    Account getById(Integer id);

    void updateAccount(Integer userId);
}