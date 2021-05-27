package com.example.db_for_st;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class AddBoys extends Activity {
    private Button btSave,btCancel;
    private EditText etBadBoys;
    private EditText etGoalsHouse;
    private EditText etGoalsGuest;
    private long MyBoyID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_boy);

        etBadBoys=(EditText)findViewById(R.id.badboys);
        etGoalsHouse=(EditText)findViewById(R.id.goalshouse);
        etGoalsGuest=(EditText)findViewById(R.id.goalsguest);
        btSave=(Button)findViewById(R.id.butSave);
        btCancel=(Button)findViewById(R.id.butCancel);

        if(getIntent().hasExtra("BadBoys")){
            BadBoys boys =(BadBoys) getIntent().getSerializableExtra("Players");
            etBadBoys.setText(boys.getBadboy());
            etGoalsHouse.setText(Integer.toString(boys.getGoalshouse()));
            etGoalsGuest.setText(Integer.toString(boys.getGoalsguest()));
            MyBoyID=boys.getId();
        }
        else
        {
            MyBoyID=-1;
        }
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BadBoys boys=new BadBoys(MyBoyID,etBadBoys.getText().toString(), Integer.parseInt(etGoalsHouse.getText().toString()), Integer.parseInt(etGoalsGuest.getText().toString()));
                Intent intent=getIntent();
                intent.putExtra("BadBoys",boys);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}