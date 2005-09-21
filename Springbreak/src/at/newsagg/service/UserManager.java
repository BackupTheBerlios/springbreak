package at.newsagg.service;

import java.util.Collection;
import java.util.List;

import at.newsagg.model.Category;
import at.newsagg.model.User;

public interface UserManager { 
	public List getUsers(); 
	public User getUser(String username);  
	public User checkUser(String username);
	public User saveUser(User user);
	public User updateUser(User user); 
	public void removeUser(String username); 
	public void setupNewUser(User user, Category cat, Collection feeds);
}