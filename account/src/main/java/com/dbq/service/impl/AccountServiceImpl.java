package com.dbq.service.impl;

import com.dbq.common.model.Account;
import com.dbq.mapper.AccountMapper;
import com.dbq.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dbq
 * @create 2018-10-19 16:56
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account getById(Integer id) {
        return accountMapper.selectById(id);
    }
}
