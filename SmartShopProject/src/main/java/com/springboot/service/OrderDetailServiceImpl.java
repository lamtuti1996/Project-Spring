package com.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.dao.OrderDetailRespository;
import com.springboot.model.OrderDetail;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	OrderDetailRespository respository;

	@Override
	public void saveOrderDetail(OrderDetail od) {
		respository.save(od);

	}

}
