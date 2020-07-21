package com.example.aplikasiskripsi;

import java.io.Serializable;

public class UserDB implements Serializable {
    private String email;
    private String password;
    private String key;

    public UserDB(){}

    public UserDB(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getKey() { return key;}

    public void setKey(String key) { this.key = key; }

    public String getEmail() { return email;}

    public void setEmail(String email) { this.email = email; }

    public String getPass() { return password; }

    public void setPass(String password) { this.password = password; }

    @Override
    public String toString() {
        return " "+email+"\n" +
                " "+password ;
    }
}
