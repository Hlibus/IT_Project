package com.parkhomenko.ITProg.service;

import com.parkhomenko.ITProg.dao.*;
import com.parkhomenko.ITProg.entity.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacadeService {

    private static final Logger LOGGER = Logger.getLogger(FacadeService.class);

    private final TableDao tableDao;
    private final TableRoleDao tableRoleDao;
    private final UserFriendDao userFriendDao;
    private final ColumnDao columnDao;
    private final TicketDao ticketDao;
    private final TicketExecutionDao ticketExecutionDao;
    private final UserDao userDao;
    private final InfoUserDao infoUserDao;

    private final UserService userService;

    @Autowired
    public FacadeService(TableDao tableDao, TableRoleDao tableRoleDao, UserFriendDao userFriendDao, ColumnDao columnDao, TicketDao ticketDao, TicketExecutionDao ticketExecutionDao, UserDao userDao, InfoUserDao infoUserDao, UserService userService) {
        this.tableDao = tableDao;
        this.tableRoleDao = tableRoleDao;
        this.userFriendDao = userFriendDao;
        this.columnDao = columnDao;
        this.ticketDao = ticketDao;
        this.ticketExecutionDao = ticketExecutionDao;
        this.userDao = userDao;
        this.infoUserDao = infoUserDao;
        this.userService = userService;
    }

    public UserService getUserService() {
        return userService;
    }


    public UserEntity findUserByEmailPass(String email, String pass) {
        LOGGER.info("start method findUserByEmailPass()");
        return userService.findByEmailPass(email, pass);
    }


    //TABLE METHODS


    //GET ALL USER TABLES
    public List<TableEntity> findUserTables(long userId){
        return tableDao.getUserTables(userId);
    }

    //GET TABLE BY ID
    public TableEntity getTableById(long tableId){
        if(tableDao.getTableById(tableId).isEmpty())
            return null;
        return tableDao.getTableById(tableId).get(0);
    }

    //CREATE NEW TABLE
    public void createNewTable(String tableName, long userId, String role){
        tableDao.addTable(tableName, userId, role);

    }

    //DELETE TABLE
    public void deleteTable(long tableId){
        tableDao.deleteTable(tableId);
    }

    //UPDATE TABLE
    public void updateTable(TableEntity tableEntity){
        tableDao.updateTable(tableEntity);
    }

    public List<TableEntity> getAllUserTableName(String name, long userId){
        return tableDao.getAllUserTableName(name, userId);
    }

    //ADD USER TO TABLE
    public void addUserToTable(TableRoleEntity tableRoleEntity){
        tableRoleDao.addTableRole(tableRoleEntity);
    }

    //DELETE USER FROM TABLE
    public void deleteUserFromTable(long userId, long tableId){
        tableRoleDao.deleteTableRole(userId, tableId);
    }


    //USER METHODS


    //GET ALL USERS
    public List<UserEntity> getAllUsers() {
        return userDao.getAllUsers();
    }

    //GET USER ROLE IN TABLE
    public List<TableRoleEntity> getUserRoleInTable(long userId, long tableId){
        return tableRoleDao.getUserRole(userId, tableId);
    }

    //GET ALL USERS FOR ADMIN
    public List<UserEntity> getAllUsersAdm() {
        return userDao.getAllUsersAdm();
    }

    //GET ALL USER FRIENDS
    public List<UserEntity> findUserFriends(long userId){
        return userFriendDao.getUserFriends(userId);
    }

    public long findUserIdByEmail(String email){
        if(userDao.findUserIdByEmail(email).isEmpty())
            return -1;
        return userDao.findUserIdByEmail(email).get(0).getUserId();
    }

    //FIND ALL USER FRIENDS IN TICKET EXECUTION
    public List<UserEntity> findMembersInTicket(long ticketId){
        return ticketExecutionDao.findMembersInTicket(ticketId);
    }

    //FIND ALL USER FRIENDS WHICH IS NOT IN TICKET EXECUTION
    public List<UserEntity> findFriendsWhichNotInTicket(long tableId, long ticketId){
        return ticketExecutionDao.findFriendsWhichNotInTicket(tableId, ticketId);
    }

    //FIND USERS IN TABLE WITH ROLE MEMBER
    public List<UserEntity> findMembersInTable(long tableId){
        return tableRoleDao.findMembersInTable(tableId);
    }

    //FIND FRIENDS WHICH NOT TABLE MEMBER
    public List<UserEntity> findFriendsWhichNotInTable(long userId, long tableId){
        return tableRoleDao.findFriendsWhichNotInTable(userId, tableId);
    }

    //GET USER COUNT
    public long getNewUserId(){
        if(userDao.getAllCountUsers().isEmpty())
            return 1;
        return userDao.getAllCountUsers().get(0).getCount() + 1;
    }

    //GET COUNT OF SIMILAR USER EMAIL
    public long getCountUserEmail(String email){
        return userDao.getCountUserEmail(email).get(0).getCount();
    }

    //create new user
    public void createNewuser(UserEntity user){
        userDao.saveUser(user);
    }

    //create info user
    public void createInfoUser(InfoUserEntity infoUser){
        infoUserDao.saveInfoUser(infoUser);
    }

    //FIND NOT FRIEND USERS
    public List<UserEntity> findNotFriendUsers(long userId){
        return userDao.findNotFriendUsersDao(userId);
    }

    //ADD NEW FRIEND
    public void addNewFriend(long userId, long friendId){
        userFriendDao.addUserFriend(userId, friendId);
    }

    //GET 1 USERFRIEND
    public List<UserFriendEntity> getUserFriend(long userId, long friendId){
        return userFriendDao.getUserFriend(userId, friendId);
    }

    //DELETE FRIEND
    public void deleteUserFriend(long userId, long friendId){
        userFriendDao.deleteUserFriend(userId, friendId);
    }


    //COLUMN METHODS


    //GET ALL COLUMNS IN TABLE
    public List<ColumnEntity> findAllTableColumns(long tableId){
        return columnDao.getAllColumnsByTable(tableId);
    }

    //GET ALL COLUMNS EXCEPT 1 IN TABLE
    public List<ColumnEntity> getAllColumnsExceptOneByTable(long tableId, long columnId){
        return columnDao.getAllColumnsExceptOneByTable(tableId,columnId);
    }

    //GET COLUMN COUNT
    public long getNewColumnId(){
        if(columnDao.getCountColumns().isEmpty())
            return 1;
        return columnDao.getCountColumns().get(0).getCount()+1;
    }

    //CREATE NEW COLUMN
    public void createColumn(ColumnEntity columnEntity){
        columnDao.addColumn(columnEntity);
    }

    //GET ALL COLUMN NAMES FROM TABLE
    public List<ColumnEntity> getAllColumnNamesInTable(String name, long tableId){
        return columnDao.getAllColumnsByName(name, tableId);
    }

    //GET COLUMN BY ID
    public ColumnEntity getColumnById(long columnId){
        if(columnDao.getColumnById(columnId).isEmpty())
            return null;
        return columnDao.getColumnById(columnId).get(0);
    }

    //DELETE COLUMN
    public void deleteColumn(long columnId){
        columnDao.deleteColumn(columnId);
    }

    //UPDATE COLUMN
    public void updateColumn(ColumnEntity columnEntity){
        columnDao.updateColumn(columnEntity);
    }


    //TICKET METHODS


    //ADD USER FOR TICKET EXECUTION
    public void addTicketExecution(TicketExecutionEntity ticketExecution){
        ticketExecutionDao.addTicketExecution(ticketExecution);
    }

    //DELETE USER FROM TICKET
    public void deleteTicketExecution(long userId, long ticketId){
        ticketExecutionDao.deleteTicketExecution(userId, ticketId);
    }

    //GET ALL TICKETS FROM COLUMN
    public List<TicketEntity> findAllColumnTickets(long tableId){
        return ticketDao.getAllColumnTickets(tableId);
    }

    //GET NEW TICKET ID
    public long getNewTicketId(){
        return ticketDao.getTicketCount().get(0).getCount()+1;
    }

    //GET COLUMN ID BY NAME
    public long getColumnIdByName(String name, long tableId){
        if(columnDao.getColumnIdByName(name, tableId).isEmpty())
            return -1;
        return columnDao.getColumnIdByName(name, tableId).get(0).getCount();
    }

    //GET TICKET BY ID
    public TicketEntity getTicketById(long ticketId){
        if(ticketDao.getTicketByID(ticketId).isEmpty())
            return null;
        return ticketDao.getTicketByID(ticketId).get(0);
    }

    //GET TICKET NAME IN COLUMN
    public List<TicketEntity> getAllTicketsInColumn(String name, long columnId){
        return ticketDao.getAllColumnsByName(name, columnId);
    }

    //CREATE NEW TICKET
    public void createTicket(TicketEntity ticketEntity){
        ticketDao.addTicket(ticketEntity);
    }

    //DELETE TICKET
    public void deleteTicket(long ticketId){
        ticketDao.deleteTicket(ticketId);
    }

    //UPDATE TICKET
    public void updateTicket(TicketEntity ticketEntity){
        ticketDao.updateTicket(ticketEntity);
    }

}
