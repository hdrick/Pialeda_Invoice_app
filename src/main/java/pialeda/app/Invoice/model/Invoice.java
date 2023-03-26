package pialeda.app.Invoice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GenerationType;

@Entity
@Table(name="invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name="supplier_invoice_number")
    private String invoiceNum;
    @Column(nullable = false, name="invoice_purchase_order_number")
    private String poNum;
    @Column(nullable = false, name="invoice_date_created")
    private String dateCreated;
    @Column(nullable = false, name="invoice_client_contact_person")
    private String clientContactPerson;

    private String supplierName;
    private String supplierAddress;
    private String supplierTin;

    private String clientName;
    private String clientTin;
    private String clientAddress;
    private String clientBusStyle;
    private String clientTerms;

    private double grandTotal;
    private String cashier;

    public Invoice(){}
    public Invoice(String invoiceNum, String poNum, String dateCreated, String clientContactPerson, String supplierName, String supplierAddress, String supplierTin, String clientName, String clientTin, String clientAddress, String clientBusStyle, String clientTerms, double grandTotal, String cashier) {
        this.invoiceNum = invoiceNum;
        this.poNum = poNum;
        this.dateCreated = dateCreated;
        this.clientContactPerson = clientContactPerson;
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierTin = supplierTin;
        this.clientName = clientName;
        this.clientTin = clientTin;
        this.clientAddress = clientAddress;
        this.clientBusStyle = clientBusStyle;
        this.clientTerms = clientTerms;
        this.grandTotal = grandTotal;
        this.cashier = cashier;
    }

    public Invoice(String invoiceNum) {
    }

    public Invoice(String invoiceNum, String poNum, String dateCreated, String supplierName, String clientName, String clientContactPerson) {
        this.invoiceNum = invoiceNum;
        this.poNum = poNum;
        this.dateCreated = dateCreated;
        this.supplierName = supplierName;
        this.clientName = clientName;
        this.clientContactPerson = clientContactPerson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getPoNum() {
        return poNum;
    }

    public void setPoNum(String poNum) {
        this.poNum = poNum;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getClientContactPerson() {
        return clientContactPerson;
    }

    public void setClientContactPerson(String clientContactPerson) {
        this.clientContactPerson = clientContactPerson;
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

    public String getSupplierTin() {
        return supplierTin;
    }

    public void setSupplierTin(String supplierTin) {
        this.supplierTin = supplierTin;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientTin() {
        return clientTin;
    }

    public void setClientTin(String clientTin) {
        this.clientTin = clientTin;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientBusStyle() {
        return clientBusStyle;
    }

    public void setClientBusStyle(String clientBusStyle) {
        this.clientBusStyle = clientBusStyle;
    }

    public String getClientTerms() {
        return clientTerms;
    }

    public void setClientTerms(String clientTerms) {
        this.clientTerms = clientTerms;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }
}


