package pialeda.app.Invoice.service;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import pialeda.app.Invoice.DAO.RoleDao;
import pialeda.app.Invoice.model.Role;




@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role createNewRole(Role role) {
        return roleDao.save(role);
    }
}
