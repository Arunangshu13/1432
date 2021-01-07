package com.fire.sx1gd1243;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {
//    private FirebaseDatabase rootNode;
    private DatabaseReference dbref;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private TextView tv;
    private Button display, addmemo;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent (this, LoginActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.logoutop:{
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        tv=findViewById(R.id.userName);
        display=findViewById(R.id.memShow);
        addmemo=findViewById(R.id.memCreate);


        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        dbref=FirebaseDatabase.getInstance().getReference().child(user.getUid());
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name=snapshot.child("Name").getValue().toString();
                String content="Welcome "+name+". ";
                tv.setText(content);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardActivity.this, RecordsActivity.class);
                finish();
                startActivity(intent);
            }
        });
        addmemo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(DashboardActivity.this, CreateMemo.class);
                finish();
                startActivity(intent);
            }
        });

    }
}