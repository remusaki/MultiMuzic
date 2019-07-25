package mikuinc.multimuzic;


import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class MusicRoomActivity extends AppCompatActivity {

    private ListView musicListView;
    private ListMusicAdapter adapter;
    private ArrayList<Musics> musicsList;

    private Button stopButton;
    private TextView textView;
    private EditText delayText;
    private int playDelay;
    private MediaPlayer flaris,prontera,persona, allstar,blend_s,epic_sax,padoru_padoru,rich_boy,silhouette,suki_yuki,ultra_instinct;
    private ImageView musicLogo;

    protected String User;
    protected String roomName;
    protected Boolean isAdmin;
    protected ValueEventListener songEventListener, roomEventListener;
    protected DatabaseReference roomDatabase,roomAdminDatabase,musicDatabase,songToPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_room);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        musicListView = (ListView) findViewById(R.id.musicList);
        musicsList = new ArrayList<>();
        adapter = new ListMusicAdapter(getApplicationContext(), musicsList);
        musicListView.setAdapter(adapter);

        musicsList.add(new Musics("Life Will Change"));
        musicsList.add(new Musics("Ragnarok Prontera"));
        musicsList.add(new Musics("Flyff Flaris"));
        musicsList.add(new Musics("All Star"));
        musicsList.add(new Musics("Smile Sweet Sister Sadistic"));
        musicsList.add(new Musics("Epic Sax Guy"));
        musicsList.add(new Musics("Padoru Padoru"));
        musicsList.add(new Musics("Rich Boy"));
        musicsList.add(new Musics("Silhouette"));
        musicsList.add(new Musics("Suki Yuki"));
        musicsList.add(new Musics("Ultra Instinct"));

        adapter.notifyDataSetChanged();


        stopButton = (Button) findViewById(R.id.stopButton);
        textView = (TextView) findViewById(R.id.textView);
        delayText = (EditText) findViewById(R.id.delay);
        musicLogo = (ImageView)findViewById(R.id.musicLogo);

        flaris = MediaPlayer.create(MusicRoomActivity.this, R.raw.flyff_flaris);
        prontera = MediaPlayer.create(MusicRoomActivity.this, R.raw.ragnarok_prontera);
        persona = MediaPlayer.create(MusicRoomActivity.this, R.raw.life_will_change);
        allstar = MediaPlayer.create(MusicRoomActivity.this, R.raw.all_star);
        blend_s = MediaPlayer.create(MusicRoomActivity.this, R.raw.blend_s);
        epic_sax = MediaPlayer.create(MusicRoomActivity.this, R.raw.epic_sax);
        padoru_padoru = MediaPlayer.create(MusicRoomActivity.this, R.raw.padoru_padoru);
        rich_boy = MediaPlayer.create(MusicRoomActivity.this, R.raw.rich_boy);
        silhouette = MediaPlayer.create(MusicRoomActivity.this, R.raw.silhouette);
        suki_yuki = MediaPlayer.create(MusicRoomActivity.this, R.raw.suki_yuki);
        ultra_instinct = MediaPlayer.create(MusicRoomActivity.this, R.raw.ultra_instinct);

        Intent i = getIntent();
        roomName = i.getStringExtra("roomName");
        User = i.getStringExtra("User");

        roomDatabase = FirebaseDatabase.getInstance().getReference().child("music_rooms").child(roomName);
        roomAdminDatabase = roomDatabase.child("admin");
        songToPlay = roomDatabase.child("song");

        roomEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String admin = (String) dataSnapshot.getValue();
                if (!User.equals(admin)) {
                    musicListView.setVisibility(View.INVISIBLE);
                    stopButton.setVisibility(View.INVISIBLE);
                    isAdmin = false;
                }else{
                    isAdmin = true;
                    musicLogo.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        // getting admin name
        roomAdminDatabase.addListenerForSingleValueEvent(roomEventListener);

        musicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                songToPlay.setValue("");
                String musicName = musicsList.get(position).getMusicName();
                songToPlay.setValue(musicName);
            }
        });

        songEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String music = dataSnapshot.getValue(String.class);
                String delay = delayText.getText().toString();
                if(delay.equals("")){
                    playDelay = 0;
                }else{
                    playDelay = Integer.parseInt(delay);
                }

                if(playDelay < 0){
                    playDelay = 0;
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        switch(music){
                            case "Ragnarok Prontera":
                                prontera.start();
                                textView.setText("Current Music: Ragnarok Prontera");
                                break;
                            case "Flyff Flaris":
                                flaris.start();
                                textView.setText("Current Music: Flyff Flaris");
                                break;
                            case "Life Will Change":
                                persona.start();
                                textView.setText("Current Music: Life Will Change");
                                break;
                            case "All Star":
                                allstar.start();
                                textView.setText("Current Music: All Star");
                                break;
                            case "Smile Sweet Sister Sadistic":
                                blend_s.start();
                                textView.setText("Current Music: Smile Sweet Sister Sadistic");
                                break;
                            case "Epic Sax Guy":
                                epic_sax.start();
                                textView.setText("Current Music: Epic Sax Guy");
                                break;
                            case "Padoru Padoru":
                                padoru_padoru.start();
                                textView.setText("Current Music: Padoru Padoru");
                                break;
                            case "Rich Boy":
                                rich_boy.start();
                                textView.setText("Current Music: Rich Boy");
                                break;
                            case "Silhouette":
                                silhouette.start();
                                textView.setText("Current Music: Silhouette");
                                break;
                            case "Suki Yuki":
                                suki_yuki.start();
                                textView.setText("Current Music: Suki Yuki");
                                break;
                            case "Ultra Instinct":
                                ultra_instinct.start();
                                textView.setText("Current Music: Ultra Instinct");
                                break;
                            case "":
                                persona.stop();
                                flaris.stop();
                                prontera.stop();
                                allstar.stop();
                                blend_s.stop();
                                epic_sax.stop();
                                padoru_padoru.stop();
                                rich_boy.stop();
                                silhouette.stop();
                                suki_yuki.stop();
                                ultra_instinct.stop();

                                textView.setText("No Music Playing");

                                flaris = MediaPlayer.create(MusicRoomActivity.this, R.raw.flyff_flaris);
                                prontera = MediaPlayer.create(MusicRoomActivity.this, R.raw.ragnarok_prontera);
                                persona = MediaPlayer.create(MusicRoomActivity.this, R.raw.life_will_change);
                                allstar = MediaPlayer.create(MusicRoomActivity.this, R.raw.all_star);
                                blend_s = MediaPlayer.create(MusicRoomActivity.this, R.raw.blend_s);
                                epic_sax = MediaPlayer.create(MusicRoomActivity.this, R.raw.epic_sax);
                                padoru_padoru = MediaPlayer.create(MusicRoomActivity.this, R.raw.padoru_padoru);
                                rich_boy = MediaPlayer.create(MusicRoomActivity.this, R.raw.rich_boy);
                                silhouette = MediaPlayer.create(MusicRoomActivity.this, R.raw.silhouette);
                                suki_yuki = MediaPlayer.create(MusicRoomActivity.this, R.raw.suki_yuki);
                                ultra_instinct = MediaPlayer.create(MusicRoomActivity.this, R.raw.ultra_instinct);
                                break;
                        }
                    }
                }, playDelay);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        songToPlay.addValueEventListener(songEventListener);

        stopButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                songToPlay.setValue("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        prontera.stop();
        flaris.stop();
        persona.stop();
        allstar.stop();
        if(isAdmin){
            songToPlay.setValue("");
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        songToPlay.removeEventListener(songEventListener);
        roomAdminDatabase.removeEventListener(roomEventListener);
        if(isAdmin){
            songToPlay.setValue("");
        }
        finish();
    }


}