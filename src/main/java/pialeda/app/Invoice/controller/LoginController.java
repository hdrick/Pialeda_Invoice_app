package pialeda.app.Invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import pialeda.app.Invoice.config.JwtTokenBlacklist;
import pialeda.app.Invoice.config.JwtUtil;
import pialeda.app.Invoice.dto.Login;
import pialeda.app.Invoice.model.User;
import pialeda.app.Invoice.service.ClientService;
import pialeda.app.Invoice.service.InvoiceService;
import pialeda.app.Invoice.service.SupplierService;
import pialeda.app.Invoice.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    JwtTokenBlacklist jwtTokenBlacklist;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("login", new Login());
        return "login";
    }

    @GetMapping("admin-dashboard")
    public String dashboard(Model model, HttpSession session){
        String token = (String) session.getAttribute("token");
        if (token != null)
        {
            String jwtToken = token.substring(7);
            // check if the token is in the blacklist
            if (jwtTokenBlacklist.isTokenInvalidated(token))
            {
                boolean hideSpan = true;
                model.addAttribute("hideSpan", hideSpan);
                model.addAttribute("error", "Invalid token");
                return "redirect:/login";
            }

            User userDetails = userService.loadUserByEmail(jwtUtil.extractEmail(token));
            if (jwtUtil.validateToken(token, userDetails))
            {
                session.setAttribute("name", userDetails.getLastName());
                session.setAttribute("role", userDetails.getRole());
                model.addAttribute("userCount", userService.getUserCount());
                model.addAttribute("supplierCount", supplierService.getSupplierCount());
                model.addAttribute("clientCount", clientService.getClientCount());
                model.addAttribute("invoiceCount", invoiceService.getInvoiceCunt());
                return "admin/dashboard";
            }
        }
        session.invalidate();
        return "redirect:/login";

    }
    @PostMapping("/login/auth")
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

                return destination ="redirect:/admin-dashboard";
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
            boolean hideSpan = true;
            model.addAttribute("hideSpan", hideSpan);
            model.addAttribute("error", "Your username or password is invalid.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        String token = (String) request.getAttribute("token");
        jwtTokenBlacklist.addInvalidatedToken(token);

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Clearing the authentication token
        SecurityContextHolder.clearContext();
        return "redirect:/login?logout";
    }

}
