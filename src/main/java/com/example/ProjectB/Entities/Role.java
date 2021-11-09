package com.example.ProjectB.Entities;



import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="roles") @NoArgsConstructor
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 45)
    private String name;


    public Long getId() {
        return id;
    }

    public Role(String name)
    {
        setName(name);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
