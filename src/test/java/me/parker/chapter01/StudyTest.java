package me.parker.chapter01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.util.Locale;

public class StudyTest {

    @Test
    @DisplayName("달러 통화 표시")
    void currency_format() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        nf.setMaximumFractionDigits(2);  // 소수점 2자리까지 지정

        System.out.println(nf.format(1400));
        System.out.println(nf.format(1400.12));
    }
}
