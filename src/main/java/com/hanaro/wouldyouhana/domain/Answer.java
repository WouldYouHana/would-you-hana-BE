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
@Table(name="Answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer answer_id;

    private Long banker_id;
    private Long question_id;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
