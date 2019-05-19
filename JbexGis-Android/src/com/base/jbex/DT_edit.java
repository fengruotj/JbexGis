package com.base.jbex;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

import com.basic.Activities.R;
import com.basic.Activities.R.layout;
import com.basic.Activities.R.menu;
import com.basic.Activities.ZJBEXBaseActivity;
import com.basic.connectservice.HttpUtil;
import com.basic.connectservice.JbexInfoService;
import com.basic.connectservice.dynamicInfoService;
import com.basic.service.model.DynamicInfo;
import com.basic.service.model.JbexInfo;
import com.basic.service.model.User;
import com.basic.ui.CustomProgressDialog;
import com.basic.util.UpLoadUtil;
import com.message.net.ChatMessage;
import com.message.net.Config;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class DT_edit extends ZJBEXBaseActivity {

	private Intent intent;
	private User owneruser=new User();
	private String str_detail;
	private String str_time;
	
	private ActionBar actionBar = null;
	private TextView txt = null;
	private ImageView dt_picture1;
	private ImageView dt_picture2;
	private TextView dt_etitText;
    
	private double dot_x,dot_y;
	
	private CustomProgressDialog progressDialog = null;
	private String imageName="";
	 private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	 private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	 private static final int PHOTO_REQUEST_CUT = 3;// 结果
	 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dt_edit);
		
		dt_picture1=(ImageView) findViewById(R.id.dt_picture1);
		dt_picture2=(ImageView) findViewById(R.id.dt_picture2);
		dt_etitText=(TextView) findViewById(R.id.dt_etitText);
		
		intent=getIntent();
		if ((intent.getExtras()) != null) {
			Bundle data = intent.getExtras();
			owneruser = (User) data.getSerializable("owneruser");
			dot_x=intent.getDoubleExtra("dot1", 0);
			dot_y=intent.getDoubleExtra("dot2", 0);
			Log.d("owneruser", owneruser.toString());
		}
		
		initView();
		initListener();
		initActionBar();

	}

	private void initActionBar() {
		// TODO Auto-generated method stub
		actionBar = getActionBar();
		actionBar.setCustomView(R.layout.session_top);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayShowCustomEnabled(true);
		View view = actionBar.getCustomView();
		ImageButton rightButton = (ImageButton) view.findViewById(R.id.btn_nnavigation);
		rightButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				publishDT();
			}

		});
		ImageButton back = (ImageButton) view.findViewById(R.id.btn_back);
		TextView backtxt=(TextView) view.findViewById(R.id.backtxt);
		back.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DT_edit.this, DT_seletedot.class);
				DT_edit.this.startActivity(intent);
				DT_edit.this.finish();
			}
		});
		
		backtxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				DT_edit.this.finish();
			}
		});
		
		txt = (TextView) view.findViewById(R.id.main_Text);
		rightButton.setBackgroundResource(R.drawable.btn_public);
		txt.setText("写说说");
	}

	protected void publishDT() {
		// TODO Auto-generated method stub
		str_detail =dt_etitText.getText().toString();
		addynamicinfoAsyncTask a=new addynamicinfoAsyncTask();
		a.execute();
	}

	private void initListener() {
		// TODO Auto-generated method stub
		dt_picture1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				imageName="dynamic_"+owneruser.getUser_id()+"_1.png";
				setImagePhoto();
			}
		});

		dt_picture2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				imageName="dynamic_"+owneruser.getUser_id()+"_2.png";
				setImagePhoto();
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		
	}
	
	private void setImagePhoto() {
		// TODO 自动生成的方法存根
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

            	
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定调用相机拍照后照片的储存路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File("/sdcard/JBEX/Cache/dynamicimages", imageName)));
                startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
                dlg.cancel();
            }
        });
        TextView tv_xiangce = (TextView) window.findViewById(R.id.tv_content2);
        tv_xiangce.setText("相册");
        tv_xiangce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            	//imageName = owneruser.getUser_id() + ".png";
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);

                dlg.cancel();
            }
        });
	}
	
	private void startPhotoZoom(Uri uri1, int size) {
		// TODO 自动生成的方法存根
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
	                Uri.fromFile(new File("/sdcard/JBEX/Cache/dynamicimages", imageName)));
	        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
	        intent.putExtra("noFaceDetection", true); // no face detection
	        startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}
	
	@SuppressLint("SdCardPath")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		if (resultCode == RESULT_OK) {
			switch(requestCode){
			 case PHOTO_REQUEST_TAKEPHOTO:

	             startPhotoZoom(
	                     Uri.fromFile(new File("/sdcard/JBEX/Cache/dynamicimages", imageName)),
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
	             Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/JBEX/Cache/dynamicimages/"
	                     + imageName);
	             if(imageName.equals("dynamic_"+owneruser.getUser_id()+"_1.png"))
	            	 dt_picture1.setImageBitmap(bitmap);
	             else 
	            	 dt_picture2.setImageBitmap(bitmap);
	             //UpLoadAsyncTask upload=new UpLoadAsyncTask();
	             //upload.execute();
	             break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	@SuppressLint("SimpleDateFormat")
	public class addynamicinfoAsyncTask extends AsyncTask<Integer, Integer, String>{
	     private File file1;
	     private File file2;
		//后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@SuppressWarnings({ "deprecation", "unused" })
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根
				String flag;
				Date date=new Date();
				flag=dynamicInfoService.addynamicinfo(owneruser, dot_x, dot_y, str_detail, date);
				
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String bir=sdf.format(date);
				Timestamp time=Timestamp.valueOf(bir);
				DynamicInfo dynamicinfo=new DynamicInfo();
				dynamicinfo.setId(Long.valueOf(flag));
				dynamicinfo.setDotX(dot_x);
				dynamicinfo.setDotY(dot_y);
				dynamicinfo.setDetail(str_detail);
				dynamicinfo.setTime(time);
				dynamicinfo.setTUser(owneruser);
				
				String actionUrl=HttpUtil.BASE_URL+"servlet/dynamicinfoUpLoadServlet";
				file1=new File("/sdcard/JBEX/Cache/dynamicimages/"+"dynamic_"+owneruser.getUser_id()+"_1.png");
			    file2=new File("/sdcard/JBEX/Cache/dynamicimages/"+"dynamic_"+owneruser.getUser_id()+"_2.png");
				if(file1.exists())
				{
					dynamicinfo.setPicture1("dynamic_"+flag+"_1.png");
					//publicinfo.setPicture2("null");
			        UpLoadUtil.uploadFile("/sdcard/JBEX/Cache/dynamicimages/"
		                 +"dynamic_"+ owneruser.getUser_id()+"_1.png","dynamic_"+ flag+"_1.png",actionUrl); 
			        dynamicInfoService.setdynamicInfo(dynamicinfo);
				}
				else{
					dynamicinfo.setPicture1("null");
				}
				 if(file2.exists()){
					 dynamicinfo.setPicture2("dynamic_"+flag+"_2.png");
					// publicinfo.setPicture1("null");
					UpLoadUtil.uploadFile("/sdcard/JBEX/Cache/dynamicimages/"
			                 +"dynamic_"+ owneruser.getUser_id()+"_2.png","dynamic_"+ flag+"_2.png",actionUrl);
					dynamicInfoService.setdynamicInfo(dynamicinfo);
				 }
				 else{
					 dynamicinfo.setPicture2("null");
				 }
				 
				 dynamicInfoService.setdynamicInfo(dynamicinfo);
				return "true";
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				super.onPostExecute(result);
				String text="";
				if(result.endsWith("true")){
					text="恭喜你！发布成功";
				}
				else 
					text="删除失败";
				
				if(file1.exists())
				 file1.delete();
				if(file2.exists())
				 file2.delete();
				 
				Toast.makeText(DT_edit.this, text, Toast.LENGTH_SHORT).show();
				
			/*	Intent intent = new Intent(JbexEditActivity.this, JbexActivity.class);
				Bundle data = new Bundle();
				data.putSerializable("owneruser", owneruser);
				intent.putExtras(data);
				JbexEditActivity.this.startActivityForResult(intent, 222);
				JbexEditActivity.this.finish();*/
				
				if (progressDialog != null){
					progressDialog.dismiss();
					progressDialog = null;
				}	
			
			}
			
	      //这里是最终用户调用Excute时的接口，当任务执行之前开始调用此方法，可以在这里显示进度对话框。
			@Override
			protected void onPreExecute() {
				// TODO 自动生成的方法存根
				super.onPreExecute();
				
				if (progressDialog == null){
					progressDialog = CustomProgressDialog.createDialog(DT_edit.this);
			    	progressDialog.setMessage("正在加载中...");
				}
				
		    	progressDialog.show();
			}
			
		}
	
	@Override
	public void processMessage(Message msg) {
		// TODO Auto-generated method stub
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
