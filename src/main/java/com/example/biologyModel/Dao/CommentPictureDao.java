package com.example.biologyModel.Dao;

import com.example.biologyModel.Entity.CommentPicture;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface CommentPictureDao {
    @Insert("INSERT INTO comment_picture(comment_id,picture_id,file_name,picture_url) VALUES (#{commentId},#{pictureId},#{fileName},#{pictureUrl});")
    int addCommentPicture(CommentPicture commentPicture);

    @ResultType(CommentPicture.class)
    @Select("SELECT comment_id as commentId,picture_id as pictureId, file_name as fileName,picture_url as pictureUrl FROM comment_picture WHERE comment_id = #{commentId}")
    List<CommentPicture> getCommentPictureByCommentId(@Param("commentId") long commentId);

    @Update("UPDATE comment_picture SET file_name = #{fileName},picture_url = #{pictureUrl} WHERE comment_id = #{commentId} AND picture_id = #{pictureId}")
    int updateCommentPicture(CommentPicture commentPicture);

    @Delete("Delete From comment_picture WHERE comment_id=#{commentId}")
    int deleteCommentPictureByCommentId(@Param("commentId")long commentId);


}
