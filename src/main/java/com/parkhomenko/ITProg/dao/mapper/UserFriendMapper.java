package com.parkhomenko.ITProg.dao.mapper;

import com.parkhomenko.ITProg.entity.UserEntity;
import com.parkhomenko.ITProg.entity.UserFriendEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFriendMapper implements RowMapper<UserFriendEntity> {

    @Override
    public UserFriendEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        UserFriendEntity userFriend = new UserFriendEntity();
        userFriend.setUserId(rs.getInt("id_user"));
        userFriend.setUserId(rs.getInt("id_friend"));
        return userFriend;

    }

}
