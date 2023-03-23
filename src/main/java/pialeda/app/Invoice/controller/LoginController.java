package pialeda.app.Invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import pialeda.app.Invoice.dto.*;
import pialeda.app.Invoice.model.Invoice;
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

}
