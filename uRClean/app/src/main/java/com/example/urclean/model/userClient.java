package com.example.urclean.model;

public class userClient {

    private String name;
    private String username;
    private String gmail;
    private String password;
    private String id;

    public userClient(){
        name = null;
        username = null;
        gmail = null;
        password = null;
    }

    public void setName(String n){
        name = n;
    }

    public String getName(){
        return name;
    }

    public void setId(String i){
        id = i;
    }

    public String getId(){
        return id;
    }

    public void setPassword(String p){
        password = p;
    }

    public String getPassword(){
        return password;
    }

    public void setUsername(String u){
        username = u;
    }

    public String getUsername(){
        return username;
    }

    public void setGmail(String g){
        gmail = g;
    }

    public String getGmail(){
        return gmail;
    }
}
