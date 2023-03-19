package pialeda.app.Invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pialeda.app.Invoice.model.User;
import pialeda.app.Invoice.service.UserService;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    //    ADMIN
//    @GetMapping("admin-dashboard")
//    public String dashboard(){
//        return "admin/dashboard";
//    }

    @GetMapping("/admin/user")
    public String users(Model model){
        model.addAttribute("userList", userService.getAllUser());
        model.addAttribute("user", new User());
        return "admin/users";
    }

    @PostMapping("/admin/createUser")
    public String createUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin-users";
    }

    @PostMapping("/admin/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin-users";
    }

    @PostMapping("/admin/deleteUser")
    public String deleteItem(@ModelAttribute("user") User user) {
        userService.deleteUser(user.getId());
        return "redirect:/admin-users";
    }
}
