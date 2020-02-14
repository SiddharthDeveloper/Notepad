package com.apkglobal.notepad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    EditText note;
    Button Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Save = findViewById(R.id.save);
        note = findViewById(R.id.note);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (note.getText().toString().isEmpty()) {

                    Toast.makeText(MainActivity.this, "Empty not required..", Toast.LENGTH_SHORT).show();
                } else {
                    String MyData = note.getText().toString();
                    try {
                        WriteinDatabase(MyData);
                        Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        try {
            if (ReadinDatabase() != null) {
                note.setText(ReadinDatabase());

            } else {
                note.setText("Empyt..");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void WriteinDatabase(String Data) throws IOException {

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("myfile.txt", 0));
        outputStreamWriter.write(Data);
        outputStreamWriter.close();// Its important to close other wise overflow occurs..


    }

    private String ReadinDatabase() throws IOException {

        String result = null;

        InputStream inputStream = openFileInput("myfile.txt");

        if (inputStream != null) {

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String mystring = "";

            StringBuilder stringBuilder = new StringBuilder();

            while ((mystring = bufferedReader.readLine()) != null) {

                stringBuilder.append(mystring);

            }

            inputStream.close();
            result = stringBuilder.toString();
        }


        return result;
    }
}
