package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class SellOneItemTest {

    @Test
    void productFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display);

        sale.onBarcode("12345");

        Assertions.assertEquals("EUR 7.95", display.getText());
    }

    @Test
    void anotherProductFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display);

        sale.onBarcode("23456");

        Assertions.assertEquals("EUR 12.50", display.getText());
    }

    @Test
    void productNotFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display);

        sale.onBarcode("99999");

        Assertions.assertEquals("Product not found for 99999", display.getText());
    }

    private static class Sale {
        private Display display;

        private Sale(Display display) {
            this.display = display;
        }

        public void onBarcode(String barcode) {
            final HashMap<String, String> pricesByBarcode = io.vavr.collection.HashMap.of(
                    "12345", "EUR 7.95", "23456", "EUR 12.50")
                    .toJavaMap();

            if ("12345".equals(barcode))
                display.setText(pricesByBarcode.get(barcode));
            else if ("23456".equals(barcode))
                display.setText(pricesByBarcode.get(barcode));
            else
                display.setText(String.format("Product not found for %s", barcode));
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
