package data;

public class EditUserResponseSuccess {
    private boolean success;

    private EditUserResponseSuccessUserElem user;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public EditUserResponseSuccessUserElem getUser() {
        return user;
    }

    public void setUser(EditUserResponseSuccessUserElem user) {
        this.user = user;
    }
}
