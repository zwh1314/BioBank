package com.example.BioBank.Service;

import com.example.BioBank.Entity.VideoComment;
import com.example.BioBank.Request.VideoCommentRequest;
import com.example.BioBank.Response.Response;


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
