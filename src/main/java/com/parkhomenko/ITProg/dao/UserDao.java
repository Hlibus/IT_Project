package com.parkhomenko.ITProg.dao;

import com.parkhomenko.ITProg.dao.mapper.NUMberMapper;
import com.parkhomenko.ITProg.dto.NUMberDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.parkhomenko.ITProg.dao.mapper.UserMapper;
import com.parkhomenko.ITProg.entity.UserEntity;

import java.util.List;

@Repository
public class UserDao {

    private final String GET_ALL_USERS = "SELECT * FROM  User";
    private final String GET_ALL_USERS_ADM = "SELECT * FROM  User WHERE user.role = 'user'";
    private final String GET_COUNT_ALL_USERS = "SELECT MAX(id_user) AS count FROM  User";


    private final String INSERT_INTO_USER = "INSERT INTO  User (id_user, email, password, nickName, role ) VALUES (?, ?, ?, ?, 'user')";
    private final String DELETE_FROM_USER = "DELETE FROM user WHERE userId = ?";
    private final String UPDATE_USER = "UPDATE User SET email = ?, password = ?, nickname = ?, role = ? WHERE id_user = ?";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserEntity> getAllUsers() {
        return jdbcTemplate.query(
                GET_ALL_USERS,
                new UserMapper());
    }

    public List<UserEntity> getAllUsersAdm() {
        return jdbcTemplate.query(
                GET_ALL_USERS_ADM,
                new UserMapper());
    }

    public List<UserEntity> findUserIdByEmail(String email) {
        String find_user_by_email = "SELECT * FROM  User WHERE email = '"+email+"'";
        return jdbcTemplate.query(
                find_user_by_email,
                new UserMapper());
    }

    public List<NUMberDto> getAllCountUsers(){
        return jdbcTemplate.query(
                GET_COUNT_ALL_USERS,
                new NUMberMapper()
        );
    }

    public List<NUMberDto> getCountUserEmail(String email){
        String get_count_user_email = "SELECT COUNT(*) AS count FROM  User WHERE email = '"+email+"'";
        return jdbcTemplate.query(
                get_count_user_email,
                new NUMberMapper()
        );
    }

    public List<UserEntity> findNotFriendUsersDao(long userId) {
        String find_not_friend_users = "SELECT * FROM User WHERE id_user NOT IN "+
                "(SELECT itprogdb.user.id_user " +
                "FROM itprogdb.user, userfriend WHERE itprogdb.user.id_user IN (SELECT id_friend FROM userfriend WHERE id_user = " + userId +
                ")) AND User.role = 'user' AND id_user != "+ userId;
        return jdbcTemplate.query(
                find_not_friend_users,
                new UserMapper());
    }

    public List<UserEntity> getUserById(long userId) {
        String find_user_by_id = "SELECT * FROM  User WHERE id_user = "+userId;
        return jdbcTemplate.query(
                find_user_by_id, new UserMapper());
    }

    private void privateAddUser(@NotNull UserEntity user) {
        if(user.getNickName()=="")
            user.setNickName("user"+user.getUserId());
        jdbcTemplate.update(
                INSERT_INTO_USER, user.getUserId(),
                user.getEmail(), user.getPassword(),
                user.getNickName()
        );
    }

    public void saveUser(UserEntity user) {
        privateAddUser(user);
    }

    private void privateDeleteUser(long userId){
        jdbcTemplate.update(
                DELETE_FROM_USER,
                userId
        );
    }

    public void deleteUser(long userId){
        privateDeleteUser(userId);
    }

    private void privateUpdateUser(@NotNull UserEntity user){
        jdbcTemplate.update(
                UPDATE_USER,
                user.getEmail(),
                user.getPassword(),
                user.getNickName(),
                user.getRole(),
                user.getUserId()
        );
    }

    public void updateUser(@NotNull UserEntity user){
        privateUpdateUser(user);
    }

}
