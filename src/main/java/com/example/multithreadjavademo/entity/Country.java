package com.example.multithreadjavademo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Country {
    private String country_id;
    private double probability;
}