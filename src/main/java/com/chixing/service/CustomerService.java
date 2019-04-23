package com.chixing.service;

import com.chixing.dao.CustomerDao;
import com.chixing.entity.Customer;
import com.chixing.entity.CustomerExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CustomerService {
    public Customer getByTelno(long telno);
    public boolean save(Customer customer);
    public Customer getById(int custId);
}
