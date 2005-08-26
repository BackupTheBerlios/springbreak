/*
 * Created on 29.03.2005
 * king
 * 
 */
package at.newsagg.dao;

import java.util.Collection;

import at.newsagg.model.Category;
import at.newsagg.model.CategoryIF;

/**
 * @author Roland Vecera
 * @version
 * created on 29.03.2005 09:30:18
 *
 */
public interface CategoryDAO {
    /**
     * save a new Category.
     * 
     * @param category
     */
    public void saveCategory(CategoryIF category);

    /**
     * update a persisted Category. updates a Category, thats already in db.
     * 
     * @param category
     */
    public void updateCategory(CategoryIF category);

    /**
     * Get Category by id.
     * 
     * @param id
     * @return
     */
    public Category getCategory(int id);
    
    public Collection getCategoriesByUser (String username);
    
    
  
    
}