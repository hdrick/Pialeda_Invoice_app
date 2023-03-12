package pialeda.app.Invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pialeda.app.Invoice.dto.Login;
import pialeda.app.Invoice.service.SupplierService;
import pialeda.app.Invoice.service.UserService;

import javax.naming.AuthenticationException;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("login", new Login());
        return "login";
    }
    @PostMapping("/loginUser")
    public String loginUser(@ModelAttribute("login") Login login, Model model) throws AuthenticationException {
        System.out.println(login.getEmail());
        System.out.println(login.getPassword());
        String result = userService.authenticate(login.getEmail());

        System.out.println(result);
        return "redirect:/admin-users";
    }

    @GetMapping("admin-dashboard")
    public String dashboard(Model model){
        model.addAttribute("userCount", userService.getUserCount());
        model.addAttribute("supplierCount", supplierService.getSupplierCount());
        return "admin/dashboard";
    }
}
