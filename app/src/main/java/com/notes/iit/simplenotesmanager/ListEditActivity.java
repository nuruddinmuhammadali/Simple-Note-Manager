package com.notes.iit.simplenotesmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ListEditActivity extends AppCompatActivity {
    private SqliteHelper sqliteHelper;
    EditText name,description;
    TextView dateModified;
    TextView characterCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_edit);
        sqliteHelper=new SqliteHelper(this);
        initializeViews();
        final TextWatcher mTextEditorWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                characterCount.setText(String.valueOf(s.length()));
            }

            public void afterTextChanged(Editable s) {
            }
        };
        description.addTextChangedListener(mTextEditorWatcher);
    }

    private void initializeViews() {
        name=(EditText)findViewById(R.id.name);
        description=(EditText)findViewById(R.id.description);
        dateModified=(TextView)findViewById(R.id.date);
        characterCount=(TextView)findViewById(R.id.characterCount);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String currentDateTime=simpleDateFormat.format(new Date());
        dateModified.setText(currentDateTime);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.save:
                Date date=new Date();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String currentDateTime=simpleDateFormat.format(date);
                Note note=new Note(name.getText().toString(),description.getText().toString(),currentDateTime);
                sqliteHelper.addNote(note);
                dateModified.setText(currentDateTime);
                String welcome = getString(R.string.noteSaved);// + model.getDisplayName();
                // TODO : initiate successful logged in experience
                Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
