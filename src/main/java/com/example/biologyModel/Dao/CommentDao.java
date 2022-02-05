package com.example.biologyModel.Dao;

import com.example.biologyModel.Entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentDao {
    @Insert("INSERT INTO comment(comment_text, comment_publisher, comment_like, comment_date) " +
            "VALUES (#{commentText}, #{commentPublisher}, #{commentLike}, NOW() )")
    int addComment(Comment comment);

    @Update("UPDATE comment SET comment_like = #{commentLike} WHERE comment_id = #{commentId}")
    int updateCommentLike(@Param("commentLike")long commentLike, @Param("commentId")long commentId);

    @Update("UPDATE comment SET comment_text = #{commentText} WHERE comment_id = #{commentId}")
    int updateCommentText(@Param("commentText")String commentText, @Param("commentId")long commentId);

    @ResultType(Comment.class)
    @Select("SELECT comment_id as commentId, comment_text as commentText, comment_publisher as commentPublisher, " +
            "comment_like as commentLike, comment_date as commentDate FROM comment WHERE comment_publisher " +
            "= #{commentPublisher}")
    List<Comment> findCommentById(@Param("commentPublisher")long commentPublisher);

    @Select("SELECT comment_id as commentId, comment_text as commentText, comment_publisher as commentPublisher, " +
            "comment_like as commentLike, comment_date as commentDate FROM comment WHERE TO_DAYS(NOW()) - " +
            "TO_DAYS(comment_date) <= 7")
    List<Comment> findCommentInOneWeek();

    @Select("SELECT comment_id as commentId, comment_text as commentText, comment_publisher as commentPublisher, " +
            "comment_like as commentLike, comment_date as commentDate FROM comment WHERE comment_text LIKE " +
            "CONCAT('%', #{textInclude}, '%')")
    List<Comment> findCommentByText(@Param("textInclude")String textInclude);

    @Delete("DELETE FROM comment WHERE comment_id = #{commentId}")
    int deleteCommentById(@Param("commentId")long commentId);

    @Select("SELECT comment_like as commentLike FROM comment WHERE comment_id =#{commentId}")
    Long getCommentLikeByCommentId(@Param("commentId") long commentId);

    @Select("SELECT comment_id from comment as commentId")
    List<Long> getCommentIds();

    @ResultType(Comment.class)
    @Select("SELECT comment_id as commentId,comment_text as commentText,comment_publisher as commentPublisher," +
            "comment_like as commentLike,comment_date as commentDate FROM comment WHERE comment_id>=(SELECT FLOOR( MAX(comment_id) * RAND()) FROM `comment` ) ORDER BY comment_id LIMIT #{number}")
    List<Comment>findCommentByNumber(@Param("number")long number);



    @Select("SELECT comment_id FROM comment WHERE comment_publisher = #{commentPublisher} AND comment_date in (SELECT MAX(comment_date) FROM comment where comment_id in (select comment_id from comment where comment_publisher = #{commentPublisher} ) ) ")
    long findCommentIdByUserId(@Param("commentPublisher")long commentPublisher);
}
