package com.example.demo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

import java.lang.annotation.Annotation;

/**
 * 商品bean，对应t_product
 */
@Setter
@Getter
@Document(indexName = "product")
public class Product {
	@Id
	private String  productId;

	@Field(type = FieldType.Text, analyzer = "ik_max_word")
	private String  productName;

	@Field(type = FieldType.Double)
	private Double  productPrice;

	@Field(type = FieldType.Text, analyzer = "ik_max_word")
	private String  productCategory;

	@Field(type = FieldType.Keyword, index = false)
	private String  productImgurl;

	@Field(type = FieldType.Integer)
	private Integer productNum = 0;

	@Field(type = FieldType.Text, analyzer = "ik_max_word")
	private String  productDescription;

	public Product(String productId, String productName, Double productPrice, String productCategory, Integer productNum, String productDescription, String productImgurl) {
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productCategory = productCategory;
		this.productNum = productNum;
		this.productDescription = productDescription;
		this.productImgurl = productImgurl;
	}

	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "{" +
					"\"productId\": \"" + this.productId + "\"," +
					"\"productName\": \"" + this.productName + "\"," +
					"\"productPrice\": " + this.productPrice + "," +
					"\"productCategory\": \"" + this.productCategory + "\"," +
					"\"productImgurl\": \"" + this.productImgurl + "\"," +
					"\"productNum\": " + this.productNum + "," +
					"\"productDescription\": \"" + this.productDescription + "\"," +
					"}";
		}
	}
}
