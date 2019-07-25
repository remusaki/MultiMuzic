package mikuinc.multimuzic;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEdit,passwordEdit;
    private Button loginButton, registerButton;
    private String User, Password, hashedPassword;

    protected DatabaseReference userDatabase, addNewAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // login for user/admin

        usernameEdit = (EditText) findViewById(R.id.username);
        passwordEdit = (EditText)findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);

        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        addNewAccount = FirebaseDatabase.getInstance().getReference().child("Users");
        loginButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                User = usernameEdit.getText().toString().toLowerCase();
                Password = passwordEdit.getText().toString();

                if(User.isEmpty() || Password.isEmpty()){
                    if(User.isEmpty() && Password.isEmpty()){
                        Toast.makeText(MainActivity.this, "Please enter Username and Password.", Toast.LENGTH_SHORT).show();
                    }else{
                        if(User.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter a Username.", Toast.LENGTH_SHORT).show();
                        }
                        if(Password.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter a Password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    try {
                        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
                        digest.update(Password.getBytes());
                        byte messageDigest[] = digest.digest();
                        // Create Hex String
                        StringBuilder hexString = new StringBuilder();
                        for (byte aMessageDigest : messageDigest) {
                            String h = Integer.toHexString(0xFF & aMessageDigest);
                            while (h.length() < 2)
                                h = "0" + h;
                            hexString.append(h);
                        }
                        hashedPassword = hexString.toString();
                    } catch (NoSuchAlgorithmException e) {
                    }
                    userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(User)) {
                                String password = snapshot.child(User).getValue(String.class);
                                if (password.equals(hashedPassword)) {
                                    Intent i = new Intent(MainActivity.this, HomepageActivity.class);
                                    i.putExtra("User", User);
                                    startActivityForResult(i,4); // code 4 to login
                                } else {
                                    Toast.makeText(MainActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Account Doesn't Exist", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        registerButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                User = usernameEdit.getText().toString().toLowerCase();
                Password = passwordEdit.getText().toString();

                if(User.isEmpty() || Password.isEmpty()){
                    if(User.isEmpty() && Password.isEmpty()){
                        Toast.makeText(MainActivity.this, "Please enter Username and Password.", Toast.LENGTH_SHORT).show();
                    }else{
                        if(User.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter a Username.", Toast.LENGTH_SHORT).show();
                        }
                        if(Password.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter a Password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    try {
                        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
                        digest.update(Password.getBytes());
                        byte messageDigest[] = digest.digest();
                        // Create Hex String
                        StringBuilder hexString = new StringBuilder();
                        for (byte aMessageDigest : messageDigest) {
                            String h = Integer.toHexString(0xFF & aMessageDigest);
                            while (h.length() < 2)
                                h = "0" + h;
                            hexString.append(h);
                        }
                        hashedPassword = hexString.toString();
                    } catch (NoSuchAlgorithmException e) {
                    }

                    userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(User)) {
                                Toast.makeText(MainActivity.this, "Username Not Available", Toast.LENGTH_SHORT).show();
                            } else {
                                addNewAccount.child(User).setValue(hashedPassword);
                                Toast.makeText(MainActivity.this, "Registered Successfully, You can now login.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 4) {
            if(resultCode == RESULT_OK) {
                String message = data.getStringExtra("message");
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                passwordEdit.setText("");
                usernameEdit.setText("");
            }
        }
    }
}
