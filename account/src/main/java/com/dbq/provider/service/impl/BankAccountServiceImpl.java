package com.dbq.provider.service.impl;

import com.dbq.common.model.BankAccount;
import com.dbq.provider.mapper.BankAccountMapper;
import com.dbq.provider.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dbq
 * @create 2018-10-19 16:56
 */
@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountMapper bankAccountMapper;

    @Override
    public BankAccount getById(Integer id) {
        return bankAccountMapper.selectById(id);
    }
}
