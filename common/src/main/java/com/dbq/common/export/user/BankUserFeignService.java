package com.dbq.common.export.user;

import com.dbq.common.export.user.fallback.BankUserFeignFallback;
import com.dbq.common.result.PojoResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author dbq
 * @create 2018-10-19 17:45
 */
@FeignClient(value = "spring-cloud-user-server", fallback = BankUserFeignFallback.class)
public interface BankUserFeignService {

    /**
     * 验证指定用户是存在和激活中
     *
     * @param userI
     * @return
     */
    @GetMapping("/bank/user/{id}")
    PojoResult<Boolean> checkIfUserApplicable(@PathVariable("id") Integer userI);

}