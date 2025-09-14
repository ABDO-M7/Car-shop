package service;

import dao.CustomerDao;
import model.Customer;

public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService() {
        this.customerDao = new CustomerDao();
    }

    public String addCustomer(Customer customer) {
        return customerDao.addCustomer(customer);
    }


    public void showMyCars(String id){
    customerDao.showMyCars(id);
    }
    public void showAllCustomers() {
        customerDao.showAllCustomers();
    }

    public void deleteCustomer(String id) {
        customerDao.deleteCustomer(id);
    }

    public void updateCustomer(String id, Customer customer) {
        customerDao.updateCustomer(id, customer);
    }

    public Customer findCustomer(String id) {
        return customerDao.findCustomer(id);
    }

    public void showMyProfile(String id) {
        customerDao.showMyProfile(id);
    }
} 