package com.dbq.controller;

import com.dbq.common.model.Account;
import com.dbq.common.result.PojoResult;
import com.dbq.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dbq
 * @create 2018-10-19 16:58
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 查询用户指定账号
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public PojoResult<Account> getAccount(@PathVariable Integer userId) {
        return new PojoResult<>(accountService.getById(userId));
    }
}
