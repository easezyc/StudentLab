package com.bnu.studentlab;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private CharSequence age,subject;//选择的阶段和学科
    private boolean chooce1=false;//选择完毕否
    private RadioGroup agegroup,subjectgroup;
    private boolean continuemark=false,warningmark=false;//标记是否需要继续实验
    private int shouldstep=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        maininit();
    }
    AlertDialog getAlertDialogWithOk()//确认选择警告框
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
        return builder.create();
    }
    AlertDialog getAlertDialogWithGiveup()//放弃实验警告框
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
        return builder.create();
    }
    AlertDialog getAlertDialogWithStoplab()//暂停实验警告框
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("暂停实验");
        builder.setMessage("确认暂停实验返回主页吗");
        builder.setPositiveButton("返回主页", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setContentView(R.layout.activity_main);
                continuemark=true;
                maininit();
            }
        });
        builder.setNegativeButton("继续实验", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);
        return builder.create();
    }
    AlertDialog getAlertDialogWithWarning()//实验未完成警告框
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("还有未完成的实验");
        builder.setMessage("还有实验未完成，点击确认放弃实验，重新开始新的实验");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                continuemark=false;
                warningmark=false;
                introduceinit();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                warningmark=true;
            }
        });
        builder.setCancelable(false);
        return builder.create();
    }
    AlertDialog getAlertDialogWithSuc()//实验成功警告框
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("实验完成");
        builder.setMessage("实验成功啦！\n 观察很认真！\n实验可能出了些小问题呢，来看看其中的道理吧！");
        builder.setPositiveButton("实验探秘", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog dialog1=getAlertDialogWithSecret();
                dialog1.show();
            }
        });
        builder.setCancelable(false);
        return builder.create();
    }
    AlertDialog getAlertDialogWithSecret()//实验探秘警告框
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("实验探秘");
        builder.setMessage("土壤上覆盖一层薄膜可以留住热量和水分，保持较高的温度和湿度，有利于植物生命活动，植物就生长得更迅速旺盛啦！");
        builder.setPositiveButton("实验结束", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                continuemark=false;
                shouldstep=1;
                setContentView(R.layout.activity_main);
                maininit();
            }
        });
        builder.setCancelable(false);
        return builder.create();
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
                if(continuemark)labtoolinit();
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "您没有需要继续的实验", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        btnlab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(continuemark){
                    AlertDialog dialog=getAlertDialogWithWarning();
                    dialog.show();
                }
                else introduceinit();
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
    private void CreateStep()//动态创建组件
    {
        for(int i=0;i<7;i++)
        {
            LinearLayout layout=(LinearLayout)findViewById(R.id.scrolllayout);
            final LinearLayout newlayout = new LinearLayout(this);
            newlayout.setOrientation(LinearLayout.HORIZONTAL);
            final Button btn = new Button(this);
            btn.setText(R.string.launch);
            btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
            TextView text=new TextView(this);
            text.setText(R.string.labstep);
            text.append(Integer.toString(i+1));
            text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
            final RadioButton rbtn=new RadioButton(this);
            if(i+1<shouldstep)rbtn.setChecked(true);
            final int j=i;
            rbtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
            rbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(shouldstep==j+1){
                        rbtn.setChecked(true);
                        shouldstep++;
                    }
                    if(j+1>shouldstep){
                        rbtn.setChecked(false);
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "请按顺序进行实验", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    if(shouldstep==8)
                    {
                        AlertDialog dialog=getAlertDialogWithSuc();
                        dialog.show();
                    }
                }
            });
            newlayout.addView(text);
            newlayout.addView(rbtn);
            newlayout.addView(btn);
            final ImageView image=new ImageView(this);
            image.setImageResource(R.drawable.introduction);
            image.setVisibility(View.GONE);
            layout.addView(newlayout);
            final LineChart chart = new LineChart(this);
            chart.setMinimumHeight(0);
            if(i==4)
            {
                chart.setMinimumHeight(500);
                Charts c=new Charts();
                LineData mLineData = c.makeLineData();
                c.setChartStyle(chart, mLineData, Color.WHITE);
                chart.setVisibility(View.GONE);
            }
            layout.addView(chart);
            layout.addView(image);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(image.getVisibility()==View.GONE)
                    {
                        chart.setVisibility(View.VISIBLE);
                        image.setVisibility(View.VISIBLE);
                        btn.setText(R.string.fold);
                    }
                    else
                    {
                        chart.setVisibility(View.GONE);
                        image.setVisibility(View.GONE);
                        btn.setText(R.string.launch);
                    }
                }
            });
        }
    }
    private void labtoolinit()//初始化labtool.xml
    {
        Button giveuplab,stoplab;
        continuemark=true;
        setContentView(R.layout.labtool);
        CreateStep();
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

