package dev.cheng.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "normal_user")
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String password;
    private String email;
}
