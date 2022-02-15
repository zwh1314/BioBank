package com.example.BioBank.Service;

import com.example.BioBank.DTO.VideoDTO;
import com.example.BioBank.Entity.Video;
import com.example.BioBank.Response.Response;
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
