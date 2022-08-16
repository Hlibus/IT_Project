package com.parkhomenko.ITProg.dao.mapper;

import com.parkhomenko.ITProg.entity.TicketEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketMapper implements RowMapper<TicketEntity> {

    @Override
    public TicketEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setTicketId(rs.getInt("id_ticket"));
        ticketEntity.setColumnId(rs.getInt("id_column"));
        ticketEntity.setName(rs.getString("name"));
        ticketEntity.setDescription(rs.getString("desсription"));
        ticketEntity.setStatus(rs.getString("status"));
        ticketEntity.setAnswer(rs.getBlob("answer"));
        ticketEntity.setMarkers(rs.getString("markers"));
        ticketEntity.setStages(rs.getString("stages"));
        return ticketEntity;

    }

}
