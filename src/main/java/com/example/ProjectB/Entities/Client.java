package com.example.ProjectB.Entities;


import com.example.ProjectB.models.ClientRequest;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "client")
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    private Integer age;
    private String address;
    private String phoneNumber;
    private String accountBalance;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "clients_roles",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")

    )
    private Set<Role> rolesSet = new HashSet<>();

    public Client() {

    }

    public Set<Role> getRolesSet() {
        return rolesSet;
    }

    public void setRolesSet(Set<Role> rolesSet) {
        this.rolesSet = rolesSet;
    }

    public Client(String username) {
        setUsername(username);
    }

    public Client(ClientRequest clientRequest)
    {
        setAddress(clientRequest.getAddress());
        setAge(clientRequest.getAge());
        setEmail(clientRequest.getEmail());
        setPhoneNumber(clientRequest.getPhoneNumber());
        setPassword(clientRequest.getPassword());
        setUsername(clientRequest.getUsername());
        setAccountBalance(clientRequest.getAccountBalance());
    }

    public void addRole(Role role)
    {
        this.rolesSet.add(role);
    }
    /*
    public String getPassword() {

        return password;
    }

    public void setPassword(
            final String password) {
        this.password = password;
    }

    public Integer getAge() {

        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(final String username) {

        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }*/

    @Override public String toString() {
        return "User{" + "username='" + username + '\''
                + ", age=" + age + '}';
    }

}
