package todaysfuture.incochat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by rishabh on 30/11/16.
 */
public class VIPAccess extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vipaccess);
        Button login=(Button)findViewById(R.id.btn_login);
        final EditText user=(EditText)findViewById(R.id.input_email);
        final EditText pass=(EditText)findViewById(R.id.input_password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                if(user.getText().toString().equals("RishabhM")&&pass.getText().toString().equals("rmiya")){
                    Intent i=new Intent(VIPAccess.this, MainActivity.class);
                    startActivity(i);
                }
                else
                    Toast.makeText(getApplicationContext(),"Wrong Password",Toast.LENGTH_LONG).show();
                    */
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i =new Intent(VIPAccess.this,NewMainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
