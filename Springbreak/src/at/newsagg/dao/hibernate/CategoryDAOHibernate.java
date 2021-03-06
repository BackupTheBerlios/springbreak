/*
 * Created on 29.03.2005
 * king
 * 
 */
package at.newsagg.dao.hibernate;

import java.util.Collection;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.type.Type;

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
    
    public Collection getCategoriesByUser (String username)
    {
        return getHibernateTemplate().find("from Category as c where c.user.username like ? order by c.title",username,Hibernate.STRING);
    }
    
    /**
     * returns all Category, that have no entry in FeedSubscriber.
     * 
     * == all Categories with no Channels in it! 
     */
    public Collection getEmptyCategoriesByUser (String username)
    {
        return getHibernateTemplate().find("from Category c where c.user.username like ? and c.id not in (select fee.category.id from FeedSubscriber fee where fee.user.username like ?) order by c.title"
              ,new Object[] {username, username},new Type[] {Hibernate.STRING,Hibernate.STRING});
    }
    
    /**
     * Delete a Category.
     * @param cat_id
     */
    public void removeFeedSubscriber(int cat_id) {
        Object c = getHibernateTemplate().load(Category.class,
                new Integer(cat_id));
        getHibernateTemplate().delete(c);
    }


   
    
}
