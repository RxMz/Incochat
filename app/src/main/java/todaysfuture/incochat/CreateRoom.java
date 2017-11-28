package todaysfuture.incochat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rishabh on 29/9/16.
 */
public class CreateRoom extends Activity {

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createroom);
        setTitle("Create Room");
        final EditText roomkaaam=(EditText)findViewById(R.id.roomname);

        Button lol= (Button)findViewById(R.id.createroomlol);
        lol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map=new HashMap<String, Object>();
                map.put(roomkaaam.getText().toString(),"");
                root.updateChildren(map);


                name = getIntent().getExtras().get("user_name").toString().trim();
                Intent intent=new Intent(getApplicationContext(),ChatRoom.class);
                intent.putExtra("room_name",roomkaaam.getText().toString());
                intent.putExtra("user_name",name);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });


    }

}
