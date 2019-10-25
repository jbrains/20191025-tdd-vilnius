package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class FindPriceInMemoryCatalogTest {
    @Test
    void productFound() throws Exception {
        final Price matchingPrice = Price.cents(795);
        Assertions.assertEquals(
                matchingPrice,
                new InMemoryCatalog(new HashMap<>() {{
                    put("12345", matchingPrice);
                }}).findPrice("12345"));
    }

    @Test
    void productNotFound() throws Exception {
        Assertions.assertEquals(
                null,
                new InMemoryCatalog(new HashMap<>() {{
                }}).findPrice("12345")
        );
    }

    public static class InMemoryCatalog implements Catalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
