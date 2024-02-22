package com.example.school.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Eleve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private String address;
    private String classs;
    private String phone;
    private LocalDate dateNaissance;

    public Eleve(Parent parent){
        this.parent =parent;
    }

    @ManyToOne
    @JoinColumn(name = "parentCIN")
    @ToString.Exclude
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Parent parent;

    @OneToMany(mappedBy = "eleve",fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<Paiement> paiements=new ArrayList<>();

//    @ManyToMany(mappedBy = "eleves", fetch = FetchType.EAGER)
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    List<Service> services=new ArrayList<>();

    @OneToMany(mappedBy = "eleve",fetch = FetchType.EAGER, cascade = CascadeType.ALL)

    private List<EtatService> etatServices = new ArrayList<>();


}
