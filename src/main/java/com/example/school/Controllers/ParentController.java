package com.example.school.Controllers;

//import com.example.school.Entities.Eleve;
import com.example.school.Entities.Parent;
import com.example.school.Repositories.EleveRepository;
import com.example.school.Repositories.ParentRepository;
import com.example.school.services.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
@RequestMapping("/parent")
public class ParentController {

    @Autowired
    private ParentService parentService;
    @GetMapping("/all")
    public List<Parent> getParents(){

        return parentService.getAllParent();
    }

    @GetMapping("/{parentId}")
    public Parent getParentById(@PathVariable String parentId){
        return parentService.getParentById(parentId);
    }

    @PostMapping("/add")
    public Parent addParent(@RequestBody Parent parent){
        return parentService.saveParent(parent);
    }

    @PutMapping("/parentId")
    public void updateParent(@RequestBody Parent parent, @PathVariable String parentId){
          parentService.updateParent(parent,parentId);
    }

    @DeleteMapping("/{id}")
    public void deleteParent(@PathVariable String id){
        parentService.deleteParentById(id);
    }
}
