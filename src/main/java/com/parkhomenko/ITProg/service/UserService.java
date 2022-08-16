package com.parkhomenko.ITProg.service;

import com.parkhomenko.ITProg.dao.UserDao;
import com.parkhomenko.ITProg.dto.UserRegistrationDto;
import com.parkhomenko.ITProg.entity.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserEntity findByEmailPass(String email, String pass) {
        for (UserEntity user : userDao.getAllUsers()) {
            if (user.getEmail()
                    .equals(email) && user.getPassword()
                    .equals(pass)) {
                return user;
            }
        }
        return null;
    }

    public void saveUser(@NotNull UserRegistrationDto form) {
        UserEntity user = new UserEntity();
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setNickName(form.getNickName());
        user.setRole("User");
        userDao.saveUser(user);
    }

}
