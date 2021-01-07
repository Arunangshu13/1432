package com.fire.sx1gd1243;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UpdateActivity extends AppCompatActivity {
    Button cancel, update;
    EditText memoD, memoT, memoTitle, memo;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
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
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        startActivity(new Intent(this, RecordsActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        memoD=findViewById(R.id.memoD);
        memoT=findViewById(R.id.memoT);
        memoTitle=findViewById(R.id.umtitle);
        memo=findViewById(R.id.umemo);
        cancel=findViewById(R.id.cancel);
        update=findViewById(R.id.update);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        String memoid=getIntent().getExtras().getString("id");

        DatabaseReference databaseReferenceFetch=FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid()).child("memos").child(memoid);
        databaseReferenceFetch.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String mem=snapshot.child("memo").getValue().toString();
                String memDT=snapshot.child("memoDT").getValue().toString();
                String memT=snapshot.child("memoTitle").getValue().toString();
                String dt[] = memDT.split(" ");
                memoD.setText(dt[0]);
                memoT.setText(dt[1]);
                memoTitle.setText(memT);
                memo.setText(mem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference= FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid()).child("memos").child(memoid);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMemoDT=memoD.getText().toString()+" "+memoT.getText();
                String newMemo =memo.getText().toString();
                String newMemoTitle=memoTitle.getText().toString();
                HashMap keyValues=new HashMap();
                keyValues.put("memoDT",newMemoDT);
                keyValues.put("memo", newMemo);
                keyValues.put("memoTitle", newMemoTitle);
                databaseReference.updateChildren(keyValues);
                Toast.makeText(UpdateActivity.this, "Data has been successfully updated ", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(UpdateActivity.this, RecordsActivity.class);
                finish();
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(UpdateActivity.this, RecordsActivity.class);
                Toast.makeText(UpdateActivity.this, "Update process cancel", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(intent);
            }
        });
    }
}