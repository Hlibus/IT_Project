package com.parkhomenko.ITProg.dao;

import com.parkhomenko.ITProg.dao.mapper.InfoUserMapper;
import com.parkhomenko.ITProg.entity.InfoUserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Blob;
import java.util.List;

@Repository
public class InfoUserDao {

    private final String GET_ALL_FROM_INFOUSER = "SELECT * FROM InfoUser";
    private String find_infouser = "SELECT * FROM InfoUser WHERE id_user = ";
    private final String INSERT_INTO_INFOUSER = "INSERT INTO  InfoUser (id_user, full_name, phone, priority_communication ) VALUES (?, ?, ?, ?)";
    private final String DELETE_FROM_INFOUSER = "DELETE FROM InfoUser WHERE id_user = ?";
    private final String UPDATE_INFOUSER = "UPDATE InfoUser SET full_name = ?, phone = ?, priority_communication = ?";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public InfoUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<InfoUserEntity> getAllInfoUser(){
        return jdbcTemplate.query(
                GET_ALL_FROM_INFOUSER,
                new InfoUserMapper()
        );
    }

    //FIND
    private List<InfoUserEntity> privateFindInfoUser(long userId){
        find_infouser += userId;
        return jdbcTemplate.query(
                find_infouser,
                new InfoUserMapper()
        );
    }

    public void findInfoUser(long userId){
        privateFindInfoUser(userId);
    }

    //ADD
    private void privateAddInfoUser(@NotNull InfoUserEntity infoUser) {
        jdbcTemplate.update(
                INSERT_INTO_INFOUSER, infoUser.getUserId(),
                infoUser.getFullName(), infoUser.getPhone(),
                infoUser.getPriorityCommunication()
        );
    }
    public void saveInfoUser(InfoUserEntity infoUser) {
        privateAddInfoUser(infoUser);
    }


    //DELETE
    private void privateDeleteInfoUser(long userId){
        jdbcTemplate.update(
                DELETE_FROM_INFOUSER,
                userId
        );
    }
    public void deleteInfoUser(long userId){
        privateDeleteInfoUser(userId);
    }


    //UPDATE
    private void privateUpdateInfoUser(@NotNull InfoUserEntity infoUser){
        jdbcTemplate.update(
                UPDATE_INFOUSER,
                infoUser.getFullName(),
                infoUser.getPhone(),
                infoUser.getPriorityCommunication()
        );
    }
    public void updateInfoUser(@NotNull InfoUserEntity infoUser){
        privateUpdateInfoUser(infoUser);
    }

}
