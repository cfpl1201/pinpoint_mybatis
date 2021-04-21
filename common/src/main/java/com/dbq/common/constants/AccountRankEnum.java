package com.dbq.common.constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 账号等级枚举
 *
 * @author dbq
 * @create 2018-10-23 9:51
 */
public enum AccountRankEnum {

    SILVER(1, "白银"),

    GOLD(2, "黄金"),

    PLATINUM(3, "白金"),

    DIAMOND(4, "钻石");

    private Integer code;
    private String label;

    private static final Map<Integer, AccountRankEnum> VALUES = new HashMap();

    static {
        Arrays.stream(AccountRankEnum.values()).forEach(e -> VALUES.put(e.code, e));
    }

    AccountRankEnum(Integer code, String label) {
        this.code = code;
        this.label = label;
    }

    public static AccountRankEnum valueOf(Integer code) {
        return VALUES.get(code);
    }

    public Integer getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }
}