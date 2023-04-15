package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.internal_affairs.common.exception.RecordNotFountException;
import uz.internal_affairs.dto.CategoryDTO;
import uz.internal_affairs.entity.Category;
import uz.internal_affairs.repository.CategoryRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CategoryService implements BaseService<CategoryDTO, Category> {

    private final CategoryRepository categoryRepository;

    @Override
    public Category add(CategoryDTO categoryDto) {
        Optional<Category> byName = categoryRepository.findByName(categoryDto.getName());
        if (byName.isPresent()) {
            throw new IllegalArgumentException();
        }
        Category category = Category.of(categoryDto);
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, CategoryDTO categoryDto) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isPresent()) {
            Category category = byId.get();
            category.setName(categoryDto.getName());
            category.setScore(categoryDto.getScore());
            return categoryRepository.save(category);

        }
        throw new RecordNotFountException("Category not fount");
    }

    @Override
    public boolean delete(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RecordNotFountException(String.format("Category %s not fount", id)));
        categoryRepository.delete(category);
        return true;
    }


}
