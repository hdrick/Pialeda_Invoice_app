package pialeda.app.Invoice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GenerationType;

@Entity
@Table(name="supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, name="supplier_name")
    private String name;

    @Column(nullable = false, name="supplier_address")
    private String address;

    @Column(nullable = false, name="supplier_cityAddress")
    private String cityAddress;

    @Column(nullable = false, name="supplier_tin")
    private String tin;

    @Column(name="supplier_secNum")
    private int secNum;

    @Column(name="supplier_secYear")
    private int secYear;

    @Column(name="supplier_atp")
    private String atp;

    @Column( name="supplier_corNum")
    private int corNum;

    @Column( name="supplier_corDate")
    private String corDate;

    @Column( name="supplier_limit")
    private double limit;

    public Supplier(){}
    public Supplier(String name, String address, String cityAddress, String tin, int secNum, int secYear, String atp, int corNum, String corDate, double limit) {
        this.name = name;
        this.address = address;
        this.cityAddress = cityAddress;
        this.tin = tin;
        this.secNum = secNum;
        this.secYear = secYear;
        this.atp = atp;
        this.corNum = corNum;
        this.corDate = corDate;
        this.limit = limit;
    }

    public Supplier(String name, String address, String cityAddress, String tin) {
        this.name = name;
        this.address = address;
        this.cityAddress = cityAddress;
        this.tin = tin;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityAddress() {
        return cityAddress;
    }

    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public int getSecNum() {
        return secNum;
    }

    public void setSecNum(int secNum) {
        this.secNum = secNum;
    }

    public int getSecYear() {
        return secYear;
    }

    public void setSecYear(int secYear) {
        this.secYear = secYear;
    }

    public String getAtp() {
        return atp;
    }

    public void setAtp(String atp) {
        this.atp = atp;
    }

    public int getCorNum() {
        return corNum;
    }

    public void setCorNum(int corNum) {
        this.corNum = corNum;
    }

    public String getCorDate() {
        return corDate;
    }

    public void setCorDate(String corDate) {
        this.corDate = corDate;
    }
}
