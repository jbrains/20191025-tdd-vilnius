package ca.jbrains.pos.test;

import ca.jbrains.pos.Catalog;
import ca.jbrains.pos.InMemoryCatalog;
import ca.jbrains.pos.Price;

import java.util.HashMap;

public class FindPriceInMemoryCatalogTest extends FindPriceInCatalogContract {

    @Override
    protected Catalog catalogWith(String barcode, Price matchingPrice) {
        return new InMemoryCatalog(new HashMap<>() {{
            put(barcode, matchingPrice);
            put(String.format("not %s", barcode), Price.cents(0));
            put(String.format("definitely not %s", barcode), Price.cents(0));
        }});
    }

    @Override
    protected Catalog catalogWithout(String barcode) {
        return new InMemoryCatalog(new HashMap<>() {{
            put(String.format("not %s", barcode), Price.cents(0));
            put(String.format("definitely not %s", barcode), Price.cents(0));
        }});
    }

}
