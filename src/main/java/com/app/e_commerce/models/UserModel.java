package com.app.e_commerce.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserModel {
    public String id;
    public String name;
    public String email;
    public String address;
    public String password;
}
