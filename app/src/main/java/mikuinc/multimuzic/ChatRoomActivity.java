package mikuinc.multimuzic;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    private ListView messageListView;
    private ListMessageAdapter adapter;
    private ArrayList<Messages> messageList;
    private String roomName, User;
    private String userFromDatabase, messageFromDatabase;
    private boolean isTheUser;

    private ImageButton sendButton;
    private EditText messageEditText;
    protected DatabaseReference roomDatabase, inputDatabase, inputMessage, inputUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        messageListView = (ListView) findViewById(R.id.messageList);
        messageList = new ArrayList<>();
        adapter = new ListMessageAdapter(getApplicationContext(), messageList);
        messageListView.setAdapter(adapter);

        /*messageListView.postDelayed(new Runnable() {
            @Override
           public void run() {
                messageListView.setSelection(adapter.getCount()-1);
            }
        }, 500);*/

        messageEditText = (EditText)findViewById(R.id.messageEditText);
        sendButton = (ImageButton)findViewById(R.id.sendButton);

        Intent i = getIntent();
        roomName = i.getStringExtra("roomName");
        User = i.getStringExtra("User");

        roomDatabase = FirebaseDatabase.getInstance().getReference().child("chat_rooms").child(roomName);
        roomDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                messageList.clear();
                for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                    if (snapshot.child("User").exists()) {
                        userFromDatabase = snapshot.child("User").getValue(String.class);
                        messageFromDatabase = snapshot.child("Message").getValue(String.class);
                        if (userFromDatabase.equals(User)) {
                            isTheUser = true;
                        } else {
                            isTheUser = false;
                        }
                        messageList.add(new Messages(userFromDatabase, messageFromDatabase, isTheUser));
                    }else{
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sendButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
            final String message = messageEditText.getText().toString();

            if(!message.isEmpty()) {
                String messageTime = Long.toString(System.currentTimeMillis());
                inputDatabase = FirebaseDatabase.getInstance().getReference().child("chat_rooms").child(roomName).child(messageTime);
                inputUser = inputDatabase.child("User");
                inputMessage = inputDatabase.child("Message");
                sendButton.setEnabled(false);
                messageEditText.setText("");

                inputUser.setValue(User);
                inputMessage.setValue(message);
                sendButton.setEnabled(true);
            }else{
                Toast.makeText(getApplicationContext(), "Message is Empty", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }
}
