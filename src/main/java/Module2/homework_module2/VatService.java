package Module2.homework_module2;

import org.apache.log4j.*;

public class VatService {
    double vatValue;
    VatProvider vatProvider;
    static final Logger logger = Logger.getLogger(VatProvider.class);

    public VatService(VatProvider vatProvider) {

        this.vatProvider = vatProvider;
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setThreshold(Level.INFO);
        consoleAppender.setLayout(new PatternLayout("%d [%p|%C{1}-%M] %m"));
        consoleAppender.activateOptions();
        Logger.getRootLogger().addAppender(consoleAppender);
    }

    public double getGrossPriceForDefaultVat(Product product) throws IncorrectVatValue {
        double grossPrice = calculateGrossPrice(product.getNetPrice(), vatProvider.getDefaultVat());
        logger.info("Gross Price for: " + product.getId() + " calculated. Price: " + grossPrice);
        return grossPrice;
    }

    private double calculateGrossPrice(double netPrice, double vatValue) throws IncorrectVatValue {

        if (vatValue > 1) {
            logger.error("WRONG VAT VALUE");
            throw new IncorrectVatValue("Incorrect VAT value!");
        }
        return netPrice * (1 + vatValue);
    }

    public double getGrossPrice(Product product) throws IncorrectVatValue {

        double grossPrice;
        double vatForType = vatProvider.getVatForType(product.getType());
        double productNetPrice = product.getNetPrice();

        if (productNetPrice > 0) {
            grossPrice = calculateGrossPrice(productNetPrice, vatForType);
        } else {
            logger.error("Price must be higher then 0");
            throw new IncorrectVatValue("Price must be higher then 0");
        }
        return grossPrice;
    }
}
