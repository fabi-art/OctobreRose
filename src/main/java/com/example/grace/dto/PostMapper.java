package com.example.grace.dto;

import com.example.grace.dto.PostDTO;
import com.example.grace.entities.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDTO toDTO(Post post) {
        PostDTO dto = new PostDTO(post);
        dto.setId(post.getId());
        dto.setContenuPost(post.getContenuPost());
        dto.setDatePost(post.getDatePost());
        dto.setUserPseudo(post.getUser() != null ? post.getUser().getPseudo() : null);
        return dto;
    }
}
