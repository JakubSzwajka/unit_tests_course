package Module2.homework_module2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VatServiceTest {

    private static VatService vatService;
    private static VatProvider vatProvider;
    private static Product product;

    @BeforeAll
    public static void setUp() {
        VatServiceTest.vatProvider = Mockito.mock(VatProvider.class);
        VatServiceTest.product = Mockito.mock(Product.class);
        when(product.getId()).thenReturn(UUID.randomUUID().toString());

        VatServiceTest.vatService = new VatService(vatProvider);
    }

    @Test
    public void ShouldGetGrossPriceForDefaultVat() throws IncorrectVatValue {
        // given
        // this.product = new Module2.Product( 20.00, "Clothes" );
        when(product.getNetPrice()).thenReturn(20.00);
        when(product.getType()).thenReturn("Clothes");

        when(vatProvider.getDefaultVat()).thenReturn(0.23);
        // when
        Double grossPrice = vatService.getGrossPriceForDefaultVat(this.product);
        // then
        assertThat(grossPrice).isEqualTo(24.60);

    }

    @Test
    public void ShouldGetGrossPriceForNonDefaultVat() throws IncorrectVatValue {
        // given
//        this.product = new Module2.Product(20.00, "Clothes");
        when(product.getNetPrice()).thenReturn(20.00);
        when(product.getType()).thenReturn("Clothes");

        when(vatProvider.getVatForType("Clothes")).thenReturn(0.08);
        // when
        Double grossPrice = vatService.getGrossPrice(VatServiceTest.product);
        // then
        assertThat(grossPrice).isEqualTo(21.6);
    }

    @Test
    public void ShouldThrowExceptionWhenVatBiggerThenOne() throws IncorrectVatValue {
        // given
        // this.product = new Module2.Product(20.0, "Exception Type");
        when(product.getNetPrice()).thenReturn(20.00);
        when(product.getType()).thenReturn("Exception Type");

        when(vatProvider.getVatForType("Exception Type")).thenReturn(1.2);
        // then
        assertThatExceptionOfType(IncorrectVatValue.class).isThrownBy(() -> {
            Double grossPrice = vatService.getGrossPrice(product);
        });
    }

    @Test
    public void ShouldThrowExceptionWhenPriceEqZero() throws IncorrectVatValue {
        // given
        when(product.getNetPrice()).thenReturn(0.0);
        when(product.getType()).thenReturn("Zero Exception Type");
        // when
        when(vatProvider.getVatForType("Zero Exception Type")).thenReturn(0.05);
        // then
        assertThatExceptionOfType(IncorrectVatValue.class).isThrownBy(() -> {
            double grossPrice = vatService.getGrossPrice(product);
        }).withMessage("Price must be higher then 0");
    }
}