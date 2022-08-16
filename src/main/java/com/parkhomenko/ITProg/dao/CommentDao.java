package com.parkhomenko.ITProg.dao;

import com.parkhomenko.ITProg.dao.mapper.CommentMapper;
import com.parkhomenko.ITProg.entity.CommentEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDao {

    private final String GET_ALL_COMMENTS = "SELECT * FROM  Comment";
    private String find_comment_by_ticket = "SELECT * FROM  Comment WHERE id_ticket = ";
    private final String INSERT_INTO_COMMENT = "INSERT INTO  Comment (id_user, id_ticket, comment) VALUES (?, ?, ?)";
    private final String DELETE_FROM_COMMENT = "DELETE FROM Comment WHERE id_comments = ?";
    private final String UPDATE_COMMENT = "UPDATE Comment SET comment = ? WHERE id_comments = ?";
    private JdbcTemplate jdbcTemplate;

    public CommentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //GET ALL
    public List<CommentEntity> getAllComments(){
        return jdbcTemplate.query(
                GET_ALL_COMMENTS,
                new CommentMapper()
        );
    }

    //FIND BY TICKET
    public List<CommentEntity> getAllComments(long ticketId){
        find_comment_by_ticket += ticketId;
        return jdbcTemplate.query(
                find_comment_by_ticket,
                new CommentMapper()
        );
    }

    //ADD
    private void privateAddComment(CommentEntity commentEntity){
        jdbcTemplate.update(
                INSERT_INTO_COMMENT,
                commentEntity.getUserId(),
                commentEntity.getTicketId(),
                commentEntity.getComment()
        );
    }

    public void addComment(CommentEntity commentEntity){
        privateAddComment(commentEntity);
    }

    //DELETE
    private void privateDeleteComment(CommentEntity commentEntity){
        jdbcTemplate.update(
                DELETE_FROM_COMMENT,
                commentEntity.getCommentId()
        );
    }

    public void deleteComment(CommentEntity commentEntity){
        privateDeleteComment(commentEntity);
    }

    //UPDATE
    private void privateUpdateComment(CommentEntity commentEntity){
        jdbcTemplate.update(
                UPDATE_COMMENT,
                commentEntity.getComment(),
                commentEntity.getCommentId()
        );
    }

    public void updateComment(CommentEntity commentEntity){
        privateUpdateComment(commentEntity);
    }

}
