package com.parkhomenko.ITProg.dao;

import com.parkhomenko.ITProg.dao.mapper.NUMberMapper;
import com.parkhomenko.ITProg.dao.mapper.TableRoleMapper;
import com.parkhomenko.ITProg.dao.mapper.UserMapper;
import com.parkhomenko.ITProg.dto.NUMberDto;
import com.parkhomenko.ITProg.entity.TableRoleEntity;
import com.parkhomenko.ITProg.entity.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TableRoleDao {

    private final String GET_ALL_TABLEROLE = "SELECT * FROM  TableRole";

    private final String INSERT_INTO_TABLEROLE = "INSERT INTO  TableRole (id_user, id_table, role) VALUES (?, ?, ?)";
    private final String DELETE_FROM_TABLEROLE = "DELETE FROM TableRole WHERE id_user = ? AND id_table = ?";
    private final String UPDATE_TABLEROLE = "UPDATE TableRole SET role = ? WHERE id_user = ?, id_table = ?";
    private JdbcTemplate jdbcTemplate;

    public TableRoleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //GET ALL
    public List<TableRoleEntity> getAllTableRole(){
        return jdbcTemplate.query(
                GET_ALL_TABLEROLE,
                new TableRoleMapper()
        );
    }

    //GET USER ROLE IN TABLE
    public List<TableRoleEntity> getUserRole(long userId, long tableId){
        String CHECK_USER_ROLE = "SELECT * FROM  TableRole WHERE TableRole.id_user = "+userId+
                " AND TableRole.id_table = "+tableId;
        return jdbcTemplate.query(
                CHECK_USER_ROLE,
                new TableRoleMapper()
        );
    }

    //ADD
    private void privateAddTableRole(TableRoleEntity tableRoleEntity){
        jdbcTemplate.update(
                INSERT_INTO_TABLEROLE,
                tableRoleEntity.getUserId(),
                tableRoleEntity.getTableId(),
                tableRoleEntity.getRole()
        );
    }

    public void addTableRole(TableRoleEntity tableRoleEntity){
        privateAddTableRole(tableRoleEntity);
    }

    //FIND USERS IN TABLE WITH ROLE MEMBER
    public List<UserEntity> findMembersInTable(long tableId){
        String find_members_in_table = "SELECT User.id_user, email, User.password, nickName, User.role FROM " +
                "User, TableRole WHERE TableRole.role = 'member' AND User.id_user = TableRole.id_user AND id_table = "+tableId;
        return jdbcTemplate.query(
                find_members_in_table,
                new UserMapper()
        );
    }

    //FIND FRIENDS WHICH NOT TABLE MEMBER
    public List<UserEntity> findFriendsWhichNotInTable(long userId, long tableId){
        String find_friend_which_not_in_table = "SELECT User.id_user, email, User.password, nickName, User.role FROM User WHERE id_user IN " +
                "(SELECT id_friend FROM  UserFriend WHERE id_user = " +userId+
                " AND id_friend NOT IN (SELECT id_user FROM  TableRole WHERE role = 'member' AND id_table = " +tableId+
                "))";
        return jdbcTemplate.query(
                find_friend_which_not_in_table,
                new UserMapper()
        );
    }

    //DELETE
    private void privateDeleteTableRole(long userId, long tableId){
        jdbcTemplate.update(
                DELETE_FROM_TABLEROLE,
                userId,
                tableId
        );
    }

    public void deleteTableRole(long userId, long tableId){
        privateDeleteTableRole(userId, tableId);
    }

    //UPDATE
    private void privateUpdateTableRole(TableRoleEntity tableRoleEntity){
        jdbcTemplate.update(
                UPDATE_TABLEROLE,
                tableRoleEntity.getRole(),
                tableRoleEntity.getUserId(),
                tableRoleEntity.getTableId()
        );
    }

    public void updateTableRole(TableRoleEntity tableRoleEntity){
        privateUpdateTableRole(tableRoleEntity);
    }

}
