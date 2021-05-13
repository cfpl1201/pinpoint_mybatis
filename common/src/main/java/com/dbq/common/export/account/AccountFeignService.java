package com.dbq.common.export.account;

import com.dbq.common.export.account.fallback.AccountFeignFallback;
import com.dbq.common.model.Account;
import com.dbq.common.result.PojoResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author dbq
 * @create 2018-10-19 17:06
 */
@FeignClient(value = "spring-cloud-account-server", fallback = AccountFeignFallback.class)
public interface AccountFeignService {

    /**
     * 查询指定用户的银行账号
     *
     * @param userId
     * @return
     */
    @GetMapping("/account/{userId}")
    PojoResult<Account> getBankAccountByUserId(@PathVariable("userId") Integer userId);

}