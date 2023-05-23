package hska.iwi.eShopMaster.model.database.dataobjects;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import hska.iwi.eShopMaster.model.businessLogic.manager.impl.CategoryManagerImpl;

/**
 * This class contains details about products.
 */
@Entity
@Table(name = "product")
public class Product implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private double price;

	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "details")
	private String details;

	public Product() {
	}

	@JsonCreator
	public Product(@JsonProperty("CategoryID")int category, @JsonProperty("Name")String name, @JsonProperty("Price")double price, @JsonProperty("Details")String details, @JsonProperty("ID") int id) throws Exception{
		this.name = name;
		this.price = price;
		this.details = details;
		this.id = id;
		CategoryManagerImpl categoryManager = new CategoryManagerImpl();
		this.category = categoryManager.getCategory(category);
	}

	public Product(String name, double price, Category category) {
		this.name = name;
		this.price = price;
		this.category = category;
	}

	public Product(String name, double price, Category category, String details) {
		this.name = name;
		this.price = price;
		this.category = category;
		this.details = details;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
