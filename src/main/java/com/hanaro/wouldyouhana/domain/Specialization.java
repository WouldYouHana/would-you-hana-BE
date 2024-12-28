package com.hanaro.wouldyouhana.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "specialization")
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "specializations")
    @JsonIgnore
    private List<Banker> bankers;

    @ManyToMany(mappedBy = "specializations")
    @JsonIgnore
    private List<Customer> customers;

}
