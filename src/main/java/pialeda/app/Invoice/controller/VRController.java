package pialeda.app.Invoice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class VRController {

    @GetMapping("vr-users")
    public String vr(){
        return "vr-staff/vr";
    }
}
