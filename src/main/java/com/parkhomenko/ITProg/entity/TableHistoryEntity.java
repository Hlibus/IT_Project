package com.parkhomenko.ITProg.entity;

import java.sql.Date;

public class TableHistoryEntity {

    private long tableHistoryId = -1;
    private long tableId = -1;
    private long userId = -1;
    private String action;
    private Date date;

    public long getTableHistoryId() {
        return tableHistoryId;
    }

    public void setTableHistoryId(long tableHistoryId) {
        this.tableHistoryId = tableHistoryId;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
