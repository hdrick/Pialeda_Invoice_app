package pialeda.app.Invoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pialeda.app.Invoice.DAO.RoleDao;
import pialeda.app.Invoice.DAO.UserDao;
import pialeda.app.Invoice.DAO.UserRepo;

import pialeda.app.Invoice.model.Role;
import pialeda.app.Invoice.model.User;
import pialeda.app.Invoice.repository.UserRepository;


import javax.validation.Valid;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

	 @Autowired
	 private UserDao userDao;
	    
	 @Autowired
	 private UserRepo userRepo;

	 @Autowired
	 private RoleDao roleDao;

	 @Autowired
	 private PasswordEncoder passwordEncoder;
	    
	 @Autowired
	 BCryptPasswordEncoder bCryptPasswordEncoder;
	    
	    
	 

    @Autowired
    private UserRepository userRepo1;

    public List<User> getAllUser(){
        return userRepo.findAll();
    }

    public void createUser(User user){
        this.userRepo.save(user);
    }

//    public void updateUser(User updateUser){
//        User user = userRepo1.findById(updateUser.getId());
//
//        user.setFirstName(updateUser.getFirstName());
//        user.setLastName(updateUser.getLastName());
//        user.setEmail(updateUser.getEmail());
//        user.setRole(updateUser.getRole());
//        this.userRepo.save(user);
//    }

    public void deleteUser(int id){
        userRepo1.deleteById(id);
    }

//    public String authenticate(String email) throws AuthenticationException {
//        User user = userRepo1.findByEmail(email);
//        System.out.println(user);
//        if(user !=null && user.getPassword().equals(user.getPassword())){
//            return user.getRole();
//        }else{
//            throw new AuthenticationException("Invalid email or password");
//        }
//    }

	public void initRoleAndUser() {
		// TODO Auto-generated method stub
		Role adminRole = new Role();
	    adminRole.setRoleName("Admin");
	    adminRole.setRoleDescription("Admin role");
	    roleDao.save(adminRole);
	
	    Role userRole = new Role();
	    userRole.setRoleName("User");
	    userRole.setRoleDescription("Default role for newly created record");
	    roleDao.save(userRole);
	
	    User adminUser = new User();
	    adminUser.setEmail("admin123@gmail.com");
	    adminUser.setPassword(getEncodedPassword("admin@pass"));
	    adminUser.setFirstName("admin");
	    adminUser.setLastName("admin");
	    Set<Role> adminRoles = new HashSet<>();
	    adminRoles.add(adminRole);
	    adminUser.setRole(adminRoles);
	    userDao.save(adminUser);

		
	}
	  public String getEncodedPassword(String password) {
	        return passwordEncoder.encode(password);
	    }

	public void registerNewUser(@Valid User user) {
		// TODO Auto-generated method stub
		Role role = roleDao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setPassword(getEncodedPassword(user.getPassword()));
        userDao.save(user);
	}
}
