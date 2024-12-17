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
@Table(name="Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String communityName;
    private Long categoryId;

    private String title;

    @ManyToOne // 다대일 관계
    @JoinColumn(name = "customer_id") // 외래 키 설정
    private Customer customer;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long likeCount;
    private Long scrapCount;
    private Long viewCount;

//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference // 순환 참조 방지
//    private List<Comment> comments;


}
