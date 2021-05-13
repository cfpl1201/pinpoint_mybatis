package com.dbq.common.export.order.fallback;import com.dbq.common.export.order.OrderFeignService;import com.dbq.common.result.PojoResult;import com.dbq.common.utils.ErrorResultGenerator;import org.springframework.stereotype.Service;import static com.dbq.common.constants.ErrorCodeEnum.SYSTEM_ERROR;/** * @author dbq * @create 2018-10-23 14:21 */@Servicepublic class OrderFeignFallback implements OrderFeignService {    @Override    public PojoResult<Boolean> checkIfUserApplicable(Integer userId) {        return ErrorResultGenerator.generatePojoResult(SYSTEM_ERROR, false);    }}