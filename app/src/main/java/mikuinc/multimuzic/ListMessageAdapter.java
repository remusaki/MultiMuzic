package mikuinc.multimuzic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by djmurusaki on 17 Dec 2017.
 */

public class ListMessageAdapter extends BaseAdapter {
    private Context context;
    private List<Messages> messageList;
    protected TextView user, message;
    public ListMessageAdapter(Context context, List<Messages> messageList){
        this.context = context;
        this.messageList = messageList;
    }
    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_message_list, null);

        boolean isTheUser = messageList.get(position).isTheUser();
        if(isTheUser){
            user = (TextView)view.findViewById(R.id.user);
            message = (TextView)view.findViewById(R.id.userMessage);
            user.setText(": "+messageList.get(position).getUser().toString());
            message.setText(messageList.get(position).getMessage().toString());
        }else{
            user = (TextView)view.findViewById(R.id.other);
            message = (TextView)view.findViewById(R.id.otherMessage);
            user.setText(messageList.get(position).getUser().toString()+": ");
            message.setText(messageList.get(position).getMessage().toString());
        }


        return view;
    }
}
