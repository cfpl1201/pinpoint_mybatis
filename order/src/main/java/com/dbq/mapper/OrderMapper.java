package com.dbq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dbq.common.model.Order;
import org.apache.ibatis.annotations.Param;

/**
 * 银行用户Mapper接口
 *
 * @author dbq
 * @create 2017-12-06 9:23
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * @param userId
     * @return
     */
    Order selectById(@Param("userId") Integer userId);

}