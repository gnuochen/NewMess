package com.r_time_run.newmess.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.r_time_run.newmess.R;
import com.r_time_run.newmess.bean.CommentBean;
import com.r_time_run.newmess.bean.ReplyBean;
import com.r_time_run.newmess.view.NoScrollListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Adapter_ListView_detail extends BaseAdapter {
	private Context context;
	private int resourceId;
	private List<CommentBean> list;
	private Handler handler;

@SuppressWarnings("unused")
private ArrayList<HashMap<String, Object>> arrayList;
	
	@SuppressWarnings("unchecked")
	public Adapter_ListView_detail(Context context,HashMap<String, Object> hashmap,List<CommentBean> list
			,int resourceId){
		
		this.context=context;
		this.arrayList=(ArrayList<HashMap<String, Object>>) hashmap.get("data");
		this.list=list;
		this.resourceId=resourceId;

	}

	public Adapter_ListView_detail(Context context,List<CommentBean> list
			,int resourceId,Handler handler){

		this.context=context;
		this.list=list;
		this.resourceId=resourceId;
		this.handler=handler;
	}
	
	public Adapter_ListView_detail(Context context){
		this.context=context;
		
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View currentView, ViewGroup arg2) {
		CommentBean bean = list.get(position);
		HolderView holderView=null;
		if (currentView==null) {
			holderView=new HolderView();
			currentView=LayoutInflater.from(context).inflate(R.layout.adapter_listview_detail, null);
			holderView.replyText = (TextView)
					currentView.findViewById(R.id.tv_reply);
			holderView.replyList = (NoScrollListView)
					currentView.findViewById(R.id.replyList);
			currentView.setTag(holderView);
		}else {
			holderView=(HolderView) currentView.getTag();
		}

		ReplyAdapter adapter = new ReplyAdapter(context, bean.getReplyList(), R.layout.reply_item);
		holderView.replyList.setAdapter(adapter);
		TextviewClickListener tcl = new TextviewClickListener(position);
		holderView.replyText.setOnClickListener(tcl);
		
		return currentView;
	}

	public class HolderView {
		
		private ImageView iv_pic;
		private TextView tv_sale_num;
		private TextView tv_name;
		private TextView tv_price;

		public ImageView commentItemImg;
		public TextView commentNickname;
		public TextView replyText;					//回复
		public TextView commentItemTime;			//评论时间
		public TextView commentItemContent;			//评论内容
		public NoScrollListView replyList;			//评论回复列表
		
	}

	/**
	 * 获取回复评论
	 */
	public void getReplyComment(ReplyBean bean,int position){
		List<ReplyBean> rList = list.get(position).getReplyList();
		rList.add(rList.size(), bean);
	}

	/**
	 * 事件点击监听
	 */
	private final class TextviewClickListener implements View.OnClickListener {
		private int position;
		public TextviewClickListener(int position){
			this.position = position;
		}
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.tv_reply:
					handler.sendMessage(handler.obtainMessage(10, position));
					break;
			}
		}
	}

}
