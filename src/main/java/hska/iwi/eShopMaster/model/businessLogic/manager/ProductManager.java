package hska.iwi.eShopMaster.model.businessLogic.manager;

import hska.iwi.eShopMaster.model.database.dataobjects.Product;

import java.util.List;

public interface ProductManager {

	public List<Product> getProducts() throws Exception;

	public Product getProductById(int id) throws Exception;

	public Product getProductByName(String name) throws Exception;

	public int addProduct(String name, double price, int categoryId, String details) throws Exception;

	public List<Product> getProductsForSearchValues(String searchValue, Double searchMinPrice, Double searchMaxPrice) throws Exception;
	
	public boolean deleteProductsByCategoryId(int categoryId) throws Exception;
	
    public void deleteProductById(int id) throws Exception;
    
}
