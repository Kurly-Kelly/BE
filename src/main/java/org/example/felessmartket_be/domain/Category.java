package org.example.felessmartket_be.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category {
    VEGETABLE("전체보기", null),
    CUCUMBER("오이", VEGETABLE),
    LETTUCE("상추", VEGETABLE),
    CARROT("당근", VEGETABLE);


    private final String title;

    @Getter
    private final Category parentCategory;
    private List<Category> childCategories;


    public static List<Category> getChildCategories(Category parent) {
        return Arrays.stream(values())
            .filter(category -> category.getParentCategory() == parent)
            .collect(Collectors.toList());

    }
}
