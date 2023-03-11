package pialeda.app.Invoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pialeda.app.Invoice.model.Invoice;
import pialeda.app.Invoice.model.InvoiceProductInfo;
import pialeda.app.Invoice.repository.InvoiceRepository;
import pialeda.app.Invoice.dto.InvoiceInfo;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
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

    public void createInvoice(InvoiceInfo invoiceInfo){
        Invoice invoice = new Invoice();
        List<InvoiceProductInfo> productList = new ArrayList<>();

        invoice.setInvoiceNum(invoiceInfo.getInvoiceNum());
        invoice.setPoNum(invoiceInfo.getPoNum());
        invoice.setDateCreated(invoiceInfo.getDateCreated());
        invoice.setClientContactPerson(invoiceInfo.getClientContactPerson());

        invoice.setSupplierName(invoiceInfo.getSupplierName());
        invoice.setSupplierAddress(invoiceInfo.getSupplierAddress());
        invoice.setSupplierTin(invoiceInfo.getSupplierTin());

        invoice.setClientName(invoiceInfo.getClientName());
        invoice.setClientTin(invoiceInfo.getClientTin());
        invoice.setClientAddress(invoiceInfo.getClientAddress());
        invoice.setClientBusStyle(invoiceInfo.getClientBusStyle());
        invoice.setClientTerms(invoiceInfo.getClientTerms());

        invoice.setGrandTotal(invoiceInfo.getGrandTotal());
        invoice.setCashier(invoiceInfo.getCashier());

        invoiceRepository.save(invoice);
    }
}
