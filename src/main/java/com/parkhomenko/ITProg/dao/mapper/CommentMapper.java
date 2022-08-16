package com.parkhomenko.ITProg.dao.mapper;

import com.parkhomenko.ITProg.entity.CommentEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper implements RowMapper<CommentEntity> {

    @Override
    public CommentEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentId(rs.getInt("id_comments"));
        commentEntity.setUserId(rs.getInt("id_user"));
        commentEntity.setTicketId(rs.getInt("id_ticket"));
        commentEntity.setComment(rs.getString("comment"));
        return commentEntity;

    }

}
