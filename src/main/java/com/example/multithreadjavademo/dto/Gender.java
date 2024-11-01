package com.example.multithreadjavademo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
 public class Gender {
    String gender;
    String name;
    int count;
    double probability;

    @Override
    public String toString() {
        return "gender='" + gender + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", probability=" + probability;
    }
}


