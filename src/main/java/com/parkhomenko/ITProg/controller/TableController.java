package com.parkhomenko.ITProg.controller;

import com.parkhomenko.ITProg.dto.TicketFormDto;
import com.parkhomenko.ITProg.entity.*;
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
public class TableController {

    private static final Logger LOGGER = Logger.getLogger(TableController.class);

    public final static String TABLE_PAGE = "table_page";
    public final static String TABLE_PAGE_FOR_MEMBER = "table_page_for_member";
    public final static String ADD_COLUMN = "addColumn";
    public final static String ADD_TICKET = "addTicket";
    public final static String EDIT_TABLE = "editTable";
    public final static String EDIT_COLUMN = "editColumn";
    public final static String EDIT_TICKET = "editTicket";
    public final static String DELETE_COLUMN = "deleteColumn";
    public final static String DELETE_TICKET = "deleteTicket";
    public final static String EDIT_TABLE_PAGE = "edit_table";
    public final static String EDIT_COLUMN_PAGE = "edit_column";
    public final static String EDIT_TICKET_PAGE = "edit_ticket";
    public final static String ADD_FRIEND_TO_TABLE = "addFriendToTable";
    public final static String ADD_FRIEND_TO_TICKET = "addFriendToTicket";
    public final static String DELETE_FRIEND_FROM_TABLE = "deleteFriendFromTable";
    public final static String DELETE_FRIEND_FROM_TICKET = "deleteFriendFromTicket";
    private final FacadeService facadeService;

    public TableController(FacadeService facadeService) {
        this.facadeService = facadeService;
    }

    //GET TABLE PAGE
    @GetMapping("/"+TABLE_PAGE + "/{userId}" + "/{tableId}")
    public String getTablePage(@NotNull Model model, @PathVariable("userId") long userId, @PathVariable("tableId")
            long tableId) {
        LOGGER.info("getTablePage method");

        List<TicketEntity> ticketList = facadeService.findAllColumnTickets(tableId);
        int progress = 0;
        int count = 0;

        for(TicketEntity ticket:ticketList){
             if(ticket.getStatus().equals("новий") || ticket.getStatus().equals("на оцінюванні")){
                 progress+=0;
             }else if(ticket.getStatus().equals("почато")){
                 progress+=10;
             }else if(ticket.getStatus().equals("не тестуванні")){
                 progress+=70;
             }else if(ticket.getStatus().equals("виконано")){
                 progress+=100;
             }
             count++;
        }
        if (count!= 0)
            progress /= count;

        model.addAttribute("columns", facadeService.findAllTableColumns(tableId));
        model.addAttribute("tickets", ticketList);
        model.addAttribute("userId", userId);
        model.addAttribute("progress", progress);

        if(facadeService.getUserRoleInTable(userId, tableId).get(0).getRole().equals("member")){
            return TABLE_PAGE_FOR_MEMBER;
        }

        return TABLE_PAGE;
    }

    //ADD NEW COLUMN
    @PostMapping("/"+TABLE_PAGE + "/{userId}" + "/{tableId}/"+ADD_COLUMN)
    public String addNewColumn(Model model, @ModelAttribute("newColumn")ColumnEntity columnEntity,
                               @PathVariable("userId") long userId, @PathVariable("tableId") long tableId){
        LOGGER.info("addNewColumn method");

        String nameErrorStack = columnEntity.getName();

        if (!StringUtils.isNotBlank(nameErrorStack)) {
            LOGGER.info("nameErrorStack");
            return getTablePage(model, userId, tableId);
        }
        if(!facadeService.getAllColumnNamesInTable(nameErrorStack, tableId).isEmpty()){
            LOGGER.info("This name is used");
            return getTablePage(model, userId, tableId);
        }

        columnEntity.setColumnId(facadeService.getNewColumnId());
        columnEntity.setTableId(tableId);

        facadeService.createColumn(columnEntity);

        return getTablePage(model, userId, tableId);
    }

    //ADD NEW TICKET
    @PostMapping("/"+TABLE_PAGE + "/{userId}" + "/{tableId}/"+ADD_TICKET)
    public String addNewTicket(Model model, @PathVariable("userId") long userId,
                               @PathVariable("tableId") long tableId,
                               @ModelAttribute("newTicket")TicketFormDto ticketForm){
        LOGGER.info("addNewTicket method");

        String nameErrorStack = ticketForm.getName();
        LOGGER.info(nameErrorStack);
        LOGGER.info(ticketForm.getColumnName());

        if(!StringUtils.isNotBlank(nameErrorStack)){
            LOGGER.info("nameErrorStack");
            return getTablePage(model, userId, tableId);
        }

        long columnId = facadeService.getColumnIdByName(ticketForm.getColumnName(), tableId);

        if(!facadeService.getAllTicketsInColumn(nameErrorStack, columnId).isEmpty() || columnId==-1){
            LOGGER.info("This name is used in this column");
            LOGGER.info(columnId);
            return getTablePage(model, userId, tableId);
        }

        String descr = "empty";
        if(StringUtils.isNotBlank(ticketForm.getDescription())){
            descr = ticketForm.getDescription();
        }

        long newTicketId = facadeService.getNewTicketId();

        TicketEntity newTicket = new TicketEntity();
        newTicket.setTicketId(newTicketId);
        newTicket.setColumnId(columnId);
        newTicket.setName(ticketForm.getName());
        newTicket.setDescription(descr);
        newTicket.setStatus(ticketForm.getStatus());

        facadeService.createTicket(newTicket);

        return getTablePage(model, userId, tableId);
    }

    //GET EDIT TABLE PAGE
    @GetMapping("/"+EDIT_TABLE_PAGE + "/{userId}" + "/{tableId}")
    public String getEditTablePage(@NotNull Model model, @PathVariable("userId") long userId, @PathVariable("tableId") long tableId) {
        LOGGER.info("getEditColumnPage method");

        TableEntity table = facadeService.getTableById(tableId);

        if(table == null)
            return getTablePage(model, userId, tableId);

        List<UserEntity> friendsForDelete = facadeService.findMembersInTable(tableId);
        List<UserEntity> friendsForAdd = facadeService.findFriendsWhichNotInTable(userId, tableId);

        model.addAttribute("userId", userId);
        model.addAttribute("tableId", tableId);
        model.addAttribute("table", table);
        model.addAttribute("friendsForAdd", friendsForAdd);
        model.addAttribute("friendsForDelete", friendsForDelete);
        return EDIT_TABLE_PAGE;
    }

    //EDIT TABLE
    @PostMapping("/"+EDIT_TABLE_PAGE + "/{userId}" + "/{tableId}/"+EDIT_TABLE)
    public String editTable(Model model, @PathVariable("userId") long userId, @PathVariable("tableId") long tableId,
                            @ModelAttribute("tableEdit")TableEntity editedTable){
        LOGGER.info("editTable method");

        TableEntity oldTable = facadeService.getTableById(tableId);

        String newName = editedTable.getName();

        if(!StringUtils.isNotBlank(newName)){
            LOGGER.info("nameErrorStack");
            return getEditTablePage(model, userId, tableId);
        }
        if(newName == oldTable.getName()){
            LOGGER.info("name has not been changed");
            return getEditTablePage(model, userId, tableId);
        }
        if(!facadeService.getAllColumnNamesInTable(newName, tableId).isEmpty()){
            LOGGER.info("This name is used");
            return getEditTablePage(model, userId, tableId);
        }

        TableEntity newTable = new TableEntity();
        newTable.setDate(oldTable.getDate());
        newTable.setName(newName);
        newTable.setTableId(oldTable.getTableId());

        facadeService.updateTable(newTable);

        return getTablePage(model, userId, tableId);
    }

    //ADD USER TO TABLE
    @GetMapping("/"+EDIT_TABLE_PAGE + "/{userId}/{tableId}/{addFriendEmail}/"+ADD_FRIEND_TO_TABLE)
    public String addUserToTable(Model model,@PathVariable("userId") long userId, @PathVariable("tableId") long tableId,
                                 @PathVariable("addFriendEmail")String addFriendEmail){
        LOGGER.info("addUserToTable method");

        long addFriendId = facadeService.findUserIdByEmail(addFriendEmail);
        LOGGER.info("addFriendEmail = "+addFriendEmail);

        if(addFriendId == -1)
            return getEditTablePage(model, userId, tableId);

        TableRoleEntity tableRoleEntity = new TableRoleEntity();
        tableRoleEntity.setUserId(addFriendId);
        tableRoleEntity.setTableId(tableId);
        tableRoleEntity.setRole("member");

        facadeService.addUserToTable(tableRoleEntity);

        return getEditTablePage(model, userId, tableId);
    }

    //DELETE USER FROM TABLE
    @GetMapping("/"+EDIT_TABLE_PAGE + "/{userId}/{tableId}/{deleteFriendEmail}/"+DELETE_FRIEND_FROM_TABLE)
    public String deleteUserFromTable(Model model,@PathVariable("userId") long userId, @PathVariable("tableId") long tableId,
                                 @ModelAttribute("deleteFriendEmail")String deleteFriendEmail){
        LOGGER.info("deleteUserFromTable method");

        long deleteFriendId = facadeService.findUserIdByEmail(deleteFriendEmail);

        if(deleteFriendId == -1)
            return getEditTablePage(model, userId, tableId);

        facadeService.deleteUserFromTable(deleteFriendId, tableId);

        return getEditTablePage(model, userId, tableId);
    }

    //GET EDIT COLUMN PAGE
    @GetMapping("/"+EDIT_COLUMN_PAGE + "/{userId}" + "/{tableId}"+ "/{columnId}")
    public String getEditColumnPage(@NotNull Model model, @PathVariable("userId") long userId, @PathVariable("tableId") long tableId,
                                    @PathVariable("columnId") long columnId) {
        LOGGER.info("getEditColumnPage method");

        ColumnEntity column = facadeService.getColumnById(columnId);

        if(column == null)
            return getTablePage(model, userId, tableId);


        model.addAttribute("userId", userId);
        model.addAttribute("tableId", tableId);
        model.addAttribute("column", column);
        return EDIT_COLUMN_PAGE;
    }

    //EDIT COLUMN
    @PostMapping("/"+EDIT_COLUMN_PAGE + "/{userId}" + "/{tableId}"+"/{columnId}/"+EDIT_COLUMN)
    public String editColumn(Model model, @PathVariable("userId") long userId, @PathVariable("tableId") long tableId,
                             @PathVariable("columnId") long columnId, @ModelAttribute("editedColumn")ColumnEntity editedColumn){
        LOGGER.info("editColumn method");

        ColumnEntity oldColumn = facadeService.getColumnById(columnId);

        String newName = editedColumn.getName();

        if(!StringUtils.isNotBlank(newName)){
            LOGGER.info("nameErrorStack");
            return getEditColumnPage(model, userId, tableId, columnId);
        }
        if(newName == oldColumn.getName()){
            LOGGER.info("name has not been changed");
            return getEditColumnPage(model, userId, tableId, columnId);
        }

        ColumnEntity newColumn = new ColumnEntity();
        newColumn.setName(newName);
        newColumn.setTableId(tableId);
        newColumn.setColumnId(columnId);

        facadeService.updateColumn(newColumn);

        return getTablePage(model, userId, tableId);
    }

    //DELETE COLUMN
    @GetMapping("/"+EDIT_COLUMN_PAGE + "/{userId}" + "/{tableId}"+ "/{columnId}/"+DELETE_COLUMN)
    public String deleteColumn(Model model, @PathVariable("userId") long userId, @PathVariable("tableId") long tableId,
                               @PathVariable("columnId") long columnId){
        LOGGER.info("deleteColumn method");

        facadeService.deleteColumn(columnId);

        return getTablePage(model, userId, tableId);
    }

    //GET EDIT TICKET PAGE
    @GetMapping("/"+EDIT_TICKET_PAGE + "/{userId}" + "/{tableId}"+ "/{columnId}"+ "/{ticketId}")
    public String getEditTicketPage(@NotNull Model model, @PathVariable("userId") long userId, @PathVariable("tableId") long tableId,
                                    @PathVariable("ticketId") long ticketId, @PathVariable long columnId) {
        LOGGER.info("getEditTicketPage method");

        TicketEntity ticket = facadeService.getTicketById(ticketId);

        if(ticket == null)
            return getTablePage(model, userId, tableId);

        ColumnEntity columnSelected = facadeService.getColumnById(columnId);

        if(columnSelected == null)
            return getTablePage(model, userId, tableId);

        List<UserEntity> ticketUsers = facadeService.findMembersInTicket(ticketId);
        List<UserEntity> notTicketUsers = facadeService.findFriendsWhichNotInTicket(tableId, ticketId);

        model.addAttribute("columns", facadeService.getAllColumnsExceptOneByTable(tableId, columnId));
        model.addAttribute("userId", userId);
        model.addAttribute("tableId", tableId);
        model.addAttribute("ticket", ticket);
        model.addAttribute("columnSelected", columnSelected);
        model.addAttribute("friendsForAdd", notTicketUsers);
        model.addAttribute("friendsForDelete", ticketUsers);
        return EDIT_TICKET_PAGE;
    }

    //EDIT TICKET
    @PostMapping("/"+EDIT_TICKET_PAGE + "/{userId}" + "/{tableId}"+ "/{columnId}"+"/{ticketId}/"+EDIT_TICKET)
    public String editTicket(Model model, @PathVariable("userId") long userId, @PathVariable("tableId") long tableId,
                             @PathVariable("columnId") long columnId, @PathVariable("ticketId") long ticketId,
                             @ModelAttribute("editedTicket")TicketEntity editedTicket){
        LOGGER.info("editTicket method");

        String newName = editedTicket.getName();
        String newDescription = editedTicket.getDescription();
        String newStatus = editedTicket.getStatus();
        String newColumnName = editedTicket.getStages();//чтоб не делать Dto файл из-за одного поля

        long newColumnId = facadeService.getColumnIdByName(newColumnName, tableId);

        if(newColumnId == -1){
            LOGGER.info("Column Id not found");
            return getEditTicketPage(model, userId, tableId, ticketId, columnId);
        }

        if(!StringUtils.isNotBlank(newName) || !StringUtils.isNotBlank(newStatus) ||
                !StringUtils.isNotBlank(newColumnName)){
            LOGGER.info("ErrorStack");
            return getEditTicketPage(model, userId, tableId, ticketId, columnId);
        }
        if(!StringUtils.isNotBlank(newDescription)){
            newDescription = "empty";
        }

        TicketEntity oldTicket = facadeService.getTicketById(ticketId);

        if(newName == oldTicket.getName() && newDescription == oldTicket.getDescription() &&
                newStatus == oldTicket.getStatus() && newColumnId == oldTicket.getColumnId()){
            LOGGER.info("nothing has been changed");
            return getEditTicketPage(model, userId, tableId, ticketId, columnId);
        }

        TicketEntity newTicket = new TicketEntity();
        newTicket.setTicketId(oldTicket.getTicketId());
        newTicket.setName(newName);
        newTicket.setDescription(newDescription);
        newTicket.setStatus(newStatus);
        newTicket.setColumnId(newColumnId);

        facadeService.updateTicket(newTicket);

        return getTablePage(model, userId, tableId);
    }

    //DELETE TICKET
    @GetMapping("/"+EDIT_TICKET_PAGE + "/{userId}" + "/{tableId}"+"/{ticketId}/"+DELETE_TICKET)
    public String deleteTicket(Model model, @PathVariable("userId") long userId, @PathVariable("tableId") long tableId,
                               @PathVariable("ticketId") long ticketId){
        LOGGER.info("deleteTicket method");

        facadeService.deleteTicket(ticketId);

        return getTablePage(model, userId, tableId);
    }

    //ADD USER TO TICKET
    @GetMapping("/"+EDIT_TABLE_PAGE + "/{userId}/{tableId}/{columnId}/{ticketId}/{addFriendEmail}/"+ADD_FRIEND_TO_TICKET)
    public String addUserToTicket(Model model, @PathVariable("userId") long userId, @PathVariable("tableId") long tableId,
                                  @PathVariable("addFriendEmail") String addFriendEmail, @PathVariable long columnId,
                                  @PathVariable long ticketId){
        LOGGER.info("addUserToTicket method");

        long addFriendId = facadeService.findUserIdByEmail(addFriendEmail);

        TicketExecutionEntity ticketExecution = new TicketExecutionEntity();
        ticketExecution.setTicketId(ticketId);
        ticketExecution.setUserId(addFriendId);
        ticketExecution.setRole("executor");

        facadeService.addTicketExecution(ticketExecution);

        return getEditTicketPage(model, userId, tableId, ticketId, columnId);
    }

    //DELETE USER FROM TICKET
    @GetMapping("/"+EDIT_TABLE_PAGE + "/{userId}/{tableId}/{columnId}/{ticketId}/{deleteFriendEmail}/"+DELETE_FRIEND_FROM_TICKET)
    public String deleteUserFromTicket(Model model, @PathVariable("userId") long userId, @PathVariable("tableId") long tableId,
                                  @PathVariable("deleteFriendEmail") String deleteFriendEmail, @PathVariable long columnId,
                                  @PathVariable long ticketId){
        LOGGER.info("deleteUserFromTicket method");

        long addFriendId = facadeService.findUserIdByEmail(deleteFriendEmail);

        facadeService.deleteTicketExecution(addFriendId, ticketId);

        return getEditTicketPage(model, userId, tableId, ticketId, columnId);
    }

}
