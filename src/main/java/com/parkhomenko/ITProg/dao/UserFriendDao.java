package com.parkhomenko.ITProg.dao;

import com.parkhomenko.ITProg.controller.LoginController;
import com.parkhomenko.ITProg.dao.mapper.UserFriendMapper;
import com.parkhomenko.ITProg.dao.mapper.UserMapper;
import com.parkhomenko.ITProg.entity.UserEntity;
import com.parkhomenko.ITProg.entity.UserFriendEntity;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserFriendDao {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    private final String GET_ALL_USERFRIENDS = "SELECT * FROM  UserFriend";
    private final String INSERT_INTO_USERFRIEND = "INSERT INTO  UserFriend (id_user, id_friend) VALUES (?, ?)";
    private final String DELETE_FROM_USERFRIEND = "DELETE FROM UserFriend WHERE id_friend = ? AND id_user = ?";
    private JdbcTemplate jdbcTemplate;

    public UserFriendDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //GET ALL
    public List<UserFriendEntity> getAllUserFriends(){
        return jdbcTemplate.query(
                GET_ALL_USERFRIENDS,
                new UserFriendMapper()
        );
    }

    //FIND
    private @NotNull List<UserEntity> privateFindUserFriends(@NotNull long userId){
        String find_userFriends = "SELECT itprogdb.user.email, itprogdb.user.id_user, itprogdb.user.password, itprogdb.user.nickname, itprogdb.user.role " +
                "FROM itprogdb.user  WHERE itprogdb.user.id_user IN (SELECT id_friend FROM userfriend WHERE id_user = " + userId + ")";
        return jdbcTemplate.query(
                find_userFriends,
                new UserMapper()
        );
    }

    public List<UserEntity> getUserFriends(long userId){
        return privateFindUserFriends(userId);
    }

    //FIND 1 USERFRIEND
    public List<UserFriendEntity> getUserFriend(long userId, long friendId){
        String find_userfriend = "SELECT * FROM  UserFriend WHERE id_friend = "+friendId +
                " AND id_user = "+userId;
        return jdbcTemplate.query(
                find_userfriend,
                new UserFriendMapper()
        );
    }

    //ADD
    private void privateAddUserFriend(long userId, long friendId){
        jdbcTemplate.update(
                INSERT_INTO_USERFRIEND,
                userId,
                friendId
        );
    }

    public void addUserFriend(long userId, long friendId){
        privateAddUserFriend(userId, friendId);
    }

    //DELETE
    private void privateDeleteUserFriend(long userId, long friendId){
        jdbcTemplate.update(
                DELETE_FROM_USERFRIEND,
                friendId,
                userId
        );
    }

    public void deleteUserFriend(long userId, long friendId){
        privateDeleteUserFriend(userId, friendId);
    }

}
