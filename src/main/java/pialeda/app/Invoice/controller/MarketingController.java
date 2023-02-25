package pialeda.app.Invoice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import pialeda.app.Invoice.model.User;

@Controller
public class MarketingController {
    @GetMapping("marketing-invoice")
    public String users(Model model){

        return "marketing/invoice";
    }
}
