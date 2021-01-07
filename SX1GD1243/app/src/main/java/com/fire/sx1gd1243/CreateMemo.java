package com.fire.sx1gd1243;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateMemo extends AppCompatActivity {
    private EditText memoDT, memoTitle, memoTI, memo;
    private static int memoId;
    private Button addMemo;
    private DatabaseReference databaseReference;
    private FirebaseAuth authentication;
    private FirebaseUser user;

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        startActivity(new Intent(this, DashboardActivity.class));
    }

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

                authentication.signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_memo);

        addMemo=findViewById(R.id.insertMem);
        memoDT=findViewById(R.id.edtxtdt);
        memoTitle=findViewById(R.id.edtxttl);
        memoTI=findViewById(R.id.edtxtti);
        memo=findViewById(R.id.edtxtmem);

        Random rand=new Random();
        int makeUnique=1+rand.nextInt(2);

        authentication= FirebaseAuth.getInstance();
        user=authentication.getCurrentUser();
        databaseReference=FirebaseDatabase.getInstance().getReference().child(user.getUid());

        //creating a new memo id
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            memoId=Integer.parseInt(snapshot.child("memIdGen").getValue().toString());
            memoId+=makeUnique;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertMemory();
            }
        });
    }
    public void insertMemory(){
        /* update the memo id
            for that get the reference of the database
        * */
        DatabaseReference generalReference=FirebaseDatabase.getInstance().getReference().child(user.getUid());
        HashMap keyValue=new HashMap();
        keyValue.put("memIdGen", memoId);
        Log.i("memoId has been created", ": "+memoId);
        String smemoID=String.valueOf(memoId);
        DatabaseReference memoReference=generalReference.child("memos").child(smemoID);
        HashMap defaultdata=new HashMap();
        String smemo= memo.getText().toString();
        String smemoDT= memoDT.getText().toString().trim()+" "+memoTI.getText().toString().trim();
        String smemoTitle=memoTitle.getText().toString().trim();

        if(smemo.equals("") || smemoDT.equals("") || smemoTitle.equals(""))
        {
            Toast.makeText(this, "All fields should be non empty", Toast.LENGTH_SHORT).show();
        }
        else {

            defaultdata.put("memoDT", smemoDT);
            defaultdata.put("memoTitle",smemoTitle);
            defaultdata.put("memo",smemo);

            memoReference.setValue(defaultdata);

            generalReference.updateChildren(keyValue).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Toast.makeText(CreateMemo.this, "Memory added to the database ! :) ", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateMemo.this, "Oh o there is some problem :( ", Toast.LENGTH_LONG).show();
                }
            });

            Intent intent=new Intent(this, DashboardActivity.class);
            finish();
            startActivity(intent);
        }


    }
}