package mikuinc.multimuzic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by djmurusaki on 8 Dec 2017.
 */

public class ListRoomAdapter extends BaseAdapter {
    private Context context;
    private List<Rooms> roomsList;


    public ListRoomAdapter(Context context, List<Rooms> roomsList){
        this.context = context;
        this.roomsList = roomsList;
    }

    @Override
    public int getCount() {
        return roomsList.size();
    }

    @Override
    public Object getItem(int position) {
        return roomsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_rooms_list, null);
        TextView roomName = (TextView)view.findViewById(R.id.roomName);
        TextView roomMembers = (TextView)view.findViewById(R.id.roomMembers);

        roomName.setText(roomsList.get(position).getRoomName());
        roomMembers.setText(roomsList.get(position).getRoomMembers() + " members");

        return view;
    }
}
