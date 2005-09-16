package at.newsagg.web;


import at.newsagg.model.User;

/**
 * @author Szabolcs Rozsnyai
 * @$Id: UserSession.java,v 1.2 2005/09/16 13:05:51 vecego Exp $
 */
public class UserSession {
 
	private User userData;
	
	public UserSession (User user) {
		this.userData = user;
	}
	
	/**
	 * @return Returns the userData.
	 */
	public User getUserData() {
		return userData;
	}
	/**
	 * @param userData The userData to set.
	 */
	public void setUserData(User userData) {
		this.userData = userData;
	}
}
