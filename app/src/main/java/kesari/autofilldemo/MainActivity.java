package kesari.autofilldemo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText UserName,UsEmail;
    private Button Savebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserName=(EditText)findViewById(R.id.primary);
        UsEmail=(EditText)findViewById(R.id.secondary);
        Savebtn=(Button)findViewById(R.id.save_button);

    }



    public void SaveData(View view) {
        SharedPreferences.Editor editor=getSharedPreferences("DATA_STORAGE",MODE_PRIVATE).edit();
        String username=UserName.getText().toString().trim();
        String useremail=UsEmail.getText().toString().trim();
        editor.putString("USER_NAME",username);
        editor.putString("USER_EMAIL",useremail);
        editor.commit();
    }
}
