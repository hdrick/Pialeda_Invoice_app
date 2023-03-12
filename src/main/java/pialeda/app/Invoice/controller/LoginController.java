package pialeda.app.Invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import pialeda.app.Invoice.dto.Login;
import pialeda.app.Invoice.service.UserService;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("login", new Login());
        return "login";
    }
//    @PostMapping("/loginUser")
//    public String loginUser(@ModelAttribute("login") Login login, Model model) throws AuthenticationException {
//        System.out.println(login.getEmail());
//        System.out.println(login.getPassword());
//        String result = userService.authenticate(login.getEmail());
//
//        System.out.println(result);
//        return "redirect:/admin-users";
//    }

    @GetMapping("admin-dashboard")
    public String dashboard(){
        return "admin/dashboard";
    }
    @GetMapping("/login/credential-validation")
    @ResponseBody
    public ResponseEntity<?> loginValidation(HttpSession session, @RequestParam("email") String email, @RequestParam("pass") String password)
    {
        String msg = userService.accountValidation(email, password, session);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping("/login/success")
    public String home(HttpSession session) {
        if (session != null) {
            // do something with the session
            // ...
            return "homepage";
        } else {
            // redirect to the login page
            return "redirect:/login";
        }
    }
}
