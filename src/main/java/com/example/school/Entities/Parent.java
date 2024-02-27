package com.example.school.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parent {
    @Id
    private String cinParent;
    private String nom;
    private String prenom;
    private String email;
    private String phoneNumber;
    private String adress;
    private LocalDate dateNaissance;
    private String photo;

    @OneToMany(mappedBy = "parent")
    List<Eleve> eleves =new ArrayList<>();
}
