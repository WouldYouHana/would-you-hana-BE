package com.hanaro.wouldyouhana.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 고객id
    private Integer customer_id;

    // 이메일
    @Column
    private String email;

    // 이름
    @Column
    private String name;

    // 닉네임
    @Column
    private String nickname;

    // 비밀번호
    @Column
    private String password;

    // 휴대전화
    @Column
    private String phone;

    // 주소
    @Column
    private String location;

    // 경험치
    @Column
    private Integer experience_points;

    // 가입일자
    @CreatedDate
    @Column
    private LocalDateTime created_at;

    // 성별
    @Column
    private String gender;

    // 생년월일
    @Column
    private String birth_date;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Question> questions;

    @ManyToMany
    @JoinTable(
            name = "CustomerSpecialization",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private List<Specialization> specializations;


}
