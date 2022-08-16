package com.parkhomenko.ITProg.dao.mapper;

import com.parkhomenko.ITProg.entity.TableRoleEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableRoleMapper implements RowMapper<TableRoleEntity> {

    @Override
    public TableRoleEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        TableRoleEntity tableRoleEntity = new TableRoleEntity();
        tableRoleEntity.setUserId(rs.getLong("id_user"));
        tableRoleEntity.setTableId(rs.getLong("id_table"));
        tableRoleEntity.setRole(rs.getString("role"));
        return tableRoleEntity;

    }

}
