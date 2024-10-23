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
@Table(name = "specialization")
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long specializationId;

    private String name;

    @ManyToMany(mappedBy = "specializations")
    private List<Banker> bankers;

    @ManyToMany(mappedBy = "specializations")
    private List<Customer> customers;

    // Getters and Setters
}
