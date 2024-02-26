package com.example.school.services;

import com.example.school.Entities.Parent;
import com.example.school.Repositories.EleveRepository;
import com.example.school.Repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class ParentService {
    @Autowired
    public ParentRepository parentRepository;
    @Autowired
    public EleveService eleveService;
     public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/webapp/images";
    @Autowired
    private EleveRepository eleveRepository;

    public List<Parent> getAllParent() {
        return parentRepository.findAll();
    }
    public Parent saveParent( Parent parent) {
        return parentRepository.save(parent);
    }

    public void updateParent(Parent parent,String parentId) {
        parentRepository.findById(parentId).map(parent1 -> {
            parent1.setCIN(parent.getCIN());
            parent1.setLastName(parent.getLastName());
            parent1.setFirstName(parent.getFirstName());
            parent1.setAddress(parent.getAddress());
            parent1.setEmail(parent.getEmail());
            parent1.setDateInscription(parent.getDateInscription());
            parent1.setImage(parent.getImage());

            return parentRepository.save(parent1);
    });
    }


    public Parent getParentById(String id) {
        return parentRepository.findById(id).get();
    }

    public void deleteParentById(String CID) {
        eleveService.getElevesByParentId(CID).forEach(p->{
            eleveRepository.delete(p);
//            System.out.println(p);
        });
        parentRepository.deleteById(CID);
    }



}
