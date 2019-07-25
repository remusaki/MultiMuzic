package mikuinc.multimuzic;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomepageActivity extends AppCompatActivity {

    private Button groupChat,musicRoom,about,logout;
    private String User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // after login will go here and get user data
        Intent i = getIntent();
        User = i.getStringExtra("User");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent i;
        switch (item.getItemId()) {
            case R.id.groupChatMenu:
                i = new Intent(HomepageActivity.this, ChatRoomListActivity.class);
                i.putExtra("User",User);
                startActivityForResult(i,1);
                return true;
            case R.id.musicRoomMenu:
                i= new Intent(HomepageActivity.this, MusicRoomListActivity.class);
                i.putExtra("User",User);
                startActivityForResult(i,1);
                return true;
            case R.id.aboutMenu:
                i= new Intent(HomepageActivity.this, About.class);
                i.putExtra("User",User);
                startActivityForResult(i,1);
                return true;
            case R.id.logoutMenu:
                i = new Intent();
                i.putExtra("message","You've successfully logout.");
                setResult(Activity.RESULT_OK,i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                User = data.getStringExtra("User");
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra("message","You've pressed the back button to main menu, You've been automatically logout.");
        setResult(Activity.RESULT_OK,i);
        finish();
    }



}
