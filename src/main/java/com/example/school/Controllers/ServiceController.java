package com.example.school.Controllers;

import com.example.school.Entities.Service;
import com.example.school.Repositories.ServiceRepository;
import com.example.school.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private ServiceService serviceService;
    @GetMapping("/all")
    public List<Service> getServices(){
        return serviceRepository.findAll();
    }

    @GetMapping("/{serviceId}")
    public Optional<Service> getServiceById(@PathVariable Long serviceId){
        return serviceRepository.findById(serviceId);
    }

    @PostMapping("/add")
    public Service addService(@RequestBody Service service){
        return serviceService.addService(service);
    }

    @PutMapping("/serviceId")
    public void updateService(@RequestBody Service service, @PathVariable Long serviceId){
        serviceRepository.findById(serviceId).map(service1 -> {
            service1.setName(service.getName());
            service1.setPrice(service.getPrice());
            service1.setPereodicity(service.getPereodicity());
            service1.setDescription(service.getDescription());
            service1.setType(service.getType());
            service1.setStart(service.getStart());
            return serviceRepository.save(service1);
        });
    }

    @DeleteMapping("/serviceId")
    public void deleteService(@PathVariable Long serviceId){
        serviceRepository.deleteById(serviceId);
    }
}
