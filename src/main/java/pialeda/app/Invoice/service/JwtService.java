package pialeda.app.Invoice.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pialeda.app.Invoice.DAO.UserDao;
import pialeda.app.Invoice.DAO.UserRepo;
import pialeda.app.Invoice.common.ApiResponse;
import pialeda.app.Invoice.config.JwtUtil;
import pialeda.app.Invoice.dto.Login;

import pialeda.app.Invoice.model.LoginResponse;
import pialeda.app.Invoice.model.User;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<?> createJwtToken(Login login) throws Exception {
	if (login.getEmail() !=null && login.getPassword() !=null )   {
	    		
				System.out.println("########User #########"+ login.getEmail());
				  User userDB = userRepo.findByEmail(login.getEmail());
	           
	           
	            if (userDB != null) {
	            	String encodedPassword = userDB.getPassword();
	
	                
	            	if(bCryptPasswordEncoder.matches(login.getPassword(), encodedPassword)) {
	                	//session.setAttribute("appUser", user);
	                	System.out.println("AppUser"+ login.getEmail());
	                	authenticate(login.getEmail(), login.getPassword());
	
				        UserDetails userDetails = loadUserByUsername(login.getEmail());
				        String newGeneratedToken = jwtUtil.generateToken(userDetails);
				
				        User appUser = userDao.findById(login.getEmail()).get();
				        return ResponseEntity.ok(new LoginResponse(appUser, newGeneratedToken));
				       // return new ResponseEntity.ok(appUser, newGeneratedToken);
	            	}else {
				    	
	            		return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Wrong credentials"), HttpStatus.NOT_FOUND);
	            	}
	            	
	            }
	           // return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Username does not exist"), HttpStatus.NOT_FOUND);
	    	}
	    	return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Username does not exist"), HttpStatus.NOT_FOUND);
		    
		    	
	}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findById(username).get();

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    getAuthority(user)
            );
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }

    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
