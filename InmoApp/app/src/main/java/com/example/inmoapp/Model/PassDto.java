package com.example.inmoapp.Model;

public class PassDto {
    public PassDto(String password) {
        this.password = password;
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PassDto{" +
                "password='" + password + '\'' +
                '}';
    }
}
