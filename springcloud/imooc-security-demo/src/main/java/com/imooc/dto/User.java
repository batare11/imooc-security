package com.imooc.dto;

import com.fasterxml.jackson.annotation.JsonView;

public class User {
	
   public interface UserSimpleView {};
   public interface UesrDetailView extends UserSimpleView{};
   private String username;
   private String password;

@JsonView(UserSimpleView.class)//username在usersimpleview视图展示
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
@JsonView(UesrDetailView.class)//password在UesrDetailView视图展示 
public String getPassword() {   //因为 UesrDetailView extends UserSimpleView
	return password;            //所以展示password的时候也会展示username
}
public void setPassword(String password) {
	this.password = password;
}
   
}
