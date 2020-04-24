package com.example.pizza.repository;

import com.example.pizza.vo.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// <VO, PK>
public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {
    @Override
    Ingredient save(Ingredient entity);

    @Override
    List<Ingredient> findAll();

    @Override
    List<Ingredient> findAllById(Iterable<Integer> integers);
}
