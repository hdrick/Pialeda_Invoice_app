package pialeda.app.Invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @GetMapping("vr/user/invoices/{pageNumbers}/filter-by-Client/{clientName}")
    public String getFilterClient(Model model, @PathVariable("pageNumbers") int currentPage,
                                  @PathVariable("clientName") String client)
    {
        System.out.println(client);
        Page<Invoice> page = invoiceService.filterPage(client, currentPage);
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

        return "vr-staff/vr";
    }

    @GetMapping("vr/search")
    public ResponseEntity<?> searchInvoice(@RequestParam("query") String query) {
        List<Invoice> keyword = invoiceService.getKeyword(query);

        return new ResponseEntity<>(keyword, HttpStatus.OK);
    }

}