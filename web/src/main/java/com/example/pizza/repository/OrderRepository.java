package com.example.pizza.repository;

import com.example.pizza.vo.Order;
import com.example.pizza.vo.UserVO;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    // 카트 상태인 주문을 찾는다.
    Optional<Order> findByStatus(Order.Status status);

    // 해당 유저의 카트를 검색 - https://jobc.tistory.com/120
    Optional<Order> findByStatusAndOrderer(Order.Status status, UserVO user);

    // 해당 유저의 모든 주문 내역
    Optional<ArrayList<Order>> findAllByStatusAndOrderer(Order.Status ordered, UserVO user);
}
