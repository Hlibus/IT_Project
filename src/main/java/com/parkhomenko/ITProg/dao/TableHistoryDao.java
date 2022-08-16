package com.parkhomenko.ITProg.dao;

import com.parkhomenko.ITProg.dao.mapper.TableHistoryMapper;
import com.parkhomenko.ITProg.entity.TableHistoryEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TableHistoryDao {

    private final String GET_ALL_TABLEHISTORY = "SELECT * FROM  TableHistory";
    private String get_tableHistory = "SELECT * FROM  TableHistory WHERE id_table = ";
    private final String INSERT_INTO_TABLEHISTORY = "INSERT INTO  TableHistory (id_table, id_user, action, date) VALUES (?, ?, ?, ?)";
    private final String DELETE_FROM_TABLEHISTORY = "DELETE FROM TableHistory WHERE id_tablehistory = ?";
    private final String UPDATE_TABLEHISTORY = "UPDATE TableHistory SET id_user = ?, action = ?, date = ? WHERE id_tablehistory = ?";
    private JdbcTemplate jdbcTemplate;

    public TableHistoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //GET ALL
    public List<TableHistoryEntity> getAllTablesHistory(){
        return jdbcTemplate.query(
                GET_ALL_TABLEHISTORY,
                new TableHistoryMapper()
        );
    }

    //GET
    public List<TableHistoryEntity> getTableHistory(long tableId){
        get_tableHistory += tableId;
        return jdbcTemplate.query(
                get_tableHistory,
                new TableHistoryMapper()
        );
    }

    //ADD
    private void privateAddTableHistory(TableHistoryEntity tableHistoryEntity){
        jdbcTemplate.update(
                INSERT_INTO_TABLEHISTORY,
                tableHistoryEntity.getTableId(),
                tableHistoryEntity.getUserId(),
                tableHistoryEntity.getAction(),
                tableHistoryEntity.getDate()
        );
    }

    public void addTableHistory(TableHistoryEntity tableHistoryEntity){
        privateAddTableHistory(tableHistoryEntity);
    }

    //DELETE
    private void privateDeleteTableHistory(TableHistoryEntity tableHistoryEntity){
        jdbcTemplate.update(
                DELETE_FROM_TABLEHISTORY,
                tableHistoryEntity.getTableHistoryId()
        );
    }

    public void deleteTableHistory(TableHistoryEntity tableHistoryEntity){
        privateDeleteTableHistory(tableHistoryEntity);
    }

    //UPDATE
    private void privateUpdateTableHistory(TableHistoryEntity tableHistoryEntity){
        jdbcTemplate.update(
                UPDATE_TABLEHISTORY,
                tableHistoryEntity.getUserId(),
                tableHistoryEntity.getAction(),
                tableHistoryEntity.getDate(),
                tableHistoryEntity.getTableHistoryId()
        );
    }

    public void updateTableHistory(TableHistoryEntity tableHistoryEntity){
        privateUpdateTableHistory(tableHistoryEntity);
    }

}
