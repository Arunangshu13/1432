package com.fire.sx1gd1243;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;




public class MemoryAdapter extends ArrayAdapter<MemoAttributes> {
    private static final String TAG = "MemoryAdapter";
    private Context mcontext;
    private int resource;


    public MemoryAdapter(@NonNull Context mcontext, int resource, @NonNull ArrayList<MemoAttributes> objects) {
        super(mcontext, resource, objects);
        this.mcontext = mcontext;
        this.resource=resource;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String memoId=getItem(position).getId();
        String memoDT=getItem(position).getMemDT();
        String memoTitle=getItem(position).getMemoTitle();
        String memo=getItem(position).getMemo();

        MemoAttributes memoAttributes=new MemoAttributes(memoId, memo, memoTitle, memoDT);
        LayoutInflater layoutInflater=LayoutInflater.from(mcontext);
        convertView=layoutInflater.inflate(resource, parent, false);

        TextView memodateTime=(TextView) convertView.findViewById(R.id.memoDateTime);
        TextView memoContent =(TextView) convertView.findViewById(R.id.memoContent);
        TextView memoTl=(TextView) convertView.findViewById(R.id.memoTitle);
        TextView edit=(TextView) convertView.findViewById(R.id.editMemo);
        TextView delete=(TextView) convertView.findViewById(R.id.deleteMemo);

        memodateTime.setText(memoDT);
        memoTl.setText(memoTitle);
        memoContent.setText(memo);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, "Redirecting to update activity ", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(), UpdateActivity.class);
                intent.putExtra("id", memoId);
                ((Activity) mcontext).finish();
                getContext().startActivity(intent);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, "Redirecting to confirm delete activity", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(), ConfirmDeleteActivity.class);
                intent.putExtra("id", memoId);
                ((Activity) mcontext).finish();
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }

}
