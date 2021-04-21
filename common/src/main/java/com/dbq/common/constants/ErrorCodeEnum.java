package com.dbq.common.constants;

/**
 * @author dbq
 * @create 2018-10-23 14:26
 */
public enum ErrorCodeEnum {

    SYSTEM_ERROR("0000", "系统异常"),

    REST_REQUEST_ERROR("0001", "REST请求异常");

    private String code;
    private String label;

    ErrorCodeEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}