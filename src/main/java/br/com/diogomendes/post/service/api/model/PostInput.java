package br.com.diogomendes.post.service.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostInput {

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    @NotBlank
    private String author;
}
