package com.example.pizza.dto;

import lombok.Data;

import java.util.List;

@Data
public class PizzaForm {
    private String name;
    private Integer price;
    private List<Integer> ingredientIds;
}
