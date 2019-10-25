import ca.jbrains.pos.Display;
import ca.jbrains.pos.InMemoryCatalog;
import ca.jbrains.pos.Price;
import ca.jbrains.pos.SellOneItemController;

import java.util.HashMap;

public class PointOfSaleTerminal {
    public static void main(String[] args) {
        final SellOneItemController controller = new SellOneItemController(
                new InMemoryCatalog(new HashMap<>() {{
                    put("12345", Price.cents(795));
                }}),
                new Display() {
                    @Override
                    public void displayPrice(Price price) {
                        System.out.println(String.format("EUR %.2f", price.euro()));

                    }

                    @Override
                    public void displayProductNotFoundMessage(String missingBarcode) {
                        System.out.println(String.format("Product not found for %s", missingBarcode));
                    }

                    @Override
                    public void displayEmptyBarcodeMessage() {
                        System.out.println(String.format("Scanning error: empty barcode"));
                    }
                }
        );
        controller.onBarcode("12345");
        controller.onBarcode("23456");
        controller.onBarcode("99999");
        controller.onBarcode("");
    }
}
