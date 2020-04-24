package com.example.pizza.repository;

import com.example.pizza.vo.Pizza;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PizzaRepository extends CrudRepository<Pizza, Integer> {
    @Override
    List<Pizza> findAll();

    @Override
    <S extends Pizza> S save(S entity);
}
