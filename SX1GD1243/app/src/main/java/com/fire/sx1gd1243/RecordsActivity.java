package com.fire.sx1gd1243;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import com.fire.sx1gd1243.MemoAttributes;
public class RecordsActivity extends AppCompatActivity {
    private ListView listView;
    private SearchView searchView;
    private ArrayList<MemoAttributes> data;

    private MemoryAdapter adapter;
//    private ArrayAdapter arrayAdapter;
    private FirebaseAuth fauth;
    private FirebaseDatabase fdb;
    private DatabaseReference dbref;
    private FirebaseUser fuser;

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
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
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
        setContentView(R.layout.activity_records);

        listView=findViewById(R.id.listv);
        data=new ArrayList<MemoAttributes>();
        searchView=findViewById(R.id.searchv);

        fauth=FirebaseAuth.getInstance();
        fuser=fauth.getCurrentUser();
        dbref=FirebaseDatabase.getInstance().getReference().child(fuser.getUid()).child("memos");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snp : snapshot.getChildren()){
                    String memoInfo="";
                    String memoId=snp.getKey();
                    memoInfo+="Memo Id : "+memoId+"\n";
                    String memoDT=snp.child("memoDT").getValue().toString();
                    memoInfo+="Date/Time : "+memoDT+"\n";
                    String memoTitle =snp.child("memoTitle").getValue().toString();
                    memoInfo+="Title : "+memoTitle+"\n";
                    String memo=snp.child("memo").getValue().toString();
                    memoInfo+="Memory : "+memo+"\n";
                    Log.i("memoInfo", memoInfo);
                    data.add(new MemoAttributes(memoId, memo, memoTitle, memoDT));
                }
//                arrayAdapter=new ArrayAdapter(RecordsActivity.this, R.layout.item_layout,R.id.data, data);
                adapter=new MemoryAdapter(RecordsActivity.this, R.layout.memory_adapter, data);
                listView.setAdapter(adapter);

//                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals(""))
                {
                    adapter=new MemoryAdapter(RecordsActivity.this, R.layout.memory_adapter, data);
                    listView.setAdapter(adapter);
                }
                else
                {
                    ArrayList<MemoAttributes> results=new ArrayList<>();
                    for(MemoAttributes mem: data)
                    {
                        if(mem.getMemo().contains(newText) || mem.getMemoTitle().contains(newText) || mem.getMemoTitle().contains(newText))
                        {
                            results.add(mem);
                        }
                    }
                    adapter=new MemoryAdapter(RecordsActivity.this, R.layout.memory_adapter, results);
                    listView.setAdapter(adapter);
                }

                return false;
            }

        });
    }

}