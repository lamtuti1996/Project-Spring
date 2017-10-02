package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.model.OrderDetail;

public interface OrderDetailRespository extends JpaRepository<OrderDetail, Integer> {

}
