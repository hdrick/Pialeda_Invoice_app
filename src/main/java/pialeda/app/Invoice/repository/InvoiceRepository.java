package pialeda.app.Invoice.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Query(value = "SELECT u FROM invoice u WHERE u.supplier_invoice_number LIKE %:keyword% OR u.invoice_purchase_order_number LIKE %:keyword% OR u.client_tin LIKE %:keyword% OR u.supplier_tin LIKE %:keyword% OR u.cashier LIKE %:keyword%", nativeQuery = true)
    Page<Invoice> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    Invoice findByInvoiceNum(String invNum);

    Page<Invoice> findByClientNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Invoice> findByClientNameAndDateCreatedContainingIgnoreCase(String name, String month, Pageable pageable);
    Page<Invoice> findBySupplierNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Invoice> findBySupplierNameAndDateCreatedContainingIgnoreCase(String name, String month, Pageable pageable);
    Page<Invoice> findByClientNameAndSupplierNameContainingIgnoreCase(String client, String supplier, Pageable pageable);

    Page<Invoice> findByDateCreatedContainingIgnoreCase(String month, Pageable pageable);
    Page<Invoice> findByClientNameAndSupplierNameAndDateCreatedContainingIgnoreCase(String client, String supplier, String month, Pageable pageable);

}
