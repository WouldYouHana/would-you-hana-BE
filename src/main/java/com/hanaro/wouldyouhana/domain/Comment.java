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
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonBackReference // 순환 참조 방지
    private Question question;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String content;

    @ManyToOne
    @JoinColumn(name="parent_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replies;

    private LocalDateTime createdAt;

    // Getters and Setters
}
