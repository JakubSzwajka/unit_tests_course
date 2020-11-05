package Module2.homework_module2;

public interface VatProvider {
    double getDefaultVat();

    double getVatForType( String type );
}
