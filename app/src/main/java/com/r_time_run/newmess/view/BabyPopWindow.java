package com.r_time_run.newmess.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.r_time_run.newmess.R;

import java.util.Calendar;


@SuppressLint("CommitPrefEdits")
public class BabyPopWindow implements OnDismissListener, OnClickListener {
	private TextView tvFoodName,tvFoodLocation,tvFoodPrice,pop_add,pop_reduce,pop_num,pop_ok;
	private ImageView pop_del,iv_adapter_grid_pic;
	
	private PopupWindow popupWindow;
	private OnItemClickListener listener;
	private final int ADDORREDUCE=1;
	private Context context;
	private String str_color="";
	private String str_type="";
	private String foodName;
	private String foodLocation;
	private String foodPrice;
	private String foodNum;

	
	
	public BabyPopWindow(Context context,String foodName,String foodLocation,String foodPrice) {
		this.context=context;
		this.foodName = foodName;
		this.foodLocation = foodLocation;
		this.foodPrice = foodPrice;
		View view=LayoutInflater.from(context).inflate(R.layout.adapter_popwindow, null);
		tvFoodName=(TextView) view.findViewById(R.id.tv_pop_foodname);
		tvFoodLocation=(TextView) view.findViewById(R.id.tv_pop_foodlocation);
		tvFoodPrice=(TextView) view.findViewById(R.id.tv_pop_foodprice);
		tvFoodName.setText(foodName);
		tvFoodLocation.setText(foodLocation);
		tvFoodPrice.setText(foodPrice);
		pop_add=(TextView) view.findViewById(R.id.pop_add);
		pop_reduce=(TextView) view.findViewById(R.id.pop_reduce);
		pop_num=(TextView) view.findViewById(R.id.pop_num);
		pop_ok=(TextView) view.findViewById(R.id.pop_ok);
		pop_del=(ImageView) view.findViewById(R.id.pop_del);
		iv_adapter_grid_pic = (ImageView) view.findViewById(R.id.iv_adapter_grid_pic);
		iv_adapter_grid_pic.getDrawingCache();

		tvFoodName.setOnClickListener(this);
		pop_add.setOnClickListener(this);
		pop_reduce.setOnClickListener(this);
		pop_ok.setOnClickListener(this);
		pop_del.setOnClickListener(this);
		popupWindow=new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setAnimationStyle(R.style.popWindow_anim_style);
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		popupWindow.setOnDismissListener(this);
		Log.e("PopWindow", foodName + foodLocation + foodPrice);
	}

	
	
	public interface OnItemClickListener{
		/** 设置点击确认按钮时监听接口 */
		public void onClickOKPop();
	}

	// 当popWindow消失时响应
	public void setOnItemClickListener(OnItemClickListener listener){
		this.listener=listener;
	}



	@Override
	public void onDismiss() {
		
	}

	/**弹窗显示的位置*/
	public void showAsDropDown(View parent){
		popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
	}

	/**消除弹窗*/
	public void dissmiss(){
		popupWindow.dismiss();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.pop_choice_16g:
//			pop_choice_16g.setBackgroundResource(R.drawable.yuanjiao_choice);
//			str_type=pop_choice_16g.getText().toString();
//			break;
		case R.id.pop_add:
			if (!pop_num.getText().toString().equals("750")) {
				
				String num_add=Integer.valueOf(pop_num.getText().toString())+ADDORREDUCE+"";
				pop_num.setText(num_add);
			}else {
				Toast.makeText(context, "不能超过最大产品数量", Toast.LENGTH_SHORT).show();
			}
			
			break;
		case R.id.pop_reduce:
			if (!pop_num.getText().toString().equals("1")) {
				String num_reduce=Integer.valueOf(pop_num.getText().toString())-ADDORREDUCE+"";
				pop_num.setText(num_reduce);
			}else {
				Toast.makeText(context, "购买数量不能低于1件", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.pop_del:
			listener.onClickOKPop();
			dissmiss();
			
			break;
		case R.id.pop_ok:
			listener.onClickOKPop();
			this.foodNum = pop_num.getText().toString();
			int nowTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			//超过19点就没法再下单
			if (nowTime <= 23){
				setSaveData();
				Toast.makeText(context, "打包成功", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "请在规定时间内打包", Toast.LENGTH_SHORT).show();
			}
			dissmiss();
			break;

		default:
			break;
		}
	}
	/**
	 * 保存打包的数据
	 * 使用SQLite来保存
	 * */
	private void setSaveData(){
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(context.getFilesDir().toString()+"/order_message_db.db3", null);
		String sqlCreatTable = "create table food_info(name varchar(50),adress varchar(100)," +
				"number varchar(10),price varchar(10),year varchar(50),month varchar(50)" +
				",day varchar(50),hour varchar(50),minute varchar(50))";

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		try {
			insertData(db,foodName,foodLocation,foodNum,foodPrice,year,month,day,hour,minute);
		} catch (SQLiteException se){
			db.execSQL(sqlCreatTable);
			insertData(db, foodName, foodLocation, foodNum, foodPrice,year,month,day,hour,minute);
		}
	}

	private void insertData(SQLiteDatabase db, String foodName, String foodLocation, String foodNum, String foodPrice,int year,int month,int day,int hour,int minute) {
		db.execSQL("insert into food_info values(?,?,?,?,?,?,?,?,?)",new String[]{foodName,foodLocation,foodNum,foodPrice,year+"",month+"",day+"",hour+"",minute+""});
		System.out.println("!!!!!插入成功");
	}

}
