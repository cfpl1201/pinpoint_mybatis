package com.dbq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Override
    public Account getById(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void updateAccount(Integer userId) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        Account account = baseMapper.selectById(queryWrapper);

        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId);
        updateWrapper.set("balance", account.getBalance()-1);
        baseMapper.update(account, updateWrapper);
    }
}
