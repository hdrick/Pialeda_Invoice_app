package pialeda.app.Invoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pialeda.app.Invoice.config.JwtUtil;
import pialeda.app.Invoice.model.User;
import pialeda.app.Invoice.repository.UserRepository;

import javax.naming.AuthenticationException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    public List<User> getAllUser(){
        return userRepo.findAll();
    }

    public void createUser(User user){
        String encodedPassword = encoder.encode(user.getPassword());
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

    public boolean loadUserByEmail(String email, String pass)
    {
        User emailExists = userRepo.findByEmail(email);
        if (emailExists == null)
        {
            return false;
        }
        else
        {
            String userDbPass = emailExists.getPassword();
            boolean isMatch = encoder.matches(pass,userDbPass);
            return isMatch;
        }
    }
    public  User loadUserByEmail(String email)
    {
        User emailExists = userRepo.findByEmail(email);
        return emailExists;
    }
    public User loadRoleByUser(String email)
    {
        User emailExists = userRepo.findByEmail(email);
        return emailExists;
    }
    public Set<SimpleGrantedAuthority> getAuthority() {
        List<String> myrole = new ArrayList<>();

        // Add some strings to the list
        myrole.add("admin");
        myrole.add("vr-staff");
        myrole.add("marketing");

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        myrole.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        });
        return authorities;
    }
}
