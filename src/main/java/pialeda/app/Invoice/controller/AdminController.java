package pialeda.app.Invoice.controller;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pialeda.app.Invoice.model.User;
import pialeda.app.Invoice.service.UserService;

@RestController
public class AdminController {

    @Autowired
    private UserService userService;
    
    
    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    //    ADMIN
//    @GetMapping("admin-dashboard")
//    public String dashboard(){
//        return "admin/dashboard";
//    }

    @GetMapping("admin-users")
    @PreAuthorize("hasRole('Admin')")
    public String users(Model model){
        model.addAttribute("userList", userService.getAllUser());
        model.addAttribute("user", new User());
        return "admin/users";
    }

    @PostMapping("/createUser")
    @PreAuthorize("hasRole('Admin')")
    public String createUser(@ModelAttribute("user")@Valid @RequestBody User user) {
       // userService.createUser(user);
    	userService.registerNewUser(user);
        return "redirect:/admin-users";
    }

//    @PostMapping("/updateUser")
//    public String updateUser(@ModelAttribute("user") User user) {
//        userService.updateUser(user);
//        return "redirect:/admin-users";
//    }
//
//    @PostMapping("/deleteUser")
//    public String deleteItem(@ModelAttribute("user") User user) {
//        userService.deleteUser(user.getId());
//        return "redirect:/admin-users";
//    }
}
