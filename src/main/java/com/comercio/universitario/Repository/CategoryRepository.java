package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
