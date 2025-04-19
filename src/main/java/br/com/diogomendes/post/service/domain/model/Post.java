package br.com.diogomendes.post.service.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @Id
    private UUID id;
    private String title;
    private String body;
    private String author;
    private Integer wordCount;
    private Double calculatedValue;
}
