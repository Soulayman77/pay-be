package com.example.school.Controllers;

import com.example.school.Entities.Eleve;
import com.example.school.Entities.EtatService;
import com.example.school.Entities.Service;
import com.example.school.Repositories.EleveRepository;
import com.example.school.services.EleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/student")
public class EleveController {

    @Autowired
    private EleveRepository eleveRepository;
    @Autowired
    private EleveService eleveService;

    @GetMapping("/all")
    public List<Eleve> getEleves(){
        return eleveRepository.findAll();
    }

    @GetMapping("/{eleveId}")
    public Optional<Eleve> getEleveById(@PathVariable Long eleveId){
        return eleveRepository.findById(eleveId);
    }

    @PostMapping("/add")
    public Eleve addEleve(@RequestBody Eleve eleve){


        return eleveService.addEleve(eleve);
    }

    @PutMapping("/eleveId")
    public void updateEleve(@RequestBody Eleve eleve, @PathVariable Long eleveId){
        eleveRepository.findById(eleveId).map(eleve1 -> {
            eleve1.setFirstName(eleve.getFirstName());
            eleve1.setLastName(eleve.getLastName());
//            eleve1.setDateNaissance(eleve.getDateNaissance());
            return eleveRepository.save(eleve1);
        });
    }

    @DeleteMapping("/{eleveId}")
    public void deleteEleve(@PathVariable Long eleveId){
        eleveRepository.deleteById(eleveId);
    }




    @GetMapping("/parentid/{id}")
    public List<Eleve> getElevesByIdParent(@PathVariable String id){
        return eleveService.getElevesByParentId(id);
    }
}
