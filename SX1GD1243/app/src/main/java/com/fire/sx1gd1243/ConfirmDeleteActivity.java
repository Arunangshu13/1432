package com.fire.sx1gd1243;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfirmDeleteActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_delete);
        String memoid=getIntent().getExtras().getString("id");
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        databaseReference=FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid()).child("memos");
        tv=findViewById(R.id.txtcontent);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(memoid!=null)
                {
                    for(DataSnapshot snp:snapshot.getChildren())
                    {
                        if(snp.getKey().equals(memoid))
                        {
                            String memoInfo="";
                            memoInfo+="Memo Id. : "+memoid+"\n";
                            String memoDateTime=snp.child("memoDT").getValue().toString();
                            memoInfo+="Memo Date & Time : "+memoDateTime+"\n";
                            String memoTitle=snp.child("memoTitle").getValue().toString();
                            memoInfo+="Title : "+memoTitle+"\n";
                            String memo=snp.child("memo").getValue().toString();
                            memoInfo+=memo+"\n";
                            tv.setText(memoInfo);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        startActivity(new Intent(this, RecordsActivity.class));
    }

    public void cancelDelete(View view){
        Intent intent=new Intent(ConfirmDeleteActivity.this, RecordsActivity.class);
        Toast.makeText(this, "Delete process has been canceled", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(intent);
    }

    public void deleteMemo(View view) {
        String id=getIntent().getExtras().getString("id");
        DatabaseReference dbref=FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid()).child("memos").child(id);
        dbref.removeValue();
        Intent intent=new Intent(ConfirmDeleteActivity.this, RecordsActivity.class);
        Toast.makeText(this, "Memory has been deleted from the database", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(intent);

    }
}