package at.generic.web.commandObj;

import java.util.List;

import at.generic.eventmodel.Profile;

/**
 * @author szabolcs
 * @version $Id: ProfileCons.java,v 1.1 2006/03/16 23:35:50 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Data Object Admin Command to present the Profiles
 * 
 */
public class ProfileCons { 
	private Profile profile;
	private List filters;
	
	/**
	 * @return Returns the filters.
	 */
	public List getFilters() {
		return filters;
	}
	/**
	 * @param filters The filters to set.
	 */
	public void setFilters(List filters) {
		this.filters = filters;
	}
	/**
	 * @return Returns the profile.
	 */
	public Profile getProfile() {
		return profile;
	}
	/**
	 * @param profile The profile to set.
	 */
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	
	
	
}