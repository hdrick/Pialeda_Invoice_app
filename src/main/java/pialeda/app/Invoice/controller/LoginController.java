package pialeda.app.Invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import pialeda.app.Invoice.config.JwtUtil;
import pialeda.app.Invoice.dto.Login;
import pialeda.app.Invoice.model.User;
import pialeda.app.Invoice.service.ClientService;
import pialeda.app.Invoice.service.InvoiceService;
import pialeda.app.Invoice.service.SupplierService;
import pialeda.app.Invoice.service.UserService;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private JwtUtil jwtUtil;

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

//    @GetMapping("admin-dashboard")
//    public String dashboard(Model model){
//        model.addAttribute("userCount", userService.getUserCount());
//        model.addAttribute("supplierCount", supplierService.getSupplierCount());
//        model.addAttribute("clientCount", clientService.getClientCount());
//        model.addAttribute("invoiceCount", invoiceService.getInvoiceCunt());
//        return "admin/dashboard";
//    }
    @PostMapping("/login/validation")
    public String loginUser(@ModelAttribute("login") Login login, Model model, HttpSession session) {
        Boolean user = userService.loadUserByEmail(login.getEmail(),login.getPassword());

        if(user == true)
        {
            User userDetails = userService.loadRoleByUser(login.getEmail());
            String destination=null;
            if(userDetails.getRole().equals("admin"))
            {
                String token = jwtUtil.generateToken(userDetails);
                session.setAttribute("token", token);

                String BearerToken = "Bearer "+token;
                HttpHeaders headers = new HttpHeaders();
                System.out.println(BearerToken);
                headers.set("Authorization", BearerToken);
                return destination ="redirect:/vr/user";
            }
            if (userDetails.getRole().equals("vr-staff"))
            {
                return destination = "redirect:vr/user";
            }
            if (userDetails.getRole().equals("marketing"))
            {
                return destination ="redirect:marketing-invoice";
            }


            return destination;
        }
        else
        {
            System.out.println(login.getEmail()+"error");
            System.out.println(login.getPassword()+"error");
            boolean hideSpan = true;
            model.addAttribute("hideSpan", hideSpan);
            model.addAttribute("error", "Your username or password is invalid.");
            return "login";
        }
    }
}
