package com.parkhomenko.ITProg.entity;

public class UserFriendEntity {

    private long userId = -1;
    private long friendId = -1;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getFriendId() {
        return friendId;
    }

    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }

}
