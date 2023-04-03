package pialeda.app.Invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
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

    public String getDefaultPage(Model model, int currentPage, String fullName)
        {
            Page<Invoice> page = invoiceService.findPage(currentPage);
            List<Client> clients = clientService.getAllClient();
            List<String> suppliers = supplierService.getAllSupplierName();
            List<Invoice> invoices = page.getContent();

            int totalPages = page.getTotalPages();
            long totalItems = page.getTotalElements();

            model.addAttribute("fullName",fullName);

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

    public String filterSortPage(Model model, String client, String supplier, String month, int currentPage, String fullName)
    {
        Page<Invoice> page = invoiceService.filterPageByClientAndSupplierSortByMonth(client, supplier, month, currentPage);
        List<Client> clients = clientService.getAllClient();
        List<String> suppliers = supplierService.getAllSupplierName();
        List<Invoice> invoices = page.getContent();

        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();

        model.addAttribute("fullName",fullName);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("invoices", invoices);

        model.addAttribute("clients", clients);
        model.addAttribute("suppliers", suppliers);

        model.addAttribute("selectedMonth", month);
        model.addAttribute("selectedClient", client);
        model.addAttribute("selectedSupplier", supplier);
        return "vr-staff/vr";
    }
    public String filterSortClientSupplierPage(Model model, String client, String supplier, String month, int currentPage, String fullName)
    {
        Page<Invoice> page = invoiceService.filterPageByClientAndSupplier(client, supplier, currentPage);
        List<Client> clients = clientService.getAllClient();
        List<String> suppliers = supplierService.getAllSupplierName();
        List<Invoice> invoices = page.getContent();

        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();

        model.addAttribute("fullName", fullName);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("invoices", invoices);

        model.addAttribute("clients", clients);
        model.addAttribute("suppliers", suppliers);

        model.addAttribute("selectedMonth", month);
        model.addAttribute("selectedClient", client);
        model.addAttribute("selectedSupplier", supplier);
        return "vr-staff/vr";
    }

    public String sortPageByMonth(Model model, String month, int currentPage, String fullName)
    {
        Page<Invoice> page = invoiceService.sortByMonthAsc(month, currentPage);
        List<Client> clients = clientService.getAllClient();
        List<String> suppliers = supplierService.getAllSupplierName();
        List<Invoice> invoices = page.getContent();

        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();

        model.addAttribute("fullName",fullName);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("invoices", invoices);

        model.addAttribute("clients", clients);
        model.addAttribute("suppliers", suppliers);

        model.addAttribute("selectedMonth", month);
        model.addAttribute("selectedClient", null);
        model.addAttribute("selectedSupplier", null);
        return "vr-staff/vr";
    }

    public String filterClientSortPage(Model model, String client, String month, int currentPage, String name)
    {
        Page<Invoice> page = invoiceService.filterPageByClientSortByMonth(client, month, currentPage);
        List<Client> clients = clientService.getAllClient();
        List<String> suppliers = supplierService.getAllSupplierName();
        List<Invoice> invoices = page.getContent();

        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();

        model.addAttribute("fullName",name);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("invoices", invoices);

        model.addAttribute("clients", clients);
        model.addAttribute("suppliers", suppliers);

        model.addAttribute("selectedMonth", month);
        model.addAttribute("selectedClient", client);
        model.addAttribute("selectedSupplier", null);
        return "vr-staff/vr";
    }

    public String filterPageClient(Model model, String client, String month, int currentPage, String fullName)
    {
        Page<Invoice> page = invoiceService.filterPageByClient(client, currentPage);
        List<Client> clients = clientService.getAllClient();
        List<String> suppliers = supplierService.getAllSupplierName();
        List<Invoice> invoices = page.getContent();

        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();

        model.addAttribute("fullName", fullName);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("invoices", invoices);

        model.addAttribute("clients", clients);
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("selectedMonth", month);
        model.addAttribute("selectedClient", client);
        model.addAttribute("selectedSupplier", null);
        return "vr-staff/vr";
    }

    public String filterSupplierSortPage(Model model, String supplier, String month, int currentPage, String fullName)
    {
        Page<Invoice> page = invoiceService.filterPageBySupplierSortByMonth(supplier, month, currentPage);
        List<Client> clients = clientService.getAllClient();
        List<String> suppliers = supplierService.getAllSupplierName();
        List<Invoice> invoices = page.getContent();

        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();

        model.addAttribute("fullName",fullName);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("invoices", invoices);

        model.addAttribute("clients", clients);
        model.addAttribute("suppliers", suppliers);

        model.addAttribute("selectedMonth", month);
        model.addAttribute("selectedClient", null);
        model.addAttribute("selectedSupplier", supplier);
        return "vr-staff/vr";
    }

    public String filterPageSupplier(Model model, String supplier, int currentPage, String fullName)
    {

        Page<Invoice> page = invoiceService.filterPageBySupplier(supplier, currentPage);
        List<Client> clients = clientService.getAllClient();
        List<String> suppliers = supplierService.getAllSupplierName();
        List<Invoice> invoices = page.getContent();

        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();

        model.addAttribute("fullName", fullName);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("invoices", invoices);

        model.addAttribute("clients", clients);
        model.addAttribute("suppliers", suppliers);

        model.addAttribute("selectedMonth", null);
        model.addAttribute("selectedClient", null);
        model.addAttribute("selectedSupplier", supplier);
        return "vr-staff/vr";
    }
}