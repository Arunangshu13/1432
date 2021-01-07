package com.fire.sx1gd1243;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

class EmptyFieldException extends Exception {
    String msg="";
    EmptyFieldException(){
        msg+="Empty fields are as equivalent to bad input";
    }
    public String getMessage(){
        return msg;
    }
}
public class LisureActivity extends AppCompatActivity {

    Button generateNumbers;
    RadioGroup radiog;
    TextView from, to;
    EditText ninitial, nfinal;
    RadioButton radiob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lisure);
        radiog=findViewById(R.id.radioGroup);
        ninitial=findViewById(R.id.ninitial);
        nfinal=findViewById(R.id.nfinal);
        from =findViewById(R.id.fromtv);
        to=findViewById(R.id.totv);
        generateNumbers=findViewById(R.id.genNumber);


        nfinal.setVisibility(View.INVISIBLE);
        ninitial.setVisibility(View.INVISIBLE);
        from.setVisibility(View.INVISIBLE);
        to.setVisibility(View.INVISIBLE);
        radiog.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radiob=findViewById(checkedId);
                if(radiob.getText().equals("Prime Factorization"))
                {
                   nfinal.setVisibility(View.INVISIBLE);
                   ninitial.setVisibility(View.VISIBLE);
                   from.setVisibility(View.VISIBLE);
                   from.setText("Enter a Number ");
                   to.setVisibility(View.INVISIBLE);
                }
                else {
                    nfinal.setVisibility(View.VISIBLE);
                    ninitial.setVisibility(View.VISIBLE);
                    from.setVisibility(View.VISIBLE);
                    from.setText("From");
                    to.setVisibility(View.VISIBLE);
                }
            }
        });
        generateNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId=radiog.getCheckedRadioButtonId();
                radiob=findViewById(radioId);
                String text="";
                try{
                    text=radiob.getText().toString();
                }catch(NullPointerException np)
                {
                    np.printStackTrace();
                }
                if(text.equals(""))
                {
                    Toast.makeText(LisureActivity.this, "No option has been selected", Toast.LENGTH_SHORT).show();
                }
                else if(text.equals("Prime Factorization"))
                {
                    String  start = ninitial.getText().toString().trim();

                    try{
                        if(start.equals(""))
                        {
                            throw new EmptyFieldException();
                        }

                        Intent intent=new Intent(LisureActivity.this, GenerateNumbers.class);
                        intent.putExtra("token", text);
                        intent.putExtra("start", start);
                        startActivity(intent);
                    }catch (EmptyFieldException ep)
                    {
                        Toast.makeText(LisureActivity.this, ep.getMessage(), Toast.LENGTH_SHORT).show();
                    }catch(Exception e)
                    {
                        Toast.makeText(LisureActivity.this, "Bad Input", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    String  start = ninitial.getText().toString().trim();
                    String  stop = nfinal.getText().toString().trim();
                    try{
                        if(start.equals("")|| stop.equals(""))
                        {
                            throw new EmptyFieldException();
                        }
                        int starti=Integer.parseInt(start);
                        int  stopi=Integer.parseInt(stop);
                        if(starti>stopi)
                        {
                            Toast.makeText(LisureActivity.this, "Initial value is larger than final value", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Intent intent=new Intent(LisureActivity.this, GenerateNumbers.class);
                            intent.putExtra("token", text);
                            intent.putExtra("start", start);
                            intent.putExtra("stop", stop);
                            startActivity(intent);
                        }

                    }catch (EmptyFieldException ep)
                    {
                        Toast.makeText(LisureActivity.this, ep.getMessage(), Toast.LENGTH_SHORT).show();
                    }catch(Exception e)
                    {
                        Toast.makeText(LisureActivity.this, "Bad Input", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}