package pialeda.app.Invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pialeda.app.Invoice.model.Client;
import pialeda.app.Invoice.model.Invoice;
import pialeda.app.Invoice.service.ClientService;
import pialeda.app.Invoice.service.InvoiceService;
import pialeda.app.Invoice.service.SupplierService;

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

    @GetMapping("vr/user/invoices/search")
    public String searchInvoice(Model model, @RequestParam(name="search", required = false) String query,
                                @RequestParam(name="page", required = false, defaultValue = "1") int currentPage)
    {
        Page<Invoice> page = invoiceService.searchPageByKeyword(query, currentPage);
        List<Client> clients = clientService.getAllClient();
        List<String> suppliers = supplierService.getAllSupplierName();
        List<Invoice> invoices = page.getContent();

        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();



        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("invoices", invoices);

        model.addAttribute("clients", clients);
        model.addAttribute("suppliers", suppliers);

        model.addAttribute("selectedMonth", null);
        model.addAttribute("selectedClient", null);
        model.addAttribute("selectedSupplier", null);

        return "vr-staff/vr";
    }

}