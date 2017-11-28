package todaysfuture.incochat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by rishabh on 23/11/16.
 */
public class NewMainActivity extends AppCompatActivity {

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private String nameou,nameor;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        getusername();
        Button join=(Button)findViewById(R.id.joinoldroom);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(NewMainActivity.this);
                builder.setTitle("Enter room name");
                final EditText inputt=new EditText(NewMainActivity.this);
                builder.setView(inputt);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        nameor=inputt.getText().toString();

                        //JOIN THE ROOM AFTER GETTING KEY
                        Intent intent2=new Intent(getApplicationContext(),ChatRoom.class);
                        intent2.putExtra("room_name",nameor); //room name
                        intent2.putExtra("user_name",nameou);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2);
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                });
                builder.show();


            }
        });
        Button newroom=(Button)findViewById(R.id.createnewroom);
        newroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
                StringBuilder sb = new StringBuilder();
                Random random = new Random();
                for (int i = 0; i < 12; i++) {
                    char c = chars[random.nextInt(chars.length)];
                    sb.append(c);
                }
                String output = sb.toString(); //the room name is here
                //CREATE THE NEW ROOM
                Map<String,Object> map=new HashMap<String, Object>();
                map.put(output,"");
                root.updateChildren(map);


                Toast.makeText(getApplicationContext(),output,Toast.LENGTH_LONG).show();

                //Give user the room key
                AlertDialog.Builder builder=new AlertDialog.Builder(NewMainActivity.this);
                builder.setTitle("Room Key");
                builder.setMessage("Here is the room key  " +output);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_SHORT);
                    }
                });

                //JOIN TO THE NEW ROOM

                Intent intent=new Intent(getApplicationContext(),ChatRoom.class);
                intent.putExtra("room_name",output); //room name
                intent.putExtra("user_name",nameou);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
    private void getusername() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Enter your name");
        final EditText input=new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nameou=input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"Please enter your name",Toast.LENGTH_LONG).show();
                getusername();
            }
        });
        builder.show();
        if(nameou==null)
            nameou="";

        //Store the name
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("name", "Elena");
        editor.commit();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.publicmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_LONG).show();
                return true;
            case R.id.vipaccess:
                Intent i=new Intent(NewMainActivity.this,VIPAccess.class);
                startActivity(i);

            case R.id.LeaveApp:
                System.exit(0);

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
