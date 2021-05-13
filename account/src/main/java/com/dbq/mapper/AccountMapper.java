package com.dbq.mapper;


import com.dbq.common.base.BaseMapper;
import com.dbq.common.model.Account;

/**
 * @author dbq
 */
public interface AccountMapper extends BaseMapper<Integer, Account> {

    /**
     * 根据主键删除数据库的记录,bank_account
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 动态字段,写入数据库记录,bank_account
     *
     * @param record
     * @return
     */
    int insertWithoutNull(Account record);

    /**
     * 根据指定主键获取一条数据库记录,bank_account
     *
     * @param id
     * @return
     */
    Account selectById(Integer id);

    /**
     * 动态字段,根据主键来更新符合条件的数据库记录,bank_account
     *
     * @param record
     * @return
     */
    int updateByIdWithoutNull(Account record);

    /**
     * 根据主键来更新符合条件的数据库记录,bank_account
     *
     * @param record
     * @return
     */
    int updateById(Account record);

    /**
     * 查询用户的账号是否存在
     *
     * @param userId
     * @return
     */
    boolean checkIfAccountExists(Integer userId);
}
