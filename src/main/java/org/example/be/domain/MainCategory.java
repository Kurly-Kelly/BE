package org.example.be.domain;

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
