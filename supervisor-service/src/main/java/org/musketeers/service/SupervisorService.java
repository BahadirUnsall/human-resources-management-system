package org.musketeers.service;

import org.musketeers.entity.Supervisor;
import org.musketeers.entity.enums.ActivationStatus;
import org.musketeers.exception.ErrorType;
import org.musketeers.exception.SupervisorServiceException;
import org.musketeers.rabbitmq.model.CreateCompanyRequestModel;
import org.musketeers.rabbitmq.model.CreateCompanyResponseModel;
import org.musketeers.rabbitmq.model.CreatePersonnelFromSupervisorModel;
import org.musketeers.rabbitmq.model.GetCompanySupervisorResponseModel;
import org.musketeers.rabbitmq.producer.CreateCompanyProducer;
import org.musketeers.rabbitmq.producer.CreatePersonnelFromSupervisorProducer;
import org.musketeers.repository.SupervisorRepository;
import org.musketeers.utility.JwtTokenManager;
import org.musketeers.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupervisorService extends ServiceManager<Supervisor, String> {

    private final SupervisorRepository supervisorRepository;

    private final JwtTokenManager jwtTokenManager;

    private final CreateCompanyProducer createCompanyProducer;

    private final CreatePersonnelFromSupervisorProducer createPersonnelFromSupervisorProducer;

    public SupervisorService(SupervisorRepository supervisorRepository, JwtTokenManager jwtTokenManager, CreateCompanyProducer createCompanyProducer, CreatePersonnelFromSupervisorProducer createPersonnelFromSupervisorProducer) {
        super(supervisorRepository);
        this.supervisorRepository = supervisorRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.createCompanyProducer = createCompanyProducer;
        this.createPersonnelFromSupervisorProducer = createPersonnelFromSupervisorProducer;
    }

    public Supervisor register(Supervisor supervisor) {
        return save(supervisor);
    }

    public Supervisor getSupervisorById(String id) {
//        jwtTokenManager.getClaimsFromToken(id).get(0)
        return findById(id);
    }

    public Supervisor getSupervisorByAuthId(String authId) {
        return supervisorRepository.findOptionalByAuthId(authId).orElseThrow(() -> new SupervisorServiceException(ErrorType.SUPERVISOR_NOT_FOUND));
    }

    public List<Supervisor> getAllSupervisors() {
        return findAll();
    }

    public Boolean softDeleteSupervisorById(String id) {
        return softDeleteById(id);
    }

    public void activate(Supervisor supervisor) {
        CreateCompanyRequestModel createCompanyRequestModel = CreateCompanyRequestModel.builder().supervisorId(supervisor.getId()).companyName(supervisor.getCompanyName()).build();
        CreateCompanyResponseModel createCompanyResponseModel = createCompanyProducer.createCompanyAndReturn(createCompanyRequestModel);
        supervisor.setCompanyId(createCompanyResponseModel.getCompanyId());
        // belki supervisorda companyName fieldı silinebilir artık ??? supervisor.setCompanyName(null);
        supervisor.setActivationStatus(ActivationStatus.ACTIVATED);
        update(supervisor);
        preparePersonnelModelFromSupervisor(supervisor);
    }

    private void preparePersonnelModelFromSupervisor(Supervisor supervisor){
        CreatePersonnelFromSupervisorModel model = CreatePersonnelFromSupervisorModel.builder()
                .authId(supervisor.getAuthId())
                .name(supervisor.getName())
                .lastName(supervisor.getLastName())
                .gender(supervisor.getGender().toString())
                .identityNumber(supervisor.getIdentityNumber())
                .email(supervisor.getEmail())
                .image(supervisor.getImage())
                .address(supervisor.getAddresses().get(0))
                .phone(supervisor.getPhones().get(0).getPhoneNumber())
                .companyId(supervisor.getCompanyId())
                .dateOfBirth(supervisor.getDateOfBirth())
                .build();
        createPersonnelFromSupervisorProducer.createPersonnelFromSupervisor(model);
    }

    public List<GetCompanySupervisorResponseModel> getSupervisorByIds(List<String> supervisorIds) {
        List<Supervisor> supervisors = supervisorRepository.findAllById(supervisorIds);
        return supervisors.stream().map(supervisor -> GetCompanySupervisorResponseModel.builder()
                .name(supervisor.getName())
                .lastName(supervisor.getLastName())
                .gender(supervisor.getGender().toString())
                .image(supervisor.getImage())
                .dateOfBirth(supervisor.getDateOfBirth().toString())
                .build()).toList();
    }
}
