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
        System.out.println("invoiceNum "+invoiceInfo.getInvoiceNum());
        System.out.println("poNum "+invoiceInfo.getPoNum());
        System.out.println("dateCreated "+invoiceInfo.getDateCreated());
        System.out.println("clientContactPerson "+invoiceInfo.getClientContactPerson());
        System.out.println("supplierName "+invoiceInfo.getSupplierName());
        System.out.println("supplierAddress "+invoiceInfo.getSupplierAddress());
        System.out.println("supplierTin "+invoiceInfo.getSupplierTin());
        System.out.println("clientName "+invoiceInfo.getClientName());
        System.out.println("clientTin "+invoiceInfo.getClientTin());
        System.out.println("clientAddress "+invoiceInfo.getClientAddress());
        System.out.println("clientBusStyle "+invoiceInfo.getClientBusStyle());
        System.out.println("clientTerms "+invoiceInfo.getClientTerms());
        System.out.println("grandTotal "+invoiceInfo.getGrandTotal());
        System.out.println("cashier "+invoiceInfo.getCashier());

        System.out.println("qty "+invoiceInfo.getQty());
        System.out.println("unit "+invoiceInfo.getUnit());
        System.out.println("article "+invoiceInfo.getArticles());
        System.out.println("unitPRice "+invoiceInfo.getUnitPrice());
        System.out.println("Amount "+invoiceInfo.getAmount());
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
