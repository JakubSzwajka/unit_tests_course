package Module2;

import java.math.BigDecimal;
import java.math.MathContext;

public class VatService {

        VatProvider vatProvider;

        public VatService(VatProvider vatProvider){ this.vatProvider = vatProvider; }

        public BigDecimal getGrossPriceForDefaultVat(Product product) throws IncorrectVatException {
            return calculateGrossPrice(product.getNetPrice(), vatProvider.getDefaultVat());
        }

        public BigDecimal getGrossPrice(Product product, String type ) throws  IncorrectVatException{
                return  calculateGrossPrice(product.getNetPrice(), vatProvider.getVatForType(type));
        }


        private BigDecimal calculateGrossPrice(BigDecimal netPrice,BigDecimal vatValue ) throws IncorrectVatException{
                MathContext m = new MathContext(4 );
                if (vatValue.compareTo(BigDecimal.ONE) == 1 ){
                        throw new IncorrectVatException("VAT must be lower!");
                }
                return netPrice.multiply(vatValue.add(BigDecimal.ONE)).round(m);
        }
}
