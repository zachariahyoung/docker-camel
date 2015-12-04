package com.zandroid.camel;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Hello {

    public Hello(String text) {
        this.text = text;
    }

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 5)
    private String text;
}
