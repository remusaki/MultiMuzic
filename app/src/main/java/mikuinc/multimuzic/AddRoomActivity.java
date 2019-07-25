package mikuinc.multimuzic;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class AddRoomActivity extends AppCompatActivity {

    private EditText roomNameEdit;
    private Button addButton;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setContentView(R.layout.activity_add_room);
        roomNameEdit = (EditText)findViewById(R.id.roomName);
    }

    public void doneAdding(View view){
        String roomName = roomNameEdit.getText().toString();
        Intent i = new Intent();
        i.putExtra("roomName", roomName);
        setResult(Activity.RESULT_OK, i);
        finish();
    }
}
