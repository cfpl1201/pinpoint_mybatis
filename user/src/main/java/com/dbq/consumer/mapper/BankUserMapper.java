package com.dbq.consumer.mapper;

import com.dbq.common.base.BaseMapper;
import com.dbq.common.model.BankUser;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 银行用户Mapper接口
 *
 * @author dbq
 * @create 2017-12-06 9:23
 */
public interface BankUserMapper extends BaseMapper<Integer, BankUser> {

    /**
     * @param userId
     * @return
     */
    BankUser selectById(@Param("userId") Integer userId);

}