package ca.jbrains.pos.test;

import ca.jbrains.pos.Catalog;
import ca.jbrains.pos.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class FindPriceInCatalogContract {
    @Test
    void productFound() throws Exception {
        final Price matchingPrice = Price.cents(795);
        Assertions.assertEquals(
                matchingPrice,
                catalogWith("12345", matchingPrice).findPrice("12345"));
    }

    protected abstract Catalog catalogWith(String barcode, Price matchingPrice);

    @Test
    void productNotFound() throws Exception {
        Assertions.assertEquals(
                null,
                catalogWithout("12345").findPrice("12345")
        );
    }

    protected abstract Catalog catalogWithout(String barcodeToAvoid);
}
