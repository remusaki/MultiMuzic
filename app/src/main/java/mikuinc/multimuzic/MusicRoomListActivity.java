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

public class MusicRoomListActivity extends AppCompatActivity {

    private ListView listViewRooms;
    private ListRoomAdapter adapter;
    private ArrayList<Rooms> roomsList;

    private String User;

    private FloatingActionButton fab;

    private FirebaseDatabase database;
    private DatabaseReference musicRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_room_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getting userinfo from previous activity
        Intent i = getIntent();
        User = i.getStringExtra("User");

        // to initiate the listview
        listViewRooms = (ListView) findViewById(R.id.musicRooms);
        roomsList = new ArrayList<>();
        adapter = new ListRoomAdapter(getApplicationContext(), roomsList);
        listViewRooms.setAdapter(adapter);

        //what the listview will do on data select
        listViewRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String roomName = roomsList.get(position).getRoomName();
                Toast.makeText(getApplicationContext(), "You've entered room: "+roomName, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MusicRoomListActivity.this, MusicRoomActivity.class);
                i.putExtra("roomName",roomName);
                i.putExtra("User",User);
                startActivityForResult(i,1); // code 1
            }
        });

        // listening to database, will first clear the list then put all again everytime a new room is added
        database = FirebaseDatabase.getInstance();
        musicRef = database.getReference().child("music_rooms");
        musicRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                roomsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String child = snapshot.getKey();
                    roomsList.add(new Rooms(child,"0"));
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
                Intent i = new Intent(MusicRoomListActivity.this, AddRoomActivity.class);
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
                for (int x = 0; x < roomsList.size(); x++) {
                    if (roomsList.get(x).getRoomName().trim().equals(room)) {
                        isRoomNameExist = true;
                        break;
                    }
                }
                if (isRoomNameExist) {
                    Toast.makeText(getApplicationContext(), "Room Already Exist", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference addDatabase = FirebaseDatabase.getInstance().getReference().child("music_rooms").child(room);

                    addDatabase.child("song").setValue("");
                    addDatabase.child("admin").setValue(User);

                    Rooms rooms = new Rooms(room, "0");
                    roomsList.add(rooms);
                    Toast.makeText(getApplicationContext(), "Room Added", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }
        }
    }



}