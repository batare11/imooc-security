package com.imooc.dto;

import com.fasterxml.jackson.annotation.JsonView;

public class User {
	
   public interface UserSimpleView {};
   public interface UesrDetailView extends UserSimpleView{};
   private String username;
   private String password;

@JsonView(UserSimpleView.class)//username��usersimpleview��ͼչʾ
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
@JsonView(UesrDetailView.class)//password��UesrDetailView��ͼչʾ 
public String getPassword() {   //��Ϊ UesrDetailView extends UserSimpleView
	return password;            //����չʾpassword��ʱ��Ҳ��չʾusername
}
public void setPassword(String password) {
	this.password = password;
}
   
}
