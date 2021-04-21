package com.dbq.common.utils;

import com.dbq.common.constants.ErrorCodeEnum;
import com.dbq.common.result.PojoResult;

/**
 * @author dbq
 * @create 2018-10-23 16:58
 */
public abstract class ErrorResultGenerator {

    /**
     * 生成简单类型的结果
     *
     * @param error
     * @param t
     * @param <T>
     * @return
     */
    public static <T> PojoResult<T> generatePojoResult(ErrorCodeEnum error, T t) {
        PojoResult<T> result = new PojoResult<>(t);
        result.setErrorMessage(error.getCode(), error.getLabel());
        return result;
    }

}
