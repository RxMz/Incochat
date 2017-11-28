package todaysfuture.incochat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by rishabh on 27/9/16.
 */
public class ChatRoom extends AppCompatActivity{
    private Button btn_send_msg;
    private EditText input_msg;
    private TextView chat_conversation;

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private String user_name,room_name;
    private DatabaseReference root ;
    private String temp_key;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom);

        btn_send_msg = (Button) findViewById(R.id.btn_send);
        input_msg = (EditText) findViewById(R.id.msg_input);
        chat_conversation = (TextView) findViewById(R.id.textView);



        user_name = getIntent().getExtras().get("user_name").toString().trim();
        room_name = getIntent().getExtras().get("room_name").toString();
        setTitle(" Room - "+room_name);


        //ROOM NAME
        //Give user the room key
        AlertDialog.Builder builder=new AlertDialog.Builder(ChatRoom.this);
        builder.setTitle("Room Key");
        builder.setMessage("Here is the room key. Share this so others can join " +room_name);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_SHORT);
            }
        });
        builder.show();


        root= FirebaseDatabase.getInstance().getReference().child(room_name);
        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input_msg.length()>=1) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    temp_key = root.push().getKey();
                    root.updateChildren(map);
                    DatabaseReference messageroot = root.child(temp_key);
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("name", user_name);
                    map2.put("msg", input_msg.getText().toString());
                    messageroot.updateChildren(map2);
                    input_msg.setText("");
                }
            }
        });
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
             append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private String chat_msg,chat_user_name;

    private void append_chat_conversation(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){

            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot)i.next()).getValue();

            chat_conversation.append(chat_user_name +" : "+chat_msg +" \n");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chatmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deletechat:
                //TODO IMP
                /*DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("incochat-ea29f").orderByChild(room_name).equalTo(room_name);

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i( "onCancelled",""+ databaseError.toException());
                    }
                });
                */
                Intent i=new Intent(ChatRoom.this,NewMainActivity.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"Done. Leave the app and it'll be deleted",Toast.LENGTH_LONG).show();

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
