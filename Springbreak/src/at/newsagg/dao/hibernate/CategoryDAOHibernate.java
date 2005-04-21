/*
 * Created on 29.03.2005
 * king
 * 
 */
package at.newsagg.dao.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import at.newsagg.dao.CategoryDAO;
import at.newsagg.model.Category;
import at.newsagg.model.CategoryIF;

/**
 * @author Roland Vecera
 * @version
 * created on 29.03.2005 09:24:26
 *
 */
public class CategoryDAOHibernate extends HibernateDaoSupport implements CategoryDAO
{
    private Log log = LogFactory.getLog(CategoryDAOHibernate.class);
    
    /**
     * save a new Category.
     * 
     * @param category
     */
    public void saveCategory(CategoryIF category) {
        getHibernateTemplate().save(category);

        if (log.isDebugEnabled()) {
            log.debug("Cagetory " + category.getTitle() + " stored!");
        }
    }
    
    
    /**
     * update a persisted Category. updates a Category, thats already in db.
     * 
     * @param category
     */
    public void updateCategory(CategoryIF category) {
        getHibernateTemplate().update(category);

        if (log.isDebugEnabled()) {
            log.debug("Category " + category.getTitle() + " updated!");
        }
    }
    
    
    /**
     * Get Category by id.
     * 
     * @param id
     * @return
     */
    public Category getCategory(int id) {
        return (Category) getHibernateTemplate().load(Category.class,
                new Integer(id));
        //return (Channel)getHibernateTemplate().find("from Channel u where
        // u.id = ?", new Integer(id)).get(0);

    }
}
