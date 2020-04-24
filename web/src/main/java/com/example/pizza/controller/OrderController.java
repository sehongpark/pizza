package com.example.pizza.controller;

import com.example.pizza.dto.OrderForm;
import com.example.pizza.service.OrderService;
import com.example.pizza.vo.Order;
import com.example.pizza.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 현재 로그인 사용자 정보 가져오기, https://t.ly/yGWJ7
    @GetMapping("/carts/{pizzaId}")
    public String add(@PathVariable Integer pizzaId,
                      @AuthenticationPrincipal UserVO currentUser) {
        // 현재 사용자 확인
        log.info(currentUser.toString());

        // 카트에 담기
        Order cart = orderService.addToCart(pizzaId, currentUser);
        log.info(cart.toString());

        return "redirect:/pizzas";
    }

    @GetMapping("/carts")
    public String carts(@AuthenticationPrincipal UserVO user, Model model) {
        log.info(user.toString());

        // 해당 사용자의 장바구니 가져오기
        Order cart = orderService.getCart(user);

        // 총합 계산
        Integer totalPrice = null;
        if (cart != null) {
            totalPrice = cart.getPizzas().stream()
                    .mapToInt(x -> x.getPrice())
                    .sum();
        }

        // 데이터, 뷰로 전달
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);

        return "orders/cart";
    }

    @PostMapping("/orders")
    public String order(OrderForm form) {
        // 폼 확인
        log.info(form.toString());

        // DB 저장
        Order ordered = orderService.receive(form);

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String index(@AuthenticationPrincipal UserVO user, // 현재 사용자 정보
                        Model model) {
        // 주문 완료 된 것들
        List<Order> orders  = orderService.getOrders(user);
        log.info(String.format("#index - %s", orders.toString()));

        // 데이터 등록
        model.addAttribute("orders", orders);
        return "orders/index";
    }
}
