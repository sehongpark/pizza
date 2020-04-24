package com.example.pizza.controller;

import com.example.pizza.dto.IngredientForm;
import com.example.pizza.repository.IngredientRepository;
import com.example.pizza.vo.Ingredient;
import com.example.pizza.vo.Ingredient.Type;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class IngredientController {

    @Autowired // 스프링이 자동 연결(DI)
    private IngredientRepository ingredientsRepository;

    @GetMapping("/ingredients")
    public String index(Model model) {
        List<Ingredient> ingredients = ingredientsRepository.findAll();
        model.addAttribute("ingredients", ingredients);
        return "ingredients/index";
    }

    @GetMapping("/ingredients/new")
    public String newIngredient() {
        return "ingredients/new";
    }

    @PostMapping("/ingredients")
    public String create(IngredientForm form) {
        // form 데이터 받기
        log.info(form.toString());
        String name = form.getName();
        Type type = Type.valueOf(form.getType().toUpperCase());

        // VO 객체 생성
        Ingredient ingredient = new Ingredient(null, name, type);
        log.info(ingredient.toString());

        // DB 저장
        Ingredient saved = ingredientsRepository.save(ingredient);
        log.info(saved.toString());

        // 리다이렉트
        return "redirect:/ingredients";
    }

    @GetMapping("/ingredients/edit/{id}") // map http request.
    public String edit(@PathVariable int id, Model model) {
        // DB 검색
        Ingredient ingredient = ingredientsRepository.findById(id).orElse(null);
        log.info(ingredient.toString());
        model.addAttribute("ingredient", ingredient);

        return "ingredients/edit"; // set view path.
    }

    @PostMapping("/ingredients/{id}")
    public String update(@PathVariable int id, IngredientForm form) {
        // 폼 데이터 확인
        log.info(form.toString());

        // VO 생성
        String name = form.getName();
        Type type = Type.valueOf(form.getType().toUpperCase());
        Ingredient ingredient = new Ingredient(id, name, type);
        log.info(ingredient.toString());

        // DB 저장
        Ingredient saved = ingredientsRepository.save(ingredient);
        log.info(saved.toString());

        // 리다이렉트
        return "redirect:/ingredients";
    }

    @GetMapping("/ingredients/delete/{id}")
    public String delete(@PathVariable int id) {
        // DB 삭제
        ingredientsRepository.deleteById(id);

        // 리다이렉트
        return "redirect:/ingredients";
    }

    @GetMapping("/ingredients/init")
    public String init() {
        ingredientsRepository.save(new Ingredient(null, "오리지날 도우", Type.DOUGH));
        ingredientsRepository.save(new Ingredient(null, "씬 도우", Type.DOUGH));
        ingredientsRepository.save(new Ingredient(null, "나폴리 도우", Type.DOUGH));
        ingredientsRepository.save(new Ingredient(null, "토마토 소스", Type.SAUCE));
        ingredientsRepository.save(new Ingredient(null, "크림 소스", Type.SAUCE));
        ingredientsRepository.save(new Ingredient(null, "모짜렐라 치즈", Type.CHEESE));
        ingredientsRepository.save(new Ingredient(null, "페퍼로니", Type.TOPPING));
        ingredientsRepository.save(new Ingredient(null, "쉬림프", Type.TOPPING));
        ingredientsRepository.save(new Ingredient(null, "포크", Type.TOPPING));
        ingredientsRepository.save(new Ingredient(null, "비프", Type.TOPPING));
        ingredientsRepository.save(new Ingredient(null, "불고기", Type.TOPPING));
        ingredientsRepository.save(new Ingredient(null, "포테이토", Type.TOPPING));
        ingredientsRepository.save(new Ingredient(null, "까망베르", Type.TOPPING));
        return "redirect:/ingredients";
    }
}
