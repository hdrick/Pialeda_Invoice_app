package pialeda.app.Invoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pialeda.app.Invoice.dto.OfficialReceiptInfo;
import pialeda.app.Invoice.model.OfficialReceipt;
import pialeda.app.Invoice.repository.OfficialRecptRepository;

@Service
public class OfficialRecptService {
    @Autowired
    private OfficialRecptRepository officialRecptRepository;

    public void createOR(int orNum,OfficialReceiptInfo orDTO){
        OfficialReceipt or = new OfficialReceipt();

        or.setSupplierName(orDTO.getSupplierName());
        or.setSupplierAddress(orDTO.getSupplierAddress());
        or.setOfficialReceiptNum(orNum);
        or.setSupplierTin(orDTO.getSupplierTin());

        or.setInvoiceNum1(orDTO.getInvoiceNum1());
        or.setInvoiceNum2(orDTO.getInvoiceNum2());
        or.setInvoiceNum3(orDTO.getInvoiceNum3());
        or.setInvoiceNum4(orDTO.getInvoiceNum4());

        or.setInvoiceAmount1(orDTO.getInvoiceAmount1());
        or.setInvoiceAmount2(orDTO.getInvoiceAmount2());
        or.setInvoiceAmount3(orDTO.getInvoiceAmount3());
        or.setInvoiceAmount4(orDTO.getInvoiceAmount4());

        or.setTotalSalesVat(orDTO.getTotalSalesVat());
        or.setLessVat(orDTO.getLessVat());
        or.setTotal(orDTO.getTotal());
        or.setLessWithHoldTax(orDTO.getLessWithHoldTax());
        or.setAmountDue(orDTO.getAmountDue());

        or.setVatableSales(orDTO.getVatableSales());
        or.setVatExemptSales(orDTO.getVatExemptSales());
        or.setZeroRatedSales(orDTO.getZeroRatedSales());
        or.setVatAmount(orDTO.getVatAmount());
        or.setTotalSales(orDTO.getTotalSales());

        or.setCash(orDTO.getCash());
        or.setCheckNo(orDTO.getCheckNo());
        or.setAmount(orDTO.getAmount());

        or.setOfficialReceiptDate(orDTO.getOfficialReceiptDate());
        or.setRecvFrom(orDTO.getRecvFrom());
        or.setClientAddress(orDTO.getClientAddress());
        or.setClientTin(orDTO.getClientTin());
        or.setClientBus(orDTO.getClientBus());
        or.setClientSumOf(orDTO.getClientSumOf());
        or.setClientPayment(orDTO.getClientPayment());
        or.setPartialPaymentFor(orDTO.getPartialPaymentFor());

        or.setCashierName(orDTO.getCashierName());
        this.officialRecptRepository.save(or);
    }

    public long getOrCount(){
        return officialRecptRepository.count();
    }

}
