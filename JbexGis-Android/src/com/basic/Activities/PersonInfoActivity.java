package com.basic.Activities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.other.actionsheet.Method.Action1;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageOptions;
import com.basic.ImageLoad.ImageStringUtil;
import com.basic.adapter.MenuItemAdapter;
import com.basic.connectservice.HttpUtil;
import com.basic.connectservice.SettingUserInfo;
import com.basic.model.menuItemBean;
import com.basic.service.model.User;
import com.basic.ui.ActionSheet;
import com.basic.ui.CustomProgressDialog;
import com.basic.ui.OptionsPopupWindow;
import com.basic.ui.OptionsPopupWindow.OnOptionsSelectListener;
import com.basic.ui.TimePopupWindow;
import com.basic.ui.TimePopupWindow.OnTimeSelectListener;
import com.basic.ui.TimePopupWindow.Type;
import com.basic.util.ListViewHeightBaseOnChildren;
import com.basic.util.StringUtils;
import com.basic.util.UpLoadUtil;
import com.message.net.ChatMessage;
import com.message.net.Config;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

@SuppressLint({ "ResourceAsColor", "SimpleDateFormat", "SdCardPath" })
public class PersonInfoActivity extends ZJBEXBaseActivity {
	private ActionBar actionBar;
	private Intent intent;
	private ListView listMenu;
	private MenuItemAdapter itemAdapter;
	private ActionSheet actionSheet;
	private List<menuItemBean> mList = new ArrayList<menuItemBean>();
	private TimePopupWindow pwTime;
	private ImageView image;
	private ImageView sex;
	private TextView friendsName;
	private String ReusltSchool = null; // 返回的学院数据
	private String ResultSex = null; // 返回的性别
	private String ResultAge = null; // 返回的年龄
	private String ResultDate = null; // 返回的生日
	private String ReusltAcademy = null; // 返回的院系
	private String telephone=null;     //返回的电话号码
	private String person_signature=null; //返回的个性签名
	private Date birthday=null;     //返回的生日
	private OptionsPopupWindow pwOption;
	private ArrayList<String> Sex = new ArrayList<String>();// Sex的数据源
	private ArrayList<String> Age = new ArrayList<String>();// Age的数据源

	private User user = new User(); // User

	private CustomProgressDialog progressDialog = null;
	
	 private String imageName;
	 private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	 private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	 private static final int PHOTO_REQUEST_CUT = 3;// 结果
	    
	//图片加载框架组件
	private DisplayImageOptions options;
	private ImageLoader mImageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_info);

		intent = getIntent();

		if ((intent.getExtras()) != null) {
			Bundle data = intent.getExtras();
			user = (User) data.getSerializable("user");
			Log.d("user", user.toString());
		}
        
		image=(ImageView) super.findViewById(R.id.image);
		friendsName=(TextView) findViewById(R.id.friendsName);
		sex=(ImageView) findViewById(R.id.sex);
		listMenu = (ListView) super.findViewById(R.id.listMenu);
		pwTime = new TimePopupWindow(this, Type.YEAR_MONTH_DAY);
		pwOption = new OptionsPopupWindow(this);
        
		initDisplayOption();
		initView();
		initListener();
		initOptions();
		initActionBar();
	}

	private void initDisplayOption() {
		// TODO 自动生成的方法存根
		options=ImageOptions.initDisplayOptions();
		mImageLoader = ImageLoader.getInstance();
	}

	private void initOptions() {
		// TODO 自动生成的方法存根
		Sex.add("男");
		Sex.add("女");
	}

	private void initListener() {
		// TODO 自动生成的方法存根

	}

	private void initActionBar() {
		// TODO 自动生成的方法存根

		actionBar=getActionBar();
		actionBar.setCustomView(R.layout.session_top);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayShowCustomEnabled(true);
		
		View view = actionBar.getCustomView();
		ImageButton nnavigation = (ImageButton) view
				.findViewById(R.id.btn_nnavigation);
		ImageButton back = (ImageButton) view.findViewById(R.id.btn_back);
		TextView txt = (TextView) view.findViewById(R.id.main_Text);
		TextView backtxt = (TextView) view.findViewById(R.id.backtxt);

		nnavigation.setBackgroundResource(R.drawable.personinfo);

		txt.setText("个人信息");

		backtxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				PersonInfoActivity.this.finish();
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				PersonInfoActivity.this.finish();
			}
		});

		nnavigation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SetFriendsAsyncTask a=new SetFriendsAsyncTask();
				a.execute();
				Intent intent=new Intent();
				intent.putExtra("user", user);
				PersonInfoActivity.this.setResult(111, intent);
			}
		});
	}

	private void initView() {
		// TODO 自动生成的方法存根
		ReusltAcademy = user.getAcademy();
		ReusltSchool = user.getSchool();
		person_signature=user.getPerson_signature();
		telephone=user.getTelephone();
		birthday=user.getBirthday();
		
		if (user.getSex() == 0){
			ResultSex = "未绑定";
			sex.setImageResource(0);
		}
		else if (user.getSex() == 1){
			ResultSex = "男";
			sex.setImageResource(R.drawable.man);
			}
		else{
			sex.setImageResource(R.drawable.woman);
			ResultSex = "女";
		}
		
		mImageLoader.displayImage(ImageStringUtil.getImageURL(user.getPicture()), image, options, animateFirstListener);
		friendsName.setText(user.getUser_nickname());
		
		ResultAge = String.valueOf(StringUtils.getAgeByBirthday(user
				.getBirthday()));

		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		ResultDate = myFmt.format(user.getBirthday());

		menuItemBean item2 = new menuItemBean("昵称", user.getUser_nickname(), 0,
				0);
		menuItemBean item3 = new menuItemBean("邮箱", user.getEmail(), 0, 0);
		menuItemBean item4 = new menuItemBean("性别", ResultSex, 0,
				R.drawable.btn_right);
		menuItemBean item5 = new menuItemBean("年龄", ResultAge, 0,
				R.drawable.btn_right);
		menuItemBean item6 = new menuItemBean("个性签名",
				person_signature, 0, R.drawable.btn_right);
		menuItemBean item7 = new menuItemBean("手机号码", telephone, 0,
				R.drawable.btn_right);
		menuItemBean item8 = new menuItemBean("学校", ReusltSchool, 0, 0);
		menuItemBean item9 = new menuItemBean("学院", ReusltAcademy, 0,
				R.drawable.btn_right);
		menuItemBean item10 = new menuItemBean("生日", ResultDate, 0,
				R.drawable.btn_right);

		mList.add(item2);
		mList.add(item3);
		mList.add(item4);
		mList.add(item5);
		mList.add(item6);
		mList.add(item7);
		mList.add(item8);
		mList.add(item9);
		mList.add(item10);

		itemAdapter = new MenuItemAdapter(this, mList);
		listMenu.setAdapter(itemAdapter);
		listMenu.setOnItemClickListener(new SchooltemListener());
		ListViewHeightBaseOnChildren.setListViewHeightBasedOnChildren(listMenu);
	}

	public void setImagePhoto(View source){
		 final AlertDialog dlg = new AlertDialog.Builder(this).create();
	        dlg.show();
	        Window window = dlg.getWindow();
	        // *** 主要就是在这里实现这种效果的.
	        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
	        window.setContentView(R.layout.alertdialog);
	        // 为确认按钮添加事件,执行退出应用操作
	        TextView tv_paizhao = (TextView) window.findViewById(R.id.tv_content1);
	        tv_paizhao.setText("拍照");
	        tv_paizhao.setOnClickListener(new View.OnClickListener() {
	            @SuppressLint("SdCardPath")
	            public void onClick(View v) {

	                imageName = user.getUser_id() + ".png";
	                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	                // 指定调用相机拍照后照片的储存路径
	                intent.putExtra(MediaStore.EXTRA_OUTPUT,
	                        Uri.fromFile(new File("/sdcard/JBEX/Cache/images", imageName)));
	                startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
	                dlg.cancel();
	            }
	        });
	        TextView tv_xiangce = (TextView) window.findViewById(R.id.tv_content2);
	        tv_xiangce.setText("相册");
	        tv_xiangce.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {

	                imageName = user.getUser_id() + ".png";
	                Intent intent = new Intent(Intent.ACTION_PICK, null);
	                intent.setDataAndType(
	                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
	                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);

	                dlg.cancel();
	            }
	        });
	}
	
	class SchooltemListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View currentview,
				int arg2, long arg3) {

			switch (arg2) {

			case 7: // 学院
				Intent intent2 = new Intent(PersonInfoActivity.this,
						ChooseSchoolActivity.class);
				intent2.putExtra("school", ReusltAcademy);
				PersonInfoActivity.this.startActivityForResult(intent2, 1);
				break;

			case 8: // 生日
				pwTime.showAtLocation(currentview, Gravity.BOTTOM, 0, 0,
						new Date());
				pwTime.setOnTimeSelectListener(new OnTimeSelectListener() {
					@Override
					public void onTimeSelect(Date date) {
						// TODO 自动生成的方法存根
						birthday=date;
						ResultDate = StringUtils.getTime(date);
						ResultAge = String.valueOf(StringUtils
								.getAgeByBirthday(date));
						menuItemBean item2 = new menuItemBean("年龄", ResultAge,
								0, R.drawable.btn_right);
						menuItemBean item = new menuItemBean("生日", ResultDate,
								0, R.drawable.btn_right);
						mList.set(3, item2);
						mList.set(8, item);
						itemAdapter.onDateChange(mList);
					}
				});
				Toast.makeText(PersonInfoActivity.this, "修改后 要保存哦！", Toast.LENGTH_SHORT).show();
				break;

			case 2: // 性别
				pwOption.setPicker(Sex);
				pwOption.setLabels("性别");
				// 设置默认选中的三级项目
				pwOption.setSelectOptions(0, 0, 0);
				pwOption.setOnoptionsSelectListener(new OnOptionsSelectListener() {
					@Override
					public void onOptionsSelect(int options1, int option2,
							int options3) {
						// TODO 自动生成的方法存根
						ResultSex = Sex.get(options1);
						menuItemBean item = new menuItemBean("性别", ResultSex,
								0, R.drawable.btn_right);
						mList.set(2, item);
						itemAdapter.onDateChange(mList);
					}
				});
				pwOption.showAtLocation(currentview, Gravity.BOTTOM, 0, 0);
				Toast.makeText(PersonInfoActivity.this, "修改后 要保存哦！", Toast.LENGTH_SHORT).show();
				break;

			case 3: // 年龄
				Toast.makeText(PersonInfoActivity.this, "请选择您的生日自动生成年龄",
						Toast.LENGTH_SHORT).show();
				pwTime.showAtLocation(currentview, Gravity.BOTTOM, 0, 0,
						new Date());
				pwTime.setOnTimeSelectListener(new OnTimeSelectListener() {
					@Override
					public void onTimeSelect(Date date) {
						// TODO 自动生成的方法存根
						birthday=date;
						ResultDate = StringUtils.getTime(date);
						ResultAge = String.valueOf(StringUtils
								.getAgeByBirthday(date));
						menuItemBean item2 = new menuItemBean("年龄", ResultAge,
								0, R.drawable.btn_right);
						menuItemBean item = new menuItemBean("生日", ResultDate,
								0, R.drawable.btn_right);
						mList.set(3, item2);
						mList.set(8, item);
						itemAdapter.onDateChange(mList);
					}
				});
				Toast.makeText(PersonInfoActivity.this, "修改后 要保存哦！", Toast.LENGTH_SHORT).show();
				break;

			case 1: // 昵称
                  
				break;

			case 4:// 个性签名
                    Intent nextIntent=new Intent(PersonInfoActivity.this, InputInfoActivity.class);
                    nextIntent.putExtra("text", person_signature);
                    nextIntent.putExtra("type", "text");
                    PersonInfoActivity.this.startActivityForResult(nextIntent,1);
				break;
				
			case 5: //电话号码
				 Intent nextNumber=new Intent(PersonInfoActivity.this, InputInfoActivity.class);
				 nextNumber.putExtra("text", telephone);
				 nextNumber.putExtra("type", "num");
                 PersonInfoActivity.this.startActivityForResult(nextNumber,1);
			   break;
			   
			default:
				break;
			}

		}
	}
 
    
	
	/**
	 * 为了得到传回的数据，必须在前面的Activity中（指MainActivity类）重写onActivityResult方法
	 * 
	 * requestCode 请求码，即调用startActivityForResult()传递过去的值 resultCode
	 * 结果码，结果码用于标识返回数据来自哪个新Activity
	 */
	@SuppressLint("SdCardPath")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (resultCode) {
		case 333:
			ReusltAcademy = data.getStringExtra("school");
			menuItemBean item = new menuItemBean("学院", ReusltAcademy, 0,R.drawable.btn_right);
			mList.set(7, item);
			itemAdapter.onDateChange(mList);
			Toast.makeText(PersonInfoActivity.this, "修改后 要保存哦！", Toast.LENGTH_SHORT).show();
			break;
		case 111:
			person_signature=data.getStringExtra("info");
			menuItemBean itemperson_signature = new menuItemBean("个性签名", person_signature, 0,R.drawable.btn_right);
			mList.set(4, itemperson_signature);
			itemAdapter.onDateChange(mList);
			Toast.makeText(PersonInfoActivity.this, "修改后 要保存哦！", Toast.LENGTH_SHORT).show();
			break;
		case 222:
			telephone=data.getStringExtra("info");
			menuItemBean itemtelephone = new menuItemBean("手机号码", telephone, 0,R.drawable.btn_right);
			mList.set(5, itemtelephone);
			itemAdapter.onDateChange(mList);
			Toast.makeText(PersonInfoActivity.this, "修改后 要保存哦！", Toast.LENGTH_SHORT).show();
			break;
			
		default:
			break;
		}
        
		if (resultCode == RESULT_OK) {
			switch(requestCode){
			 case PHOTO_REQUEST_TAKEPHOTO:

	             startPhotoZoom(
	                     Uri.fromFile(new File("/sdcard/JBEX/Cache/images", imageName)),
	                     480);
	             break;
	             
			 case PHOTO_REQUEST_GALLERY:
	             if (data != null)
	                 startPhotoZoom(data.getData(), 480);
	             break;
	            
			 case PHOTO_REQUEST_CUT:
	             // BitmapFactory.Options options = new BitmapFactory.Options();
	             //
	             // /**
	             // * 最关键在此，把options.inJustDecodeBounds = true;
	             // * 这里再decodeFile()，返回的bitmap为空
	             // * ，但此时调用options.outHeight时，已经包含了图片的高了
	             // */
	             // options.inJustDecodeBounds = true;
	             Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/JBEX/Cache/images/"
	                     + imageName);
	            // image.setImageBitmap(bitmap);
	             UpLoadAsyncTask upload=new UpLoadAsyncTask();
	             upload.execute();
	             break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	
	 private void updateAvatarInServer(String imageName2) {
		// TODO 自动生成的方法存根
		 user.setPicture(imageName2);
		 SettingUserInfo.settingUserInfo(user);
		 String actionUrl=HttpUtil.BASE_URL+"servlet/UpLoadServlet";
		 UpLoadUtil.uploadFile("/sdcard/JBEX/Cache/images/"
                 + imageName, imageName2,actionUrl);
		 
		 
	}

	private void startPhotoZoom(Uri uri1, int size) {
	        Intent intent = new Intent("com.android.camera.action.CROP");
	        intent.setDataAndType(uri1, "image/*");
	        // crop为true是设置在开启的intent中设置显示的view可以剪裁
	        intent.putExtra("crop", "true");

	        // aspectX aspectY 是宽高的比例
	        intent.putExtra("aspectX", 1);
	        intent.putExtra("aspectY", 1);

	        // outputX,outputY 是剪裁图片的宽高
	        intent.putExtra("outputX", size);
	        intent.putExtra("outputY", size);
	        intent.putExtra("return-data", false);

	        intent.putExtra(MediaStore.EXTRA_OUTPUT,
	                Uri.fromFile(new File("/sdcard/JBEX/Cache/images", imageName)));
	        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
	        intent.putExtra("noFaceDetection", true); // no face detection
	        startActivityForResult(intent, PHOTO_REQUEST_CUT);
	    }
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.person_info, menu);
		return true;
	}

	public void Exitbtn(View source) {
		actionSheet = new ActionSheet(PersonInfoActivity.this);
		actionSheet.show("确定要退出么？", new String[] { "退出" },
				new Action1<Integer>() {
					@Override
					public void invoke(Integer index) {
						actionSheet.hide();
						if (index == 0) {
							PersonInfoActivity.this.setResult(222);
							PersonInfoActivity.this.finish();
						}
					}
				});
	}
	
	public class SetFriendsAsyncTask extends AsyncTask<Integer, Integer, String>{
	     
	     //后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根
				user.setAcademy(ReusltAcademy);
				user.setSchool(ReusltSchool);
				user.setBirthday(birthday);
				user.setPerson_signature(person_signature);
				user.setTelephone(telephone);
				int sex = 0;
				if(ResultSex.endsWith("未绑定"))
					sex=0;
				else if(ResultSex.endsWith("男"))
					sex=1;
				else if(ResultSex.endsWith("女"))
					sex=2;
				user.setSex(sex);
				boolean flag = SettingUserInfo.settingUserInfo(user);
				Log.d("flag------->", String.valueOf(flag));
				
				return "success";
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				super.onPostExecute(result);
				
				if (progressDialog != null){
					progressDialog.dismiss();
					progressDialog = null;
				}	
				Toast.makeText(PersonInfoActivity.this, "恭喜你 保存成功哦！", Toast.LENGTH_SHORT).show();
			}
			
	      //这里是最终用户调用Excute时的接口，当任务执行之前开始调用此方法，可以在这里显示进度对话框。
			@Override
			protected void onPreExecute() {
				// TODO 自动生成的方法存根
				super.onPreExecute();
				
				if (progressDialog == null){
					progressDialog = CustomProgressDialog.createDialog(PersonInfoActivity.this);
			    	progressDialog.setMessage("正在加载中...");
				}
				
		    	progressDialog.show();
			}
			
		}
	
	public class UpLoadAsyncTask extends AsyncTask<Integer, Integer, String>{
	     
	     //后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根
				updateAvatarInServer(imageName);
				return "success";
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				super.onPostExecute(result);
				user.setPicture(imageName);
				mImageLoader.clearDiskCache();
				mImageLoader.clearMemoryCache();
				mImageLoader.displayImage(ImageStringUtil.getImageURL(user.getPicture()), image, options, animateFirstListener);
				if (progressDialog != null){
					progressDialog.dismiss();
					progressDialog = null;
				}	
				Intent intent=new Intent();
				intent.putExtra("user", user);
				PersonInfoActivity.this.setResult(111, intent);
				Toast.makeText(PersonInfoActivity.this, "恭喜你 保存成功哦！", Toast.LENGTH_SHORT).show();
			}
			
	      //这里是最终用户调用Excute时的接口，当任务执行之前开始调用此方法，可以在这里显示进度对话框。
			@Override
			protected void onPreExecute() {
				// TODO 自动生成的方法存根
				super.onPreExecute();
				
				if (progressDialog == null){
					progressDialog = CustomProgressDialog.createDialog(PersonInfoActivity.this);
			    	progressDialog.setMessage("正在加载中...");
				}
				
		    	progressDialog.show();
			}
			
		}



	@Override
	public void processMessage(Message msg) {
		// TODO 自动生成的方法存根
		if(msg.what==Config.SEND_NOTIFICATION){
			Bundle bundle=msg.getData();
			ChatMessage chatMessage=(ChatMessage)bundle.getSerializable("chatMessage");
			saveToDb(chatMessage,Config.DateBase_GET_MESSAGE);

			sendNotifycation(chatMessage.getSelf(),chatMessage.getFriend());}
		else if (msg.what==Config.SEND_NOTIFICATION_JBEX_FRIEND){
			sendNotifycation_JBEXFriend();
		}
	}
}
