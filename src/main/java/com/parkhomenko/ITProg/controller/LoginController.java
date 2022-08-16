package com.parkhomenko.ITProg.controller;

import com.parkhomenko.ITProg.dto.EmailPassDto;
import com.parkhomenko.ITProg.entity.UserEntity;
import com.parkhomenko.ITProg.service.FacadeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Controller
public class LoginController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    public final static String LOGIN_PATH = "login";
    public final static String DEFAULT_PATH = "page_after_login";
    public final static String REDIRECT_PATH = "redirect:/";
    public final static String ADMIN_PATH = "admin_page";
    public final static String PASS_ERROR_DATA_TRANSFER = "passError";
    public final static String EMAIL_ERROR_DATA_TRANSFER = "emailError";
    public final static String USER_NON_REGISTER = "User is not registered";
    private final FacadeService facadeService;

    public LoginController(FacadeService facadeService) {
        this.facadeService = facadeService;
    }

    @GetMapping("/")
    public String general() {
        LOGGER.info("general method");
        return REDIRECT_PATH + LOGIN_PATH;
    }

    @GetMapping("/" + DEFAULT_PATH)
    public String index() {
        LOGGER.info("index method");
        return DEFAULT_PATH;
    }

    @GetMapping("/" + ADMIN_PATH)
    public String admIndex(Model model) {
        LOGGER.info("admIndex method");

        List<UserEntity> users = facadeService.getAllUsersAdm();

        model.addAttribute("users", users);

        return ADMIN_PATH;
    }

    @GetMapping("/administration/{userId}/signIn")
    public String admUserSignIn(Model model, @PathVariable long userId){
        return REDIRECT_PATH + DEFAULT_PATH + "/" + userId;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        LOGGER.info("Open log.html (GetMapping)");
        model.addAttribute("loginPass", new EmailPassDto());
        return LOGIN_PATH;
    }

    @PostMapping("/login")
    public String authorizeUser(Model model, @ModelAttribute("loginPass") EmailPassDto user) {
        LOGGER.info("Open log.html (PostMapping)");

        String passErrorStack = (user.getPass());
        String emailErrorStack = (user.getEmail());

        LOGGER.info("email: "+emailErrorStack);
        LOGGER.info("pass: "+passErrorStack);

        if (!StringUtils.isNotBlank(passErrorStack) || !StringUtils.isNotBlank(emailErrorStack)) {
            model.addAttribute(PASS_ERROR_DATA_TRANSFER, passErrorStack);
            model.addAttribute(EMAIL_ERROR_DATA_TRANSFER, emailErrorStack);
            LOGGER.info("ErrorStack");
            return LOGIN_PATH;
        }

        UserEntity userEnt = facadeService.findUserByEmailPass(user.getEmail(), user.getPass());

        if (userEnt == null) {
            model.addAttribute(PASS_ERROR_DATA_TRANSFER, USER_NON_REGISTER);
            model.addAttribute(EMAIL_ERROR_DATA_TRANSFER, StringUtils.EMPTY);
            LOGGER.info("userEnt == null");
            return LOGIN_PATH;
        }

        long userId = userEnt.getUserId();

        LOGGER.info("role = " + userEnt.getRole());
        if(userEnt.getRole().equals("admin")){
            LOGGER.info(REDIRECT_PATH + ADMIN_PATH);
            return REDIRECT_PATH + ADMIN_PATH;
        }

        LOGGER.info(REDIRECT_PATH + DEFAULT_PATH + "/" + userId);
        return REDIRECT_PATH + DEFAULT_PATH + "/" + userId;
    }

}
