package pialeda.app.Invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pialeda.app.Invoice.dto.ClientInfo;
import pialeda.app.Invoice.model.Client;
import pialeda.app.Invoice.service.ClientService;


@Controller
public class MarketingController {
    @Autowired
    private ClientService clientService;

    @GetMapping("marketing-invoice")
    public String users(Model model){
        model.addAttribute("clientList", clientService.getAllClient());
        model.addAttribute("clientInfo", new ClientInfo());
        return "marketing/invoice";
    }


    @PostMapping("/createClient")
    public String createClient(@ModelAttribute("clientInfo") ClientInfo clientInfo, Model model) {
        System.out.println("WORKINNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        clientService.createClient(clientInfo);
        return "redirect:/marketing-invoice";
    }


    @GetMapping("/getClientInfo")
    @ResponseBody
    public Client getClientInfo(@RequestParam("name") String name) {
        Client client = clientService.findByName(name);
        Client clientInfo = new Client(client.getName(), client.getAddress(), client.getCityAddress(), client.getTin(), client.getAgent());
        return clientInfo;
    }


}
