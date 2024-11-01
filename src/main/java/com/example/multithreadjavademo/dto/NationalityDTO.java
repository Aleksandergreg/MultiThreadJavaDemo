package com.example.multithreadjavademo.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class NationalityDTO {
    private List<Country> country;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Country {
        private String country_id;
        private double probability;
    }
}