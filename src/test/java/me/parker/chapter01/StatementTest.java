package me.parker.chapter01;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class StatementTest {

    private static final ObjectMapper ob = new ObjectMapper();
    private Map<String, Play> plays = new HashMap<>();
    private Invoice invoice;

    @BeforeEach
    void setUp() throws IOException {
        File playJson = new File("src/test/resources/plays.json");
        plays = ob.readValue(playJson, new TypeReference<>() {});

        File invoiceJson = new File("src/test/resources/invoices.json");
        List<Invoice> invoices = ob.readValue(invoiceJson, new TypeReference<>() {});
        invoice = invoices.get(0);
    }

    @Test
    @DisplayName("test/resources 내의 json 파일이 Java Object 로 정상적으로 매핑되는지 테스트")
    void jsonToObject() {
        System.out.println(plays);
        assertThat(plays).hasSize(3);

        System.out.println(invoice);
        assertThat(invoice.getCustomer()).isEqualTo("BigCo");
        assertThat(invoice.getPerformances()).hasSize(3);
    }

    @Test
    @DisplayName("statement 메서드 테스트한다.")
    void example_test() {
        String result = Statement.statement(invoice, plays);

        assertThat(result).isEqualTo(
                "청구 내역 (고객명: BigCo)\n" +
                "  Hamlet: $650.00 (55석)\n" +
                "  As You Like It: $580.00 (35석)\n" +
                "  Othello: $500.00 (40석)\n" +
                "총액: $1,730.00\n" +
                "적립 포인트: 47점\n"
        );
    }
}
