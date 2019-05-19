package org.other.fragment.adapter;

import java.util.ArrayList;
import java.util.List;









import org.other.ui.DraggableFlagView;

import com.basic.Activities.R;
import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageStringUtil;
import com.basic.model.MessageBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter implements DraggableFlagView.OnDraggableFlagViewListener{
	private List<MessageBean> mListMsgBean = null;
	private Context mContext;
	private LayoutInflater mInflater;
	
	private ImageLoader imageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	
	
	public MessageAdapter(List<MessageBean> listMsgBean, Context context, ImageLoader imageLoader,DisplayImageOptions option){
		mListMsgBean = listMsgBean;
		mContext = context;
		this.imageLoader=imageLoader;
		this.options=option;
		mInflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListMsgBean.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListMsgBean.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView= mInflater.inflate(R.layout.message_item_layout, null);
			viewHolder.imageView=(ImageView) convertView.findViewById(R.id.img_msg_item);
			viewHolder.nameMsg=(TextView)convertView.findViewById(R.id.name_msg_item);
			viewHolder.timeMsg=(TextView) convertView.findViewById(R.id.time_msg_item);
			viewHolder.contentMsg=(TextView)convertView.findViewById(R.id.content_msg_item);
			viewHolder.draggableFlagView = (DraggableFlagView) convertView.findViewById(R.id.main_dfv);
	        
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}

		imageLoader.displayImage(ImageStringUtil.getImageURL(mListMsgBean.get(position).getPhotoDrawableId()), viewHolder.imageView, options, animateFirstListener);
		viewHolder.nameMsg.setText(mListMsgBean.get(position).getMessageName());
		viewHolder.contentMsg.setText(mListMsgBean.get(position).getMessageContent());
		viewHolder.timeMsg.setText(mListMsgBean.get(position).getMessageTime());
		viewHolder.draggableFlagView.setOnDraggableFlagViewListener(this);
		int num=mListMsgBean.get(position).getNum();
		if(num==0)
			viewHolder.draggableFlagView.setVisibility(View.GONE);
		else
		viewHolder.draggableFlagView.setText(String.valueOf(num));
		return convertView;
	}
	
	public void onDateChange(ArrayList<MessageBean> apk_list) {
		// TODO 自动生成的方法存根
		this.mListMsgBean = apk_list;
		this.notifyDataSetChanged();
	}
	
   class ViewHolder{
	   public ImageView imageView;
	   public TextView nameMsg;
	   public TextView contentMsg;
	   public TextView timeMsg;
	   private DraggableFlagView draggableFlagView;
   }

@Override
public void onFlagDismiss(DraggableFlagView view) {
	// TODO 自动生成的方法存根
	
}
}
