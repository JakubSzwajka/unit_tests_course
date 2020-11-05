package Module2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

public class VatServiceTest {

    VatService vatService;
    VatProvider vatProvider;

    private Product generateProduct(String netPrice, String type) {
        return new Product(UUID.randomUUID(), new BigDecimal(netPrice), type);
    }

    @Test
    @DisplayName("should calculate gross price for default VAT")
    void shouldCalculateGrossPriceForDefaultVat() throws IncorrectVatException {
        // given
        when(vatProvider.getDefaultVat()).thenReturn(new BigDecimal("0.23"));
        Product product = generateProduct("20.00", "Clothes");
        // when
        BigDecimal result = vatService.getGrossPriceForDefaultVat(product);
        // then
        assertThat(result).isEqualTo(new BigDecimal("24.60"));
    }

    @Test
    void shouldThrowExceptionWhenVatIsTooHigh(){
        // given
        String type = "Clothes";
        Product product = generateProduct("10.00", type);
        when(vatProvider.getVatForType(type)).thenReturn(BigDecimal.TEN);
        // then
        assertThatExceptionOfType(IncorrectVatException.class).isThrownBy(() -> {
            vatService.getGrossPrice(product, type);
        }).withMessage("VAT must be lower!");
    }

    @Test
    void shouldCalculateGrossPriceForOtherVatValue() throws IncorrectVatException {
        // given
        String type = "Books";
        Product product = generateProduct("10.00", type );
        when(vatProvider.getVatForType(type)).thenReturn(new BigDecimal("0.08"));
        // when
        BigDecimal result = vatService.getGrossPrice(product, type);
        // then
        assertThat(result).isEqualTo(new BigDecimal("10.80"));
    }

    @BeforeEach
    void prepareVatService(){
        vatProvider = Mockito.mock(VatProvider.class);
        vatService = new VatService(vatProvider);
    }
}