package com.basic.Activities;

import java.util.ArrayList;
import java.util.List;

import org.other.constant.Constant;
import org.other.fragment.BaseFragment;
import org.other.fragment.MessageFragment;
import org.other.ui.BottomControlPanel;
import org.other.ui.BottomControlPanel.BottomPanelCallback;

import com.base.jbex.FootRoutActivity;
import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageOptions;
import com.basic.ImageLoad.ImageStringUtil;
import com.basic.connectservice.GetListUser;
import com.basic.service.model.User;
import com.basic.slidingmenu.view.SlidingMenu;
import com.basic.ui.TopView;
import com.basic.ui.menuItem;
import com.basic.util.FaceConversionUtil;
import com.message.net.ChatMessage;
import com.message.net.Communication;
import com.message.net.Config;
import com.message.net.Friend;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.os.Bundle;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ResourceAsColor")
public class MainActivity extends ZJBEXBaseActivity implements
		BottomPanelCallback {
	
	BottomControlPanel bottomPanel = null;
	private FragmentManager fragmentManager = null;
	private FragmentTransaction fragmentTransaction = null;
	private SlidingMenu mLeftMenu;
	private TextView mainText;
	private TopView topview;
	private View add_menuView;
	private PopupWindow popupWindow;
	private ImageView add_Menu;
	private ImageView toggleMenu;
	private menuItem item1;
	private menuItem item2;
	private menuItem item3;
	private menuItem item4;
	private menuItem item5;
	private TextView setting_Textview;
	
	private User user=new User();
	
	String email;
    
	
	//图片加载框架组件
	private DisplayImageOptions options;
	private ImageLoader mImageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
		
	public static String currFragTag = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		
		/**
		 * 先从登陆界面拿到user，然后拿到email，再创建一个线程去服务器拿数据。
		 */
		Intent intent = this.getIntent();
		if((intent.getExtras())!=null){
			Bundle data = intent.getExtras();
			user = (User) data.getSerializable("user");
			Log.d("user", user.toString());
		}
		email = user.getEmail();
		
        setSelf(user);
        
       //添加表情包和其他东西
		new Thread(new Runnable() {
            @Override
            public void run() {
            	
                FaceConversionUtil.getInstace().getFileText(getApplication());
                
            }
        }).start();
		
		toggleMenu=(ImageView) findViewById(R.id.toggleMenu);
		mainText=(TextView) findViewById(R.id.main_Text);
		mLeftMenu = (SlidingMenu) findViewById(R.id.id_menu);
		topview=(TopView) findViewById(R.id.top_View);
		add_Menu=(ImageView) findViewById(R.id.mainactivity_add);
		item1=(menuItem) findViewById(R.id.item1);
		item2=(menuItem) findViewById(R.id.item2);
		item3=(menuItem) findViewById(R.id.item3);
		item4=(menuItem) findViewById(R.id.item4);
		item5=(menuItem) findViewById(R.id.item5);
		setting_Textview=(TextView) findViewById(R.id.setting_Textview);
		
		initDisplayOption();
		initUI();
		initListener();
		fragmentManager = getFragmentManager();
		setDefaultFirstFragment(Constant.FRAGMENT_FLAG_Contents);
	}

	private void initDisplayOption() {
		// TODO 自动生成的方法存根
		options=ImageOptions.initDisplayOptions();
		mImageLoader = ImageLoader.getInstance();
	}

	private void initListener() {

		add_Menu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根

				showWindow(v);
			}
		});

		topview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intentPersion_info = new Intent(MainActivity.this,
						PersonInfoActivity.class);
				
				Bundle data = new Bundle();
				data.putSerializable("user", user);
				intentPersion_info.putExtras(data);
				
				startActivityForResult(intentPersion_info,1);
			}
		});

		item1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intentPersion_info = new Intent(MainActivity.this,
						PersonInfoActivity.class);
				
				Bundle data = new Bundle();
				data.putSerializable("user", user);
				intentPersion_info.putExtras(data);
				
				startActivityForResult(intentPersion_info,1);
			}
		});

		item2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intentPersion_info = new Intent(MainActivity.this,
						CollectionActivity.class);
				Bundle data = new Bundle();
				data.putSerializable("owneruser", user);
				intentPersion_info.putExtras(data);
				startActivity(intentPersion_info);
			}
		});

		item3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自 动生成的方法存根
				Intent intentMyattention = new Intent(MainActivity.this,
						MyattentionActivity.class);
				Bundle data = new Bundle();
				data.putSerializable("owneruser", user);
				intentMyattention.putExtras(data);
				startActivity(intentMyattention);
			}
		});

		item4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intentFootRout = new Intent(MainActivity.this,
						FootRoutActivity.class);
				Bundle data = new Bundle();
				data.putSerializable("owneruser", user);
				intentFootRout.putExtras(data);
				startActivity(intentFootRout);
			}
		});
		
		item5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intentMypublish = new Intent(MainActivity.this,
						MyPublishActivity.class);
				Bundle data = new Bundle();
				data.putSerializable("owneruser", user);
				intentMypublish.putExtras(data);
				startActivity(intentMypublish);
			}
		});
		
		setting_Textview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intentSetting = new Intent(MainActivity.this,
						MineSettingActivity.class);
				
				Bundle data = new Bundle();
				data.putSerializable("owneruser", user);
				intentSetting.putExtras(data);
				
				startActivityForResult(intentSetting,1);
			}
		});
	}

	protected void showWindow(View parent) {
		if (popupWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			add_menuView = layoutInflater.inflate(R.layout.add_menu, null);

			menuItem add_Friends_item = (menuItem) add_menuView
					.findViewById(R.id.add_Friends_item);
			add_Friends_item.getLeftmenu_item_txt().setText("添加朋友");

			add_Friends_item.setOnClickListener(new OnClickListener() {

				@Override 
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根
					Intent searchfriend=new Intent(MainActivity.this, SearchFriendsActivity.class);
					Bundle data = new Bundle();
					data.putSerializable("user", user);
					searchfriend.putExtras(data);
					MainActivity.this.startActivity(searchfriend);
				}
			});

			menuItem add_Groups_item = (menuItem) add_menuView
					.findViewById(R.id.add_Groups_item);
			add_Groups_item.getLeftmenu_item_txt().setText("分组管理");

			add_Groups_item.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根

				}
			});

			popupWindow = new PopupWindow(add_menuView, 300, 350);

		}

		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);

		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		// 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
		int xPos = windowManager.getDefaultDisplay().getWidth();
		/*
		 * / 2 - popupWindow.getWidth() / 2;
		 */
		Log.i("coder", "xPos:" + xPos);

		popupWindow.showAsDropDown(parent, xPos - 20, 30);
	}

	private void initUI() {
		bottomPanel = (BottomControlPanel) findViewById(R.id.bottom_layout);
		
		mImageLoader.displayImage(ImageStringUtil.getImageURL(user.getPicture()), toggleMenu, options,animateFirstListener);
		
		if (bottomPanel != null) {
			bottomPanel.initBottomPanel();
			bottomPanel.setBottomCallback(this);
		}

		if (topview != null) {
			mImageLoader.displayImage(ImageStringUtil.getImageURL(user.getPicture()), topview.getmImageView(), options, animateFirstListener);
			topview.setText(user.getUser_nickname());
			// topview.setAutographText("'我就是我，不一样的烟火'");
		}

		if (item1 != null) {
			item1.setleftmenu_image(R.drawable.left_menu01);
			item1.setleftmenu_item_txt("我的基本信息");
			// item1.getLeftmenu_item_txt().setTextColor(R.color.white);
			// item1.setrightmenu_image(R.drawable.left_menu01);
		}

		if (item2 != null) {
			item2.setleftmenu_image(R.drawable.left_menu02);
			item2.setleftmenu_item_txt("我的收藏");
			// item2.getLeftmenu_item_txt().setTextColor(R.color.darkgray);
			// item2.setrightmenu_image(R.drawable.left_menu02);
		}

		if (item3 != null) {
			item3.setleftmenu_image(R.drawable.left_menu03);
			item3.setleftmenu_item_txt("我的关注");
			// item3.getLeftmenu_item_txt().setTextColor(R.color.darkgray);
			// item3.setrightmenu_image(R.drawable.left_menu03);
		}

		if (item4 != null) {
			item4.setleftmenu_image(R.drawable.left_menu04);
			item4.setleftmenu_item_txt("我的足迹");
			// item4.getLeftmenu_item_txt().setTextColor(R.color.darkgray);
			// item4.setrightmenu_image(R.drawable.left_menu04);
		}
        
		if (item5 != null) {
			item5.setleftmenu_image(R.drawable.left_menu04);
			item5.setleftmenu_item_txt("我的发布");
			// item4.getLeftmenu_item_txt().setTextColor(R.color.darkgray);
			// item4.setrightmenu_image(R.drawable.left_menu04);
		}

		
	}

	public void toggleMenu(View view) {
		mLeftMenu.toggle();
	}

	/*
	 * 处理BottomControlPanel的回调
	 * 
	 * @see
	 * org.yanzi.ui.BottomControlPanel.BottomPanelCallback#onBottomPanelClick
	 * (int)
	 */
	@Override
	public void onBottomPanelClick(int itemId) {
		// TODO Auto-generated method stub
		String tag = "";
		if ((itemId & Constant.BTN_FLAG_MESSAGE) != 0) {
			tag = Constant.FRAGMENT_FLAG_MESSAGE;
		} else if ((itemId & Constant.BTN_FLAG_Contents) != 0) {
			tag = Constant.FRAGMENT_FLAG_Contents;
		} else if ((itemId & Constant.BTN_FLAG_Friends) != 0) {
			tag = Constant.FRAGMENT_FLAG_Friends;
		}
		setTabSelection(tag); // 切换Fragment
	}

	private void setDefaultFirstFragment(String tag) {
		Log.i("yan", "setDefaultFirstFragment enter... currFragTag = "
				+ currFragTag);
		setTabSelection(tag);
		bottomPanel.defaultBtnChecked();
		Log.i("yan", "setDefaultFirstFragment exit...");
	}

	private void commitTransactions(String tag) {
		if (fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
			fragmentTransaction.commit();
			currFragTag = tag;
			fragmentTransaction = null;
		}
	}

	private FragmentTransaction ensureTransaction() {
		if (fragmentTransaction == null) {
			fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

		}
		return fragmentTransaction;

	}

	private void attachFragment(int layout, Fragment f, String tag) {
		if (f != null) {
			if (f.isDetached()) {
				ensureTransaction();
				fragmentTransaction.attach(f);

			} else if (!f.isAdded()) {
				ensureTransaction();
				fragmentTransaction.add(layout, f, tag);
			}
		}
	}

	private Fragment getFragment(String tag) {

		Fragment f = fragmentManager.findFragmentByTag(tag);

		if (f == null) {
			Toast.makeText(getApplicationContext(),
					"fragment = null tag = " + tag, Toast.LENGTH_SHORT).show();
			f = BaseFragment.newInstance(getApplicationContext(), tag);
		}
		return f;

	}

	private void detachFragment(Fragment f) {

		if (f != null && !f.isDetached()) {
			ensureTransaction();
			fragmentTransaction.detach(f);
		}
	}

	/**
	 * 切换fragment
	 * 
	 * @param tag
	 */
	private void switchFragment(String tag) {
		if (TextUtils.equals(tag, currFragTag)) {
			return;
		}
		// 把上一个fragment detach掉
		if (currFragTag != null && !currFragTag.equals("")) {
			detachFragment(getFragment(currFragTag));
		}
		attachFragment(R.id.fragment_content, getFragment(tag), tag);
		commitTransactions(tag);
	}

	/**
	 * 设置选中的Tag
	 * 
	 * @param tag
	 */
	public void setTabSelection(String tag) {
		// 开启一个Fragment事务
		fragmentTransaction = fragmentManager.beginTransaction();
		if (TextUtils.equals(tag, Constant.FRAGMENT_FLAG_MESSAGE)) {
			mainText.setText("我的消息");
		} else if (TextUtils.equals(tag, Constant.FRAGMENT_FLAG_Friends)) {
			mainText.setText("我的好友");
		} else {
			mainText.setText("主界面");
		}
		/*
		 * if(TextUtils.equals(tag, Constant.FRAGMENT_FLAG_MESSAGE)){ if
		 * (messageFragment == null) { messageFragment = new MessageFragment();
		 * }
		 * 
		 * }else if(TextUtils.equals(tag, Constant.FRAGMENT_FLAG_CONTACTS)){ if
		 * (contactsFragment == null) { contactsFragment = new
		 * ContactsFragment(); }
		 * 
		 * }else if(TextUtils.equals(tag, Constant.FRAGMENT_FLAG_NEWS)){ if
		 * (newsFragment == null) { newsFragment = new NewsFragment(); }
		 * 
		 * }else if(TextUtils.equals(tag,Constant.FRAGMENT_FLAG_SETTING)){ if
		 * (settingFragment == null) { settingFragment = new SettingFragment();
		 * } }else if(TextUtils.equals(tag, Constant.FRAGMENT_FLAG_SIMPLE)){ if
		 * (simpleFragment == null) { simpleFragment = new SimpleFragment(); }
		 * 
		 * }
		 */
		/*
		 * if(TextUtils.equals(tag,Constant.FRAGMENT_FLAG_Contents)){
		 * 
		 * } else
		 */
		switchFragment(tag);

	}
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		switch (resultCode) {
		case 111:
			user=(User) data.getSerializableExtra("user");
			mImageLoader.displayImage(ImageStringUtil.getImageURL(user.getPicture()), toggleMenu, options);
			mImageLoader.displayImage(ImageStringUtil.getImageURL(user.getPicture()), topview.getmImageView(), options);
			break;
		case 222:
			Intent nextIntent = new Intent(MainActivity.this,
					LoginActivity.class);
			MainActivity.this.startActivity(nextIntent); 
			con.sendExitQuest();
			this.finish();
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, requestCode, data);
	}
	
    private static List<Friend> queryFriendsMessage(String userid){
    	List<Friend> list=new ArrayList<Friend>();
    	list=getFriendMessageList(userid);
    	return list;
    }
    
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		currFragTag = "";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void processMessage(Message msg) {
		// TODO 自动生成的方法存根
		if(msg.what==Config.SEND_MessageFragment){
			if(currFragTag.equals(Constant.FRAGMENT_FLAG_MESSAGE)){
				playMsg();
				Bundle bundle=msg.getData();
				ChatMessage chatMessage=(ChatMessage)bundle.getSerializable("chatMessage");
				saveToDb(chatMessage,Config.DateBase_GET_MESSAGE);
				MessageFragment f=(MessageFragment) getFragment(currFragTag);
				f.onRefresh();
			}
			else{
			Bundle bundle=msg.getData();
			ChatMessage chatMessage=(ChatMessage)bundle.getSerializable("chatMessage");
			saveToDb(chatMessage,Config.DateBase_GET_MESSAGE);
			
			sendNotifycation(chatMessage.getSelf(),chatMessage.getFriend());
			}
	   }
		else if(msg.what==Config.SEND_NOTIFICATION_JBEX_FRIEND){
			sendNotifycation_JBEXFriend();
		}
		else if(msg.what==Config.SEND_MessageOffline){
			playMsg();
			Bundle bundle=msg.getData();
			ArrayList<ChatMessage> ChatMessageList=new ArrayList<ChatMessage>();
			ChatMessageList=bundle.getParcelableArrayList("chatMessageList");
			MessageOfflineSaveToDb(ChatMessageList);

		}
	}

}
