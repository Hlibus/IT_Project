package com.parkhomenko.ITProg.dao;

import com.parkhomenko.ITProg.dao.mapper.NUMberMapper;
import com.parkhomenko.ITProg.dao.mapper.TableMapper;
import com.parkhomenko.ITProg.dto.NUMberDto;
import com.parkhomenko.ITProg.entity.TableEntity;
import com.parkhomenko.ITProg.entity.TableRoleEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class TableDao {

    private final String GET_ALL_TABLES = "SELECT * FROM  itprogdb.table";
    private final String GET_COUNT_ALL_TABLES = "SELECT MAX(id_table) AS count FROM  itprogdb.table";
    private final String INSERT_INTO_TABLEROLE = "INSERT INTO  TableRole (id_user, id_table, role) VALUES (?, ?, ?)";
    private final String INSERT_INTO_TABLE = "INSERT INTO  itprogdb.table (id_table, name, date) VALUES (?, ?, ?)";
    private final String DELETE_FROM_TABLE= "DELETE FROM itprogdb.table WHERE id_table = ?";
    private final String UPDATE_TABLE = "UPDATE itprogdb.table SET name = ? WHERE id_table = ?";
    private JdbcTemplate jdbcTemplate;

    public TableDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //GET ALL
    public List<TableEntity> getAllTables(){
        return jdbcTemplate.query(
                GET_ALL_TABLES,
                new TableMapper()
        );
    }

    //  GET TABLE BY ID
    public List<TableEntity> getTableById(long tableId){
        String GET_TABLE_BY_ID = "SELECT * FROM  itprogdb.table WHERE id_table = "+tableId;
        return jdbcTemplate.query(
                GET_TABLE_BY_ID,
                new TableMapper()
        );
    }

    //GET ALL USER TABLES WITH SIMILAR NAME
    public List<TableEntity> getAllUserTableName(String name, long userId){
        String get_all_user_tables_with_similar_name = "SELECT * FROM  itprogdb.table WHERE itprogdb.table.name = " +
                "'"+name+"' AND id_table IN (SELECT id_table FROM tableRole WHERE id_user = "+userId+")";
        return jdbcTemplate.query(
                get_all_user_tables_with_similar_name,
                new TableMapper()
        );
    }

    //FIND TABLES BY USER ID
    public List<TableEntity> getUserTables(long userId){
        String find_tables_by_user = "SELECT itprogdb.table.id_table, itprogdb.table.name, itprogdb.table.date FROM itprogdb.table JOIN tablerole ON itprogdb.table.id_table = tablerole.id_table WHERE id_user = "+ userId;
        return jdbcTemplate.query(
                find_tables_by_user,
                new TableMapper()
        );
    }

    public List<NUMberDto> getAllCountTables(){
        return jdbcTemplate.query(
                GET_COUNT_ALL_TABLES,
                new NUMberMapper()
        );
    }

    //ADD
    private void privateAddTable(String tableName, long userId, String role){
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = formatForDateNow.format(dateNow);

        List<NUMberDto> nuMberDtos = getAllCountTables();
        int count = nuMberDtos.get(0).getCount()+1;
        jdbcTemplate.update(
                INSERT_INTO_TABLE,
                count,
                tableName,
                date
        );
        TableRoleEntity tableRoleEntity = new TableRoleEntity();
        tableRoleEntity.setUserId(userId);
        tableRoleEntity.setTableId(count);
        tableRoleEntity.setRole(role);
        privateAddTableRole(tableRoleEntity);
    }

    public void addTable(String tableName, long userId, String role){
        privateAddTable(tableName, userId, role);
    }

    private void privateAddTableRole(TableRoleEntity tableRoleEntity){
        jdbcTemplate.update(
                INSERT_INTO_TABLEROLE,
                tableRoleEntity.getUserId(),
                tableRoleEntity.getTableId(),
                tableRoleEntity.getRole()
        );
    }

    //DELETE
    private void privateDeleteTable(long tableId){
        jdbcTemplate.update(
                DELETE_FROM_TABLE,
                tableId
        );
    }

    public void deleteTable(long tableId){
        privateDeleteTable(tableId);
    }

    //UPDATE
    private void privateUpdateTable(TableEntity tableEntity){
        jdbcTemplate.update(
                UPDATE_TABLE,
                tableEntity.getName(),
                tableEntity.getTableId()
        );
    }

    public void updateTable(TableEntity tableEntity){
        privateUpdateTable(tableEntity);
    }

}
