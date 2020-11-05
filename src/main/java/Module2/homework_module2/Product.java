package Module2.homework_module2;

import java.util.UUID;

public class Product {
    private String id;
    private String type;
    private double netPrice;

    public Product( double netPrice , String type){
        this.id = UUID.randomUUID().toString();
        this.netPrice = netPrice;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getNetPrice(){
        return this.netPrice;
    }
}
