package me.parker.chapter01;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class Statement {

    public static String statement(Invoice invoice, Map<String, Play> plays) {
        var totalAmount = 0;
        var volumeCredits = 0;
        var result = "청구 내역 (고객명: " + invoice.getCustomer() + ")\n";
        final var format = NumberFormat.getCurrencyInstance(Locale.US);
        format.setMaximumFractionDigits(2);

        for (Invoice.Performance perf : invoice.getPerformances()) {
            final var play = plays.get(perf.getPlayID());
            var thisAmount = 0;

            switch (play.getType()) {
                case "tragedy":  // 비극
                    thisAmount = 40_000;
                    if (perf.getAudience() > 30) {
                        thisAmount += 1_000 * (perf.getAudience() - 30);
                    }
                    break;
                case "comedy":   // 희극
                    thisAmount = 30_000;
                    if (perf.getAudience() > 20) {
                        thisAmount += 10_000 + 500 * (perf.getAudience() - 20);
                    }
                    thisAmount += 300 * perf.getAudience();
                    break;
                default:
                    throw new IllegalArgumentException("알 수 없는 장르: " + play.getType());
            }

            // 포인트를 적립한다.
            volumeCredits += Math.max(perf.getAudience() - 30, 0);
            // 희극 관객 5명마다 추가 포인트를 제공한다.
            if ("comedy".equals(play.getType())) {
                volumeCredits += Math.floor(perf.getAudience() / 5);
            }

            // 청구 내역을 출력한다.
            result += "  " + play.getName() + ": "
                    + format.format(thisAmount / 100)
                    + " (" + perf.getAudience() + "석)\n";
            totalAmount += thisAmount;
        }

        result += "총액: " + format.format(totalAmount / 100) + "\n";
        result += "적립 포인트: " + volumeCredits + "점\n";

        return result;
    }
}
