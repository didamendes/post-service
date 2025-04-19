package br.com.diogomendes.post.service.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostOutput {
    private UUID id;
    private String title;
    private String body;
    private String author;
    private Integer wordCount;
    private Double calculatedValue;
}
