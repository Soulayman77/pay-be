package com.example.school.Controllers;

import com.example.school.Entities.CategorieService;
import com.example.school.Entities.Eleve;
import com.example.school.Repositories.CategorieServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/categorieService")
public class CategorieServiceController {
    @Autowired
    private CategorieServiceRepository categorieServiceRepository;
    @GetMapping(path = "/all")
    public List<CategorieService> getCategorieServices(){
        return categorieServiceRepository.findAll();
    }

    @GetMapping("/{categorieServiceId}")
    public Optional<CategorieService> getCategorieServiceById(@PathVariable Long categorieServiceId){
        return categorieServiceRepository.findById(categorieServiceId);
    }

    @PostMapping
    public CategorieService addCategorieService(@RequestBody CategorieService categorieService){
        return categorieServiceRepository.save(categorieService);
    }

    @PutMapping("/categorieServiceId")
    public void updateEleve(@RequestBody CategorieService categorieService, @PathVariable Long categorieServiceId){
        categorieServiceRepository.findById(categorieServiceId).map(categorieService1 -> {
            categorieService1.setNomCategorie(categorieService.getNomCategorie());
            return categorieServiceRepository.save(categorieService1);
        });
    }

    @DeleteMapping("/categorieServiceId")
    public void deleteCategorieService(@PathVariable Long categorieServiceId){
        categorieServiceRepository.deleteById(categorieServiceId);
    }
}
