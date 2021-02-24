package me.parker.chapter01;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class StatementTest {

    private static final ObjectMapper ob = new ObjectMapper();

    @Test
    @DisplayName("test/resources 내의 json 파일이 Java Object 로 정상적으로 매핑되는지 테스트")
    void jsonToObject() throws IOException {
        File playJson = new File("src/test/resources/plays.json");
        Map<String, Play> plays = ob.readValue(playJson, new TypeReference<>() {});

        System.out.println(plays);
        assertThat(plays).hasSize(3);

        File invoiceJson = new File("src/test/resources/invoices.json");
        List<Invoice> invoices = ob.readValue(invoiceJson, new TypeReference<>() {});
        Invoice invoice = invoices.get(0);

        System.out.println(invoice);
        assertThat(invoice.getCustomer()).isEqualTo("BigCo");
        assertThat(invoice.getPerformances()).hasSize(3);
    }
}
