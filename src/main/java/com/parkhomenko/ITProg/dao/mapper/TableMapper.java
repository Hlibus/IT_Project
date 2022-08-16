package com.parkhomenko.ITProg.dao.mapper;

import com.parkhomenko.ITProg.entity.TableEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableMapper implements RowMapper<TableEntity> {

    @Override
    public TableEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableId(rs.getInt("id_table"));
        tableEntity.setName(rs.getString("name"));
        tableEntity.setDate(rs.getDate("date"));
        return tableEntity;

    }

}
