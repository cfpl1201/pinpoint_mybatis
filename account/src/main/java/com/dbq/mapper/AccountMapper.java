package com.dbq.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dbq.common.model.Account;

/**
 * @author dbq
 */
public interface AccountMapper extends BaseMapper<Account> {

    /**
     * 根据指定主键获取一条数据库记录,bank_account
     *
     * @param id
     * @return
     */
    Account selectById(Integer id);
}
