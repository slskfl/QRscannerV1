package com.example.qrscanner;

import java.util.HashMap;
import java.util.Map;

public class FireBasePost {

    public String id;
    public String name;
    public String pwd;
    public String email;
    public String tel;
    public String address;
    public int grade;


    public FireBasePost() {
    }

    public FireBasePost(String id, String name, String pwd, String email, String tel, String address, int grade) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.email = email;
        this.tel = tel;
        this.address = address;
        this.grade = grade;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result=new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("pwd", pwd);
        result.put("email", email);
        result.put("tel", tel);
        result.put("address", address);
        result.put("grade", grade);

        return result;
    }
}
