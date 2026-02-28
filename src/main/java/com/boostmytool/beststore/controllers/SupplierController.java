package com.boostmytool.beststore.controllers;

import com.boostmytool.beststore.models.Supplier;
import com.boostmytool.beststore.services.SupplierRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierRepository repo;

    public SupplierController(SupplierRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("data", repo.findAll());
        return "suppliers/supplier_list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "suppliers/supplier_form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Supplier supplier) {
        repo.save(supplier);
        return "redirect:/suppliers";
    }
}