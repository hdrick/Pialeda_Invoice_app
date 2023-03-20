package pialeda.app.Invoice.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pialeda.app.Invoice.model.Invoice;

import java.util.List;



@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    Invoice findById(int id);
    List<Invoice> findByClientNameContainingIgnoreCase(String query);
    List<Invoice> findByClientName(String client);

    @Query(value ="SELECT COALESCE(SUM(grand_total), 0) FROM invoice where supplier_name like:suppliername", nativeQuery = true)
    double findSumLimitByName(@Param("suppliername") String suppliername);
}
