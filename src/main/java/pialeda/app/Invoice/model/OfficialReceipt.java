package pialeda.app.Invoice.model;

import javax.persistence.*;

@Entity
@Table(name="official_receipt")
public class OfficialReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String supplierName;
    private String supplierAddress;
    private int officialReceiptNum;
    private String supplierTin;

    private String invoiceNum1;
    private String invoiceNum2;
    private String invoiceNum3;
    private String invoiceNum4;

    private double invoiceAmount1;
    private double invoiceAmount2;
    private double invoiceAmount3;
    private double invoiceAmount4;

    private double totalSalesVat;
    private double lessVat;
    private double total;
    private double lessWithHoldTax;
    private double amountDue;

    private double vatableSales;
    private double vatExemptSales;
    private double zeroRatedSales;
    private double vatAmount;
    private double totalSales;

    private double cash;
    private double checkNo;
    private double amount;

    private String officialReceiptDate;
    private String recvFrom;
    private String clientAddress;
    private String clientTin;
    private String clientBus;
    private double clientSumOf;
    private double clientPayment;
    private String partialPaymentFor;

    private String cashierName;

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public int getOfficialReceiptNum() {
        return officialReceiptNum;
    }

    public void setOfficialReceiptNum(int officialReceiptNum) {
        this.officialReceiptNum = officialReceiptNum;
    }

    public String getSupplierTin() {
        return supplierTin;
    }

    public void setSupplierTin(String supplierTin) {
        this.supplierTin = supplierTin;
    }

    public String getInvoiceNum1() {
        return invoiceNum1;
    }

    public void setInvoiceNum1(String invoiceNum1) {
        this.invoiceNum1 = invoiceNum1;
    }

    public String getInvoiceNum2() {
        return invoiceNum2;
    }

    public void setInvoiceNum2(String invoiceNum2) {
        this.invoiceNum2 = invoiceNum2;
    }

    public String getInvoiceNum3() {
        return invoiceNum3;
    }

    public void setInvoiceNum3(String invoiceNum3) {
        this.invoiceNum3 = invoiceNum3;
    }

    public String getInvoiceNum4() {
        return invoiceNum4;
    }

    public void setInvoiceNum4(String invoiceNum4) {
        this.invoiceNum4 = invoiceNum4;
    }

    public double getInvoiceAmount1() {
        return invoiceAmount1;
    }

    public void setInvoiceAmount1(double invoiceAmount1) {
        this.invoiceAmount1 = invoiceAmount1;
    }

    public double getInvoiceAmount2() {
        return invoiceAmount2;
    }

    public void setInvoiceAmount2(double invoiceAmount2) {
        this.invoiceAmount2 = invoiceAmount2;
    }

    public double getInvoiceAmount3() {
        return invoiceAmount3;
    }

    public void setInvoiceAmount3(double invoiceAmount3) {
        this.invoiceAmount3 = invoiceAmount3;
    }

    public double getInvoiceAmount4() {
        return invoiceAmount4;
    }

    public void setInvoiceAmount4(double invoiceAmount4) {
        this.invoiceAmount4 = invoiceAmount4;
    }

    public double getTotalSalesVat() {
        return totalSalesVat;
    }

    public void setTotalSalesVat(double totalSalesVat) {
        this.totalSalesVat = totalSalesVat;
    }

    public double getLessVat() {
        return lessVat;
    }

    public void setLessVat(double lessVat) {
        this.lessVat = lessVat;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getLessWithHoldTax() {
        return lessWithHoldTax;
    }

    public void setLessWithHoldTax(double lessWithHoldTax) {
        this.lessWithHoldTax = lessWithHoldTax;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public double getVatableSales() {
        return vatableSales;
    }

    public void setVatableSales(double vatableSales) {
        this.vatableSales = vatableSales;
    }

    public double getVatExemptSales() {
        return vatExemptSales;
    }

    public void setVatExemptSales(double vatExemptSales) {
        this.vatExemptSales = vatExemptSales;
    }

    public double getZeroRatedSales() {
        return zeroRatedSales;
    }

    public void setZeroRatedSales(double zeroRatedSales) {
        this.zeroRatedSales = zeroRatedSales;
    }

    public double getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(double vatAmount) {
        this.vatAmount = vatAmount;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(double checkNo) {
        this.checkNo = checkNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getOfficialReceiptDate() {
        return officialReceiptDate;
    }

    public void setOfficialReceiptDate(String officialReceiptDate) {
        this.officialReceiptDate = officialReceiptDate;
    }

    public String getRecvFrom() {
        return recvFrom;
    }

    public void setRecvFrom(String recvFrom) {
        this.recvFrom = recvFrom;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientTin() {
        return clientTin;
    }

    public void setClientTin(String clientTin) {
        this.clientTin = clientTin;
    }

    public String getClientBus() {
        return clientBus;
    }

    public void setClientBus(String clientBus) {
        this.clientBus = clientBus;
    }

    public double getClientSumOf() {
        return clientSumOf;
    }

    public void setClientSumOf(double clientSumOf) {
        this.clientSumOf = clientSumOf;
    }

    public double getClientPayment() {
        return clientPayment;
    }

    public void setClientPayment(double clientPayment) {
        this.clientPayment = clientPayment;
    }

    public String getPartialPaymentFor() {
        return partialPaymentFor;
    }

    public void setPartialPaymentFor(String partialPaymentFor) {
        this.partialPaymentFor = partialPaymentFor;
    }
}
