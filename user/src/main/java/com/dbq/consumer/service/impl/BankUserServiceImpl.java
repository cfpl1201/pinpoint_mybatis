package com.dbq.consumer.service.impl;

import com.dbq.common.export.account.BankAccountFeignService;
import com.dbq.common.model.BankAccount;
import com.dbq.common.result.PojoResult;
import com.dbq.consumer.util.HttpUtil;
import com.dbq.consumer.mapper.BankUserMapper;
import com.dbq.consumer.service.BankUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dbq
 * @create 2018-10-23 9:48
 */
@Service
public class BankUserServiceImpl implements BankUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankUserServiceImpl.class);

    @Autowired
    private BankAccountFeignService bankAccountFeignService;

    @Autowired
    private BankUserMapper bankUserMapper;

    @Override
    public BankAccount getBankAccount(Integer userId) {
        boolean exists = bankUserMapper.selectById(userId) != null;
        if (!exists) {
            LOGGER.debug("用户Id:[{}]不存在", userId);
            return null;
        }

        try {
            HttpUtil.doPost("https://www.huining.info/");
        } catch (Exception e) {
            System.out.println("-----------   end call ");
        }
        PojoResult<BankAccount> result = bankAccountFeignService.getBankAccountByUserId(userId);
        result.getContent().setBank(result.getContent().getBank()+System.currentTimeMillis());
        if (!result.isSuccess()) {
            LOGGER.debug("查询用户Id:[{}]失败,ErrorCode:[{}]", userId, result.getErrorCode());
            return null;
        }

        return result.getContent();
    }
}
