package pialeda.app.Invoice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pialeda.app.Invoice.dto.Login;
import pialeda.app.Invoice.service.JwtService;


@RestController
public class LoginController {

	@Autowired
	private JwtService jwtService;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("login", new Login());
        return "login";
    }
   @GetMapping("/loginUser")
    public String loginUser(@ModelAttribute("login") Login login, Model model) throws Exception {
        System.out.println(login.getEmail());
        System.out.println(login.getPassword());
        ResponseEntity<?> result = jwtService.createJwtToken(login);

        System.out.println(result);
        return "redirect:/admin-users";
    }

    @GetMapping("admin-dashboard")
    public String dashboard(){
        return "admin/dashboard";
    }
    
}
