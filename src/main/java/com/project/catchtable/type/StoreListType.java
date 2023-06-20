package com.project.catchtable.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreListType {
    ALPHABET("A"),
    DISTANCE("D"),
    RATING("R");

    private final String type;
}
