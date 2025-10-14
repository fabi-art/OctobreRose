package com.example.grace.services;

import com.example.grace.entities.Post;
import com.example.grace.repositories.PostRepository;
import com.example.grace.dto.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<PostDTO> getAllPostDTO() {
        // Transforme chaque Post en PostDTO
        return postRepository.findAll().stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getRecentPostDTOs(int limit) {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "datePost"))
                .stream()
                .limit(limit)
                .map(PostDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PostDTO> getAllPostDTO(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("datePost").descending());
        return postRepository.findAll(pageable)
                .map(PostDTO::new);
    }

}
