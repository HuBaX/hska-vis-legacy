package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CategoryManagerImpl implements CategoryManager{
	
	public CategoryManagerImpl() {
	}

	public List<Category> getCategories() throws Exception {
		// Start the timer
		long startTime = System.currentTimeMillis();

		String apiUrl = "http://category-service:8081/getCategories";
		URL url = new URL(apiUrl);
		StringBuilder response = this.getResponse(url);

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(response.toString());
		JsonNode categoriesNode = jsonNode.get("categories");
		List<Category> categories = objectMapper.readValue(categoriesNode.toString(), new TypeReference<List<Category>>() {});
		// Calculate the response time
		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;
		System.out.printf("Response Time for getCategories: %d %n", responseTime);
		return categories;
	}

	public Category getCategory(int id) throws Exception{
		long startTime = System.currentTimeMillis();
		String apiUrl = "http://category-service:8081/getCategory?id="+id;
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
		JsonNode categoryNode = jsonNode.get("category");
		Category category = objectMapper.readValue(categoryNode.toString(),  new TypeReference<Category>() {});
		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;
		System.out.printf("Response Time for getCategory: %d %n", responseTime);
		return category;
	}

	public Category getCategoryByName(String name) throws Exception{
		long startTime = System.currentTimeMillis();
		String apiUrl = "http://category-service:8081/getCategory?name="+name;
		URL url = new URL(apiUrl);
		StringBuilder response = this.getResponse(url);
		
		Category category = this.buildCategory(response);
		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;
		System.out.printf("Response Time for getCategoryByName: %d %n", responseTime);
		return category;
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

	private Category buildCategory(StringBuilder response) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(response.toString());
		Category category = objectMapper.readValue(jsonNode.toString(),  new TypeReference<Category>() {});
		return category;
	}

	public void addCategory(String name) throws Exception{
		long startTime = System.currentTimeMillis();
		URL url = new URL("http://category-service:8081/addCategory");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		JSONObject jo = new JSONObject();
		jo.put("name", name);
		String jsonInputString = jo.toString();
		OutputStream os = con.getOutputStream();
		byte[] input = jsonInputString.getBytes("utf-8");
		os.write(input, 0, input.length);
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
		StringBuilder res = new StringBuilder();
		String responseLine = null;
		while((responseLine = br.readLine()) != null){
			res.append(responseLine.trim());
		}
		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;
		System.out.printf("Response Time for addCategory: %d %n", responseTime);
	}

	public void delCategory(Category cat) throws Exception{
		long startTime = System.currentTimeMillis();
		this.delCategoryById(cat.getId());
		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;
		System.out.printf("Response Time for delCategory: %d %n", responseTime);
	}

	public void delCategoryById(int id) throws Exception {
		long startTime = System.currentTimeMillis();
		URL url = new URL("http://category-service:8081/delCategoryById?id="+id);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("DELETE");
		connection.getResponseCode();
		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;
		System.out.printf("Response Time for delCategoryById: %d %n", responseTime);
	}
}
