package com.example.pizza.controller;

import com.example.pizza.dto.PizzaForm;
import com.example.pizza.repository.IngredientRepository;
import com.example.pizza.repository.PizzaRepository;
import com.example.pizza.vo.Ingredient;
import com.example.pizza.vo.Ingredient.Type;
import com.example.pizza.vo.Pizza;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping("/pizzas")
    public String index(Model model) {
        List<Pizza> pizzas = pizzaRepository.findAll();
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/pizzas/new")
    public String newPizza(Model model) {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        model.addAttribute("ingredients", ingredients);
        return "pizzas/new";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/pizzas")
    public String create(PizzaForm form) {
        // 폼 확인
        log.info(form.toString());

        // VO 생성
        String name = form.getName();
        Integer price = form.getPrice();
        List<Ingredient> ingredients = ingredientRepository.findAllById(form.getIngredientIds());
        Pizza pizza = new Pizza(null, name, price, ingredients);
        log.info(pizza.toString());

        // DB 저장
        Pizza saved = pizzaRepository.save(pizza);
        log.info(saved.toString());

        return "redirect:/pizzas";
    }

    @GetMapping("/pizzas/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Pizza pizza = pizzaRepository.findById(id).orElse(null);
        model.addAttribute("pizza", pizza);
        return "pizzas/show";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/pizzas/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        // 수정할 피자
        Pizza pizza = pizzaRepository.findById(id).orElse(null);
        model.addAttribute("pizza", pizza);

        // 모든 재료 목록
        List<Ingredient> ingredients = ingredientRepository.findAll();
        model.addAttribute("ingredients", ingredients);

        return "pizzas/edit";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/pizzas/{id}")
    public String update(@PathVariable Integer id, PizzaForm form) {
        // 폼 데이터 확인
        log.info(form.toString());

        // VO 생성
        String name = form.getName();
        Integer price = form.getPrice();
        List<Ingredient> ingredients = ingredientRepository.findAllById(form.getIngredientIds());
        Pizza pizza = new Pizza(id, name, price, ingredients);
        log.info(pizza.toString());

        // DB 저장
        Pizza saved = pizzaRepository.save(pizza);
        log.info(saved.toString());

        return "redirect:/pizzas";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/pizzas/delete/{id}")
    public String delete(@PathVariable Integer id) {
        pizzaRepository.deleteById(id);
        return "redirect:/pizzas";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/pizzas/init")
    public String init() {
        // 재료
        ingredientRepository.save(new Ingredient(null, "오리지날 도우", Type.DOUGH));
        ingredientRepository.save(new Ingredient(null, "씬 도우", Type.DOUGH));
        ingredientRepository.save(new Ingredient(null, "나폴리 도우", Type.DOUGH));
        ingredientRepository.save(new Ingredient(null, "토마토 소스", Type.SAUCE));
        ingredientRepository.save(new Ingredient(null, "크림 소스", Type.SAUCE));
        ingredientRepository.save(new Ingredient(null, "모짜렐라 치즈", Type.CHEESE));
        ingredientRepository.save(new Ingredient(null, "페퍼로니", Type.TOPPING));
        ingredientRepository.save(new Ingredient(null, "쉬림프", Type.TOPPING));
        ingredientRepository.save(new Ingredient(null, "포크", Type.TOPPING));
        ingredientRepository.save(new Ingredient(null, "비프", Type.TOPPING));
        ingredientRepository.save(new Ingredient(null, "불고기", Type.TOPPING));
        ingredientRepository.save(new Ingredient(null, "포테이토", Type.TOPPING));
        ingredientRepository.save(new Ingredient(null, "까망베르", Type.TOPPING));

        // 페퍼로니 피자
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(4);
        ids.add(6);
        ids.add(7);
        List<Ingredient> ingredients = ingredientRepository.findAllById(ids);
        pizzaRepository.save(new Pizza(null, "페퍼로니 피자", 9900, ingredients));

        // 포테이토 피자
        ids.clear();
        ids.add(2);
        ids.add(4);
        ids.add(6);
        ids.add(9);
        ids.add(12);
        ingredients = ingredientRepository.findAllById(ids);
        pizzaRepository.save(new Pizza(null, "포테이토 피자", 9900, ingredients));

        // 까망베르 피자
        ids.clear();
        ids.add(3);
        ids.add(5);
        ids.add(6);
        ids.add(13);
        ingredients = ingredientRepository.findAllById(ids);
        pizzaRepository.save(new Pizza(null, "까망베르 피자", 9900, ingredients));

        return "redirect:/pizzas";
    }
}
