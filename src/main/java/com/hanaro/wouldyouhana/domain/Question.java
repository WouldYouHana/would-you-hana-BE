package com.hanaro.wouldyouhana.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name="customer_id")
    private Long customerId;

    @ManyToOne // 다대일 관계 설정
    @JoinColumn(name = "category_id", nullable = false) // 외래 키 설정
    private Category category; // Category 객체 추가

    private String title;
    private String content;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long likeCount;
    private Long scrapCount;
    private Long viewCount;

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "question_id")  // 외래 키 설정
    private Answer answers;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // 순환 참조 방지
    //@JoinColumn(name = "question_id")  // 외래 키 설정
    private List<Comment> comments;

    //@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "question_id")  // 외래 키 설정
    private List<String> filePaths;

    // 조회수 증가
    public void incrementViewCount() {
        this.viewCount++;
    }

    // 좋아요 수 증가
    public void incrementLikesCount() {
        this.likeCount++;
    }
    // 좋아요 수 감소
    public void decrementLikesCount() {
        this.likeCount--;
    }

    // 스크랩 수 증가
    public void incrementScrapCount() {
        this.scrapCount++;
    }
    // 스크랩 수 감소
    public void decrementScrapCount() {
        this.scrapCount--;
    }

//    public void addAnswers(Answer answer) {
//        answers.add(answer);
//    }
//
//    public void removeAnswers(Answer answer) {
//        answers.remove(answer);
//    }

    public void addComments(Comment comment) {
        comments.add(comment);
        comment.setQuestion(this);
    }

    public void removeComments(Comment comment) {
        comments.remove(comment);
        comment.setQuestion(null);
    }
}
