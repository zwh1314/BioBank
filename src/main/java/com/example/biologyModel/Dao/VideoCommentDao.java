package com.example.biologyModel.Dao;

import com.example.biologyModel.Entity.VideoComment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VideoCommentDao {
    @Insert("INSERT INTO video_comment(video_id, comment_text, comment_publisher, comment_like, comment_date) VALUES" +
            "(#{videoId}, #{commentText}, #{commentPublisher}, #{commentLike}, NOW())")
    int addComment(VideoComment videoComment);

    @Update("UPDATE video_comment SET comment_like = #{commentLike} WHERE comment_id = #{commentId}")
    int updateCommentLike(@Param("commentLike")long commentLike, @Param("commentId")long commentId);

    @Update("UPDATE video_comment SET comment_text = #{commentText} WHERE comment_id = #{commentId}")
    int updateCommentText(@Param("commentText")String commentText, @Param("commentId")long commentId);

    @ResultType(VideoComment.class)
    @Select("SELECT comment_id as commentId, video_id as videoId, comment_text as commentText, comment_publisher as commentPublisher, " +
            "comment_like as commentLike, comment_date as commentDate FROM video_comment WHERE video_id " +
            "= #{videoId}")
    List<VideoComment> findCommentByVideoId(@Param("videoId")long videoId);

    @ResultType(VideoComment.class)
    @Select("SELECT comment_id as commentId, video_id as videoId, comment_text as commentText, comment_publisher as commentPublisher, " +
            "comment_like as commentLike, comment_date as commentDate FROM video_comment WHERE comment_publisher " +
            "= #{commentPublisher}")
    List<VideoComment> findCommentById(@Param("commentPublisher")long commentPublisher);

    @Select("SELECT comment_id as commentId, video_id as videoId, comment_text as commentText, comment_publisher as commentPublisher, " +
            "comment_like as commentLike, comment_date as commentDate FROM video_comment WHERE TO_DAYS(NOW()) - " +
            "TO_DAYS(comment_date) <= 7")
    List<VideoComment> findCommentInOneWeek();

    @Select("SELECT comment_id as commentId, video_id as videoId, comment_text as commentText, comment_publisher as commentPublisher, " +
            "comment_like as commentLike, comment_date as commentDate FROM video_comment WHERE comment_text LIKE " +
            "CONCAT('%', #{textInclude}, '%')")
    List<VideoComment> findCommentByText(@Param("textInclude")String textInclude);

    @Delete("DELETE FROM video_comment WHERE comment_id = #{commentId}")
    int deleteCommentById(@Param("commentId")long commentId);

    @Select("SELECT comment_like as commentLike FROM video_comment WHERE comment_id =#{commentId}")
    Long getCommentLikeByCommentId(@Param("commentId") long commentId);

    @Select("SELECT comment_id from video_comment as commentId")
    List<Long> getCommentIds();
}
