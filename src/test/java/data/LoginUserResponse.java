package data;

public class LoginUserResponse {
    private boolean success;
    private String accessToken;
    private String refreshToken;
    private LoginUserResponseUser user;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LoginUserResponseUser getUser() {
        return user;
    }

    public void setUser(LoginUserResponseUser user) {
        this.user = user;
    }
}

