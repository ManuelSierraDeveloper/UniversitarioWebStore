package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
public class CategoryRepositoryTest extends AbstractRepositoryIT {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldCreateReadUpdateDeleteCategory() {
        Category saved = categoryRepository.save(Category.builder().nameCategorie("Tecnologia").build());

        assertThat(saved.getIdCategorie()).isNotNull();
        assertThat(categoryRepository.findById(saved.getIdCategorie())).isPresent();

        saved.setNameCategorie("Tecnologia y Gadgets");
        Category updated = categoryRepository.save(saved);

        assertThat(updated.getNameCategorie()).isEqualTo("Tecnologia y Gadgets");

        categoryRepository.deleteById(updated.getIdCategorie());

        assertThat(categoryRepository.findById(updated.getIdCategorie())).isEmpty();
    }
}