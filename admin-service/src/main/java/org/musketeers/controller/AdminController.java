package org.musketeers.controller;


import lombok.RequiredArgsConstructor;
import org.musketeers.dto.request.AdminRegisterDto;
import org.musketeers.dto.request.AdminSupervisorRegistrationDecisionRequestDto;
import org.musketeers.dto.response.RegisteredSupervisorsResponseDTO;
import org.musketeers.entity.Admin;
import org.musketeers.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.musketeers.constant.Endpoint.*;

@RestController
@RequestMapping(ROOT + ADMIN )
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

    private final AdminService adminService;

    @GetMapping(GET_ALL)
    public ResponseEntity<List<Admin>> getAllAdmins(){
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @GetMapping(GET + "/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable String id){
        return ResponseEntity.ok((adminService.getAdminById(id)));
    }

    @PutMapping(UPDATE)
    public ResponseEntity<Admin> updateSupervisorById(@RequestBody Admin admin){
        return ResponseEntity.ok(adminService.update(admin));
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody AdminRegisterDto dto){
        return ResponseEntity.ok(adminService.register(dto));
    }
//
//    @DeleteMapping(DELETE + "/{id}")
//    public ResponseEntity<Boolean> softDeleteSupervisorById(@PathVariable String id) {
//        return ResponseEntity.ok(adminService.softDeleteSupervisorById(id));
//    }

//    @PutMapping(UPDATE + "/{id}")

    @GetMapping(GET_ALL_REGISTERED_SUPERVISORS)
    public ResponseEntity<List<RegisteredSupervisorsResponseDTO>> getAllRegisteredSupervisors(){
        String adminId = "5";
        return adminService.getAllRegisteredSupervisors(adminId);
    }

    @PostMapping(HANDLE_SUPERVISOR_REGISTRATION)
    public ResponseEntity<String> acceptSupervisorRegistration(@RequestBody AdminSupervisorRegistrationDecisionRequestDto dto){
        return ResponseEntity.ok(adminService.handleSupervisorRegistration(dto));
    }










}
