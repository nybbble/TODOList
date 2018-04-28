package com.example.bmetin.hw1;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingleTask extends AppCompatActivity {

    private String task_key = null;
    private TextView singleTask;
    private TextView singleTime;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);

        task_key = getIntent().getExtras().getString("TaskId");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tasks");

        singleTask = (TextView) findViewById(R.id.singleTask);
        singleTime = (TextView) findViewById(R.id.singleTime);

        Button deleteBtn = (Button) findViewById(R.id.deleteBtn);
        Button updateBtn = (Button) findViewById(R.id.updateBtn);

        mDatabase.child(task_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String task_title = (String) dataSnapshot.child("name").getValue();
                String task_time = (String) dataSnapshot.child("time").getValue();

                singleTask.setText(task_title);
                singleTime.setText(task_time);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTask(task_key);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = singleTask.getText().toString().trim();
                String time = singleTime.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    updateTask(name, time);
                }
            }
        });



    }

    /*
    private void showUpdateDeleteDialog(final String name, final String time) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_single_task, null);
        dialogBuilder.setView(dialogView);


        final Button updateBtn = (Button) dialogView.findViewById(R.id.updateBtn);
        final Button deleteBtn = (Button) dialogView.findViewById(R.id.deleteBtn);

        dialogBuilder.setTitle("Updating Task"+task_key);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = singleTask.getText().toString().trim();
                String time = singleTime.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    updateTask(name, time);
                    b.dismiss();
                }
            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }
*/


    private void deleteTask(String name)
    {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Tasks").child(task_key);

        dR.removeValue();

        Toast.makeText(this,"Task is deleted !", Toast.LENGTH_LONG).show();
    }


    private boolean updateTask(String name, String time) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Tasks").child(task_key);

        //updating artist
        Task task = new Task(name,time);
        dR.setValue(task);
        Toast.makeText(getApplicationContext(), "Task Updated", Toast.LENGTH_LONG).show();
        return true;
    }



}
