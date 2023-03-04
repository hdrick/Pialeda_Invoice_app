package pialeda.app.Invoice.DAO;



import org.springframework.data.repository.CrudRepository;


import org.springframework.stereotype.Repository;

import pialeda.app.Invoice.model.User;




@Repository
public interface UserDao extends CrudRepository<User, String> {

	
	
}
