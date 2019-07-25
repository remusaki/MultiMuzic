package mikuinc.multimuzic;

/**
 * Created by djmurusaki on 17 Dec 2017.
 */

public class Messages {
    public String user, message;
    public boolean isTheUser;
    public Messages(String user, String message, boolean isTheUser){
        this.user = user;
        this.message = message;
        this.isTheUser = isTheUser;
    }


    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public boolean isTheUser() {
        return isTheUser;
    }
}
