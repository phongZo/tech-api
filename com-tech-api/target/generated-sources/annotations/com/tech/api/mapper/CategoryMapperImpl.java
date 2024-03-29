package com.tech.api.mapper;

import com.tech.api.dto.category.CategoryDto;
import com.tech.api.form.category.CreateCategoryForm;
import com.tech.api.form.category.UpdateCategoryForm;
import com.tech.api.storage.model.Category;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-05T13:12:03+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category fromCreateCategoryFormToEntity(CreateCategoryForm createCategoryForm) {
        if ( createCategoryForm == null ) {
            return null;
        }

        Category category = new Category();

        category.setImage( createCategoryForm.getCategoryImage() );
        category.setOrdering( createCategoryForm.getCategoryOrdering() );
        category.setKind( createCategoryForm.getCategoryKind() );
        category.setDescription( createCategoryForm.getCategoryDescription() );
        category.setName( createCategoryForm.getCategoryName() );

        return category;
    }

    @Override
    public void fromUpdateCategoryFormToEntity(UpdateCategoryForm updateCategoryForm, Category category) {
        if ( updateCategoryForm == null ) {
            return;
        }

        if ( updateCategoryForm.getCategoryImage() != null ) {
            category.setImage( updateCategoryForm.getCategoryImage() );
        }
        if ( updateCategoryForm.getCategoryOrdering() != null ) {
            category.setOrdering( updateCategoryForm.getCategoryOrdering() );
        }
        if ( updateCategoryForm.getCategoryDescription() != null ) {
            category.setDescription( updateCategoryForm.getCategoryDescription() );
        }
        if ( updateCategoryForm.getCategoryName() != null ) {
            category.setName( updateCategoryForm.getCategoryName() );
        }
        if ( updateCategoryForm.getStatus() != null ) {
            category.setStatus( updateCategoryForm.getStatus() );
        }
    }

    @Override
    public CategoryDto fromEntityToAdminDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setCategoryImage( category.getImage() );
        categoryDto.setCategoryOrdering( category.getOrdering() );
        categoryDto.setCategoryName( category.getName() );
        Long id = categoryParentCategoryId( category );
        if ( id != null ) {
            categoryDto.setParentId( id.intValue() );
        }
        categoryDto.setCategoryDescription( category.getDescription() );
        categoryDto.setCategoryKind( category.getKind() );
        categoryDto.setCreatedDate( category.getCreatedDate() );
        categoryDto.setCreatedBy( category.getCreatedBy() );
        categoryDto.setModifiedDate( category.getModifiedDate() );
        categoryDto.setModifiedBy( category.getModifiedBy() );
        categoryDto.setId( category.getId() );
        categoryDto.setStatus( category.getStatus() );

        return categoryDto;
    }

    @Override
    public List<CategoryDto> fromEntityListToCategoryDtoList(List<Category> categories) {
        if ( categories == null ) {
            return null;
        }

        List<CategoryDto> list = new ArrayList<CategoryDto>( categories.size() );
        for ( Category category : categories ) {
            list.add( fromEntityToAdminDto( category ) );
        }

        return list;
    }

    @Override
    public CategoryDto fromEntityToAdminDtoAutoComplete(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setCategoryImage( category.getImage() );
        categoryDto.setId( category.getId() );
        categoryDto.setCategoryName( category.getName() );

        return categoryDto;
    }

    @Override
    public List<CategoryDto> fromEntityListToCategoryDtoAutoComplete(List<Category> categories) {
        if ( categories == null ) {
            return null;
        }

        List<CategoryDto> list = new ArrayList<CategoryDto>( categories.size() );
        for ( Category category : categories ) {
            list.add( fromEntityToAdminDtoAutoComplete( category ) );
        }

        return list;
    }

    private Long categoryParentCategoryId(Category category) {
        if ( category == null ) {
            return null;
        }
        Category parentCategory = category.getParentCategory();
        if ( parentCategory == null ) {
            return null;
        }
        Long id = parentCategory.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
