package data;

public class CreateUserRequest {
    String email;
    String password;
    String name;

    public CreateUserRequest(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public CreateUserRequest(){

    }
}
