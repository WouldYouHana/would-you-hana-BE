package com.hanaro.wouldyouhana.domain;

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
@Table(name = "Question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long question_id;

    private Long customer_id;
    private Long category_id;

    private String title;
    private String content;
    private String location;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id")  // 외래 키 설정
    private List<Answer> answers;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id")  // 외래 키 설정
    private List<Likes> likes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id")  // 외래 키 설정
    private List<Scrap> scraps;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id")  // 외래 키 설정
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id")  // 외래 키 설정
    private List<Image> images;
}
