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
        var result = "청구 내역 (고객명: " + invoice.getCustomer() + ")\n";

        for (Invoice.Performance perf : invoice.getPerformances()) {
            // 청구 내역을 출력한다.
            result += "  " + playFor(perf).getName() + ": "
                    + usd(amountFor(perf))
                    + " (" + perf.getAudience() + "석)\n";
        }

        result += "총액: " + usd(totalAmount()) + "\n";
        result += "적립 포인트: " + totalVolumeCredits() + "점\n";

        return result;
    }

    private int totalAmount() {
        var result = 0;
        for (Invoice.Performance perf : invoice.getPerformances()) {
            result += amountFor(perf);
        }
        return result;
    }

    private int totalVolumeCredits() {
        var result = 0;
        for (Invoice.Performance perf : invoice.getPerformances()) {
            result += volumeCreditsFor(perf);
        }
        return result;
    }

    private String usd(long aNumber) {
        final var format = NumberFormat.getCurrencyInstance(Locale.US);
        format.setMaximumFractionDigits(2);

        return format.format((double) aNumber / 100);
    }

    private int volumeCreditsFor(Invoice.Performance aPerformance) {
        var result = 0;
        result += Math.max(aPerformance.getAudience() - 30, 0);
        if ("comedy".equals(playFor(aPerformance).getType())) {
            result += Math.floor((double) aPerformance.getAudience() / 5);
        }
        return result;
    }

    private Play playFor(Invoice.Performance aPerformance) {
        return plays.get(aPerformance.getPlayID());
    }

    private int amountFor(Invoice.Performance aPerformance) {
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
