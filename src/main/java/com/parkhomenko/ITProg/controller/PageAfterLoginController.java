package com.parkhomenko.ITProg.controller;

import com.parkhomenko.ITProg.entity.ColumnEntity;
import com.parkhomenko.ITProg.entity.TableEntity;
import com.parkhomenko.ITProg.entity.UserEntity;
import com.parkhomenko.ITProg.service.FacadeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PageAfterLoginController {

    private static final Logger LOGGER = Logger.getLogger(PageAfterLoginController.class);

    public final static String DEFAULT_PATH = "page_after_login";
    public final static String REDIRECT_DEFAULT_PATH = "redirect:/page_after_login";
    public final static String TABLE_PAGE = "table_page";
    public final static String REDIRECT_PATH = "redirect:/";
    public final static String LOGIN_PATH = "login";
    public final static String ADD_NEW_FRIEND = "addNewFriend";
    public final static String DELETE_FRIEND = "deleteFriend";

    public final static String DELETE_TABLE = "deleteTable";
    public final static String EDIT_TABLE_PAGE = "edit_table";
    private final FacadeService facadeService;

    public PageAfterLoginController(FacadeService facadeService) {
        this.facadeService = facadeService;
    }

    @GetMapping("/"+DEFAULT_PATH + "/{userId}")
    public String getPageAfterLogin(@NotNull Model model, @PathVariable("userId") long userId) {
        LOGGER.info("getPageAfterLogin method");
        model.addAttribute("tabless", facadeService.findUserTables(userId));
        model.addAttribute("friends", facadeService.findUserFriends(userId));
        model.addAttribute("users", facadeService.findNotFriendUsers(userId));
        model.addAttribute("userId", userId);
        return DEFAULT_PATH;
    }

    @PostMapping("/"+DEFAULT_PATH+"/{userId}")
    public String createNewTable(@NotNull Model model, @ModelAttribute("newTable") TableEntity tableEntity, @PathVariable long userId) {
        LOGGER.info("createNewTable method");

        LOGGER.info("String = "+ tableEntity.getName());
        if (tableEntity.getName() == "" || tableEntity.getName() == " ") {
            LOGGER.info("ErrorStack(TableName)");

            return getPageAfterLogin(model, userId);
        }

        if(!facadeService.getAllUserTableName(tableEntity.getName(), userId).isEmpty()){
            LOGGER.info("This name is used");
            return getPageAfterLogin(model, userId);
        }

        facadeService.createNewTable(tableEntity.getName(), userId, "creator");

        model.addAttribute("userId", userId);

        getPageAfterLogin(model,userId);
        return getPageAfterLogin(model, userId);
    }

    @GetMapping("/"+DEFAULT_PATH+"/{userId}/{newFriendId}/"+ADD_NEW_FRIEND)
    public String addNewFriend(@NotNull Model model, @PathVariable("userId") long userId, @PathVariable("newFriendId") long newFriendId) {
        LOGGER.info("addNewFriend method");
        if(facadeService.getUserFriend(userId, newFriendId).isEmpty())
            facadeService.addNewFriend(userId, newFriendId);

        return getPageAfterLogin(model, userId);
    }

    @GetMapping("/"+DEFAULT_PATH+"/{userId}/{friendId}/"+DELETE_FRIEND)
    public String deleteFriend(@NotNull Model model, @PathVariable("userId") long userId, @PathVariable("friendId") long friendId) {
        LOGGER.info("deleteFriend method");
        facadeService.deleteUserFriend(userId, friendId);

        return getPageAfterLogin(model, userId);
    }

    //DELETE TABLE
    @GetMapping("/"+EDIT_TABLE_PAGE + "/{userId}" + "/{tableId}/"+DELETE_TABLE)
    public String deleteTable(Model model, @PathVariable("userId") long userId, @PathVariable("tableId") long tableId){
        LOGGER.info("deleteTable method");

        facadeService.deleteTable(tableId);

        return getPageAfterLogin(model, userId);
    }


}
