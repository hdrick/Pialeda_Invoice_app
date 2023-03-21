package pialeda.app.Invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pialeda.app.Invoice.config.JwtUtil;
import pialeda.app.Invoice.model.Invoice;
import pialeda.app.Invoice.model.User;
import pialeda.app.Invoice.service.ClientService;
import pialeda.app.Invoice.service.InvoiceService;
import pialeda.app.Invoice.service.SupplierService;
import pialeda.app.Invoice.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class VRController {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("vr/user")
    public String invoice(Model model, HttpSession session) {

        String token = (String) session.getAttribute("token");
        String email = jwtUtil.extractEmail(token);

        User userExist = userService.loadUserByEmail(email);
        try
        {
            if (jwtUtil.validateToken(token, userExist))
            {
                model.addAttribute("invoiceList", invoiceService.getAllInvoice());
                model.addAttribute("invoice", new Invoice());

                model.addAttribute("clientList", clientService.getAllClient());
                model.addAttribute("supplierList", supplierService.getAllSupplier());

                session.setAttribute("name", userExist.getFirstName());

                return "vr-staff/vr";
            }
            return "redirect:login";
        }
        catch (Exception e)
        {
            return "redirect:login";
        }

    }

    @GetMapping("vr/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable int id) {
        Optional<Invoice> invoice = invoiceService.getInvoiceById(id);

        if (invoice.isPresent()) {
            return new ResponseEntity<>(invoice.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("vr/search")
    public ResponseEntity<?> searchInvoice(@RequestParam("query") String query) {
        List<Invoice> keyword = invoiceService.getKeyword(query);

        return new ResponseEntity<>(keyword, HttpStatus.OK);
    }

    @GetMapping("vr/all-invoice")
    @ResponseBody
    public List<Invoice> getAll() {
        return invoiceService.getAllInvoice();
    }

}