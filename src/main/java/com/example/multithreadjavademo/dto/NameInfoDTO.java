package com.example.multithreadjavademo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NameInfoDTO {
    private String name;
    private String gender;
    private double genderProbability;
    private int age;
    private int ageCount;
    private String country;
    private double countryProbability;
}