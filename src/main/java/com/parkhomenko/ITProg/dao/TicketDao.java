package com.parkhomenko.ITProg.dao;

import com.parkhomenko.ITProg.dao.mapper.ColumnMapper;
import com.parkhomenko.ITProg.dao.mapper.NUMberMapper;
import com.parkhomenko.ITProg.dao.mapper.TicketMapper;
import com.parkhomenko.ITProg.dto.NUMberDto;
import com.parkhomenko.ITProg.entity.ColumnEntity;
import com.parkhomenko.ITProg.entity.TicketEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketDao {

    private final String GET_ALL_TICKET = "SELECT * FROM  Ticket";
    private final String get_count_of_ticket = "SELECT MAX(id_ticket) AS count FROM  Ticket";
    private final String INSERT_INTO_TICKET = "INSERT INTO  Ticket (id_ticket, id_column, name, Ticket.desсription, status) VALUES (?, ?, ?, ?, ?)";
    private final String DELETE_FROM_TICKET = "DELETE FROM Ticket WHERE id_ticket = ?";
    private final String UPDATE_TICKET_COLUMN = "UPDATE Ticket SET id_column = ? WHERE id_ticket = ?";
    private final String UPDATE_TICKET = "UPDATE Ticket SET id_column = ?, name = ?, Ticket.desсription = ?, status = ?, answer = ?, markers = ?, stages = ? " +
            "WHERE id_ticket = ?";
    private JdbcTemplate jdbcTemplate;

    public TicketDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //GET ALL
    public List<TicketEntity> getAllTickets(){
        return jdbcTemplate.query(
                GET_ALL_TICKET,
                new TicketMapper()
        );
    }

    //GET TICKET BY ID
    public List<TicketEntity> getTicketByID(long ticketId){
        String GET_TICKET_BY_ID = "SELECT * FROM  Ticket WHERE id_ticket = "+ticketId;
        return jdbcTemplate.query(
                GET_TICKET_BY_ID,
                new TicketMapper()
        );
    }

    //GET NEW TICKET ID
    public List<NUMberDto> getTicketCount(){
        return jdbcTemplate.query(
                get_count_of_ticket,
                new NUMberMapper()
        );
    }

    //FIND TICKET NAME
    public List<TicketEntity> getAllColumnsByName(String name, long columnId){
        String find_ticket_name_in_column = "SELECT * FROM Ticket WHERE itprogdb.Ticket.name = '" + name+
                "' AND id_column = "+columnId;
        return jdbcTemplate.query(
                find_ticket_name_in_column,
                new TicketMapper()
        );
    }

    //FIND BY ID
    public List<TicketEntity> findTicketById(long ticketId){
        String find_ticket_by_id = "SELECT * FROM  Ticket WHERE id_ticket = "+ticketId;
        return jdbcTemplate.query(
                find_ticket_by_id,
                new TicketMapper()
        );

    }

    //FIND BY COLUMN
    public List<TicketEntity> getAllColumnTickets(long tableId){
        String find_ticket_by_column = "SELECT * FROM  Ticket WHERE id_column IN " +
                "(SELECT id_column FROM itprogdb.Column WHERE id_table = "+tableId+")";
        return jdbcTemplate.query(
                find_ticket_by_column,
                new TicketMapper()
        );

    }

    //ADD
    private void privateAddTicket(@NotNull TicketEntity ticketEntity){
        jdbcTemplate.update(
                INSERT_INTO_TICKET,
                ticketEntity.getTicketId(),
                ticketEntity.getColumnId(),
                ticketEntity.getName(),
                ticketEntity.getDescription(),
                ticketEntity.getStatus()
        );
    }

    public void addTicket(TicketEntity ticketEntity){
        privateAddTicket(ticketEntity);
    }

    //DELETE
    private void privateDeleteTicket(long ticketId){
        jdbcTemplate.update(
                DELETE_FROM_TICKET,
                ticketId
        );
    }

    public void deleteTicket(long ticketId){
        privateDeleteTicket(ticketId);
    }

    //UPDATE
    private void privateChangeTicketColumn(long ticketId, long columnId){
        jdbcTemplate.update(
                UPDATE_TICKET_COLUMN,
                columnId,
                ticketId
        );
    }

    public void changeTicketColumn(long ticketId, long columnId){
        privateChangeTicketColumn(ticketId, columnId);
    }

    //UPDATE
    private void privateUpdateTicket(@NotNull TicketEntity ticketEntity){
        jdbcTemplate.update(
                UPDATE_TICKET,
                ticketEntity.getColumnId(),
                ticketEntity.getName(),
                ticketEntity.getDescription(),
                ticketEntity.getStatus(),
                ticketEntity.getAnswer(),
                ticketEntity.getMarkers(),
                ticketEntity.getStages(),
                ticketEntity.getTicketId()
        );
    }

    public void updateTicket(TicketEntity ticketEntity){
        privateUpdateTicket(ticketEntity);
    }

}
