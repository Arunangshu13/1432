package com.fire.sx1gd1243;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class GenerateNumbers extends AppCompatActivity {

    ArrayList<String> listOfNumbers;
    ArrayAdapter adapter;
    SearchView searchView;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_numbers);
        String text=getIntent().getExtras().getString("token");
        listView=findViewById(R.id.listofnum);
        listOfNumbers=new ArrayList<String>();
        int start=0, stop=0;
        if(text.equals("Prime Factorization"))
        {
             start=Integer.parseInt(getIntent().getExtras().getString("start"));
        }
        else
        {
            start=Integer.parseInt(getIntent().getExtras().getString("start"));
            stop=Integer.parseInt(getIntent().getExtras().getString("stop"));
        }
        int presentNum=start;
        switch(text)
        {
            case "Magic Numbers":
            {
                while(presentNum!=stop)
                {
                    if(isMagicNumber(presentNum)==1)
                    {
                        listOfNumbers.add(presentNum+"");
                    }
                    presentNum++;
                }
                break;
            }
            case "Armstrong Numbers":
            {
                String listData="";
                while(presentNum!=stop)
                {
                    int temp=presentNum;
                    int numLen=getNumLength(presentNum);
                    int rem=0, sum=0;
                    while(temp!=0)
                    {
                        rem=temp%10;
                        sum+=power(rem, numLen);
                        temp=(int)temp/10;
                    }
                    if(sum==presentNum) {
                        listData = "" + presentNum;
                        listOfNumbers.add(listData);
                    }
                    presentNum++;
                }
                break;
            }
            case "Prime Factorization":
            {
                int divisor=2, finalPresentNum=presentNum;
                while(divisor <= (int)(finalPresentNum/2))
                {
                    if(isPrime(divisor))
                    {
                        int count=0;
                        while(presentNum%divisor==0)
                        {
                            count++;
                            presentNum=(int)presentNum/divisor;
                        }
                        if(count!=0)
                        {
                            listOfNumbers.add(divisor+" x "+count);
                        }
                    }
                    divisor++;
                }
                break;
            }
            case "Prime Numbers":
            {

                while(presentNum!=stop) {
                    boolean flag=isPrime(presentNum);
                    if(flag || presentNum==1 || presentNum==2)
                    {
                      listOfNumbers.add(presentNum+"");
                    }
                    presentNum++;
                }
                break;
            }
            default:
            {
                Toast.makeText(this, "Never reachable code", Toast.LENGTH_SHORT).show();
            }
        }

        adapter=new ArrayAdapter(GenerateNumbers.this, android.R.layout.simple_list_item_1, listOfNumbers );
        listView.setAdapter(adapter);
        searchView=findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    public int getNumLength(int num)
    {
        int len=0;
        while(num!=0){
            len++;
            num=(int)num/10;
        }
        return len;
    }
    public int power(int a, int b)
    {
        int pow=1;
        if(b==0)
        {
            return 1;
        }

        else {
            for(int i=0;i<b;i++)
            {
                pow*=a;
            }
            return pow;
        }
    }
    public int isMagicNumber(int num){
        if(getNumLength(num)==1)
        {
            if(num==1)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
        else
        {
            int temp=num, rem=0, sum=0;
            while(temp!=0)
            {
                rem=temp%10;
                sum=sum+rem;
                temp=(int)temp/10;
            }
            return isMagicNumber(sum);
        }
    }
    public boolean isPrime(int num){
        boolean flag=true;
        for(int i=2;i<(int)num/2;i++)
        {
            if(num%i==0)
            {
                flag=false;
            }
        }
        return flag;
    }
}