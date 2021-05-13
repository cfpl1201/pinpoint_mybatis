package com.dbq.mapper;

import com.dbq.common.base.BaseMapper;
import com.dbq.common.model.Order;
import org.apache.ibatis.annotations.Param;

/**
 * 银行用户Mapper接口
 *
 * @author dbq
 * @create 2017-12-06 9:23
 */
public interface OrderMapper extends BaseMapper<Integer, Order> {

    /**
     * @param userId
     * @return
     */
    Order selectById(@Param("userId") Integer userId);

}