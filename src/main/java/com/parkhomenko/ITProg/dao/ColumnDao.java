package com.parkhomenko.ITProg.dao;

import com.parkhomenko.ITProg.dao.mapper.ColumnMapper;
import com.parkhomenko.ITProg.dao.mapper.NUMberMapper;
import com.parkhomenko.ITProg.dto.NUMberDto;
import com.parkhomenko.ITProg.entity.ColumnEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ColumnDao {

    private final String GET_ALL_COLUMNS = "SELECT * FROM itprogdb.Column";


    private String get_count_of_columns = "SELECT MAX(id_column) AS count FROM itprogdb.column";
    private final String INSERT_INTO_COLUMN = "INSERT INTO  itprogdb.Column (id_column, id_table, name) VALUES (?, ?, ?)";
    private final String DELETE_FROM_COLUMN = "DELETE FROM itprogdb.Column WHERE id_column = ?";
    private final String UPDATE_COLUMN = "UPDATE itprogdb.Column SET name = ? WHERE id_column = ?";
    private JdbcTemplate jdbcTemplate;

    public ColumnDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //GET ALL
    public List<ColumnEntity> getAllColumns(){
        return jdbcTemplate.query(
                GET_ALL_COLUMNS,
                new ColumnMapper()
        );
    }

    //GET COLUMN BY ID
    public List<ColumnEntity> getColumnById(long columnId){
        String GET_COLUMN_BY_ID = "SELECT * FROM itprogdb.Column WHERE id_column = "+columnId;
        return jdbcTemplate.query(
                GET_COLUMN_BY_ID,
                new ColumnMapper()
        );
    }

    //GET COLUMN ID BY NAME
    public List<NUMberDto> getColumnIdByName(String name, long tableId){
        String get_it_by_name = "SELECT id_column AS 'count' FROM itprogdb.Column WHERE itprogdb.Column.name = '"+name+"'" +
                "AND id_table = "+ tableId;
        return jdbcTemplate.query(
                get_it_by_name,
                new NUMberMapper()
        );
    }

    //GET ALL NAMES FROM TABLE COLUMN
    public List<ColumnEntity> getAllColumnsByName(String name, long tableId){
        String GET_ALL_COLUMNS_NAME = "SELECT * FROM itprogdb.Column WHERE itprogdb.Column.name = '" + name+
                "' AND id_table = "+tableId;
        return jdbcTemplate.query(
                GET_ALL_COLUMNS_NAME,
                new ColumnMapper()
        );
    }

    //GET COUNT
    public List<NUMberDto> getCountColumns(){
        return jdbcTemplate.query(
                get_count_of_columns,
                new NUMberMapper()
        );
    }

    //FIND ALL BY TABLE
    public List<ColumnEntity> getAllColumnsByTable(long tableId){
        String find_columns = "SELECT * FROM itprogdb.Column WHERE id_table = "+tableId;
        return jdbcTemplate.query(
                find_columns,
                new ColumnMapper()
        );
    }

    //FIND ALL EXCEPT 1 BY TABLE
    public List<ColumnEntity> getAllColumnsExceptOneByTable(long tableId, long columnId){
        String find_columns_except_one = "SELECT * FROM itprogdb.Column WHERE id_table = " +
                ""+tableId+" AND id_column != "+columnId;
        return jdbcTemplate.query(
                find_columns_except_one,
                new ColumnMapper()
        );
    }

    //ADD
    private void privateAddColumn(ColumnEntity columnEntity){
        jdbcTemplate.update(
                INSERT_INTO_COLUMN,
                columnEntity.getColumnId(),
                columnEntity.getTableId(),
                columnEntity.getName()
        );
    }

    public void addColumn(ColumnEntity columnEntity){
        privateAddColumn(columnEntity);
    }

    //DELETE
    private void privateDeleteColumn(long columnId){
        jdbcTemplate.update(
                DELETE_FROM_COLUMN,
                columnId
        );
    }

    public void deleteColumn(long columnId){
        privateDeleteColumn(columnId);
    }

    //UPDATE
    private void privateUpdateColumn(ColumnEntity columnEntity){
        jdbcTemplate.update(
                UPDATE_COLUMN,
                columnEntity.getName(),
                columnEntity.getColumnId()
        );
    }

    public void updateColumn(ColumnEntity columnEntity){
        privateUpdateColumn(columnEntity);
    }

}
