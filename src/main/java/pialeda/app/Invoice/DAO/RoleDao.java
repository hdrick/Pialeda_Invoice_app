package pialeda.app.Invoice.DAO;



import org.springframework.data.repository.CrudRepository;


import org.springframework.stereotype.Repository;

import pialeda.app.Invoice.model.Role;



@Repository
public interface RoleDao extends CrudRepository<Role, String> {

	
	

}
