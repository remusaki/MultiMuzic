package mikuinc.multimuzic;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatRoomListActivity extends AppCompatActivity {

    private ListView listViewChatRooms;

    private ListRoomAdapter adapter;
    private ArrayList<Rooms> chatRoomList;


    private String User;

    private FloatingActionButton fab;

    private FirebaseDatabase database;
    private DatabaseReference chatRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getting userinfo from previous activity
        Intent i = getIntent();
        User = i.getStringExtra("User");

        // to initiate the listview
        listViewChatRooms = (ListView) findViewById(R.id.chatRooms);
        chatRoomList = new ArrayList<>();
        adapter = new ListRoomAdapter(getApplicationContext(), chatRoomList);
        listViewChatRooms.setAdapter(adapter);

        //what the listview will do on data select
        listViewChatRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String roomName = chatRoomList.get(position).getRoomName();
                Toast.makeText(getApplicationContext(), "You've entered room: "+roomName, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ChatRoomListActivity.this, ChatRoomActivity.class);

                i.putExtra("roomName",roomName);
                i.putExtra("User",User);
                startActivityForResult(i,1); // code 1
            }
        });

        // listening to database, will first clear the list then put all again everytime a new room is added
        database = FirebaseDatabase.getInstance();
        chatRef = database.getReference().child("chat_rooms");
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatRoomList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String roomName = snapshot.getKey();
                    //String member = snapshot.child("Member").getValue(String.class);
                    chatRoomList.add(new Rooms(roomName,"0"));
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // to add new room
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChatRoomListActivity.this, AddRoomActivity.class);
                startActivityForResult(i, 2); // code 2
            }
        });
    }
    // since we've used startActivityForResult, when that activity ended we can retrive the data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                User = data.getStringExtra("User");
            }
        }else if(requestCode == 2){
            if(resultCode == RESULT_OK){
                String room = data.getStringExtra("roomName");

                Boolean isRoomNameExist = false;
                for (int x = 0; x < chatRoomList.size(); x++) {
                    if (chatRoomList.get(x).getRoomName().trim().equals(room)) {
                        isRoomNameExist = true;
                        break;
                    }
                }
                if (isRoomNameExist) {
                    Toast.makeText(getApplicationContext(), "Room Already Exist", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference addDatabase = FirebaseDatabase.getInstance().getReference().child("chat_rooms").child(room);

                    addDatabase.child("Member").setValue("0");
                    Toast.makeText(getApplicationContext(), "Room Added", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }
        }
    }



}
