package mikuinc.multimuzic;

/**
 * Created by djmurusaki on 8 Dec 2017.
 */

public class Rooms {
    private String roomMembers;
    private String roomName;

    public Rooms(String roomnName, String roomMembers){
        this.roomMembers = roomMembers;
        if(roomMembers != null){
            this.roomName = roomnName;
        }else{
            this.roomName = "0";
        }

    }

    public String getRoomMembers() {
        return roomMembers;
    }
    public String getRoomName() {
        return roomName;
    }
}
