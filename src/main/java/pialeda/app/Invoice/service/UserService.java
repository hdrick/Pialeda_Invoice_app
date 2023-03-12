package pialeda.app.Invoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pialeda.app.Invoice.dto.Login;
import pialeda.app.Invoice.model.User;
import pialeda.app.Invoice.repository.UserRepository;

import javax.naming.AuthenticationException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public List<User> getAllUser(){
        return userRepo.findAll();
    }

    public void createUser(User user){
        this.userRepo.save(user);
    }

    public void updateUser(User updateUser){
        User user = userRepo.findById(updateUser.getId());

        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setEmail(updateUser.getEmail());
        user.setRole(updateUser.getRole());
        this.userRepo.save(user);
    }

    public long getUserCount(){

        return userRepo.count();
    }

    public void deleteUser(int id){
        userRepo.deleteById(id);
    }

    public String authenticate(String email) throws AuthenticationException {
        User user = userRepo.findByEmail(email);
        System.out.println(user);
        if(user !=null && user.getPassword().equals(user.getPassword())){
            return user.getRole();
        }else{
            throw new AuthenticationException("Invalid email or password");
        }
    }
}
