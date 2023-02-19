package data;

public class CreateUserRequest {
    public final String email;
    public final String password;
    public final String name;

    public CreateUserRequest(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
