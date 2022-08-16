package com.parkhomenko.ITProg.dao;

import com.parkhomenko.ITProg.dao.mapper.TicketExecutionMapper;
import com.parkhomenko.ITProg.dao.mapper.UserMapper;
import com.parkhomenko.ITProg.entity.TicketExecutionEntity;
import com.parkhomenko.ITProg.entity.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketExecutionDao {

    private final String GET_ALL_TICKETEXECUTION = "SELECT * FROM  TicketExecution";
    private  String find_ticketExecution = "SELECT * FROM  TicketExecution WHERE id_ticket = ";
    private final String INSERT_INTO_TICKETEXECUTION_WITH_ALL_FIELDS = "INSERT INTO  TicketExecution (id_user, id_ticket, role, mark) VALUES (?, ?, ?, ?)";
    private final String INSERT_INTO_TICKETEXECUTION = "INSERT INTO  TicketExecution (id_user, id_ticket, role) VALUES (?, ?, ?)";
    //private final String DELETE_FROM_TICKETEXECUTION_WITH_TICKETID = "DELETE FROM TicketExecution WHERE id_ticket = ?";
    private final String DELETE_FROM_TICKETEXECUTION_WITH_TICKET_AND_USER_ID = "DELETE FROM TicketExecution WHERE id_user = ? AND id_ticket = ?";
    private final String UPDATE_TICKETEXECUTION = "UPDATE TicketExecution SET role = ?, mark = ? WHERE id_user = ?, id_ticket = ?";
    private JdbcTemplate jdbcTemplate;

    public TicketExecutionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //FIND ALL USER FRIENDS IN TICKET EXECUTION
    public List<UserEntity> findMembersInTicket(long ticketId){
        String find_members_in_ticket = "SELECT User.id_user, email, User.password, nickName, User.role FROM " +
                "user WHERE User.id_user IN (SELECT id_user FROM TicketExecution WHERE id_ticket = " +ticketId+")";
        return jdbcTemplate.query(
                find_members_in_ticket,
                new UserMapper()
        );
    }

    //FIND ALL USER FRIENDS WHICH IS NOT IN TICKET EXECUTION
    public List<UserEntity> findFriendsWhichNotInTicket(long tableId, long ticketId){
        String find_friend_which_not_in_ticket = "SELECT User.id_user, email, User.password, nickName, User.role FROM " +
                "user WHERE User.id_user IN (SELECT id_user FROM TableRole WHERE id_table = " +tableId+
                " AND id_user NOT IN (SELECT id_user FROM TicketExecution WHERE id_ticket = " +ticketId+
                "))";
        return jdbcTemplate.query(
                find_friend_which_not_in_ticket,
                new UserMapper()
        );
    }

    //GET ALL
    public List<TicketExecutionEntity> getAllTicketExecution(){
        return jdbcTemplate.query(
                GET_ALL_TICKETEXECUTION,
                new TicketExecutionMapper()
        );
    }

    //FIND
    public List<TicketExecutionEntity> getTicketExecutionById(long ticketId){
        find_ticketExecution += ticketId;
        return jdbcTemplate.query(
                find_ticketExecution,
                new TicketExecutionMapper()
        );
    }

    //ADD
    private void privateAddTicketExecutionWithMark(TicketExecutionEntity ticketExecution){
        jdbcTemplate.update(
                INSERT_INTO_TICKETEXECUTION_WITH_ALL_FIELDS,
                ticketExecution.getUserId(),
                ticketExecution.getTicketId(),
                ticketExecution.getRole(),
                ticketExecution.getMark()
        );
    }

    //ADD WITH 3/4 FIELDS
    private void privateAddTicketExecution(TicketExecutionEntity ticketExecution){
        jdbcTemplate.update(
                INSERT_INTO_TICKETEXECUTION,
                ticketExecution.getUserId(),
                ticketExecution.getTicketId(),
                ticketExecution.getRole()
        );
    }

    public void addTicketExecution(TicketExecutionEntity ticketExecution){
        privateAddTicketExecution(ticketExecution);
    }

    //DELETE 1 TABLE
    private void privateDeleteTicketExecution(long userId, long ticketId){
        jdbcTemplate.update(
                DELETE_FROM_TICKETEXECUTION_WITH_TICKET_AND_USER_ID,
                userId,
                ticketId
        );
    }

    public void deleteTicketExecution(long userId, long ticketId){
        privateDeleteTicketExecution(userId, ticketId);
    }

    //UPDATE
    private void privateUpdateTicketExecution(TicketExecutionEntity ticketExecution){
        jdbcTemplate.update(
                UPDATE_TICKETEXECUTION,
                ticketExecution.getUserId(),
                ticketExecution.getTicketId()
        );
    }

    public void updateTicketExecution(TicketExecutionEntity ticketExecution){
        privateUpdateTicketExecution(ticketExecution);
    }

}
