package com.hanaro.wouldyouhana.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private String title;
    @ManyToOne // 다대일 관계 설정
    @JoinColumn(name = "category_id", nullable = false) // 외래 키 설정
    private Category category; // Category 객체 추가
    private String location;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder.Default
    private Long likeCount = 0L;
    @Builder.Default
    private Long scrapCount = 0L;
    @Builder.Default
    private Long viewCount = 0L;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // 순환 참조 방지
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

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

    public void addComments(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComments(Comment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }

}
