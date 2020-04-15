package com.example.wallpaperpro;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference refrence;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Deleter this");

        refrence = FirebaseDatabase.getInstance().getReference().child("Imageadd");

        recyclerView = (RecyclerView)findViewById(R.id.recyle12);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Task, TaskViewholder> adapter =
                new FirebaseRecyclerAdapter<Task, TaskViewholder>(
                        Task.class,
                        R.layout.cust_design,
                        TaskViewholder.class,
                        refrence
                ) {
                    @Override
                    protected void populateViewHolder(TaskViewholder taskViewholder, Task task, int i) {

                        taskViewholder.setImage(task.getImage());

                        final String key = getRef(i).getKey().toString();

                        taskViewholder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(),FullDataInformation.class);
                            intent.putExtra("key",key);
                            startActivity(intent);
                            }
                        });
                    }
                };

        recyclerView.setAdapter(adapter);
    }



    public static class TaskViewholder extends  RecyclerView.ViewHolder
    {
        View mView;
        public TaskViewholder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setImage(String image){

            ImageView imageView12 = (ImageView) mView.findViewById(R.id.image1);
            Picasso.get().load(image).into(imageView12);

        }
    }

}
