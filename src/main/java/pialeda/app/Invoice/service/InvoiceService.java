package pialeda.app.Invoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pialeda.app.Invoice.dto.InvoiceProdInfo;
import pialeda.app.Invoice.dto.InvoiceWrapper;
import pialeda.app.Invoice.model.Client;
import pialeda.app.Invoice.model.Invoice;
import pialeda.app.Invoice.model.InvoiceProductInfo;
import pialeda.app.Invoice.model.Supplier;
import pialeda.app.Invoice.repository.ClientRepository;
import pialeda.app.Invoice.repository.InvoiceProdInfoRepository;
import pialeda.app.Invoice.repository.InvoiceRepository;
import pialeda.app.Invoice.dto.InvoiceInfo;
import pialeda.app.Invoice.repository.SupplierRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceProdInfoRepository invoiceProdInfoRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ClientRepository clientRepository;

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

    public Page<Invoice> findPage(int pageNumber)
    {
        Pageable pageable = PageRequest.of(pageNumber -1, 7);
        return invoiceRepository.findAll(pageable);
    }
    public Page<Invoice> searchPageByKeyword(String keyword, int pageNumber)
    {
        Pageable pageable = PageRequest.of(pageNumber -1, 7);
        return invoiceRepository.findByKeyword(keyword, pageable);
    }
    public Page<Invoice> filterPageByClient(String name, int pageNumber)
    {
        Pageable pageable = PageRequest.of(pageNumber -1, 7);
        return invoiceRepository.findByClientNameContainingIgnoreCase(name, pageable);
    }
    public Page<Invoice> filterPageByClientSortByMonth(String name, String month, int pageNumber)
    {
        Sort sort = Sort.by("dateCreated");
        sort = sort.ascending();
        Pageable pageable = PageRequest.of(pageNumber -1, 7, sort);
        return invoiceRepository.findByClientNameAndDateCreatedContainingIgnoreCase(name, month, pageable);
    }
    public Page<Invoice> filterPageBySupplierSortByMonth(String name, String month, int pageNumber)
    {
        Sort sort = Sort.by("dateCreated");
        sort = sort.ascending();
        Pageable pageable = PageRequest.of(pageNumber -1, 7, sort);
        return invoiceRepository.findBySupplierNameAndDateCreatedContainingIgnoreCase(name, month, pageable);
    }
    public Page<Invoice> filterPageBySupplier(String name, int pageNumber)
    {
        Pageable pageable = PageRequest.of(pageNumber -1, 7);
        return invoiceRepository.findBySupplierNameContainingIgnoreCase(name, pageable);
    }
    public Page<Invoice> filterPageByClientAndSupplier(String client, String supplier, int pageNumber)
    {
        Pageable pageable = PageRequest.of(pageNumber -1, 7);
        return invoiceRepository.findByClientNameAndSupplierNameContainingIgnoreCase(client, supplier, pageable);
    }

    public Page<Invoice> filterPageByClientAndSupplierSortByMonth(String client, String supplier, String month, int pageNumber)
    {
        Sort sort = Sort.by("dateCreated");
        sort = sort.ascending();
        Pageable pageable = PageRequest.of(pageNumber -1, 7, sort);
        return invoiceRepository.findByClientNameAndSupplierNameAndDateCreatedContainingIgnoreCase(client, supplier, month, pageable);
    }
    public Page<Invoice> sortByMonthAsc(String month, int pageNumber)
    {

        Sort sort = Sort.by("dateCreated");
        sort = sort.ascending();
        Pageable pageable = PageRequest.of(pageNumber -1, 7, sort);
        return invoiceRepository.findByDateCreatedContainingIgnoreCase(month, pageable);
    }


//    DRICKS...
    public Page<Invoice> getInvoicesPaginated(int currentPage, int size){
        Pageable p = PageRequest.of(currentPage, size);
        return invoiceRepository.findAll(p);
    }

    public Invoice getInvByIdAndFillFields(int id){
        return invoiceRepository.findById(id);
    }

    public List<InvoiceProductInfo> getAllProdByInvNum(String invNum){
        return invoiceProdInfoRepository.findByInvoiceNumber(invNum);
    }

    public boolean updateInvoices(String invoiceNumber, String dateCreated,
                                 String supplierName, String clientName, String clientContactPerson,
                                 String totalAmt, List<String> qtyList, List<String> unitList, List<String> articlesList,
                                 List<String> unitPriceList, List<String> amountList, List<String> prodIdList){

       try {
           Invoice invDb = invoiceRepository.findByInvoiceNum(invoiceNumber);
           Supplier supplier = supplierRepository.findByName(supplierName);
           Client client = clientRepository.findByName(clientName);
           List<InvoiceProductInfo> productInfo = invoiceProdInfoRepository.findByInvoiceNumber(invoiceNumber);

           invDb.setDateCreated(dateCreated);

           invDb.setSupplierName(supplier.getName());
           invDb.setSupplierAddress(supplier.getAddress());
           invDb.setSupplierTin(supplier.getTin());

           invDb.setClientName(client.getName());
           invDb.setClientContactPerson(clientContactPerson);
           invDb.setClientTin(client.getTin());
           invDb.setClientAddress(client.getAddress());

           String totalAmtNew = totalAmt.replace(",", "");
           invDb.setGrandTotal(Double.parseDouble(totalAmtNew));

           // Update the product info for each product
           for (int i = 0; i < prodIdList.size(); i++) {
               String prodId = prodIdList.get(i);
               for (InvoiceProductInfo prodInfo : productInfo) {
                   if (String.valueOf(prodInfo.getId()).equals(prodId)) {
                       prodInfo.setQty(Integer.parseInt(qtyList.get(i)));
                       prodInfo.setUnit(unitList.get(i));
                       prodInfo.setArticles(articlesList.get(i));
                       prodInfo.setUnitPrice(Double.parseDouble(unitPriceList.get(i)));
                       prodInfo.setAmount(Double.parseDouble(amountList.get(i)));
                       break;
                   }
               }
           }

           invoiceRepository.save(invDb);
           invoiceProdInfoRepository.saveAll(productInfo);
           return true;
       }catch (Exception e){
           e.printStackTrace();
           return false;
       }
    }

//    public Page<Invoice> findAllWithSort(String field, String direction, int pageNumber)
//    {
//        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name())?
//                Sort.by(field).ascending(): Sort.by(field).descending();
//
//        Pageable pageable = PageRequest.of(pageNumber -1, 7, sort);
//
//        return invoiceRepository.findAll(pageable);
//    }

}
