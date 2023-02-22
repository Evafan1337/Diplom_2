package data;

public class CreateOrderResponse {

    private String name;
    private CreateOrderResponseNumberElem order;
    private boolean success;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CreateOrderResponseNumberElem getOrder() {
        return order;
    }

    public void setOrder(CreateOrderResponseNumberElem order) {
        this.order = order;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
