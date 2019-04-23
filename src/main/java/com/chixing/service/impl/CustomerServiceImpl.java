package com.chixing.service.impl;

import com.chixing.dao.CustomerDao;
import com.chixing.entity.Customer;
import com.chixing.entity.CustomerExample;
import com.chixing.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerDao customerDao;
    public Customer getByTelno(long telno){
        CustomerExample example = new CustomerExample();
        example.createCriteria().andCustTelnoEqualTo(telno);
        List<Customer> customerList = customerDao.selectByExample(example);
        if(customerList == null || customerList.size()==0){//List为空
            return null;
        }else {
            return customerList.get(0);
        }

    }

    @Override
    public boolean save(Customer customer)
    {
        return customerDao.insert(customer)>0;
    }

    @Override
    public Customer getById(int custId) {
        return customerDao.selectByPrimaryKey(custId);
    }
}
