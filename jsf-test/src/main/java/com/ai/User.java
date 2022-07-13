package com.ai;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Model
public class User {

	private int id;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String address;
    List<User> usersList;
    
    
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    
    public List<User> usersList() {
    	try{
            usersList = new ArrayList<>();
            Statement stmt=MyConnection.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("select * from users");  
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getString("gender"));
                user.setAddress(rs.getString("address"));
                usersList.add(user);
            }
           
        }catch(Exception e){
            System.out.println(e);
        }
        return usersList;
    }
    
 // Used to save user record
    public String save(){
        int result = 0;
        try{
            PreparedStatement stmt = MyConnection.getConnection().prepareStatement("insert into users(name,email,password,gender,address) values(?,?,?,?,?)");
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, gender);
            stmt.setString(5, address);
           
            result = stmt.executeUpdate();
            this.showMessage("Record Saved");
        }catch(Exception e){
            System.out.println(e);
        }
        if(result !=0)
            return "index.xhtml?faces-redirect=true";
        else return "create.xhtml?faces-redirect=true";
    }
    
    
 // Used to fetch record to update
    public String edit(int id){
        User user = null;
        System.out.println(id);
        try{
            
            Statement stmt=MyConnection.getConnection().createStatement();  
            ResultSet rs=stmt.executeQuery("select * from users where id = "+(id));
            rs.next();
            user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setGender(rs.getString("gender"));
            user.setAddress(rs.getString("address"));
            user.setPassword(rs.getString("password"));  
             sessionMap.put("editUser", user);
         
        }catch(Exception e){
            System.out.println(e);
        }       
        return "/edit.xhtml?faces-redirect=true";
    }
    // Used to update user record
    public String update(User u){
        //int result = 0;
        try{
            PreparedStatement stmt=MyConnection.getConnection().prepareStatement("update users set name=?,email=?,password=?,gender=?,address=? where id=?");  
            stmt.setString(1,u.getName());  
            stmt.setString(2,u.getEmail());  
            stmt.setString(3,u.getPassword());  
            stmt.setString(4,u.getGender());  
            stmt.setString(5,u.getAddress());  
            stmt.setInt(6,u.getId());  
            stmt.executeUpdate();
         
            this.showMessage("Record Updated.");
        }catch(Exception e){
            System.out.println();
        }
        return "/index.xhtml?faces-redirect=true";      
    }
    // Used to delete user record
    public void delete(int id){
        try{
        	PreparedStatement stmt = MyConnection.getConnection().prepareStatement("delete from users where id = "+id);  
            stmt.executeUpdate();  
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    // Used to set user gender
    public String getGenderName(char gender){
        if(gender == 'M'){
            return "Male";
        }else 
        return "Female";
    }
    
    public void showMessage(String msg) {
    	FacesContext context = FacesContext.getCurrentInstance();
    	FacesMessage message = new FacesMessage("Notice", msg);
    	context.addMessage(null, message);
    }
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
    
    
}

