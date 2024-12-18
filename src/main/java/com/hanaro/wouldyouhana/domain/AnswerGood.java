package com.hanaro.wouldyouhana.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "AnswerGood")
public class AnswerGood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "answer_id")  // 게시글 ID
    private Answer answer;  // 도움돼요가 눌린 게시글

    @ManyToOne
    @JoinColumn(name = "customer_id")  // 사용자 ID
    private Customer customer;  // 도움돼요를 누른 사용자

}
