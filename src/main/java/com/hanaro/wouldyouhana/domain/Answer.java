package com.hanaro.wouldyouhana.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="Answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 다대일 관계
    @JoinColumn(name = "banker_id") // 외래 키 설정
    private Banker banker;

    @OneToOne // 하나의 질문에 하나의 답변
    @JoinColumn(name = "question_id")  // 외래 키 설정
    private Question question;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Builder.Default
    private Long goodCount = 0L;

    // 도움 수 증가
    public void incrementGoodCount() {
        this.goodCount++;
    }
    // 도움 수 증가
    public void decrementGoodCount() {
        this.goodCount--;
    }
}
