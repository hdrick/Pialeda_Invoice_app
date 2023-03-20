package pialeda.app.Invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pialeda.app.Invoice.model.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    Supplier findByName(String name);


}
