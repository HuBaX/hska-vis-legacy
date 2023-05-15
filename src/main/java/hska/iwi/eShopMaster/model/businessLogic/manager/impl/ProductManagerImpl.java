package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.database.dataAccessObjects.ProductDAO;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductManagerImpl implements ProductManager {
	private ProductDAO helper;
	
	public ProductManagerImpl() {
		helper = new ProductDAO();
	}

	public List<Product> getProducts() throws Exception{
		String apiUrl = "http://product-service:8082/getProducts";
		URL url = new URL(apiUrl);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		StringBuilder response = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			response.append(line);
		}
		reader.close();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(response.toString());
		JsonNode productsNode = jsonNode.get("products");
		List<Product> products = objectMapper.readValue(productsNode.toString(), new TypeReference<List<Product>>() {});
		return products;
	}
	
	public List<Product> getProductsForSearchValues(String searchDescription,
			Double searchMinPrice, Double searchMaxPrice) throws Exception{	
			URIBuilder uriBuilder = new URIBuilder("http://product-service:8082/getProductsBySearchValues");
			if (searchDescription != null)
			uriBuilder.addParameter("details", searchDescription);
			if (searchMinPrice != null)
			uriBuilder.addParameter("minPrice", searchMinPrice.toString());
			if (searchMaxPrice != null)
			uriBuilder.addParameter("maxPrice", searchMaxPrice.toString());
			URL url = uriBuilder.build().toURL();
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
	
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuilder response = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(response.toString());
			JsonNode productsNode = jsonNode.get("products");
			List<Product> products = objectMapper.readValue(productsNode.toString(), new TypeReference<List<Product>>() {});
			return products;
	}

	public Product getProductById(int id) throws Exception{
		String apiUrl = "http://product-service:8082/getProductById?id="+id;
		URL url = new URL(apiUrl);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		StringBuilder response = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			response.append(line);
		}
		reader.close();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(response.toString());
		return objectMapper.readValue(jsonNode.toString(), new TypeReference<Product>() {});
	}

	public Product getProductByName(String name) throws Exception{
		String apiUrl = "http://product-service:8082/getProductByName?name="+name;
		URL url = new URL(apiUrl);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		StringBuilder response = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			response.append(line);
		}
		reader.close();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(response.toString());
		return objectMapper.readValue(jsonNode.toString(), new TypeReference<Product>() {});
	}
	
	public int addProduct(String name, double price, int categoryId, String details) throws Exception {
		int productId = -1;
		
		CategoryManager categoryManager = new CategoryManagerImpl();
		Category category = categoryManager.getCategory(categoryId);
		
		if(category != null){
			String productString;
			if(details == null){
				JSONObject jo = new JSONObject();
				jo.put("name", name);
				jo.put("price", price);
				jo.put("categoryId", category.getId());
				productString = jo.toString();
			} else{
				JSONObject jo = new JSONObject();
				jo.put("name", name);
				jo.put("price", price);
				jo.put("categoryId", category.getId());
				jo.put("details", details);
				productString = jo.toString();
			}
			URL url = new URL("http://product-service:8082/addProduct");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			byte[] input = productString.getBytes("utf-8");
			os.write(input, 0, input.length);
			os.flush();
			os.close();

			productId = this.getProductByName(name).getId();
			System.out.println(productId);
		}
			 
		return productId;
	}
	

	public void deleteProductById(int id) throws Exception{
		URL url = new URL("http://product-service:8082/delProductById?id="+id);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestProperty(
			"Content-Type", "application/x-www-form-urlencoded" );
		httpCon.setRequestMethod("DELETE");
		httpCon.connect();
	}

	public boolean deleteProductsByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		return false;
	}

}
