package com.parkhomenko.ITProg.dao.mapper;

import com.parkhomenko.ITProg.entity.UserEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserMapper implements RowMapper<UserEntity> {

    @Override
    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        UserEntity user = new UserEntity();
        user.setUserId(rs.getInt("id_user"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setNickName(rs.getString("nickName"));
        user.setRole(rs.getString("role"));
        return user;
    }

}
