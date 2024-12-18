package com.hanaro.wouldyouhana.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Likes")
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")  // 게시글 ID
    private Post post;  // 좋아요가 눌린 게시글

    @ManyToOne
    @JoinColumn(name = "customer_id")  // 사용자 ID
    private Customer customer;  // 좋아요를 누른 사용자

    // Getters and Setters
}
