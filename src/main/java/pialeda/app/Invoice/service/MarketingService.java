package pialeda.app.Invoice.service;

import java.util.HashSet;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pialeda.app.Invoice.DAO.RoleDao;
import pialeda.app.Invoice.DAO.UserDao;
import pialeda.app.Invoice.model.Role;
import pialeda.app.Invoice.model.User;



@Service
public class MarketingService {
	
	@Autowired
    private UserDao userDao;
    
 
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public void initRoleAndUser() {
    Role marketingRole = new Role();
    marketingRole.setRoleName("marketing");
    marketingRole.setRoleDescription("Marketing Role");
    roleDao.save(marketingRole);
    }
    

	public User registerNewMarketing(@Valid User user) {
		// TODO Auto-generated method stub
		Role role = roleDao.findById("marketing").get();
	      Set<Role> marketingRoles = new HashSet<>();
	      marketingRoles.add(role);
	      user.setRole(marketingRoles);
	      user.setPassword(getEncodedPassword(user.getPassword()));

	      return userDao.save(user);
		}
		
		public String getEncodedPassword(String password) {
	        return passwordEncoder.encode(password);
	    }



}
