package data;

import java.util.ArrayList;

public class CreateOrderResponseSuccessNumberElem {
    private String number;

    private String _id;

    private CreateOrderResponseOwner owner;

    private String status;
    private String name;
    private String createdAt;
    private String updatedAt;
    private int price;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public CreateOrderResponseOwner getOwner() {
        return owner;
    }

    public void setOwner(CreateOrderResponseOwner owner) {
        this.owner = owner;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    private ArrayList<CreateOrderResponseOrderIngridientsElem> ingredients;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ArrayList<CreateOrderResponseOrderIngridientsElem> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<CreateOrderResponseOrderIngridientsElem> ingredients) {
        this.ingredients = ingredients;
    }
}
