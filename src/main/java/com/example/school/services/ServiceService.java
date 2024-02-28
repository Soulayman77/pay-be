package com.example.school.services;

import com.example.school.Entities.Service;
import com.example.school.Entities.ServiceType;
import com.example.school.Repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.stereotype.Service
public class ServiceService {
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    EleveService eleveService;
    public Service addService(Service service){
        serviceRepository.save(service);
        if (service.getType() == ServiceType.OBLIGATORY) {
            eleveService.getAllStudents().forEach(p->{
                eleveService.addServiceToEleve(p,service);
            });
        }
        return service;
    }
}
