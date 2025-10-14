package com.example.grace.services;

import com.example.grace.dto.PostDTO;
import com.example.grace.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();        // Retourne tous les posts
    List<PostDTO> getAllPostDTO();    // Retourne tous les posts sous forme de DTO
    List<PostDTO> getRecentPostDTOs(int limit);
    Page<PostDTO> getAllPostDTO(int page, int size);



}
