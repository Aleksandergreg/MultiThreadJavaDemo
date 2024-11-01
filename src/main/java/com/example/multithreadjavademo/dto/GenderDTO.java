package com.example.multithreadjavademo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GenderDTO {
    private String gender;
    private double probability;
}