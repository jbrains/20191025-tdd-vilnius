package ca.jbrains.pos.test;

import net.jqwik.api.*;
import net.jqwik.api.Tuple.Tuple1;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;

public class CatalogContract {
    @Data
    Iterable<Tuple1<CatalogFactory>> catalogFactories() {
        return Table.of(
                Tuple.of(
                        new CatalogFactory() {
                            @Override
                            public Catalog catalogWith(String barcode, Price matchingPrice) {
                                return new InMemoryCatalog(new HashMap() {{
                                    put(String.format("not %s", barcode), Price.cents(0));
                                    put(barcode, matchingPrice);
                                    put(String.format("certainly not %s", barcode), Price.cents(0));
                                }});
                            }

                            @Override
                            public Catalog catalogWithout(String missingBarcode) {
                                return new InMemoryCatalog(new HashMap() {{
                                    put(String.format("not %s", missingBarcode), Price.cents(0));
                                    put(String.format("certainly not %s", missingBarcode), Price.cents(0));
                                }});
                            }
                        })
        );
    }

    @Property
    @FromData("catalogFactories")
    void findTheMatchingPrice(@ForAll CatalogFactory catalogFactory) throws Exception {
        final Price matchingPrice = Price.cents(795);

        final Catalog catalog = catalogFactory.catalogWith("12345", matchingPrice);

        Assertions.assertEquals(matchingPrice, catalog.findPrice("12345"));
    }

    public interface CatalogFactory {
        Catalog catalogWith(String barcode, Price matchingPrice);

        Catalog catalogWithout(String missingBarcode);
    }

    public static class InMemoryCatalog implements Catalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        @Override
        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
