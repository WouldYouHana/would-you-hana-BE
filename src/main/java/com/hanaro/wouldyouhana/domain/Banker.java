package com.hanaro.wouldyouhana.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Banker")
public class Banker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String branchName;

    @OneToMany(mappedBy = "id")
    private List<Comment> answers;

    @ManyToMany
    @JoinTable(
            name = "BankerSpecialization",
            joinColumns = @JoinColumn(name = "banker_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private List<Specialization> specializations;

    // Getters and Setters
}
