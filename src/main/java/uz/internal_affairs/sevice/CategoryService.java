package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.internal_affairs.dto.CategoryDto;
import uz.internal_affairs.entity.CategoryEntity;
import uz.internal_affairs.repository.CategoryRepository;



import java.util.List;
import java.util.stream.Collectors;

@Service("categoryService")
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final Logger log = LoggerFactory.getLogger(getClass().getName());


    public List<CategoryDto> getCategoryList(){
        List<CategoryEntity> all = categoryRepository.findAll();
        return all.stream().map(CategoryEntity::toDto).collect(Collectors.toList());
    }
}
