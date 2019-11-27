package com.apigateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apigateway.entity.ApiGatewayEntity;

@Repository("ApiGatewayRepository")
public interface ApiGatewayRepository extends JpaRepository<ApiGatewayEntity,Integer>{

}
