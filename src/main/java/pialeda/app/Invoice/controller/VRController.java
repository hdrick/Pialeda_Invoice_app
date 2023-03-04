package pialeda.app.Invoice.controller;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pialeda.app.Invoice.model.User;
import pialeda.app.Invoice.service.VRService;

@RestController
public class VRController {
	
	@Autowired
	VRService vrService;

    @GetMapping("vr-users")
    @PreAuthorize("hasAnyRole('vr-staff', 'Admin')")
    public String vr(){
        return "vr-staff/vr";
    }
    
    @PostMapping({"/CreateVRAccount"})
    @PreAuthorize("hasRole('Admin')")
    public User registerNewUser(@Valid @RequestBody User user) {
        return vrService.registerNewVR(user);
    }
    
    
    
    
}
