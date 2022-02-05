package com.example.biologyModel.Service;

import com.example.biologyModel.Entity.VideoComment;
import com.example.biologyModel.Request.VideoCommentRequest;
import com.example.biologyModel.Response.Response;


import java.util.List;

public interface VideoCommentService {
    Response<Boolean> addVideoComment(VideoCommentRequest videoCommentRequest);

    Response<Boolean> updateVideoCommentLikeNumber(long commentId);

    Response<Boolean> updateVideoCommentText(String VideoCommentText, long commentId);

    Response<List<VideoComment>> getVideoCommentByPublisher(long publisherId);

    Response<List<VideoComment>> getVideoCommentInOneWeek();

    Response<List<VideoComment>> getVideoCommentByRelativeText(String relativeText);

    Response<Boolean> deleteVideoCommentById(long videoCommentId);
}
