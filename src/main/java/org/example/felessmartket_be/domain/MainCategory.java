package org.example.felessmartket_be.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MainCategory {
    VEGETABLE("채소"),
    FRUIT("과일"),
    SEAFOOD("수산물");

    private final String displayName;


}
