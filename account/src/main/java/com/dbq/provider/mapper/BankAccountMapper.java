package com.dbq.provider.mapper;


import com.dbq.common.base.BaseMapper;
import com.dbq.common.model.BankAccount;

/**
 * @author dbq
 */
public interface BankAccountMapper extends BaseMapper<Integer, BankAccount> {

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
    int insertWithoutNull(BankAccount record);

    /**
     * 根据指定主键获取一条数据库记录,bank_account
     *
     * @param id
     * @return
     */
    BankAccount selectById(Integer id);

    /**
     * 动态字段,根据主键来更新符合条件的数据库记录,bank_account
     *
     * @param record
     * @return
     */
    int updateByIdWithoutNull(BankAccount record);

    /**
     * 根据主键来更新符合条件的数据库记录,bank_account
     *
     * @param record
     * @return
     */
    int updateById(BankAccount record);

    /**
     * 查询用户的账号是否存在
     *
     * @param userId
     * @return
     */
    boolean checkIfAccountExists(Integer userId);
}
