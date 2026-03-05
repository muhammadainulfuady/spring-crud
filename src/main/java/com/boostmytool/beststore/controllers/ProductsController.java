package com.boostmytool.beststore.controllers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.boostmytool.beststore.models.Product;
import com.boostmytool.beststore.models.ProductDto;
import com.boostmytool.beststore.services.CategoryRepository;
import com.boostmytool.beststore.services.ProductsRepository;
import com.boostmytool.beststore.services.SupplierRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private final ProductsRepository repo;
    private final CategoryRepository categoryRepo;
    private final SupplierRepository supplierRepo;

    public ProductsController(ProductsRepository repo,
            CategoryRepository categoryRepo,
            SupplierRepository supplierRepo) {
        this.repo = repo;
        this.categoryRepo = categoryRepo;
        this.supplierRepo = supplierRepo;
    }

    // ================= LIST =================
    @GetMapping({ "", "/" })
    public String showProductList(Model model) {
        List<Product> products = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("products", products);
        return "products/index";
    }

    // ================= CREATE =================
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("productDto", new ProductDto());
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("suppliers", supplierRepo.findAll());
        return "products/CreateProduct";
    }

    @PostMapping("/create")
    public String createProduct(
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult result,
            Model model) {

        if (productDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("productDto", "imageFile", "Image wajib diisi"));
        }

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepo.findAll());
            model.addAttribute("suppliers", supplierRepo.findAll());
            return "products/CreateProduct";
        }

        MultipartFile image = productDto.getImageFile();
        Date createdAt = new Date();
        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream,
                        Paths.get(uploadDir + storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception ex) {
            System.out.println("Upload Error: " + ex.getMessage());
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategoryObj(productDto.getCategoryObj());
        product.setSupplierObj(productDto.getSupplierObj());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCreatedAt(createdAt);
        product.setImageFileName(storageFileName);

        repo.save(product);

        return "redirect:/products";
    }

    // ================= EDIT =================
    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id) {

        Optional<Product> optionalProduct = repo.findById(id);
        if (optionalProduct.isEmpty()) {
            return "redirect:/products";
        }

        Product product = optionalProduct.get();
        model.addAttribute("product", product);

        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setBrand(product.getBrand());
        productDto.setCategoryObj(product.getCategoryObj());
        productDto.setSupplierObj(product.getSupplierObj());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());

        model.addAttribute("productDto", productDto);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("suppliers", supplierRepo.findAll());

        return "products/EditProduct";
    }

    @PostMapping("/edit")
    public String updateProduct(
            @RequestParam int id,
            @ModelAttribute ProductDto productDto,
            BindingResult result,
            Model model) {

        Optional<Product> optionalProduct = repo.findById(id);
        if (optionalProduct.isEmpty()) {
            return "redirect:/products";
        }

        Product product = optionalProduct.get();

        if (result.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryRepo.findAll());
            model.addAttribute("suppliers", supplierRepo.findAll());
            return "products/EditProduct";
        }

        String uploadDir = "public/images/";

        if (productDto.getImageFile() != null && !productDto.getImageFile().isEmpty()) {

            try {
                Files.deleteIfExists(Paths.get(uploadDir + product.getImageFileName()));
            } catch (Exception ex) {
                System.out.println("Delete Error: " + ex.getMessage());
            }

            MultipartFile image = productDto.getImageFile();
            String storageFileName = new Date().getTime() + "_" + image.getOriginalFilename();

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream,
                        Paths.get(uploadDir + storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
                product.setImageFileName(storageFileName);
            } catch (Exception ex) {
                System.out.println("Upload Error: " + ex.getMessage());
            }
        }

        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategoryObj(productDto.getCategoryObj());
        product.setSupplierObj(productDto.getSupplierObj());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());

        repo.save(product);

        return "redirect:/products";
    }

    // ================= DELETE =================
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam int id) {

        Optional<Product> optionalProduct = repo.findById(id);
        if (optionalProduct.isEmpty()) {
            return "redirect:/products";
        }

        Product product = optionalProduct.get();

        try {
            Files.deleteIfExists(Paths.get("public/images/" + product.getImageFileName()));
        } catch (Exception ex) {
            System.out.println("Delete Error: " + ex.getMessage());
        }

        repo.delete(product);
        return "redirect:/products";
    }
}