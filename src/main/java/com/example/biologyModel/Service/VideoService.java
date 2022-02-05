package com.example.biologyModel.Service;

import com.example.biologyModel.DTO.VideoDTO;
import com.example.biologyModel.Entity.Video;
import com.example.biologyModel.Response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
  Response<Boolean> addVideo(Video video, MultipartFile video_mp4);

  Response<Boolean> updateVideoTextContent(String textContent, long videoId);

  Response<Boolean> updateVideoLikeNumber(long likeNumber, long videoId);

  Response<List<VideoDTO>> getVideoByPublisherId(long publisherId);

  Response<List<VideoDTO>> getVideoByRelativeText(String relativeText);

  Response<List<VideoDTO>> getVideoInOneWeek();

  Response<Boolean> deleteVideoById(long videoId);

  Response<List<VideoDTO>> getVideoByNumber(long number);
}
