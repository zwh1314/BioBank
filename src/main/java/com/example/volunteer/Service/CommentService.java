package com.example.volunteer.Service;

import com.example.volunteer.DTO.CommentDTO;
import com.example.volunteer.Response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CommentService {
    Response<Boolean> addComment(long userId, String commentText, MultipartFile[] commentPicture);

    Response<Boolean> updateCommentLikeNumber(long commentId);

    Response<Boolean> updateCommentText(String commentText, long commentId);

    Response<List<CommentDTO>> getCommentByPublisher(long publisherId);

    Response<List<CommentDTO>> getCommentInOneWeek();

    Response<List<CommentDTO>> getCommentByRelativeText(String relativeText);

    Response<Boolean> deleteCommentById(long commentId);

    Response<Boolean> addCommentPicture(long commentId, MultipartFile[] commentPicture);

    Response<List<CommentDTO>> getCommentByNumber(long number);

}
