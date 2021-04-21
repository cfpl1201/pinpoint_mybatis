package com.dbq.provider.controller;

import com.dbq.common.model.BankAccount;
import com.dbq.common.result.PojoResult;
import com.dbq.provider.service.BankAccountService;
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
@RequestMapping("/bank/account")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    /**
     * 查询用户指定账号
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public PojoResult<BankAccount> getAccount(@PathVariable Integer userId) {
        return new PojoResult<>(bankAccountService.getById(userId));
    }
}
