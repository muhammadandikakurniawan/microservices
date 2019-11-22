package com.apigateway.dao;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.apigateway.entity.ApiGatewayEntity;
import com.apigateway.repository.ApiGatewayRepository;

@Service
public class ApiGatewayDao {
	
	@Autowired
	private ApiGatewayRepository apiGatewayRepo;
	
	@Autowired
	private HttpServletRequest httpReq;
	
	@Async
	@Transactional(rollbackFor = {Exception.class},propagation=Propagation.REQUIRED)
	public Integer AddLog() {
		ApiGatewayEntity dataLog = new ApiGatewayEntity();
		dataLog.setClient(this.httpReq.getHeader("origin"));
		dataLog.setEnd_point(this.httpReq.getRequestURI());
		Integer res = 0;
		try {
			this.apiGatewayRepo.save(dataLog);
			res = 1;
		}catch(Exception e) {
			res = 0;
			TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
			TransactionInterceptor.currentTransactionStatus().flush();
		}
		return res;
	}
}
