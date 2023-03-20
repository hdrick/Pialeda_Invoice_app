package pialeda.app.Invoice.dto;

public class SupplierInfo {
    private String name;
    private String address;
    private String cityAddress;
    private String tin;
    private int secNum;
    private int secYear;
    private String atp;
    private int corNum;
    private String corDate;

    private double limit;

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
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
