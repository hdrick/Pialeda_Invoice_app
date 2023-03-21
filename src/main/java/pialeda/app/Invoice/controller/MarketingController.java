package pialeda.app.Invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pialeda.app.Invoice.dto.*;
import pialeda.app.Invoice.model.Client;
import pialeda.app.Invoice.model.Supplier;
import pialeda.app.Invoice.service.ClientService;
import pialeda.app.Invoice.service.OfficialRecptService;
import pialeda.app.Invoice.service.SupplierService;
import pialeda.app.Invoice.service.InvoiceService;
import java.time.LocalDate;
import java.time.Month;

@Controller
public class MarketingController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private OfficialRecptService officialRecptService;

//    @GetMapping("marketing-invoice")
//    public String users(Model model){
//        model.addAttribute("clientList", clientService.getAllClient());
//        model.addAttribute("supplierList", supplierService.getAllSupplier());
//
//        model.addAttribute("clientInfo", new ClientInfo());
//        model.addAttribute("supplierInfo", new SupplierInfo());
//
//        InvoiceWrapper wrapper = new InvoiceWrapper();
//        wrapper.setInvoiceInfo(new InvoiceInfo());
//        wrapper.setInvoiceProdInfo(new InvoiceProdInfo());
//        model.addAttribute("wrapper", wrapper);
//
//        return "marketing/invoice";
//    }
    @GetMapping("marketing-officialreceipt")
    public String test(Model model){
            // get the current date
            LocalDate currentDate = LocalDate.now();

            // get the current day and month
            int currentDay = currentDate.getDayOfMonth();
            int  currentMonth = currentDate.getMonthValue();
            int generateORNumber = (int) officialRecptService.getOrCount();

            if(generateORNumber == 0 || generateORNumber < 0){
                generateORNumber += 1;
            }else {
                generateORNumber += 1;
            }
            // format generateORNumber with a leading zero
            String generateORNumberStr = String.format("%02d", generateORNumber);
            String resultStr = String.format("%d%d%s", currentMonth, currentDay, generateORNumberStr);
            int result = Integer.parseInt(resultStr);

            model.addAttribute("generateORNumber", result);
            model.addAttribute("officialReceiptInfo", new OfficialReceiptInfo());
            return "marketing/officialreceipt";


    }

    @GetMapping("marketing-view/invoices")
    public String viewInvoices(Model model){
        return "marketing/invoice-view";
    }


    @PostMapping("/createInvoice")
    public String createInvoice(@ModelAttribute("wrapper") InvoiceWrapper wrapper, RedirectAttributes redirectAttributes){
        InvoiceInfo invoiceInfo = wrapper.getInvoiceInfo();

        double sumOfGrandTotal = invoiceService.getSuppTotalLimit(invoiceInfo.getSupplierName());
        double supplierLimit = supplierService.findLimitByName(invoiceInfo.getSupplierName());
        double remainingLimit = supplierLimit - sumOfGrandTotal;

        if(invoiceInfo.getGrandTotal() <= remainingLimit){
            invoiceService.createInvoice(wrapper);
            boolean hideDivSuccess = true;
            redirectAttributes.addFlashAttribute("hideDivSuccess", hideDivSuccess);
            return "redirect:/marketing-invoice";
        }else{
            boolean hideDivError = true;
            redirectAttributes.addFlashAttribute("hideDivError", hideDivError);
            return "redirect:/marketing-invoice";
        }
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

    @PostMapping("/createOfficialReceipt")
    public String createOfficialReceipt(@RequestParam("orNumber") int orNumber,
                                        @ModelAttribute("officialReceiptInfo") OfficialReceiptInfo officialReceiptInfo,
                                        Model model) {
        System.out.println(officialReceiptInfo.getRecvFrom());
        officialRecptService.createOR(orNumber,officialReceiptInfo);
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
