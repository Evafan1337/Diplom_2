package data;

public class CreateOrderResponseSuccess {

    private String name;
    private CreateOrderResponseSuccessNumberElem order;
    private boolean success;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CreateOrderResponseSuccessNumberElem getOrder() {
        return order;
    }

    public void setOrder(CreateOrderResponseSuccessNumberElem order) {
        this.order = order;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
