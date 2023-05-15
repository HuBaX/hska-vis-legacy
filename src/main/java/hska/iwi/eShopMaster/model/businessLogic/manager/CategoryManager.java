package hska.iwi.eShopMaster.model.businessLogic.manager;

import hska.iwi.eShopMaster.model.database.dataobjects.Category;

import java.util.List;

public interface CategoryManager {

	public List<Category> getCategories() throws Exception;
	
	public Category getCategory(int id) throws Exception;
	
	public Category getCategoryByName(String name) throws Exception;
	
	public void addCategory(String name) throws Exception;
	
	public void delCategory(Category cat) throws Exception;
	
	public void delCategoryById(int id) throws Exception;

	
}
