package org.example.be.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MainCategory {
    VEGETABLE("채소"),
    FRUIT("과일·견과·쌀"),
    SEAFOOD("수산·해산·건어물");


    private final String displayName;


}
