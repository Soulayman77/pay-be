package com.example.school;

import com.example.school.Entities.*;
import com.example.school.Repositories.*;
import com.example.school.services.EleveService;
import com.example.school.services.ParentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.Paths;

@SpringBootApplication
public class SchoolApplication {


    public static void main(String[] args) {

        SpringApplication.run(SchoolApplication.class, args);
        String currentDirectory = Paths.get("").toAbsolutePath().toString();

    }

   @Bean
    CommandLineRunner coucou(

           EleveRepository eleveRepository,
           PaiementRepository paiementRepository,
           ServiceRepository serviceRepository,
           FactureRepository factureRepository,
           RemiseRepository remiseRepository,
           CategorieServiceRepository categorieServiceRepository,
           EtatServiceRepository etatServiceRepository,
           EleveService eleveService,
           ParentService parentService

   ){
        return args -> {
            Parent parent = new Parent();
            Parent parent2 = new Parent();
            parent.setCIN("PA238948");
            parent2.setCIN("PA238949");
            parent.setFirstName("TTo");
            parent2.setFirstName("Ziyad");
            parent.setLastName("KHOKI");
            parent2.setLastName("KHAWAKHI");
            parentService.saveParent(parent);
            parentService.saveParent(parent2);
//
            CategorieService categorieService = new CategorieService();
            CategorieService categorieService2 = new CategorieService();
            categorieService.setNomCategorie("Ext");
            categorieServiceRepository.save(categorieService);
            categorieServiceRepository.save(categorieService2);


            Service natation = new Service();
            natation.setPrice(23);
            natation.setName("natation");
            natation.setType(ServiceType.OBLICATORY);
            System.out.println(natation.getType());
            serviceRepository.save(natation);
//

            Eleve eleve = new Eleve();
            eleve.setFirstName("Tati");
            eleve.setLastName("TATITO");
            eleve.setParent(parent);

//
            Eleve eleve2 = new Eleve();
            Eleve eleve3 = new Eleve();
            eleve2.setFirstName("TADFj");
            eleve2.setLastName("KIKI");
            eleve2.setParent(parent);
            eleve3.setLastName("AIT AHMID");
            eleve3.setFirstName("Tarik");
            eleve3.setParent(parent2);
            eleveService.addEleve(eleve3);
            eleveService.addEleve(eleve2);
            eleveService.addEleve(eleve);


            Paiement paiement = new Paiement();
            paiement.setMontant(23.3);
            Paiement paiement2 = new Paiement();
            paiement.setMontant(23458.3);
            paiementRepository.save(paiement);
            paiementRepository.save(paiement2);
//


            Facture facture = new Facture();
            facture.setMontant(232);
            factureRepository.save(facture);
            paiement.setFacture(facture);
            paiement2.setFacture(facture);
            paiementRepository.save(paiement);
            paiementRepository.save(paiement2);

            Remise discount = new Remise();
            discount.setPercentage(4.2);
            discount.setNameDiscount("Lilah");
            Remise discount2 = new Remise();
            discount2.setNameDiscount("Aid Adha");
            discount2.setConditionn("Homme endicap");
            discount2.setPercentage(5.2);
            remiseRepository.save(discount);
            remiseRepository.save(discount2);
            serviceRepository.save(natation);

            EtatService etatService = new EtatService();
            EtatService etatService2 = new EtatService();
            etatService.setService(natation);
            etatService.setEleve(eleve2);
            etatService.setPayer(false);

            etatService2.setService(natation);
            etatService2.setEleve(eleve2);
            etatServiceRepository.save(etatService);
            etatServiceRepository.save(etatService2);



//            System.out.println(eleveService.getElevesByParentId("PA238948"));
//            System.out.println("$$$$$$$$");
//            parentRepository.findAll().forEach(p->{
//                System.out.println(p);
//            });
//            System.out.println("After");
//            parentService.deleteParentById("PA238948");
//            parentRepository.findAll().forEach(p->{
//                System.out.println(p);
//            });


//            etatServiceRepository.findEtatServicesByPayer(false).forEach(p->{
//                System.out.println(p);
//
//            });
            etatServiceRepository.findAll().forEach(p->{
                System.out.println(p);
            });


            remiseRepository.findAll().forEach(p->{
                System.out.println(p);
            });
            factureRepository.findAll().forEach(p->{
                System.out.println(p);
            });

            serviceRepository.findAll().forEach(p->{
                System.out.println(p);
            });


            paiementRepository.findAll().forEach(p->{
                System.out.println(p);
            });
//

//            eleveRepository.findAll().forEach(p->{
//                System.out.println(p);
//            });
// parentRepository.findAll().forEach(p->{
//                System.out.println(p);
//            });
//

        };
   }
//
//
}
