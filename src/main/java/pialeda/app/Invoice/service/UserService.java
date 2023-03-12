package pialeda.app.Invoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pialeda.app.Invoice.model.User;
import pialeda.app.Invoice.repository.UserRepository;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getAllUser(){
        return userRepo.findAll();
    }

    public void createUser(User user){
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
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

    public String accountValidation(String email, String pass, HttpSession session)
    {
        User emailExists = userRepo.findByEmail(email);
        if (emailExists != null)
        {
            boolean password = bCryptPasswordEncoder.matches(pass, emailExists.getPassword());
            if (password == true)
            {
                session.setAttribute("name", emailExists.getFirstName());
                return "welcome "+emailExists.getFirstName();
            }
            return "wrong password";
        }
        return "wrong email";
    }
}
