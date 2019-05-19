package org.other.fragment.adapter;

import java.util.List;

import com.basic.Activities.R;
import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageStringUtil;
import com.basic.connectservice.HttpUtil;
import com.basic.model.FriendsBean;
import com.basic.model.FriendsGroupBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendsAdapter extends  BaseExpandableListAdapter{
	private List<FriendsGroupBean> mListFriendsGroupBean  = null;
	private Context mContext;
	private LayoutInflater mInflater;
	private ImageLoader imageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	
	public FriendsAdapter( List<FriendsGroupBean> listGroupBean,Context context,ImageLoader imageLoader,DisplayImageOptions options) {
		mListFriendsGroupBean=listGroupBean;
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		this.imageLoader=imageLoader;
		this.options=options;
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO 自动生成的方法存根
		return mListFriendsGroupBean.get(arg0).getFriendslist().get(arg1);
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		// TODO 自动生成的方法存根
		return arg1;
	}

	

	@Override
	public int getChildrenCount(int arg0) {
		// TODO 自动生成的方法存根
		return mListFriendsGroupBean.get(arg0).getFriendslist().size();
	}

	@Override
	public Object getGroup(int arg0) {
		// TODO 自动生成的方法存根
		return mListFriendsGroupBean.get(arg0);
	}

	@Override
	public int getGroupCount() {
		// TODO 自动生成的方法存根
		return mListFriendsGroupBean.size();
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO 自动生成的方法存根
		return arg0;
	}

	@Override
	public View getGroupView(int arg0, boolean arg1, View convertView, ViewGroup arg3) {
		// TODO 自动生成的方法存根
		ViewHolderGroup ViewHolder=null;
		if(convertView==null){
		ViewHolder=new ViewHolderGroup();
		convertView= mInflater.inflate(R.layout.group_item_layout, null);
		
		ViewHolder.group_name=(TextView) convertView.findViewById(R.id.group_name);
		ViewHolder.online_txt=(TextView) convertView.findViewById(R.id.online_txt);
		convertView.setTag(ViewHolder);
		}
		else{
			ViewHolder=	(ViewHolderGroup) convertView.getTag();
		}
		
		ViewHolder.group_name.setText(mListFriendsGroupBean.get(arg0).getGroupName());
		ViewHolder.online_txt.setText(mListFriendsGroupBean.get(arg0).getOnline_txt());
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO 自动生成的方法存根
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO 自动生成的方法存根
		return true;
	}

	@Override
	public View getChildView(int arg0, int arg1, boolean arg2, View convertView,
			ViewGroup arg4) {
		// TODO 自动生成的方法存根
		ViewHolder viewHolder=null;
		
		if(convertView == null) {
			viewHolder=new ViewHolder();
		    convertView= mInflater.inflate(R.layout.friends_item_layout, null);
		    viewHolder.imageView=(ImageView) convertView.findViewById(R.id.img_friends_item);
		    viewHolder.nameFriends = (TextView)convertView.findViewById(R.id.name_friends_item);
		    viewHolder.AutographFriends = (TextView)convertView.findViewById(R.id.autograph_friends_item);
		    viewHolder.OnlineFriends = (TextView)convertView.findViewById(R.id.OnLine_friends_item);
		    
		   /* 这个东西在一些需要用到Adapter自定控件显示方式的时候非常有用
		    Adapter 有个getView方法，可以使用setTag把查找的view缓存起来方便多次重用*/
		    convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String imageUrl=ImageStringUtil.getImageURL(mListFriendsGroupBean.get(arg0).getFriendslist().get(arg1).getPhotoDrawableId());
		imageLoader.displayImage(imageUrl, viewHolder.imageView, options, animateFirstListener);
		viewHolder.nameFriends.setText(mListFriendsGroupBean.get(arg0).getFriendslist().get(arg1).getFriendsName());
		viewHolder.AutographFriends.setText(mListFriendsGroupBean.get(arg0).getFriendslist().get(arg1).getFriendsAutograph());
		String Online;
		if(mListFriendsGroupBean.get(arg0).getFriendslist().get(arg1).isOnline()){
			Online="[在线]";
		}
		else{
			Online="[离开]";
		}
		viewHolder.OnlineFriends.setText(Online);
		
		return convertView;
	}
	
	 class ViewHolder{
		 public ImageView imageView ;
		 public TextView nameFriends; 
		 public TextView AutographFriends;
		 public TextView OnlineFriends;
	 }
	 
	 class ViewHolderGroup{
		 public TextView group_name;
		 public TextView online_txt;
	 }
}

