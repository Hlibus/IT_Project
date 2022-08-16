package com.parkhomenko.ITProg.dao.mapper;

import com.parkhomenko.ITProg.entity.ColumnEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColumnMapper implements RowMapper<ColumnEntity> {

    @Override
    public ColumnEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        ColumnEntity columnEntity = new ColumnEntity();
        columnEntity.setColumnId(rs.getInt("id_column"));
        columnEntity.setTableId(rs.getInt("id_table"));
        columnEntity.setName(rs.getString("name"));
        return columnEntity;

    }

}
