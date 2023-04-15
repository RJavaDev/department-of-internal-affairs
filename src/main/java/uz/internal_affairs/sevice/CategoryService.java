package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.internal_affairs.dto.CategoryDto;
import uz.internal_affairs.entity.Category;
import uz.internal_affairs.repository.CategoryRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CategoryService implements BaseService<CategoryDto,Category>{

    private final CategoryRepository categoryRepository;

    @Override
    public Category add(CategoryDto categoryDto) {
        Optional<Category> byName = categoryRepository.findByName(categoryDto.getName());
        if (byName.isPresent()){
            throw new IllegalArgumentException();
        }
        Category category=Category.of(categoryDto);
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, CategoryDto categoryDto) {
        Optional<Category> byId = categoryRepository.findById(id);

        return null;
    }



    @Override
    public boolean delete(Long id) {
        return false;
    }


}
