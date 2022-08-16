package com.parkhomenko.ITProg.dao.mapper;

import com.parkhomenko.ITProg.entity.InfoUserEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoUserMapper implements RowMapper<InfoUserEntity> {

    @Override
    public InfoUserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        InfoUserEntity infoUser = new InfoUserEntity();
        infoUser.setUserId(rs.getInt("id_user"));
        infoUser.setFullName(rs.getString("full_name"));
        infoUser.setPhone(rs.getString("phone"));
        infoUser.setPriorityCommunication(rs.getString("priority_communication"));
        return infoUser;
    }

}
