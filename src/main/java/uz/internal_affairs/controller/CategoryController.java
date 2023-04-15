package uz.internal_affairs.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.internal_affairs.dto.CategoryDto;
import uz.internal_affairs.sevice.CategoryService;

@Controller
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.add(categoryDto));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }



}
