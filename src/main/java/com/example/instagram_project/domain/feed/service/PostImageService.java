package com.example.instagram_project.domain.feed.service;

import com.example.instagram_project.S3Manager;
import com.example.instagram_project.domain.feed.entity.Post;
import com.example.instagram_project.domain.feed.entity.PostImage;
import com.example.instagram_project.domain.feed.repository.PostImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final PostImageRepository postImageRepository;
    private final S3Manager s3Manager;

    @Transactional
    public void saveAll(Post post, List<MultipartFile> multipartFiles) {
        multipartFiles.forEach(file -> {
            String url = s3Manager.uploadFile(file);
            PostImage postImage = PostImage.builder()
                    .image(url)
                    .post(post)
                    .build();
            postImageRepository.save(postImage);
        });
    }

    @Transactional
    public void updateAll(Post post, List<MultipartFile> newPostImages) {
        deleteAllByPost(post);
        saveAll(post, newPostImages);
    }


    private void deleteAllByPost(Post post) {
        List<PostImage> existingPostImages = postImageRepository.findAllByPost(post);
        existingPostImages.forEach(postImage -> s3Manager.deleteFile(postImage.getImage()));
        postImageRepository.deleteAllByPost(post);
    }
}
