package org.example.felessmartket_be.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category {

    // 대분류
    MEAT("고기", null),
    VEGETABLE("채소", null),

    // 소분류 (MEAT)
    PORK("돼지고기", MEAT),
    BEEF("소고기", MEAT),

    // 소분류 (VEGETABLE)
    CUCUMBER("오이", VEGETABLE),
    LETTUCE("상추", VEGETABLE),
    CARROT("당근", VEGETABLE);

    private final String title;

    @Getter
    private final Category parentCategory;

    public static List<Category> getChildCategories(Category parent) {
        return Arrays.stream(values())
            .filter(category -> category.getParentCategory() == parent)
            .collect(Collectors.toList());
    }

    public boolean isParentCategory() {
        return this.parentCategory == null;
    }

    public static List<Category> getAllCategories(Category parent) {
        List<Category> childCategories = getChildCategories(parent);
        childCategories.add(parent); // 대분류 자체도 포함
        return childCategories;
    }
}
