package org.other.fragment;

import org.other.constant.Constant;
import org.other.ui.ArcMenu;
import org.other.ui.ArcMenu.OnMenuItemClickListener;
import org.other.ui.RippleBackground;

import com.basic.Activities.MainActivity;
import com.basic.Activities.MyattentionActivity;
import com.basic.Activities.PoiSearchActivity;
import com.basic.Activities.R;
import com.basic.Activities.SchoolInfoActivity;
import com.basic.service.model.User;
import com.base.jbex.AboutSchool;
import com.base.jbex.JbexActivity;
import com.base.jbex.DT_index;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class ContentFragment extends BaseFragment {
	
	private ImageButton m_Public = null;
	private ImageButton m_FriendsWith = null;
	private ImageButton m_News = null;
	private ImageButton m_schoolinfo=null;
	private ImageButton m_attentions=null;
	private ImageButton m_PoiSearch=null;
	private MainActivity mMainActivity ;
	private ArcMenu mArcMenu;
	private User owneruser=new  User();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.content_layout,
				container, false);
		final RippleBackground rippleBackground=(RippleBackground)contactsLayout.findViewById(R.id.content);
		rippleBackground.startRippleAnimation();
		m_Public=(ImageButton) contactsLayout.findViewById(R.id.btn_publicInfo);
		m_FriendsWith=(ImageButton) contactsLayout.findViewById(R.id.btn_FriendsWith);
		m_News=(ImageButton) contactsLayout.findViewById(R.id.btn_News);
		m_schoolinfo=(ImageButton) contactsLayout.findViewById(R.id.btn_schoolinfo);
		m_attentions=(ImageButton) contactsLayout.findViewById(R.id.btn_attention);
		m_PoiSearch=(ImageButton) contactsLayout.findViewById(R.id.m_PoiSearch);
		mMainActivity =(MainActivity) getActivity();
		owneruser=mMainActivity.getUser();
		mArcMenu=(ArcMenu) contactsLayout.findViewById(R.id.btn_contents);
		initView();
		initListener();
		return contactsLayout;
	}

	private void initListener() {

		m_schoolinfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(mMainActivity,SchoolInfoActivity.class);
				mMainActivity.startActivity(intent);
			}
		});
		
		m_Public.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(mMainActivity,AboutSchool.class);
				Bundle data = new Bundle();
				data.putSerializable("owneruser", owneruser);
				intent.putExtras(data);
				mMainActivity.startActivity(intent);
			}
		});
		
		m_FriendsWith.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(mMainActivity,JbexActivity.class);
				Bundle data = new Bundle();
				data.putSerializable("owneruser", owneruser);
				intent.putExtras(data);
				mMainActivity.startActivity(intent);
			}
		});
		
		m_News.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(mMainActivity,DT_index.class);
				Bundle data = new Bundle();
				data.putSerializable("owneruser", owneruser);
				intent.putExtras(data);
				mMainActivity.startActivity(intent);
				//Toast.makeText(getActivity(), "点击了好友动态模块", Toast.LENGTH_SHORT).show();
			}
		});
		
		mArcMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public void onClick(View view, int pos) {
					Toast.makeText(mMainActivity, pos+":"+view.getTag(), Toast.LENGTH_SHORT).show();
			}
		});	
		
		m_attentions.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intentMyattention = new Intent(mMainActivity,
						MyattentionActivity.class);
				Bundle data = new Bundle();
				data.putSerializable("owneruser", owneruser);
				intentMyattention.putExtras(data);
				startActivity(intentMyattention);
			}
		});
		
		m_PoiSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intentMyPoiSearch = new Intent(mMainActivity,
						PoiSearchActivity.class);
				Bundle data = new Bundle();
				data.putSerializable("owneruser", owneruser);
				intentMyPoiSearch.putExtras(data);
				startActivity(intentMyPoiSearch);
			}
		});
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MainActivity.currFragTag = Constant.FRAGMENT_FLAG_Contents;
	}
	
	public void initView(){
	}
}
