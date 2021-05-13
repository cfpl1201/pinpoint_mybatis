package com.dbq.common.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据库表：account
 *
 * @author Administrator
 * @create 2018-08-01
 */
public class Account {

    /**
     * 账户主键
     */
    private Integer id;

    /**
     * 所属用户ID
     */
    private Integer userId;

    private Double total;

    private Double used;

    private Double balance;

    private Date lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getUsed() {
        return used;
    }

    public void setUsed(Double used) {
        this.used = used;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}