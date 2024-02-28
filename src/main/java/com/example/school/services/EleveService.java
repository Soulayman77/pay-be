package com.example.school.services;

import com.example.school.Entities.Eleve;
import com.example.school.Entities.EtatService;
import com.example.school.Entities.ServiceType;
import com.example.school.Repositories.EleveRepository;
import com.example.school.Repositories.EtatServiceRepository;
import com.example.school.Repositories.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EleveService {
    private EleveRepository eleveRepository;
    private EtatServiceRepository etatServiceRepository;
    private ServiceRepository serviceRepository;


    public Eleve addEleve(Eleve eleve) {
        LocalDate date = eleve.getDateNaissance();
//
        List<com.example.school.Entities.Service> obligatoryServices = serviceRepository.findAll().stream()
                .filter(service -> ServiceType.OBLIGATORY.equals(service.getType()))
                .collect(Collectors.toList());
        obligatoryServices.forEach(p->{
            EtatService etatService = new EtatService();
            etatService.setService(p);
        eleve.setDateNaissance(date);
        eleveRepository.save(eleve);
            etatService.setEleve(eleve);
            etatServiceRepository.save(etatService);
        });
        return eleve;
    }
//    public Eleve association(Eleve eleve){
//
//        etatServiceRepository.findEtatServicesByServiceType(ServiceType.OBLICATORY).forEach((p) -> {
//            eleve.getEtatServices().add(p);
//        });
//        return eleve;
//    }

    public List<Eleve> getAllStudents() {
        return eleveRepository.findAll();
    }

    public Eleve getStudentById(Long id) {
        return eleveRepository.findById(id).get();
    }
    public List<Eleve> getElevesByParentId(String id) {
        return eleveRepository.findElevesByParentCIN(id);
    }

    public void updateEleve(Eleve eleve, Long id ) {
        eleveRepository.findById(id).map(eleve1 -> {
            eleve1.setFirstName(eleve.getFirstName());
            eleve1.setLastName(eleve.getLastName());
//            eleve1.setDateNaissance(eleve.getDateNaissance());
            return eleveRepository.save(eleve1);
        });
    }

    public void deleteStudentById(Long id) {
        eleveRepository.deleteById(id);
    }

    public void addServiceToEleve(Eleve eleve, com.example.school.Entities.Service service) {
        EtatService etatService = new EtatService();
        etatService.setService(service);
        etatService.setEleve(eleve);
        etatServiceRepository.save(etatService);
    }

}
