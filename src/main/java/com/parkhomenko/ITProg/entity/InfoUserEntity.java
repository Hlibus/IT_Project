package com.parkhomenko.ITProg.entity;

import java.sql.Blob;

public class InfoUserEntity {

    private long userId = -1;
    private String fullName;
    private String phone;
    private String priorityCommunication;

    public InfoUserEntity() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPriorityCommunication() {
        return priorityCommunication;
    }

    public void setPriorityCommunication(String priorityCommunication) {
        this.priorityCommunication = priorityCommunication;
    }

    @Override
    public String toString() {
        return "InfoUserEntity{" +
                "userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", priorityCommunication='" + priorityCommunication + '\'' +
                '}';
    }
}
