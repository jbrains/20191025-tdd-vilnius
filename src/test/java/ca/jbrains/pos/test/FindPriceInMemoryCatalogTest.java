package ca.jbrains.pos.test;

import java.util.HashMap;
import java.util.Map;

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
