package com.boostmytool.beststore.models;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.*;

public class ProductDto {

    @NotEmpty(message = "The name is required")
    private String name;

    @NotEmpty(message = "The brand is required")
    private String brand;

    @NotNull(message = "Category is required")
    private Category categoryObj;

    @NotNull(message = "Supplier is required")
    private Supplier supplierObj;

    @Min(0)
    private double price;

    @Size(min = 10, message = "The description should be at least 10 characters")
    @Size(max = 2000, message = "The description cannot exceed 2000 characters")
    private String description;

    private MultipartFile imageFile;

    // name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // brand
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    // category
    public Category getCategoryObj() {
        return categoryObj;
    }

    public void setCategoryObj(Category categoryObj) {
        this.categoryObj = categoryObj;
    }

    // supplier
    public Supplier getSupplierObj() {
        return supplierObj;
    }

    public void setSupplierObj(Supplier supplierObj) {
        this.supplierObj = supplierObj;
    }

    // price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // image
    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}