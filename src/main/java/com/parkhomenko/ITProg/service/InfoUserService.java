package com.parkhomenko.ITProg.service;

import com.parkhomenko.ITProg.dao.InfoUserDao;
import com.parkhomenko.ITProg.dto.UserRegistrationDto;
import com.parkhomenko.ITProg.entity.InfoUserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

public class InfoUserService {

    private final InfoUserDao infoUserDao;

    @Autowired
    public InfoUserService(InfoUserDao infoUserDao) {
        this.infoUserDao = infoUserDao;
    }

    public void saveInfoUser(@NotNull UserRegistrationDto form) {
        InfoUserEntity infoUserEntity = new InfoUserEntity();
        infoUserEntity.setFullName(form.getFullName());
        infoUserEntity.setPhone(form.getPhone());
        infoUserDao.saveInfoUser(infoUserEntity);
    }

}
