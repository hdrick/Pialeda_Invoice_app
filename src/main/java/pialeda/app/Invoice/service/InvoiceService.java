package pialeda.app.Invoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pialeda.app.Invoice.model.Invoice;
import pialeda.app.Invoice.repository.InvoiceRepository;
import pialeda.app.Invoice.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Optional<Invoice> getInvoiceById(int id)
    {
        if (invoiceRepository.findById(id) == null )
        {
            throw new EntityNotFoundException();
        }
        Optional<Invoice> invoice = Optional.ofNullable(invoiceRepository.findById(id));
        return invoice;
    }
    public List<Invoice> getAllInvoice(){
        return invoiceRepository.findAll();
    }

    public List<Invoice> getKeyword(String query)
    {
        return invoiceRepository.findByClientNameContainingIgnoreCase(query);
    }

    public List<Invoice> getAddress(String add)
    {
        return invoiceRepository.findByClientName(add);
    }

    public List<Invoice> getClientNameOrSupplierName(String client, String supplier) {
        List<Invoice> filter = invoiceRepository.findByClientNameOrSupplierNameContaining(client, supplier);
        return filter;
    }


}
