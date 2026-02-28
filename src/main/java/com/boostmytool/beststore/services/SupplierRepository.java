package com.boostmytool.beststore.services;

import com.boostmytool.beststore.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
