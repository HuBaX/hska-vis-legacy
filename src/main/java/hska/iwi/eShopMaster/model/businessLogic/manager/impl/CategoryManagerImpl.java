package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.database.dataAccessObjects.CategoryDAO;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CategoryManagerImpl implements CategoryManager{
	private CategoryDAO helper;
	
	public CategoryManagerImpl() {
		helper = new CategoryDAO();
	}

	public List<Category> getCategories() throws Exception{
		String apiUrl = "http://category-service:8081/getCategories";
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
		JsonNode productsNode = jsonNode.get("categories");
		List<Category> categories = objectMapper.readValue(productsNode.toString(), new TypeReference<List<Category>>() {});
		return categories;
	}

	public Category getCategory(int id) throws Exception{
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
		Category categories = objectMapper.readValue(jsonNode.toString(),  new TypeReference<Category>() {});
		return categories;
	}

	public Category getCategoryByName(String name) throws Exception{
		String apiUrl = "http://category-service:8081/getCategory?name="+name;
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
		Category categories = objectMapper.readValue(jsonNode.toString(),  new TypeReference<Category>() {});
		return categories;
	}

	public void addCategory(String name) throws Exception{
		URL url = new URL("http://category-service:8081/addCategory");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		String jsonInputString = "{\"name\":\""+name+"\"}";
		OutputStream os = con.getOutputStream();
		byte[] input = jsonInputString.getBytes("utf-8");
		os.write(input, 0, input.length);
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
		StringBuilder res = new StringBuilder();
		String responseLine = null;
		while((responseLine = br.readLine()) != null){
			res.append(responseLine.trim());
		}
		System.out.print(res.toString());	
	}

	public void delCategory(Category cat) throws Exception{
		this.delCategoryById(cat.getId());
	}

	public void delCategoryById(int id) throws Exception {
		URL url = new URL("http://category-service:8081/delCategoryById");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		String jsonInputString = "{\"id\":"+id+"}";
		OutputStream os = con.getOutputStream();
		byte[] input = jsonInputString.getBytes("utf-8");
		os.write(input, 0, input.length);
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
		StringBuilder res = new StringBuilder();
		String responseLine = null;
		while((responseLine = br.readLine()) != null){
			res.append(responseLine.trim());
		}
		System.out.print(res.toString());	
	}
}
