package com.example.school.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Eleve {
    @Id
    private Long idEleve;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String niveau;
    private String adresse;

    @ManyToOne
    @JoinColumn(name = "parentCIN")
    private Parent parent;

    @OneToMany(mappedBy = "eleve",fetch = FetchType.EAGER)
    List<Paiement> paiements=new ArrayList<>();

    @ManyToMany(mappedBy = "eleves", fetch = FetchType.EAGER)
    List<Service> services=new ArrayList<>();
}
