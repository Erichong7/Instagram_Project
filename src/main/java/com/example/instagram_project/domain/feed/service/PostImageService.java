package com.example.instagram_project.domain.feed.service;

import com.example.instagram_project.S3Manager;
import com.example.instagram_project.domain.feed.entity.Post;
import com.example.instagram_project.domain.feed.entity.PostImage;
import com.example.instagram_project.domain.feed.repository.PostImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final PostImageRepository postImageRepository;
    private final S3Manager s3Manager;

    @Transactional
    public List<PostImage> saveAll(Post post, List<MultipartFile> multipartFiles) {

        List<PostImage> postImages = new ArrayList<>();

        for (int i = 0; i < multipartFiles.size(); i++) {
            MultipartFile file = multipartFiles.get(i);
            String url = s3Manager.uploadFile(file);

            PostImage postImage = PostImage.builder()
                    .image(url)
                    .post(post)
                    .build();

            postImages.add(postImage);
            postImageRepository.save(postImage);
        }

       return postImages;
    }

}
