package com.r_time_run.newmess.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.r_time_run.newmess.R;

/**
 * Created by zouxiaobang on 15/10/24.
 */
public class PopupWindowManager {
    private Context context;
    private View popup;
    private Bundle data;
    private Button bt_pop_collection,bt_pop_ok,bt_pop_evaluate,bt_pop_delete;
    private EditText tv_pop_praisetext;
    private TextView tv_buy_time;
    private TextView tv_foodName,tv_foodDir,tv_num_good,tv_num_buy,tv_price_buy;

    public PopupWindowManager(Context context,View popup,Bundle data){
        this.context = context;
        this.popup = popup;
        this.data = data;

        initPopup();
    }

    private void initPopup(){
        tv_foodName = (TextView)popup.findViewById(R.id.tv_foodName);
        tv_foodDir = (TextView)popup.findViewById(R.id.tv_foodDir);
        tv_num_good = (TextView)popup.findViewById(R.id.tv_num_good);
        tv_num_buy = (TextView)popup.findViewById(R.id.tv_num_buy);
        bt_pop_collection = (Button)popup.findViewById(R.id.bt_pop_collection);
        bt_pop_ok = (Button)popup.findViewById(R.id.bt_pop_ok);
        tv_pop_praisetext = (EditText)popup.findViewById(R.id.tv_pop_praisetext);
        tv_price_buy = (TextView) popup.findViewById(R.id.tv_price_buy);
        tv_buy_time = (TextView) popup.findViewById(R.id.tv_buy_time);
        bt_pop_evaluate = (Button) popup.findViewById(R.id.bt_pop_evaluate);
        bt_pop_delete = (Button) popup.findViewById(R.id.bt_pop_delete);


        /**
         * 评价按钮
         */
        bt_pop_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_pop_praisetext.setVisibility(View.VISIBLE);
                bt_pop_ok.setVisibility(View.VISIBLE);
            }
        });
        /**
         * 确认评价
         */
        bt_pop_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String praiseText = tv_pop_praisetext.getText().toString();
                Toast.makeText(context,praiseText,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setDataToView(){
        String foodName = data.getString("title");
        String dir = data.getString("dir");
        String time = data.getString("time");
        String praise = data.getString("praise");
        String buy = data.getString("buy");
        String price = data.getString("price");

        tv_foodName.setText(foodName);
        tv_foodDir.setText(dir);
        tv_num_good.setText(praise+"");
        tv_num_buy.setText(buy + "");
        tv_price_buy.setText(price);
        tv_buy_time.setText(time);
    }


}
