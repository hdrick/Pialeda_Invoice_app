package pialeda.app.Invoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pialeda.app.Invoice.dto.InvoiceProdInfo;
import pialeda.app.Invoice.dto.InvoiceWrapper;
import pialeda.app.Invoice.model.Invoice;
import pialeda.app.Invoice.model.InvoiceProductInfo;
import pialeda.app.Invoice.repository.InvoiceProdInfoRepository;
import pialeda.app.Invoice.repository.InvoiceRepository;
import pialeda.app.Invoice.dto.InvoiceInfo;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;


@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceProdInfoRepository invoiceProdInfoRepository;

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

    public void createInvoice(InvoiceWrapper wrapper){
        Invoice invoice = new Invoice();
        InvoiceInfo invoiceInfo = wrapper.getInvoiceInfo();
        InvoiceProdInfo invoiceProdInfo = wrapper.getInvoiceProdInfo();


        //Insert invoice info
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

        //insertNotNullItem: function to check if each row has value and needed to insert in specific invoice
        insertNotNullItem(invoiceProdInfo.getQty1(),invoiceProdInfo.getUnit1(),
                 invoiceProdInfo.getArticles1(),invoiceProdInfo.getUnitPrice1(),
                 invoiceProdInfo.getAmount1(),invoiceInfo.getInvoiceNum(),invoiceInfo.getPoNum());

        insertNotNullItem(invoiceProdInfo.getQty2(),invoiceProdInfo.getUnit2(),
                invoiceProdInfo.getArticles2(),invoiceProdInfo.getUnitPrice2(),
                invoiceProdInfo.getAmount2(),invoiceInfo.getInvoiceNum(),invoiceInfo.getPoNum());

        insertNotNullItem(invoiceProdInfo.getQty3(),invoiceProdInfo.getUnit3(),
                invoiceProdInfo.getArticles3(),invoiceProdInfo.getUnitPrice3(),
                invoiceProdInfo.getAmount3(),invoiceInfo.getInvoiceNum(),invoiceInfo.getPoNum());

        insertNotNullItem(invoiceProdInfo.getQty4(),invoiceProdInfo.getUnit4(),
                invoiceProdInfo.getArticles4(),invoiceProdInfo.getUnitPrice4(),
                invoiceProdInfo.getAmount4(),invoiceInfo.getInvoiceNum(),invoiceInfo.getPoNum());

        insertNotNullItem(invoiceProdInfo.getQty5(),invoiceProdInfo.getUnit5(),
                invoiceProdInfo.getArticles5(),invoiceProdInfo.getUnitPrice5(),
                invoiceProdInfo.getAmount5(),invoiceInfo.getInvoiceNum(),invoiceInfo.getPoNum());

        insertNotNullItem(invoiceProdInfo.getQty6(),invoiceProdInfo.getUnit6(),
                invoiceProdInfo.getArticles6(),invoiceProdInfo.getUnitPrice6(),
                invoiceProdInfo.getAmount6(),invoiceInfo.getInvoiceNum(),invoiceInfo.getPoNum());

        insertNotNullItem(invoiceProdInfo.getQty7(),invoiceProdInfo.getUnit7(),
                invoiceProdInfo.getArticles7(),invoiceProdInfo.getUnitPrice7(),
                invoiceProdInfo.getAmount7(),invoiceInfo.getInvoiceNum(),invoiceInfo.getPoNum());

        insertNotNullItem(invoiceProdInfo.getQty8(),invoiceProdInfo.getUnit8(),
                invoiceProdInfo.getArticles8(),invoiceProdInfo.getUnitPrice8(),
                invoiceProdInfo.getAmount8(),invoiceInfo.getInvoiceNum(),invoiceInfo.getPoNum());

        insertNotNullItem(invoiceProdInfo.getQty9(),invoiceProdInfo.getUnit9(),
                invoiceProdInfo.getArticles9(),invoiceProdInfo.getUnitPrice9(),
                invoiceProdInfo.getAmount9(),invoiceInfo.getInvoiceNum(),invoiceInfo.getPoNum());

        insertNotNullItem(invoiceProdInfo.getQty10(),invoiceProdInfo.getUnit10(),
                invoiceProdInfo.getArticles10(),invoiceProdInfo.getUnitPrice10(),
                invoiceProdInfo.getAmount10(),invoiceInfo.getInvoiceNum(),invoiceInfo.getPoNum());

        invoiceRepository.save(invoice);
    }

    public void insertNotNullItem(int qty, String unit, String articles, double unitPrice, double amount,String invNum, String poNum){
        InvoiceProductInfo invoiceProduct = new InvoiceProductInfo();
        if(qty !=0 && !(unit.isEmpty() ||  articles.isEmpty()) && unitPrice!=0.0){
            invoiceProduct.setInvoiceNumber(invNum);
            invoiceProduct.setPurchaseOrderNumber(poNum);
            invoiceProduct.setQty(qty);
            invoiceProduct.setUnit(unit);
            invoiceProduct.setArticles(articles);
            invoiceProduct.setUnitPrice(unitPrice);
            invoiceProduct.setAmount(amount);

            invoiceProdInfoRepository.save(invoiceProduct);
        }
    }

    public long getInvoiceCunt(){
        return invoiceRepository.count();
    }

    public double getSuppTotalLimit(String suppName){
        return invoiceRepository.findSumLimitByName(suppName);
    }
}
