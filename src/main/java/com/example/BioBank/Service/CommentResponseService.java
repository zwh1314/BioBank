package com.example.BioBank.Service;

import com.example.BioBank.Entity.CommentResponse;
import com.example.BioBank.Request.CommentResponseRequest;
import com.example.BioBank.Response.Response;

import java.util.List;

public interface CommentResponseService {
    Response<Boolean> addCommentResponse(CommentResponseRequest commentResponseRequest);

    Response<Boolean> updateResponseLikeNumber(long responseLikeNumber, long responseId);

    Response<Boolean> updateResponseText(String responseText, long responseId);

    Response<List<CommentResponse>> getCommentResponseByCommentId(long commentId);

    Response<List<CommentResponse>> getVideoCommentResponseByCommentId(long commentId);

    Response<List<CommentResponse>> getCommentResponseInOneWeek();

    Response<List<CommentResponse>> getVideoCommentResponseInOneWeek();

    Response<List<CommentResponse>> getCommentResponseByRelativeText(String relativeText);

    Response<List<CommentResponse>> getVideoCommentResponseByRelativeText(String relativeText);

    Response<List<CommentResponse>> getCommentResponseByPublisherId(long publisherId);

    Response<List<CommentResponse>> getVideoCommentResponseByPublisherId(long publisherId);

    Response<Boolean> deleteCommentResponseById(long responseId);

    Response<List<CommentResponse>> getVideoCommentResponseByNumber(long number);
}
