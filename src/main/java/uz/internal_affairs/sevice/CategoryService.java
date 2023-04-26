package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.internal_affairs.entity.CategoryEntity;
import uz.internal_affairs.repository.CategoryRepository;



import java.util.List;

@Service("categoryService")
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryEntity>getCategoryList(){
        List<CategoryEntity> all = categoryRepository.findAll();
        return all;
    }
}
