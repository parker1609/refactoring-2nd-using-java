package me.parker.chapter01;

import lombok.Getter;
import lombok.ToString;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

@Getter
@ToString
public class Statement {
    private Invoice invoice;
    private Map<String, Play> plays;

    public Statement(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    public String statement() {
        var totalAmount = 0;
        var volumeCredits = 0;
        var result = "청구 내역 (고객명: " + invoice.getCustomer() + ")\n";
        final var format = NumberFormat.getCurrencyInstance(Locale.US);
        format.setMaximumFractionDigits(2);

        for (Invoice.Performance perf : invoice.getPerformances()) {
            // 포인트를 적립한다.
            volumeCredits += Math.max(perf.getAudience() - 30, 0);
            // 희극 관객 5명마다 추가 포인트를 제공한다.
            if ("comedy".equals(playFor(perf).getType())) {
                volumeCredits += Math.floor(perf.getAudience() / 5);
            }

            // 청구 내역을 출력한다.
            result += "  " + playFor(perf).getName() + ": "
                    + format.format(amountFor(perf, playFor(perf)) / 100)
                    + " (" + perf.getAudience() + "석)\n";
            totalAmount += amountFor(perf, playFor(perf));
        }

        result += "총액: " + format.format(totalAmount / 100) + "\n";
        result += "적립 포인트: " + volumeCredits + "점\n";

        return result;
    }

    private Play playFor(Invoice.Performance aPerformance) {
        return plays.get(aPerformance.getPlayID());
    }

    private int amountFor(Invoice.Performance aPerformance, Play play) {
        int result = 0;

        switch (playFor(aPerformance).getType()) {
            case "tragedy":  // 비극
                result = 40_000;
                if (aPerformance.getAudience() > 30) {
                    result += 1_000 * (aPerformance.getAudience() - 30);
                }
                break;
            case "comedy":   // 희극
                result = 30_000;
                if (aPerformance.getAudience() > 20) {
                    result += 10_000 + 500 * (aPerformance.getAudience() - 20);
                }
                result += 300 * aPerformance.getAudience();
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 장르: " + playFor(aPerformance).getType());
        }
        return result;
    }
}
