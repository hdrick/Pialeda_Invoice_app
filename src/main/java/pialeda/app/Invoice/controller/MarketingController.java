package pialeda.app.Invoice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pialeda.app.Invoice.dto.*;
import pialeda.app.Invoice.model.*;
import pialeda.app.Invoice.service.ClientService;
import pialeda.app.Invoice.service.OfficialRecptService;
import pialeda.app.Invoice.service.SupplierService;
import pialeda.app.Invoice.service.InvoiceService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @GetMapping("marketing-officialreceipt")
    public String test(Model model){
        String role = GlobalUser.getUserRole();
        String destination=null;
        if(role == null){
            return destination = "redirect:/login";
        } else if (role.equals("vr-staff")) {
            return destination = "redirect:/vr/user/invoices";
        } else if (role.equals(("marketing"))) {
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
            return destination = "marketing/officialreceipt";
        } else if (role.equals("admin")) {
            return destination = "redirect:/admin-dashboard";
        }
        return destination;
    }

    @GetMapping("marketing-view/invoices")
    public String viewInvoices(Model model){
        return "marketing/invoice-view";
    }


    @PostMapping("/createInvoice")
    public String createInvoice(@ModelAttribute("invoiceInfo") InvoiceInfo invoiceInfo,
                                 RedirectAttributes redirectAttributes){


        double sumOfGrandTotal = invoiceService.getSuppTotalLimit(invoiceInfo.getSupplierName());
        double supplierLimit = supplierService.findLimitByName(invoiceInfo.getSupplierName());
        double remainingLimit = supplierLimit - sumOfGrandTotal;

        if(invoiceInfo.getGrandTotal() <= remainingLimit){
//            invoiceService.createInvoice(invoiceInfo);
            System.out.println("Invoice Number: "+invoiceInfo.getInvoiceNum());
            System.out.println("PoNum: "+invoiceInfo.getPoNum());
            System.out.println("ateCreated: "+invoiceInfo.getDateCreated());
            System.out.println("ClientContactPerson: "+invoiceInfo.getClientContactPerson());
            System.out.println("SupplierName: "+invoiceInfo.getSupplierName());
            System.out.println("SupplierAddress: "+invoiceInfo.getSupplierAddress());
            System.out.println("SupplierTin: "+invoiceInfo.getSupplierTin());
            System.out.println("ClientName: "+invoiceInfo.getClientName());
            System.out.println("ClientTin: "+invoiceInfo.getClientTin());
            System.out.println("ClientAddress: "+invoiceInfo.getClientAddress());
            System.out.println("ClientBusStyle: "+invoiceInfo.getClientBusStyle());
            System.out.println("ClientTerms: "+invoiceInfo.getClientTerms());
            System.out.println("GrandTotal: "+invoiceInfo.getGrandTotal());
            System.out.println("AddVat: "+invoiceInfo.getAddVat());
            System.out.println("AmountNetOfVat: "+invoiceInfo.getAmountNetOfVat());
            System.out.println("TotalSalesVatInc: "+invoiceInfo.getTotalSalesVatInc());
            System.out.println("Cashier: "+invoiceInfo.getCashier());
            boolean hideDivSuccess = true;
            redirectAttributes.addFlashAttribute("hideDivSuccess", hideDivSuccess);

            return "redirect:/marketing-invoice";
        }else{
            boolean hideDivError = true;
            redirectAttributes.addFlashAttribute("hideDivError", hideDivError);
            return "redirect:/marketing-invoice";
        }
    }

//    @PostMapping("/createInvoice")
//    public String createInvoice(@ModelAttribute("invoiceInfo") InvoiceInfo invoiceInfo,
//                                @RequestParam("data") String data, RedirectAttributes redirectAttributes){
//        System.out.println("Invoice Number: "+invoiceInfo.getInvoiceNum());
//        System.out.println("PoNum: "+invoiceInfo.getPoNum());
//        System.out.println("ateCreated: "+invoiceInfo.getDateCreated());
//        System.out.println("ClientContactPerson: "+invoiceInfo.getClientContactPerson());
//        System.out.println("SupplierName: "+invoiceInfo.getSupplierName());
//        System.out.println("SupplierAddress: "+invoiceInfo.getSupplierAddress());
//        System.out.println("SupplierTin: "+invoiceInfo.getSupplierTin());
//        System.out.println("ClientName: "+invoiceInfo.getClientName());
//        System.out.println("ClientTin: "+invoiceInfo.getClientTin());
//        System.out.println("ClientAddress: "+invoiceInfo.getClientAddress());
//        System.out.println("ClientBusStyle: "+invoiceInfo.getClientBusStyle());
//        System.out.println("ClientTerms: "+invoiceInfo.getClientTerms());
//        System.out.println("GrandTotal: "+invoiceInfo.getGrandTotal());
//        System.out.println("AddVat: "+invoiceInfo.getAddVat());
//        System.out.println("AmountNetOfVat: "+invoiceInfo.getAmountNetOfVat());
//        System.out.println("TotalSalesVatInc: "+invoiceInfo.getTotalSalesVatInc());
//        System.out.println("Cashier: "+invoiceInfo.getCashier());
//        List<Map<String, String>> tableData = new ArrayList<>();
//        try {
//            tableData = new ObjectMapper().readValue(data, new TypeReference<>() {});
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Table data: " + tableData);
//        return "redirect:/newinvoice";
//    }

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



    @GetMapping("/invoice-view")
    public String invoiceView(Model model){
        String role = GlobalUser.getUserRole();
        String destination=null;
        if(role == null){
            return destination = "redirect:/login";
        } else if (role.equals("vr-staff")) {
            return destination = "redirect:/vr/user/invoices";
        } else if (role.equals(("marketing"))) {
            return destination = findPaginated(0, model);
        } else if (role.equals("admin")) {
            return destination = "redirect:/admin-dashboard";
        }
        return destination;

    }

    @GetMapping("/page/{pageno}")
    public String findPaginated(@PathVariable int pageno, Model model){
        Page<Invoice> invoiceList= invoiceService.getInvoicesPaginated(pageno, 8);

        model.addAttribute("invoiceList", invoiceList);
        model.addAttribute("currentPage",pageno);
        model.addAttribute("totalPages",invoiceList.getTotalPages());
        model.addAttribute("totalItem", invoiceList.getTotalElements());

        return "marketing/invoice-view";
    }

    @GetMapping("/specific-inv")
    @ResponseBody
    public Map<String, Object> getInvById(@RequestParam("id") int id){
        Invoice inv= invoiceService.getInvByIdAndFillFields(id);
        List<InvoiceProductInfo> prodList = invoiceService.getAllProdByInvNum(inv.getInvoiceNum());

        Map<String, Object> result = new HashMap<>();
        result.put("invoiceNum", inv.getInvoiceNum());
        result.put("poNum", inv.getPoNum());
        result.put("dateCreated", inv.getDateCreated());
        result.put("supplierName", inv.getSupplierName());
        result.put("clientName", inv.getClientName());
        result.put("clientContactPerson", inv.getClientContactPerson());
        result.put("productList", prodList);

        return result;
    }

    @PostMapping("/submit-form")
    public String handleSubmitForm(
            @RequestParam("inv-num") String invoiceNumber,
            @RequestParam("date-created") String dateCreated,
            @RequestParam("supp-name") String supplierName,
            @RequestParam("client-name") String clientName,
            @RequestParam("client-cp") String clientContactPerson,
            @RequestParam("total-amt") String totalAmt,
            @RequestParam("qty-input") List<String> qtyList,
            @RequestParam("unit-input") List<String> unitList,
            @RequestParam("articles-input") List<String> articlesList,
            @RequestParam("unitP-input") List<String> unitPriceList,
            @RequestParam("amount-input") List<String> amountList,
            @RequestParam("id-input") List<String> prodIdList,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

         boolean ifSuccess = invoiceService.updateInvoices(invoiceNumber, dateCreated, supplierName, clientName, clientContactPerson, totalAmt, qtyList, unitList, articlesList, unitPriceList, amountList, prodIdList);

         if(ifSuccess == true){
             boolean hideDivSuccess = true;
             redirectAttributes.addFlashAttribute("hideDivSuccess", hideDivSuccess);
             return "redirect:/invoice-view";
         }else{
             boolean hideDivError = true;
             redirectAttributes.addFlashAttribute("hideDivError", hideDivError);
             return "redirect:/invoice-view";
         }
    }




}
