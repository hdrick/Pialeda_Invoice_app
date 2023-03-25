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

//    @GetMapping("vr/user")
//    public String invoice(Model model) {
//        model.addAttribute("invoiceList", invoiceService.getAllInvoice());
//        model.addAttribute("invoice", new Invoice());
//
//        model.addAttribute("clientList", clientService.getAllClient());
//        model.addAttribute("supplierList", supplierService.getAllSupplier());
//        return "vr-staff/vr";
//    }

    @GetMapping("vr/user/invoices")
    public String getAllPages(Model model)
    {
        return getOnePage(model, 1);
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

    @GetMapping("vr/user/invoices/{pageNumbers}/{field}")
    public String getPageWithSort(Model model, @PathVariable("pageNumbers") int currentPage,
                                  @PathVariable String field,
                                  @PathVariable("sortDir") String sortDir)
    {
        Page<Invoice> page = invoiceService.findAllWithSort(field, sortDir, currentPage);
        List<Invoice> invoices = page.getContent();
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")? "desc" : "asc");
        model.addAttribute("invoices", invoices);


        return "vr-staff/vr";
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

}