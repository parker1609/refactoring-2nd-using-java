package me.parker.chapter01;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Play {
    private String name;
    private String type;

    @Builder
    public Play(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
