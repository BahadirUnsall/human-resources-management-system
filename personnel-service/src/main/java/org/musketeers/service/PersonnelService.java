package org.musketeers.service;

import org.musketeers.entity.Personnel;
import org.musketeers.repository.PersonnelRepository;
import org.musketeers.utility.JwtTokenManager;
import org.musketeers.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonnelService extends ServiceManager<Personnel, String> {

    private final PersonnelRepository personnelRepository;

    private final JwtTokenManager jwtTokenManager;

    public PersonnelService(PersonnelRepository personnelRepository, JwtTokenManager jwtTokenManager) {
        super(personnelRepository);
        this.personnelRepository = personnelRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    //rabbitmq consumer ile yapılacak...
//    public Personnel register(Personnel personnel) {
//        return save(personnel);
//    }

    public Personnel getPersonnelById(String id) {
//        jwtTokenManager.getClaimsFromToken(id).get(0);
        return findById(id);
    }

    public List<Personnel> getAllPersonnel() {
        return findAll();
    }

    public Boolean softDeletePersonnelById(String id) {
        return softDeleteById(id);
    }
}
