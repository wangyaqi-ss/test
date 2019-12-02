package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.bean.CustomerBean;
import com.fh.dao.ICustomerMapper;
import com.fh.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service("customerService")
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private ICustomerMapper customerMapper;

    @Override
    @Transactional
    public CustomerBean isRegister(String phone) {
        //判断手机号是否存在
        QueryWrapper<CustomerBean> queryWrapper = new QueryWrapper<CustomerBean>();
        queryWrapper.eq("phone", phone);
        CustomerBean customerBean = customerMapper.selectOne(queryWrapper);
        if (customerBean == null){
            customerBean = new CustomerBean();
            customerBean.setModifedTime(new Date());
            customerBean.setUserStats(1);
            customerBean.setPhone(phone);
            customerBean.setCartId(UUID.randomUUID().toString().replace("-",""));
            customerMapper.insert(customerBean);
        }else{
            customerBean.setModifedTime(new Date());
            customerMapper.updateById(customerBean);
        }
        return customerBean;
    }
}































