package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
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
	
	public ProductManagerImpl() {
	}

	public List<Product> getProducts() throws Exception{
		long startTime = System.currentTimeMillis();
		String apiUrl = "http://product-service:8082/getProducts";
		URL url = new URL(apiUrl);
		StringBuilder response = this.getResponse(url);

		List<Product> products = this.buildProductList(response);
		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;
		System.out.printf("Response Time for getProducts: %d %n", responseTime);
		return products;
	}
	
	public List<Product> getProductsForSearchValues(String searchDescription,
			Double searchMinPrice, Double searchMaxPrice) throws Exception{	
			long startTime = System.currentTimeMillis();
			URIBuilder uriBuilder = new URIBuilder("http://product-service:8082/getProductsBySearchValues");
			if (searchDescription != null)
			uriBuilder.addParameter("details", searchDescription);
			if (searchMinPrice != null)
			uriBuilder.addParameter("minPrice", searchMinPrice.toString());
			if (searchMaxPrice != null)
			uriBuilder.addParameter("maxPrice", searchMaxPrice.toString());
			URL url = uriBuilder.build().toURL();
			StringBuilder response = this.getResponse(url);
			
			List<Product> products = this.buildProductList(response);
			long endTime = System.currentTimeMillis();
			long responseTime = endTime - startTime;
			System.out.printf("Response Time for getProductsForSearchValues: %d %n", responseTime);
			return products;
	}

	public Product getProductById(int id) throws Exception{
		long startTime = System.currentTimeMillis();
		String apiUrl = "http://product-service:8082/getProduct?id="+id;
		URL url = new URL(apiUrl);
		StringBuilder response = this.getResponse(url);
		System.out.println("GetProductById: " + response.toString());
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(response.toString());
		JsonNode productsNode = jsonNode.get("product");
		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;
		System.out.printf("Response Time for getProductById: %d %n", responseTime);
		return objectMapper.readValue(productsNode.toString(), new TypeReference<Product>() {});
	}

	public Product getProductByName(String name) throws Exception{
		long startTime = System.currentTimeMillis();
		String apiUrl = "http://product-service:8082/getProductByName?name="+name;
		URL url = new URL(apiUrl);
		StringBuilder response = this.getResponse(url);

		List<Product> products = this.buildProductList(response);
		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;
		System.out.printf("Response Time for getProductByName: %d %n", responseTime);
		return products.get(0);
	}

	private StringBuilder getResponse(URL url) throws Exception{
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		StringBuilder response = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			response.append(line);
		}
		reader.close();

		return response;
	}

	private List<Product> buildProductList(StringBuilder response) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(response.toString());
		JsonNode productsNode = jsonNode.get("products");
		List<Product> products = objectMapper.readValue(productsNode.toString(), new TypeReference<List<Product>>() {});
		return products;
	}
	
	public int addProduct(String name, double price, int categoryId, String details) throws Exception {
		long startTime = System.currentTimeMillis();
		int productId = -1;
		
		CategoryManager categoryManager = new CategoryManagerImpl();
		Category category = categoryManager.getCategory(categoryId);
		
		if(category != null){
			JSONObject jo = new JSONObject();
			jo.put("name", name);
			jo.put("price", price);
			jo.put("categoryId", category.getId());
			if(details != null){
				jo.put("details", details);
			}
			String productString = jo.toString();
			
			URL url = new URL("http://product-service:8082/addProduct");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			byte[] input = productString.getBytes("utf-8");
			os.write(input, 0, input.length);
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			StringBuilder res = new StringBuilder();
			String responseLine = null;
			while((responseLine = br.readLine()) != null){
				res.append(responseLine.trim());
			}  
			productId = this.getProductByName(name).getId();
		}
		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;
		System.out.printf("Response Time for addProduct: %d %n", responseTime);	 
		return productId;
	}
	

	public void deleteProductById(int id) throws Exception{
		long startTime = System.currentTimeMillis();
		URL url = new URL("http://product-service:8082/delProductById?id="+id);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("DELETE");
		connection.getResponseCode();
		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;
		System.out.printf("Response Time for deleteProductById: %d %n", responseTime);
	}

	public boolean deleteProductsByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		return false;
	}

}
