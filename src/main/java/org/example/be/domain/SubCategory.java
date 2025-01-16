package org.example.be.domain;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SubCategory {
    // 채소
    ROOT_VEGETABLE("고구마·감자·당근", MainCategory.VEGETABLE),
    LEAF_VEGETABLE("시금치·쌈채소·나물", MainCategory.VEGETABLE),
    GREEN_VEGETABLE("오이·호박·고추", MainCategory.VEGETABLE),

    // 과일
    APPLE_PEAR("사과·배", MainCategory.FRUIT),
    CITRUS("감귤류", MainCategory.FRUIT),

    // 수산물
    FISH("생선류", MainCategory.SEAFOOD),
    SHELLFISH("조개류", MainCategory.SEAFOOD);

    private final String displayName;
    private final MainCategory mainCategory;

    public static List<SubCategory> getSubCategoriesByMain(MainCategory mainCategory) {
        return Arrays.stream(SubCategory.values())
            .filter(subCategory -> subCategory.getMainCategory() == mainCategory)
            .toList();
    }

}
