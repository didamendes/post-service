package br.com.diogomendes.post.service.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostSummaryOutput {
    private UUID id;
    private String title;
    private String summary;
}
