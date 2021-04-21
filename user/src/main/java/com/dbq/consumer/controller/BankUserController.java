package com.dbq.consumer.controller;

import com.dbq.common.model.BankAccount;
import com.dbq.common.result.PojoResult;
import com.dbq.consumer.service.BankUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dbq
 * @create 2018-10-19 11:58
 */
@RestController
@RequestMapping("/bank/user")
public class BankUserController {

    @Autowired
    private BankUserService bankUserService;

    /**
     * 查询指定用户的银行账号
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/account")
    public PojoResult<BankAccount> getAccountByUserId(@PathVariable Integer id) {
        return new PojoResult<>(bankUserService.getBankAccount(id));
    }

}
