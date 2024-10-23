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
    private Long id;

    private Long customer_id;
    private Long category_id;

    private String title;
    private String content;
    private String location;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Integer likeCount;
    private Integer scrapCount;
    private Integer viewCount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "question_id")  // 외래 키 설정
    private List<Answer> answers;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "question_id")  // 외래 키 설정
    private List<Comment> comments;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "question_id")  // 외래 키 설정
    private List<Image> images;
}
