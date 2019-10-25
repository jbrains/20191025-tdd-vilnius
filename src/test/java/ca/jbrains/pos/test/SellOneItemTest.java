package ca.jbrains.pos.test;

import io.vavr.collection.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SellOneItemTest {

    @Test
    void productFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, HashMap.of(
                "12345", "EUR 7.95"
                , "23456", "EUR 12.50"
        ));

        sale.onBarcode("12345");

        Assertions.assertEquals("EUR 7.95", display.getText());
    }

    @Test
    void anotherProductFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, HashMap.of(
                "12345", "EUR 7.95"
                , "23456", "EUR 12.50"
        ));

        sale.onBarcode("23456");

        Assertions.assertEquals("EUR 12.50", display.getText());
    }

    @Test
    void productNotFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, HashMap.of(
                "12345", "EUR 7.95"
                , "23456", "EUR 12.50"
        ));

        sale.onBarcode("99999");

        Assertions.assertEquals("Product not found for 99999", display.getText());
    }

    @Test
    void emptyBarcode() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, HashMap.of(
                "12345", "EUR 7.95"
                , "23456", "EUR 12.50"
        ));

        sale.onBarcode("");

        Assertions.assertEquals("Scanning error: empty barcode", display.getText());
    }

    private static class Sale {
        private final Display display;
        private final HashMap<String, String> pricesByBarcode;

        private Sale(Display display, final HashMap<String, String> pricesByBarcode) {
            this.display = display;
            this.pricesByBarcode = pricesByBarcode;
        }

        public void onBarcode(String barcode) {
            if ("".equals(barcode))
                display.setText("Scanning error: empty barcode");
            else
                display.setText(
                        pricesByBarcode.get(barcode)
                                .getOrElse(String.format("Product not found for %s", barcode)));
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
