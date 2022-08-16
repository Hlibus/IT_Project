package com.parkhomenko.ITProg.dao.mapper;

import com.parkhomenko.ITProg.entity.TicketExecutionEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketExecutionMapper implements RowMapper<TicketExecutionEntity> {

    @Override
    public TicketExecutionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        TicketExecutionEntity ticketExecution = new TicketExecutionEntity();
        ticketExecution.setUserId(rs.getInt("id_user"));
        ticketExecution.setTicketId(rs.getInt("id_ticket"));
        ticketExecution.setRole(rs.getString("role"));
        ticketExecution.setMark(rs.getDouble("mark"));
        return ticketExecution;

    }

}
