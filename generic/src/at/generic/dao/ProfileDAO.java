package at.generic.dao;

import java.util.List;

import at.generic.eventmodel.Profile;

/**
 * @author szabolcs
 * @version $Id: ProfileDAO.java,v 1.1 2006/03/16 23:35:50 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * DAO interface for Profile
 * 
 */
public interface ProfileDAO extends DAO 
{ 
	/**
	 * @return List with Profiles
	 */
	public List getProfiles(); 
	
	/**
	 * @param id 
	 * @return Profile event
	 */
	public Profile getProfile(Integer id);
	
	/**
	 * Save or update profile
	 * 
	 * @param profile Profile to save
	 */
	public void saveOrUpdateProfile(Profile profile); 
	
	/**
	 * Update Profile
	 * 
	 * @param profile Profile to update
	 */
	public void updateProfile(Profile profile); 
	
	/**
	 * Remove Profile
	 * 
	 * @param id profile id to remove
	 */
	public void removeProfile(Integer id);
	
	/**
	 * Returns a Profile according to the given name
	 * 
	 * @param name
	 * @return
	 */
	public Profile getProfileByName(String name);
}