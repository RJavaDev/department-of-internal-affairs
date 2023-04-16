package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.internal_affairs.common.exception.RecordNotFountException;
import uz.internal_affairs.dto.CategoryDto;
import uz.internal_affairs.entity.CategoryEntity;
import uz.internal_affairs.repository.CategoryRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CategoryService implements BaseService<CategoryDto, CategoryEntity> {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryEntity add(CategoryDto categoryDto) {
        Optional<CategoryEntity> byName = categoryRepository.findByName(categoryDto.getName());
        if (byName.isPresent()) {
            throw new IllegalArgumentException();
        }
        CategoryEntity categoryEntity = CategoryEntity.of(categoryDto);
        return categoryRepository.save(categoryEntity);
    }

    @Override
    public CategoryEntity update(Long id, CategoryDto categoryDto) {
        Optional<CategoryEntity> byId = categoryRepository.findById(id);
        if (byId.isPresent()) {
            CategoryEntity categoryEntity = byId.get();
            categoryEntity.setName(categoryDto.getName());
            categoryEntity.setScore(categoryDto.getScore());
            return categoryRepository.save(categoryEntity);

        }
        throw new RecordNotFountException("Category not fount");
    }

    @Override
    public boolean delete(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(() -> new RecordNotFountException(String.format("Category %s not fount", id)));
        categoryRepository.delete(categoryEntity);
        return true;
    }


}
