package com.apigateway.entity;

import javax.persistence.*;

@Entity
@Table(name="tb_api_gateway",schema="public")
public class ApiGatewayEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="client")
	private String client;
	
	@Column(name="end_point")
	private String end_point;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getEnd_point() {
		return end_point;
	}

	public void setEnd_point(String end_point) {
		this.end_point = end_point;
	}
	
	
}
