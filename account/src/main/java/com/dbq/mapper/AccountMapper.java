package com.dbq.mapper;


import com.dbq.common.base.BaseMapper;
import com.dbq.common.model.Account;

/**
 * @author dbq
 */
public interface AccountMapper extends BaseMapper<Integer, Account> {

    /**
     * 根据指定主键获取一条数据库记录,bank_account
     *
     * @param id
     * @return
     */
    Account selectById(Integer id);
}
