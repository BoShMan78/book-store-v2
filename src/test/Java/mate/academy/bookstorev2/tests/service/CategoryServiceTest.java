package mate.academy.bookstorev2.tests.service;

import java.util.List;
import java.util.Optional;
import mate.academy.bookstorev2.dto.category.CategoryDto;
import mate.academy.bookstorev2.dto.category.CreateCategoryRequestDto;
import mate.academy.bookstorev2.mapper.CategoryMapper;
import mate.academy.bookstorev2.model.Category;
import mate.academy.bookstorev2.repository.category.CategoryRepository;
import mate.academy.bookstorev2.service.CategoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;

    @Test
    @DisplayName("""
            Verify findAll() method works""")
    public void findAll_ValidPageable_ReturnsAllCategory() {
        // Given
        Category category = new Category();
        category.setName("Test category");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());

        List<Category> categoryList = List.of(category);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> categoryPage = new PageImpl<>(categoryList, pageable,categoryList.size());

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        // When
        List<CategoryDto> categoryDtoList = categoryService.findAll(pageable);

        //Then
        assertThat(categoryDtoList).hasSize(1);
        assertThat(categoryDtoList.get(0)).isEqualTo(categoryDto);
    }

    @Test
    @DisplayName("""
            Verify getById() method works
            """)
    public void getById_WithValidCategoryId_ShouldReturnValidCategory() {
        // Given
        Long id = 12345L;
        Category category = new Category();
        category.setId(id);
        category.setName("Test category");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        //When
        CategoryDto actualCategoruDto = categoryService.getById(id);
        String actualCategoryName = actualCategoruDto.getName();

        //Then
        String expectedCategoryName = category.getName();
        assertEquals(expectedCategoryName, actualCategoryName);
    }

    @Test
    @DisplayName("""
            Verify save() method works
            """)
    public void save_ValidCreateCategoryRequestDto_ReturnCategoryDto() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Test category");
        Category category = new Category();
        category.setName(requestDto.getName());
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());

        when(categoryMapper.toModel(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        //When
        CategoryDto actualCategoryDto = categoryService.save(requestDto);

        // Then
        assertThat(actualCategoryDto).isEqualTo(categoryDto);
        verify(categoryRepository, times(1)).save(category);
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

    @Test
    @DisplayName("""
            Verify update() method works
            """)
    public void update_ValidCreateCategoryRequestDto_ReturnsCategoryDto() {
        // Given
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Updated Test category");
        Long id = 12345L;
        Category category = new Category();
        category.setName("Test category");
        category.setId(id);

        Category updatedCategory = new Category();
        updatedCategory.setId(category.getId());
        updatedCategory.setName(requestDto.getName());

        CategoryDto updatedCategoryDto = new CategoryDto();
        updatedCategoryDto.setName(updatedCategory.getName());

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        doNothing().when(categoryMapper).updateCategoryFromDto(requestDto, category);
        when(categoryRepository.save(category)).thenReturn(updatedCategory);
        when(categoryMapper.toDto(updatedCategory)).thenReturn(updatedCategoryDto);

        //When
        CategoryDto actual = categoryService.update(id, requestDto);

        //Then
        assertThat(actual.getName()).isEqualTo(updatedCategoryDto.getName());
        verify(categoryRepository, times(1)).save(category);
        verify(categoryRepository).findById(id);
        verify(categoryMapper).updateCategoryFromDto(requestDto, category);
        verify(categoryMapper).toDto(updatedCategory);
    }
}
