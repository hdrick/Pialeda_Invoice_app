package pialeda.app.Invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pialeda.app.Invoice.model.User;
import pialeda.app.Invoice.repository.UserRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class InvoiceManagementApplication {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(InvoiceManagementApplication.class, args);
	}
	@PostConstruct
	public void init() {
		if (userRepository.count() == 0) {
			User adminUser = new User();
			adminUser.setRole("admin");
			adminUser.setPassword(bCryptPasswordEncoder.encode("adminpassword"));
			adminUser.setEmail("admin@gmail.com");
			adminUser.setFirstName("admin");
			adminUser.setLastName("admin");
			userRepository.save(adminUser);
		}
	}
}
