package sg.edu.rp.c346.id20014063.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnInsert, btngetTask;
    TextView tvResult;
    ListView lv;
    ArrayAdapter<Task> aa;
    ArrayList<Task> al;
    EditText etName, etDate;
    Boolean asc = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInsert = findViewById(R.id.btnInsert);
        btngetTask = findViewById(R.id.btnGetTasks);
        tvResult = findViewById(R.id.textViewResults);
        lv = findViewById(R.id.listView);
        al = new ArrayList<>();
        etName = findViewById(R.id.editTextItemName);
        etDate = findViewById(R.id.editTextDate);

        aa = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                dbh.insertTask(etName.getText().toString(), etDate.getText().toString());
            }
        });

        btngetTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                ArrayList<String> data = dbh.getTaskContent();
                dbh.close();

                String txt = "";
                for (int i = 0; i<data.size(); i++){
                    Log.d("Database Content", i + ". " + data.get(i));
                    txt += i + ". " + data.get(i) + "\n";
                }
                tvResult.setText(txt);
                if(asc){
                    asc = false;
                }else{
                    asc = true;
                }

                al.clear();
                al.addAll(dbh.getTasks(asc));
                aa.notifyDataSetChanged();

            }
        });
    }
}