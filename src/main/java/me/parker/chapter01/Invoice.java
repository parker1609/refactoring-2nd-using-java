package me.parker.chapter01;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Invoice {
    private String customer;
    private List<Performance> performances;

    @Builder
    public Invoice(String customer, List<Performance> performances) {
        this.customer = customer;
        this.performances = performances;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @ToString
    public static class Performance {
        private String playID;
        private Integer audience;

        @Builder
        public Performance(String playID, Integer audience) {
            this.playID = playID;
            this.audience = audience;
        }
    }
}
