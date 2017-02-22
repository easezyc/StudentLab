package com.bnu.studentlab;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private CharSequence age,subject;//选择的阶段和学科
    private boolean chooce1=false;//选择完毕否
    private RadioGroup agegroup,subjectgroup;
    private boolean continuemark,warningmark;//标记是否需要继续实验
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        continuemark=false;
        warningmark=false;
        maininit();
    }
    AlertDialog getAlertDialogWithOk()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("确认选择");
        builder.setMessage("选择的阶段为："+age+"\n选择的科目为："+subject);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chooce1=true;
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chooce1=false;
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog=builder.create();
        return dialog;
    }
    AlertDialog getAlertDialogWithGiveup()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("放弃实验");
        builder.setMessage("确认放弃实验吗");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setContentView(R.layout.activity_main);
                maininit();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);
        AlertDialog dialog=builder.create();
        return dialog;
    }
    AlertDialog getAlertDialogWithStoplab()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("返回主页");
        builder.setMessage("确认暂停实验返回主页吗");
        builder.setPositiveButton("返回主页", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setContentView(R.layout.activity_main);
                maininit();
            }
        });
        builder.setNegativeButton("继续实验", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);
        AlertDialog dialog=builder.create();
        return dialog;
    }
    AlertDialog getAlertDialogWithWarning()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("还有未完成的实验");
        builder.setMessage("还有实验未完成，点击确认放弃实验，重新开始新的实验");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                continuemark=false;
                warningmark=false;
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                warningmark=true;
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog=builder.create();
        return dialog;
    }
    private void maininit()
    {
        Button btnok1,btnlab1,continuelab;
        TabHost tabhost;
        tabhost=(TabHost)findViewById(R.id.tabhost);
        btnok1=(Button)findViewById(R.id.btnok);
        agegroup=(RadioGroup)findViewById(R.id.agegroup);
        subjectgroup=(RadioGroup)findViewById(R.id.subjectgroup);
        btnlab1=(Button)findViewById(R.id.lab1);
        continuelab=(Button)findViewById(R.id.continuelab);
        tabhost.setup();
        tabhost.addTab(tabhost.newTabSpec("TEST").setIndicator("首页").setContent(R.id.tab1));
        tabhost.addTab(tabhost.newTabSpec("TEST2").setIndicator("实验").setContent(R.id.tab2));
        tabhost.addTab(tabhost.newTabSpec("TEST3").setIndicator("设置").setContent(R.id.tab3));
        tabhost.addTab(tabhost.newTabSpec("TEST4").setIndicator("个人信息").setContent(R.id.tab4));
        continuelab.setOnClickListener(new View.OnClickListener() {//继续实验
            @Override
            public void onClick(View v) {
                if(continuemark==true)labtoolinit();
                else {
                    Toast toast=new Toast(getApplicationContext());
                    toast = Toast.makeText(getApplicationContext(),
                            "您没有需要继续的实验", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        btnlab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(continuemark==true){
                    AlertDialog dialog=getAlertDialogWithWarning();
                    dialog.show();
                }
                if(warningmark==false)introduceinit();
            }
        });
        btnok1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < agegroup.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) agegroup.getChildAt(i);
                    if (radioButton.isChecked()) {
                        age=radioButton.getText();
                        //Log.i("tag", "lsn 单选按钮，您的阶段是：" + radioButton.getText());
                    }
                }
                for (int i = 0; i < subjectgroup.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) subjectgroup.getChildAt(i);
                    if (radioButton.isChecked()) {
                        subject=radioButton.getText();
                        //Log.i("tag", "lsn 单选按钮，您的科目是：" + radioButton.getText());
                    }
                }
                AlertDialog dialog=getAlertDialogWithOk();
                dialog.show();
            }
        });
    }
    private void labtoolinit()//初始化labtool.xml
    {
        Button giveuplab,stoplab;
        setContentView(R.layout.labtool);
        giveuplab=(Button)findViewById(R.id.giveuplab);
        stoplab=(Button)findViewById(R.id.stoplab);
        giveuplab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=getAlertDialogWithGiveup();
                dialog.show();
                continuemark=false;
            }
        });
        stoplab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=getAlertDialogWithStoplab();
                dialog.show();
            }
        });
    }
    private void introduceinit()//初始化labintroduction.xml
    {
        Button returnchoocelab,begin;
        setContentView(R.layout.labintroduction);
        returnchoocelab=(Button)findViewById(R.id.returnchoocelab);
        begin=(Button)findViewById(R.id.begin);
        continuemark=true;
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labtoolinit();
            }
        });
        returnchoocelab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                maininit();
            }
        });
    }
}
