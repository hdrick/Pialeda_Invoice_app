package pialeda.app.Invoice.service;

import java.util.HashSet;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pialeda.app.Invoice.DAO.RoleDao;
import pialeda.app.Invoice.DAO.UserDao;
import pialeda.app.Invoice.model.Role;
import pialeda.app.Invoice.model.User;




@Service
public class VRService{

	
	@Autowired
    private UserDao userDao;
    
 
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public void initRoleAndUser() {
    Role vrRole = new Role();
    vrRole.setRoleName("vr-staff");
    vrRole.setRoleDescription("VR-Staff Role");
    roleDao.save(vrRole);
    }
    
	public User registerNewVR(@Valid User user) {
		// TODO Auto-generated method stub
		Role role = roleDao.findById("vr-staff").get();
      Set<Role> vrRoles = new HashSet<>();
      vrRoles.add(role);
      user.setRole(vrRoles);
      user.setPassword(getEncodedPassword(user.getPassword()));

      return userDao.save(user);
	}
	
	public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }



}
