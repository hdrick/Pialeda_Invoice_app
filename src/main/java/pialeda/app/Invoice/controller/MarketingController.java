package pialeda.app.Invoice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pialeda.app.Invoice.model.User;
import pialeda.app.Invoice.service.MarketingService;

@RestController
public class MarketingController {
	
	@Autowired
	MarketingService marketingService;
	
    @GetMapping("marketing-invoice")
    @PreAuthorize("hasAnyRole('marketing', 'Admin')")
    public String users(Model model){

        return "marketing/invoice";
    }
    
    @PostMapping({"/CreateMarketingAccount"})
    @PreAuthorize("hasRole('Admin')")
    public User registerNewUser(@Valid @RequestBody User user) {
        return marketingService.registerNewMarketing(user);
    }
}
