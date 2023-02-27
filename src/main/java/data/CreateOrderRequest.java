package data;

import java.util.ArrayList;
import java.util.List;

public class CreateOrderRequest {

    public final ArrayList<String> ingredients;

    public CreateOrderRequest(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

}
