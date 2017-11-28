package todaysfuture.incochat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private Button add_room;
    private EditText room_name;

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private ListView listview;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>();
    private String nameou;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_room=(Button)findViewById(R.id.btn_add_room);
        room_name=(EditText)findViewById(R.id.room_name_edittext);
        listview=(ListView)findViewById(R.id.listView);

        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list_of_rooms);
        listview.setAdapter(arrayAdapter);
        getusername();

        add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map=new HashMap<String, Object>();
                map.put(room_name.getText().toString(),"");
                root.updateChildren(map);

            }
        });
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set=new HashSet<String>();
                Iterator i =dataSnapshot.getChildren().iterator();
                while(i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());
                }
                list_of_rooms.clear();
                list_of_rooms.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),ChatRoom.class);
                intent.putExtra("room_name",((TextView)view).getText().toString());
                intent.putExtra("user_name",nameou);
                startActivity(intent);
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

        //Store the name
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("name", "Elena");
        editor.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_adder:
                Intent inte=new Intent(this,CreateRoom.class);
                inte.putExtra("user_name",nameou);
                startActivity(inte);
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;
            case R.id.LeaveApp:
                System.exit(0);

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
