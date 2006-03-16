package at.generic.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import at.generic.dao.ProfileDAO;
import at.generic.eventmodel.Eventtype;
import at.generic.eventmodel.Profile;

/**
 * @author szabolcs
 * @version $Id: ProfileDAOHibernate.java,v 1.1 2006/03/16 23:35:50 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * DAO interface Hibernate implementation
 * 
 */
public class ProfileDAOHibernate extends HibernateDaoSupport implements ProfileDAO { 
	private Log log = LogFactory.getLog(EventDAOHibernate.class); 
	
	/**
	 * @return List with Profiles
	 */
	public List getProfiles() {
		return getHibernateTemplate().find("from Profile");
	}
	
	/**
	 * @param id 
	 * @return Profile event
	 */
	public Profile getProfile(Integer id) {
		return(Profile) getHibernateTemplate().get(Profile.class, id);
	}
	
	
	/**
	 * Save or update profile
	 * 
	 * @param profile Profile to save
	 */
	public void saveOrUpdateProfile(Profile profile) {
		if (profile.getPid() == null) 
			getHibernateTemplate().save(profile);
		else if (this.getProfile(profile.getPid()) == null)
			getHibernateTemplate().save(profile);
		else 
			this.updateProfile(profile);
	}
	
	/**
	 * Update Profile
	 * 
	 * @param profile Profile to update
	 */
	public void updateProfile(Profile profile) {
		getHibernateTemplate().update(profile);
	}
	
	/**
	 * Remove Profile
	 * 
	 * @param id profile id to remove
	 */
	public void removeProfile(Integer id) {
		Profile profile = (Profile)getHibernateTemplate().get(Profile.class, id); 
		getHibernateTemplate().delete(profile); 
	}
	
	/**
	 * Returns a Profile according to the given name
	 * 
	 * @param name
	 * @return
	 */
	public Profile getProfileByName(String name) {
		List list = getHibernateTemplate().find("from Profile where name = '" + name + "'");
		if (list.size() > 0) {
			Iterator i = list.iterator();
			while (i.hasNext()) {
				Profile et = (Profile) i.next();
				return et;
			}
		}
		
		return null;
	}
}