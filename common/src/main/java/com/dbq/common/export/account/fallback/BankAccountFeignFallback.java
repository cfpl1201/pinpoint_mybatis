package com.dbq.common.export.account.fallback;

import com.dbq.common.export.account.BankAccountFeignService;
import com.dbq.common.model.BankAccount;
import com.dbq.common.result.PojoResult;
import com.dbq.common.utils.ErrorResultGenerator;
import org.springframework.stereotype.Service;

import static com.dbq.common.constants.ErrorCodeEnum.SYSTEM_ERROR;

/**
 * @author dbq
 * @create 2018-10-23 14:20
 */
@Service
public class BankAccountFeignFallback implements BankAccountFeignService {

    @Override
    public PojoResult<BankAccount> getBankAccountByUserId(Integer userId) {
        return ErrorResultGenerator.generatePojoResult(SYSTEM_ERROR, null);
    }
}
