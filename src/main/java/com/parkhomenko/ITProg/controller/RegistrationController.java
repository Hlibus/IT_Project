package com.parkhomenko.ITProg.controller;

import com.parkhomenko.ITProg.dao.UserDao;
import com.parkhomenko.ITProg.dto.UserRegistrationDto;
import com.parkhomenko.ITProg.entity.InfoUserEntity;
import com.parkhomenko.ITProg.entity.UserEntity;
import com.parkhomenko.ITProg.service.FacadeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private static final Logger LOGGER = Logger.getLogger(RegistrationController.class);
    private static final String REGISTER = "registration";
    private static final String REDIRECT_PATH = "redirect:/";
    public final static String DEFAULT_PATH = "page_after_login";
    private final FacadeService facadeService;

    @Autowired
    public RegistrationController(FacadeService facadeService) {
        this.facadeService = facadeService;
    }

    @GetMapping("/registration")
    public String getRegisterPage(Model model) {
        LOGGER.info("Open registration.html (GetMapping)");
        UserRegistrationDto form = new UserRegistrationDto();
        model.addAttribute("form", form);
        return REGISTER;
    }

    @PostMapping("/registration")
    public String createNewUser(Model model, @ModelAttribute("userReg") UserRegistrationDto userRegistrationDto) {
        LOGGER.info("USER REGISTRATION (PostMapping)");


        //проверка полей
        String emailErrorStack = (userRegistrationDto.getEmail());
        String passErrorStack = (userRegistrationDto.getPassword());
        String confirmPasswordErrorStack = (userRegistrationDto.getConfirmPassword());
        String nickNameErrorStack = (userRegistrationDto.getNickName());
        String fullNameErrorStack = (userRegistrationDto.getFullName());
        String phoneErrorStack = (userRegistrationDto.getPhone());

        LOGGER.info(passErrorStack);
        LOGGER.info(confirmPasswordErrorStack);

        if (!StringUtils.isNotBlank(passErrorStack) || !StringUtils.isNotBlank(emailErrorStack)
                || !StringUtils.isNotBlank(confirmPasswordErrorStack)) {
            LOGGER.info("FieldsErrorStack");
            return REGISTER;
        }

        if(!passErrorStack.equals(confirmPasswordErrorStack)){
            LOGGER.info("Passwords do not match");
            return REGISTER;
        }

        if(facadeService.getCountUserEmail(emailErrorStack)!=0){
            LOGGER.info("this email already register");
            return REGISTER;
        }


        long newUserId = facadeService.getNewUserId();
        UserEntity user = new UserEntity();
        user.setUserId(newUserId);
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(userRegistrationDto.getPassword());
        user.setNickName(userRegistrationDto.getNickName());

        InfoUserEntity infoUser = new InfoUserEntity();
        infoUser.setUserId(newUserId);
        infoUser.setFullName(userRegistrationDto.getFullName());
        infoUser.setPhone(userRegistrationDto.getPhone());

        facadeService.createNewuser(user);
        facadeService.createInfoUser(infoUser);

        return REDIRECT_PATH + DEFAULT_PATH + "/" + newUserId;
    }

}
