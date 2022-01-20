package ru.gb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.entity.Category;
import ru.gb.entity.Manufacturer;

import java.util.Optional;

public interface CategoryDao extends JpaRepository<Category, Long> {
    Optional<Manufacturer> findByName(String name);
}
