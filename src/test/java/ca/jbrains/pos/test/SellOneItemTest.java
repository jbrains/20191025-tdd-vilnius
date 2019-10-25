package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SellOneItemTest {

    @Test
    void productFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, io.vavr.collection.HashMap.of(
                "12345", "EUR 7.95", "23456", "EUR 12.50")
                .toJavaMap());

        sale.onBarcode("12345");

        Assertions.assertEquals("EUR 7.95", display.getText());
    }

    @Test
    void anotherProductFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, io.vavr.collection.HashMap.of(
                "12345", "EUR 7.95", "23456", "EUR 12.50")
                .toJavaMap());

        sale.onBarcode("23456");

        Assertions.assertEquals("EUR 12.50", display.getText());
    }

    @Test
    void productNotFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, io.vavr.collection.HashMap.of(
                "12345", "EUR 7.95", "23456", "EUR 12.50")
                .toJavaMap());

        sale.onBarcode("99999");

        Assertions.assertEquals("Product not found for 99999", display.getText());
    }

    private static class Sale {
        private Display display;
        private final Map<String, String> pricesByBarcode;

        private Sale(Display display, final Map<String, String> pricesByBarcode) {
            this.display = display;
            this.pricesByBarcode = pricesByBarcode;
        }

        public void onBarcode(String barcode) {
            final String price = pricesByBarcode.get(barcode);
            if (price == null) {
                display.setText(String.format("Product not found for %s", barcode));
            } else {
                display.setText(price);
            }
        }
    }

    private static class Display {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
