package uz.internal_affairs.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.internal_affairs.dto.CategoryDto;
import uz.internal_affairs.dto.response.HttpResponse;
import uz.internal_affairs.entity.CategoryEntity;
import uz.internal_affairs.sevice.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Tag(name = "Category Controller", description = "This Controller for user")
public class CategoryController {
    private final CategoryService categoryService;

    /**
     *  Information about category
     * @author bekzod1660
     * @return HttpResponse of Category List Info
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public HttpResponse<Object> getCategoryListInfo(){
        HttpResponse<Object> response = new HttpResponse<>(true);
        try {
            List<CategoryDto> categoryList = categoryService.getCategoryList();
            response.code(HttpResponse.Status.OK).success(true).body(categoryList).message("successfully!!!");
        } catch (Exception e) {
            e.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
