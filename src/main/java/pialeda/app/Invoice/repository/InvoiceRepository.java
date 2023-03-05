package pialeda.app.Invoice.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pialeda.app.Invoice.model.Invoice;

import java.util.List;
import java.util.stream.Collectors;


@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    Invoice findById(int id);
    List<Invoice> findByClientNameContainingIgnoreCase(String query);
    List<Invoice> findByClientName(String client);
    List<Invoice> findByClientNameOrSupplierNameContaining(String client, String supplier);
}
