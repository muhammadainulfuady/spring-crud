package com.boostmytool.beststore.services;

import com.boostmytool.beststore.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
