package com.boostmytool.beststore.controllers;

import com.boostmytool.beststore.models.Category;
import com.boostmytool.beststore.services.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository repo;

    public CategoryController(CategoryRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("data", repo.findAll());
        return "categories/category_list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("category", new Category());
        return "categories/category_form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Category category) {
        repo.save(category);
        return "redirect:/categories";
    }
}