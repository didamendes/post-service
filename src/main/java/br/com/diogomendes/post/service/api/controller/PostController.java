package br.com.diogomendes.post.service.api.controller;

import br.com.diogomendes.post.service.api.model.PostInput;
import br.com.diogomendes.post.service.api.model.PostOutput;
import br.com.diogomendes.post.service.api.model.PostSummaryOutput;
import br.com.diogomendes.post.service.domain.model.Post;
import br.com.diogomendes.post.service.domain.repository.PostRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.UUID;

import static br.com.diogomendes.post.service.infraestructure.rabbitmq.RabbitMQConfig.FANOUT_EXCHANGE_NAME;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final RabbitTemplate rabbitTemplate;
    private final PostRepository postRepository;

    @PostMapping
    public PostOutput createPost(@Valid @RequestBody PostInput input) {
        PostOutput postOutput = PostOutput.builder()
                .id(UUID.randomUUID())
                .title(input.getTitle())
                .body(input.getBody())
                .author(input.getAuthor())
                .build();

        rabbitTemplate.convertAndSend(FANOUT_EXCHANGE_NAME, "novoPost", postOutput);

        return postOutput;
    }

    @GetMapping("/{id}")
    public PostOutput getPostById(@PathVariable UUID id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        return convert(post);
    }

    @GetMapping
    public Page<PostSummaryOutput> getAll(@PageableDefault Pageable pageable) {
        Page<Post> pages = postRepository.findAll(pageable);
        return pages.map(this::converterSumarry);
    }

    private PostSummaryOutput converterSumarry(Post post) {
        return PostSummaryOutput.builder()
                .id(post.getId())
                .title(post.getTitle())
                .summary(pegarTresLinas(post.getBody()))
                .build();
    }

    public static String pegarTresLinas(String texto) {
        String[] linhas = texto.split("\n");
        return String.join("\n", Arrays.copyOfRange(linhas, 0, Math.min(3, linhas.length)));
    }


    private PostOutput convert(Post post) {
        return PostOutput.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .author(post.getAuthor())
                .wordCount(post.getWordCount())
                .calculatedValue(post.getCalculatedValue())
                .build();
    }

}
