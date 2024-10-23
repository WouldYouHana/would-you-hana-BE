package com.hanaro.wouldyouhana.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Post")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer question_id;

    private Long customer_id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String title;
    private String content;
    private String location;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @OneToMany(mappedBy="question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;

    @OneToMany(mappedBy="question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes;

    @OneToMany(mappedBy="question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scrap> scraps;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;
}
