package com.boostmytool.beststore.models;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryObj;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplierObj;

    private double price;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Date createdAt;
    private String imageFileName;

    // ===== ID =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // ===== NAME =====
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ===== BRAND =====
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    // ===== CATEGORY =====
    public Category getCategoryObj() {
        return categoryObj;
    }

    public void setCategoryObj(Category categoryObj) {
        this.categoryObj = categoryObj;
    }

    // ===== SUPPLIER =====
    public Supplier getSupplierObj() {
        return supplierObj;
    }

    public void setSupplierObj(Supplier supplierObj) {
        this.supplierObj = supplierObj;
    }

    // ===== PRICE =====
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // ===== DESCRIPTION =====
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ===== CREATED AT =====
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // ===== IMAGE =====
    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}