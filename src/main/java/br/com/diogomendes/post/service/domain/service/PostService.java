package br.com.diogomendes.post.service.domain.service;

import br.com.diogomendes.post.service.api.model.PostOutput;
import br.com.diogomendes.post.service.domain.model.Post;
import br.com.diogomendes.post.service.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void createPost(PostOutput output) {
        Post post = Post.builder()
                .id(output.getId())
                .title(output.getTitle())
                .body(output.getBody())
                .author(output.getAuthor())
                .wordCount(output.getWordCount())
                .calculatedValue(output.getCalculatedValue())
                .build();

        postRepository.save(post);
    }

}
