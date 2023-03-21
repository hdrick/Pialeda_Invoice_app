package pialeda.app.Invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
<<<<<<< HEAD
import pialeda.app.Invoice.dto.*;
import pialeda.app.Invoice.model.Invoice;
=======
import pialeda.app.Invoice.config.JwtUtil;
import pialeda.app.Invoice.dto.Login;
>>>>>>> feature/security
import pialeda.app.Invoice.model.User;
import pialeda.app.Invoice.service.ClientService;
import pialeda.app.Invoice.service.InvoiceService;
import pialeda.app.Invoice.service.SupplierService;
import pialeda.app.Invoice.service.UserService;

import javax.naming.AuthenticationException;
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

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("login", new Login());
        return "login";
    }

    @PostMapping("/loginUser")
    public String loginUser(@ModelAttribute("login") Login login, Model model) {
        Boolean user = userService.loadUserByEmail(login.getEmail(),login.getPassword());

        if(user == true){
            User userRole = userService.loadUser(login.getEmail());
            String destination=null;
            if(userRole.getRole().equals("admin")){
                return destination ="redirect:admin-dashboard";
            } else if (userRole.getRole().equals("vr-staff")) {
                return destination = "redirect:vr/user";
            } else if (userRole.getRole().equals("marketing")) {
                return destination ="redirect:marketing-invoice";
            }
            return destination;
        }else{
            System.out.println(login.getEmail()+"error");
            System.out.println(login.getPassword()+"error");
            boolean hideSpan = true;
            model.addAttribute("hideSpan", hideSpan);
            return "login";
        }
    }

    @GetMapping("admin-dashboard")
<<<<<<< HEAD
    public String dashboard(Model model){
        model.addAttribute("userCount", userService.getUserCount());
        model.addAttribute("supplierCount", supplierService.getSupplierCount());
        model.addAttribute("clientCount", clientService.getClientCount());
        model.addAttribute("invoiceCount", invoiceService.getInvoiceCunt());
        return "admin/dashboard";
    }

    @GetMapping("vr/user")
    public String invoices(Model model) {
        model.addAttribute("invoiceList", invoiceService.getAllInvoice());
        model.addAttribute("invoice", new Invoice());

        model.addAttribute("clientList", clientService.getAllClient());
        model.addAttribute("supplierList", supplierService.getAllSupplier());
        return "vr-staff/vr";
    }

    @GetMapping("marketing-invoice")
    public String users(Model model){
        model.addAttribute("clientList", clientService.getAllClient());
        model.addAttribute("supplierList", supplierService.getAllSupplier());

        model.addAttribute("clientInfo", new ClientInfo());
        model.addAttribute("supplierInfo", new SupplierInfo());

        InvoiceWrapper wrapper = new InvoiceWrapper();
        wrapper.setInvoiceInfo(new InvoiceInfo());
        wrapper.setInvoiceProdInfo(new InvoiceProdInfo());
        model.addAttribute("wrapper", wrapper);

        return "marketing/invoice";
    }

=======
    public String dashboard(Model model, HttpSession session){
        String token = (String) session.getAttribute("token");
        if (token != null)
        {
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
        // Invalidating the session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Clearing the authentication token
        SecurityContextHolder.clearContext();
        return "redirect:/login?logout";
    }

>>>>>>> feature/security
}
