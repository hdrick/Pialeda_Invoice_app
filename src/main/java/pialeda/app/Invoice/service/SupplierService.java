package pialeda.app.Invoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pialeda.app.Invoice.dto.ClientInfo;
import pialeda.app.Invoice.dto.SupplierInfo;
import pialeda.app.Invoice.model.Client;
import pialeda.app.Invoice.model.Supplier;
import pialeda.app.Invoice.repository.SupplierRepository;

import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    public void createSupplier(SupplierInfo supplierInfo){
        Supplier supplier = new Supplier();

        supplier.setName(supplierInfo.getName());
        supplier.setAddress(supplierInfo.getAddress());
        supplier.setCityAddress(supplierInfo.getCityAddress());
        supplier.setTin(supplierInfo.getTin());
        supplier.setSecNum(supplierInfo.getSecNum());
        supplier.setSecYear(supplierInfo.getSecYear());
        supplier.setAtp(supplierInfo.getAtp());
        supplier.setCorNum(supplierInfo.getCorNum());
        supplier.setCorDate(supplierInfo.getCorDate());
        supplier.setLimit(supplierInfo.getLimit());

        supplierRepository.save(supplier);
    }

    public long getSupplierCount(){
        return supplierRepository.count();
    }

    public List<Supplier> getAllSupplier(){
        return supplierRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
    public Supplier findByName(String name){
        return supplierRepository.findByName(name);
    }

    public double findLimitByName(String name){
        Supplier supp = supplierRepository.findByName(name);

        return supp.getLimit();
    }

}
