package artemis.altmanager.logintypes.McLeaks;

public class RedeemResponse {

    final String username;
    final String token;

    RedeemResponse(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return this.username;
    }

    public String getToken() {
        return this.token;
    }
}