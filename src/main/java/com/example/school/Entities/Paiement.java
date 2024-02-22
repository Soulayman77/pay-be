package com.example.school.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaiement;
    private LocalDate dateTransaction;
    @Column(nullable = false)
    private double montant;
//    public Paiement(Eleve eleve){
//        this.eleve = eleve;
//    }

    @ToString.Exclude
    @ManyToOne
//    @JoinColumn(name = "eleveId", nullable = false)
    private Eleve eleve;

    @ManyToOne
    @JoinColumn(name = "factureId")

    private Facture facture;
}
