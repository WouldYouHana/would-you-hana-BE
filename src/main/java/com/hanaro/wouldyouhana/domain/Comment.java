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
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comment_id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String content;

    @OneToMany(mappedBy = "comment")
    private List<ReplyComment> replies;

    private LocalDateTime created_at;

    // Getters and Setters
}
