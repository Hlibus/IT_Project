package com.parkhomenko.ITProg.dao.mapper;

import com.parkhomenko.ITProg.entity.TableHistoryEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableHistoryMapper implements RowMapper<TableHistoryEntity> {

    @Override
    public TableHistoryEntity mapRow(@NotNull ResultSet rs, int rowNum) throws SQLException {

        TableHistoryEntity tableHistoryEntity = new TableHistoryEntity();
        tableHistoryEntity.setTableHistoryId(rs.getInt("id_tableHistory"));
        tableHistoryEntity.setTableId(rs.getInt("id_table"));
        tableHistoryEntity.setUserId(rs.getInt("id_user"));
        tableHistoryEntity.setAction(rs.getString("action"));
        tableHistoryEntity.setDate(rs.getDate("date"));
        return tableHistoryEntity;

    }

}
