package pialeda.app.Invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pialeda.app.Invoice.dto.*;
import pialeda.app.Invoice.model.Invoice;
import pialeda.app.Invoice.model.User;
import pialeda.app.Invoice.service.ClientService;
import pialeda.app.Invoice.service.InvoiceService;
import pialeda.app.Invoice.service.SupplierService;
import pialeda.app.Invoice.service.UserService;
import pialeda.app.Invoice.dto.GlobalUser;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;


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
        String role = GlobalUser.getUserRole();
        String destination=null;
        if (role == null){
            model.addAttribute("login", new Login());
            return "/login";
        }else if(role.equals("marketing")){
            return destination = "redirect:/marketing-invoice";
        } else if (role.equals("vr-staff")) {
            return destination = "redirect:/vr/user/invoices";
        } else if (role.equals("admin")) {
            return destination = "redirect:/admin-dashboard";
        }
        return destination;
    }

    @PostMapping("/loginUser")
    public String loginUser(@ModelAttribute("login") Login login, Model model) {
        Boolean user = userService.loadUserByEmail(login.getEmail(),login.getPassword());

        if(user == true){
            String destination=null;
            User userDb = userService.loadUser(login.getEmail());
            String userEmail = GlobalUser.setUserEmail(userDb.getEmail());
            String userROle = GlobalUser.setUserRole(userDb.getRole());
            String userFname = GlobalUser.setUserFirstName(userDb.getFirstName());
            String userLname = GlobalUser.setUserLastName(userDb.getLastName());
            String fullName = userFname+" "+userLname;
            System.out.println(userEmail);
            System.out.println(userROle);
            System.out.println(fullName);

            if(userROle.equals("admin")){

//                redirectAttrs.addAttribute("fullName", fullName);
                return destination ="redirect:/admin-dashboard";
            } else if (userROle.equals("vr-staff")) {

                return destination = "redirect:/vr/user/invoices";
            } else if (userROle.equals("marketing")) {

                return destination ="redirect:/marketing-invoice";
            }
            return "destination";
//            return "login";
        }else{
            boolean hideSpan = true;
            model.addAttribute("hideSpan", hideSpan);
            return "login";
        }
    }


    //Admin CONTROLLER
    @GetMapping("admin-dashboard")
    public String dashboard(Model model){
        String role = GlobalUser.getUserRole();
        String userFname = GlobalUser.getUserFirstName();
        String userLname = GlobalUser.getUserLastName();
        String fullName = userLname+", "+userFname;
        String destination=null;


        System.out.println("FIRSTTTTTT");
        if(role == null){
            return destination = "redirect:/login";
        }
        else if (role.equals("marketing")) {
            return destination = "redirect:/marketing-invoice";
        }
        else if (role.equals("vr-staff")) {

            return destination = "redirect:/vr/user/invoices";
        }
        else if (role.equals("admin")) {
            model.addAttribute("userCount", userService.getUserCount());
            model.addAttribute("supplierCount", supplierService.getSupplierCount());
            model.addAttribute("clientCount", clientService.getClientCount());
            model.addAttribute("invoiceCount", invoiceService.getInvoiceCunt());
            model.addAttribute("fullName",fullName);
            return destination = "admin/dashboard";
        }
        return destination;
    }


    //VR CONTROLLER
    @GetMapping("vr/user/invoices")
    public String getAllPages(Model model) {
        String role = GlobalUser.getUserRole();
        String userFname = GlobalUser.getUserFirstName();
        String userLname = GlobalUser.getUserLastName();
        String fullName = userLname+", "+userFname;
        String destination=null;

        if(role == null){
            return "redirect:/login";
        } else if (role.equals("admin")) {
            return destination = "redirect:/admin-dashboard";
        }
        else if (role.equals("marketing")) {
            return destination = "redirect:/marketing-invoice";
        } else if (role.equals("vr-staff")) {
            model.addAttribute("invoiceList", invoiceService.getAllInvoice());
            model.addAttribute("invoice", new Invoice());

            model.addAttribute("clientList", clientService.getAllClient());
            model.addAttribute("supplierList", supplierService.getAllSupplier());
            model.addAttribute("fullName",fullName);
            return getOnePage(model, 1);
        }
        return destination;
    }

    @GetMapping("vr/user/invoices/{pageNumbers}")
    public String getOnePage(Model model, @PathVariable("pageNumbers") int currentPage)
    {
        Page<Invoice> page = invoiceService.findPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<Invoice> invoices = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("invoices", invoices);

        return "vr-staff/vr";
    }
    //MARKETING CONTROLLER
    @GetMapping("marketing-invoice")
    public String users(Model model){
        String role = GlobalUser.getUserRole();
        String userFname = GlobalUser.getUserFirstName();
        String userLname = GlobalUser.getUserLastName();
        String fullName = userLname+", "+userFname;
        String destination=null;


        System.out.println("FIRSTTTTTT");
        if(role == null){
            return destination = "redirect:/login";
        }
        else if (role.equals("marketing")) {
            model.addAttribute("clientList", clientService.getAllClient());
            model.addAttribute("supplierList", supplierService.getAllSupplier());

            model.addAttribute("clientInfo", new ClientInfo());
            model.addAttribute("supplierInfo", new SupplierInfo());

            InvoiceWrapper wrapper = new InvoiceWrapper();
            wrapper.setInvoiceInfo(new InvoiceInfo());
            wrapper.setInvoiceProdInfo(new InvoiceProdInfo());
            model.addAttribute("wrapper", wrapper);
            model.addAttribute("fullName",fullName);
            return destination = "marketing/invoice";
        }
        else if (role.equals("vr-staff")) {
            return destination = "redirect:/vr/user/invoices";
        }
        else if (role.equals("admin")) {
            return destination = "redirect:admin-dashboard";
        }
        return destination;
    }
    @GetMapping("unauthorized")
    public String unauthorizedUser(){
            return "Unauthorized-Access";
    }


    @GetMapping("logout")
    public String logout(){
        String role = GlobalUser.getUserRole();


        System.out.println("USER ROLE BEFORE LOGOUT: "+role);
        String newRole = GlobalUser.setUserRole(null);
        System.out.println("USER ROLE after LOGOUT: "+newRole);
        return "redirect:/login";
    }



        private SecureRandom random = new SecureRandom();
        public String generateToken() {
            // Generate a random token
            String token = new BigInteger(200, random).toString(32);

            // Trim the token to 40 characters
            if (token.length() > 40) {
                token = token.substring(0, 40);
            }

            return token;
        }

}
