package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.internal_affairs.dto.CategoryDto;
import uz.internal_affairs.entity.CategoryEntity;

@Service
@RequiredArgsConstructor
public class CategoryService implements BaseService<CategoryEntity, CategoryDto>{
    private final CategoryService categoryService;



    @Override
    public CategoryDto add(CategoryEntity categoryEntity) {

        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public CategoryDto update(Long id, CategoryEntity categoryEntity) {
        return null;
    }
}
