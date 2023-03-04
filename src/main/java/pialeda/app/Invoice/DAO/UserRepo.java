package pialeda.app.Invoice.DAO;

import java.util.Set;


import org.springframework.data.jpa.repository.JpaRepository;

import pialeda.app.Invoice.model.User;




public interface UserRepo extends JpaRepository<User, Long> {

	User findByEmail(String email);

	User findByPassword(String password);


}
