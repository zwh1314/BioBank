package com.example.biologyModel.Dao;

import com.example.biologyModel.Entity.CommentResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentResponseDao {
    @Insert("INSERT INTO comment_response(response_comment, response_text, response_publisher, " +
            "response_type, response_like, response_date) VALUES(#{commentId}, #{responseText}, " +
            "#{responsePublisher}, #{responseType}, #{responseLike}, NOW())")
    int addCommentResponse(CommentResponse commentResponse);

    @Update("UPDATE comment_response SET response_like = #{responseLike} WHERE response_id = #{responseId}")
    int updateResponseLike(@Param("responseLike")long responseLike, @Param("responseId")long responseId);

    @Update("UPDATE comment_response SET response_text = #{responseText} WHERE response_id = #{responseId}")
    int updateResponseText(@Param("responseText") String responseText, @Param("responseId")long responseId);

    @ResultType(CommentResponse.class)
    @Select("SELECT response_id as responseId, response_comment as commentId, response_text as " +
            "responseText, response_publisher as responsePublisher, response_type as responseType, " +
            "response_like as responseLike, response_date as responseDate FROM comment_response WHERE " +
            "response_comment = #{commentId} GROUP BY response_type HAVING response_type = #{responseType}")
    List<CommentResponse> findCommentResponseByCommentId(@Param("commentId")long commentId, @Param("responseType")long responseType);

    @ResultType(CommentResponse.class)
    @Select("SELECT response_id as responseId, response_comment as commentId, response_text as " +
            "responseText, response_publisher as responsePublisher, response_type as responseType, " +
            "response_like as responseLike, response_date as responseDate FROM comment_response WHERE " +
            "TO_DAYS(NOW()) - TO_DAYS(response_date) <= 7 AND response_type = #{responseType}")
    List<CommentResponse> findCommentResponseInOneWeek(@Param("responseType")long responseType);

    @ResultType(CommentResponse.class)
    @Select("SELECT response_id as responseId, response_comment as commentId, response_text as " +
            "responseText, response_publisher as responsePublisher, response_type as responseType, " +
            "response_like as responseLike, response_date as responseDate FROM comment_response WHERE " +
            "response_text LIKE CONCAT('%', #{textInclude}, '%') AND response_type = #{responseType}")
    List<CommentResponse> findCommentResponseByText(@Param("textInclude")String textInclude, @Param("responseType")long responseType);

    @ResultType(CommentResponse.class)
    @Select("SELECT response_id as responseId, response_comment as commentId, response_text as " +
            "responseText, response_publisher as responsePublisher, response_type as responseType, " +
            "response_like as responseLike, response_date as responseDate FROM comment_response WHERE " +
            "response_publisher = #{responsePublisher} AND response_type = #{responseType}")
    List<CommentResponse> findCommentResponseByPublisher(@Param("responsePublisher")long responsePublisher, @Param("responseType")long responseType);

    @Delete("DELETE FROM comment_response WHERE response_id = #{responseId}")
    int deleteCommentResponseById(@Param("responseId")long responseId);

    @Select("SELECT response_like as responseLike FROM comment_response WHERE response_id =#{responseId}")
    Long getResponseLikeByResponseId(@Param("responseId") long responseId);

    @Select("SELECT response_id from comment_response as responseId")
    List<Long> getResponseIds();


    @ResultType(CommentResponse.class)
    @Select("SELECT response_id as responseId, response_comment as commentId, response_text as " +
            "responseText, response_publisher as responsePublisher, response_type as responseType, " +
            "response_like as responseLike, response_date as responseDate FROM comment_response " +
            "WHERE  response_type = #{responseType} ORDER BY Rand(response_id) LIMIT #{number}")
    List<CommentResponse> findCommentResponseByNumber(@Param("number")long number,@Param("responseType")long responseType);
}
