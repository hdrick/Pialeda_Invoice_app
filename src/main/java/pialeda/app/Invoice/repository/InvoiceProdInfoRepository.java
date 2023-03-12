package pialeda.app.Invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pialeda.app.Invoice.model.InvoiceProductInfo;

@Repository
public interface InvoiceProdInfoRepository extends JpaRepository<InvoiceProductInfo, Integer>{

}
