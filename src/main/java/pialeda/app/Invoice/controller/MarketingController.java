package pialeda.app.Invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pialeda.app.Invoice.dto.ClientInfo;
import pialeda.app.Invoice.dto.InvoiceInfo;
import pialeda.app.Invoice.dto.SupplierInfo;
import pialeda.app.Invoice.model.Client;
import pialeda.app.Invoice.model.Supplier;
import pialeda.app.Invoice.service.ClientService;
import pialeda.app.Invoice.service.SupplierService;
import pialeda.app.Invoice.service.InvoiceService;


@Controller
public class MarketingController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private InvoiceService invoiceService;



    @GetMapping("marketing-invoice")
    public String users(Model model){
        model.addAttribute("clientList", clientService.getAllClient());
        model.addAttribute("supplierList", supplierService.getAllSupplier());

        model.addAttribute("clientInfo", new ClientInfo());
        model.addAttribute("supplierInfo", new SupplierInfo());
        model.addAttribute("invoiceInfo", new InvoiceInfo());
        return "marketing/invoice";
    }

    @PostMapping("/createInvoice")
    public String createInvoice(@ModelAttribute("invoiceInfo") InvoiceInfo invoiceInfo, Model model){

        invoiceService.createInvoice(invoiceInfo);
        return "redirect:/marketing-invoice";
    }

    @PostMapping("/createClient")
    public String createClient(@ModelAttribute("clientInfo") ClientInfo clientInfo, Model model) {
        clientService.createClient(clientInfo);
        return "redirect:/marketing-invoice";
    }

    @PostMapping("/createSupplier")
    public String createSupplier(@ModelAttribute("supplierInfo") SupplierInfo supplierInfo, Model model) {
        supplierService.createSupplier(supplierInfo);
        return "redirect:/marketing-invoice";
    }


    @GetMapping("/getClientInfo")
    @ResponseBody
    public Client getClientInfo(@RequestParam("name") String name) {
        Client client = clientService.findByName(name);
        Client clientInfo = new Client(client.getName(), client.getAddress(), client.getCityAddress(), client.getTin(), client.getAgent());
        return clientInfo;
    }

    @GetMapping("/getSupplierInfo")
    @ResponseBody
    public Supplier getSupplierInfo(@RequestParam("name") String name) {
        Supplier supplier = supplierService.findByName(name);
        Supplier supplierInfo = new Supplier(supplier.getName(), supplier.getAddress(), supplier.getCityAddress(), supplier.getTin());
        return supplierInfo;
    }
}
