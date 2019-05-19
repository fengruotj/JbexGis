package com.base.jbex;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.base.jbex.EditPublic.addpublicinfonAsyncTask;
import com.basic.Activities.R;
import com.basic.Activities.R.layout;
import com.basic.Activities.R.menu;
import com.basic.Activities.ZJBEXBaseActivity;
import com.basic.connectservice.HttpUtil;
import com.basic.connectservice.JbexInfoService;
import com.basic.connectservice.PublicInfoService;
import com.basic.service.model.JbexInfo;
import com.basic.service.model.PublicInfo;
import com.basic.service.model.User;
import com.basic.ui.CustomProgressDialog;
import com.basic.ui.OptionsPopupWindow;
import com.basic.ui.OptionsPopupWindow.OnOptionsSelectListener;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.TimePicker.OnTimeChangedListener;

public class JbexEditActivity extends ZJBEXBaseActivity {
    
	private Intent intent;
	private User owneruser=new User();
	
	private double dot_x, dot_y;
	
	private ActionBar actionBar = null;
	private TextView txt = null;
	private ImageView publicinfo1;
	private ImageView publicinfo2;
	private TimePicker timepicker;
	private DatePickerDialog picker;
	private TextView timeText;
	private RelativeLayout LabelRelative;
	private TextView jbex_label;
	private EditText ev_title;
	private EditText ev_details;
	
	private String str_title;
	private String str_time;
	private String str_details;
	private String label;
	
	private CustomProgressDialog progressDialog = null;
	private String imageName="";
	 private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	 private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	 private static final int PHOTO_REQUEST_CUT = 3;// 结果
	 
	 private int iYear,iMonth,iDay,iHour,iMinute;
	 
	 private OptionsPopupWindow pwOption;
	 private ArrayList<String> Jbex_label = new ArrayList<String>();// label的数据源
	 
	private DatePickerDialog.OnDateSetListener DatePickerListener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {
            // TODO Auto-generated method stub
             
        }
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jbex_edit);
		
		publicinfo1=(ImageView) findViewById(R.id.publicinfo1);
		publicinfo2=(ImageView) findViewById(R.id.publicinfo2);
		timepicker=(TimePicker) findViewById(R.id.timepicker);
		timeText=(TextView) findViewById(R.id.timetext);
		LabelRelative=(RelativeLayout) findViewById(R.id.area_rl);
		jbex_label=(TextView) findViewById(R.id.jbex_label);
		ev_title = (EditText) super.findViewById(R.id.edit_tltle);
		ev_details= (EditText) super.findViewById(R.id.edit_details);
		
		timepicker.setIs24HourView(true);
		
		intent=getIntent();
		if ((intent.getExtras()) != null) {
			Bundle data = intent.getExtras();
			owneruser = (User) data.getSerializable("owneruser");
			dot_x=intent.getDoubleExtra("dot1", 0);
			dot_y=intent.getDoubleExtra("dot2", 0);
			Log.d("owneruser", owneruser.toString());
		}
		pwOption = new OptionsPopupWindow(this);
		
		initOptions();
		initView();
		initListener();
		initActionBar();
		
	}
   
	private void initOptions() {
		// TODO 自动生成的方法存根
		Jbex_label.add("学习");
		Jbex_label.add("吃饭");
		Jbex_label.add("KTV");
		Jbex_label.add("散步");
		Jbex_label.add("篮球");
		Jbex_label.add("足球");
		Jbex_label.add("运动");
		Jbex_label.add("跑步");
		Jbex_label.add("健身");
		Jbex_label.add("奶茶");
	}

	private void publishJbex() {
		// TODO 自动生成的方法存根
		 str_title = ev_title.getText().toString();
		 str_details = ev_details.getText().toString();
		 label=jbex_label.getText().toString();
		 
		 addjbexinfoAsyncTask a=new addjbexinfoAsyncTask();
	     a.execute();
	}
	
	private void initActionBar() {
		// TODO 自动生成的方法存根
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
				publishJbex();
			}

		});
		ImageButton back = (ImageButton) view.findViewById(R.id.btn_back);
		TextView backtxt=(TextView) view.findViewById(R.id.backtxt);
		back.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(JbexEditActivity.this, SelectDot.class);
				JbexEditActivity.this.startActivity(intent);
				JbexEditActivity.this.finish();
			}
		});
		
		backtxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				JbexEditActivity.this.finish();
			}
		});
		
		txt = (TextView) view.findViewById(R.id.main_Text);
		rightButton.setBackgroundResource(R.drawable.btn_public);
		txt.setText("附近消息");
	}

	private void initListener() {
		// TODO 自动生成的方法存根
		publicinfo1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				imageName="jbex_"+owneruser.getUser_id()+"_1.png";
				setImagePhoto();
			}

		});

		publicinfo2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				imageName="jbex_"+owneruser.getUser_id()+"_2.png";
				setImagePhoto();
			}
		});

		timeText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				setPicker();
			}

		});

		timepicker.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker arg0, int arg1, int arg2) {
				// TODO 自动生成的方法存根
				iHour=arg0.getCurrentHour();
				iMinute=arg0.getCurrentMinute();
			}
		});
		
		LabelRelative.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				pwOption.setPicker(Jbex_label);
				pwOption.setLabels("标签");
				// 设置默认选中的三级项目
				pwOption.setSelectOptions(0, 0, 0);
				pwOption.setOnoptionsSelectListener(new OnOptionsSelectListener() {
					
					@Override
					public void onOptionsSelect(int options1, int option2, int options3) {
						// TODO 自动生成的方法存根
						jbex_label.setText(Jbex_label.get(options1));
					}
				});
				pwOption.showAtLocation(arg0, Gravity.BOTTOM, 0, 0);
			}
		});
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
                        Uri.fromFile(new File("/sdcard/JBEX/Cache/jbexinfoimages", imageName)));
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
	

	private void setPicker() {
		// TODO 自动生成的方法存根
		picker = new DatePickerDialog(JbexEditActivity.this, DatePickerListener,
				iYear, iMonth, iDay);
        picker.setCancelable(true);
        picker.setCanceledOnTouchOutside(true);
        picker.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Picker", "Correct behavior!");
                        iYear=picker.getDatePicker().getYear();
                        iMonth=picker.getDatePicker().getMonth();
                        iDay=picker.getDatePicker().getDayOfMonth();
                        timeText.setText(iYear +"年"+(iMonth+1)+"月"+ iDay+"日");
                    }
                });
        picker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Picker", "Cancel!");
                        picker.dismiss();
                    }
                });
        picker.show();
	}
	private void initView() {
		// TODO 自动生成的方法存根
		final Calendar objTime = Calendar.getInstance();
        iYear = objTime.get(Calendar.YEAR);
        iMonth = objTime.get(Calendar.MONTH);
        iDay = objTime.get(Calendar.DAY_OF_MONTH);
        iHour=timepicker.getCurrentHour();
	     iMinute=timepicker.getCurrentMinute();
		 timeText.setText(iYear +"年"+(iMonth+1)+"月"+ iDay+"日");	
	}
  
	
	@SuppressLint("SdCardPath")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		if (resultCode == RESULT_OK) {
			switch(requestCode){
			 case PHOTO_REQUEST_TAKEPHOTO:

	             startPhotoZoom(
	                     Uri.fromFile(new File("/sdcard/JBEX/Cache/jbexinfoimages", imageName)),
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
	             Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/JBEX/Cache/jbexinfoimages/"
	                     + imageName);
	             if(imageName.equals("jbex_"+owneruser.getUser_id()+"_1.png"))
	             publicinfo1.setImageBitmap(bitmap);
	             else 
	              publicinfo2.setImageBitmap(bitmap);
	             //UpLoadAsyncTask upload=new UpLoadAsyncTask();
	             //upload.execute();
	             break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
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
	                Uri.fromFile(new File("/sdcard/JBEX/Cache/jbexinfoimages", imageName)));
	        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
	        intent.putExtra("noFaceDetection", true); // no face detection
	        startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}
   
	@SuppressLint("SimpleDateFormat")
	public class addjbexinfoAsyncTask extends AsyncTask<Integer, Integer, String>{
	     private File file1;
	     private File file2;
		//后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@SuppressWarnings({ "deprecation", "unused" })
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根
				String flag;
				Date date=new Date(iYear-1900, iMonth, iDay, iHour, iMinute);
				flag=JbexInfoService.addJbexinfo(owneruser,dot_x,dot_y,str_title,str_details,date,label);
				
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String bir=sdf.format(date);
				Timestamp time=Timestamp.valueOf(bir);
				JbexInfo jbexinfo=new JbexInfo();
				jbexinfo.setId(Long.valueOf(flag));
				jbexinfo.setDotX(dot_x);
				jbexinfo.setDotY(dot_y);
				jbexinfo.setLabel(label);
				jbexinfo.setTitle(str_title);
				jbexinfo.setDetail(str_details);
				jbexinfo.setTime(time);
				jbexinfo.setTUser(owneruser);
				
				String actionUrl=HttpUtil.BASE_URL+"servlet/jbexinfoUpLoadServlet";
				file1=new File("/sdcard/JBEX/Cache/jbexinfoimages/"+"jbex_"+owneruser.getUser_id()+"_1.png");
			    file2=new File("/sdcard/JBEX/Cache/jbexinfoimages/"+"jbex_"+owneruser.getUser_id()+"_2.png");
				if(file1.exists())
				{
					jbexinfo.setPicture1("jbex_"+flag+"_1.png");
					//publicinfo.setPicture2("null");
			        UpLoadUtil.uploadFile("/sdcard/JBEX/Cache/jbexinfoimages/"
		                 +"jbex_"+ owneruser.getUser_id()+"_1.png","jbex_"+ flag+"_1.png",actionUrl); 
			        JbexInfoService.setJbexInfo(jbexinfo);
				}
				else{
					jbexinfo.setPicture1("null");
				}
				 if(file2.exists()){
					 jbexinfo.setPicture2("jbex_"+flag+"_2.png");
					// publicinfo.setPicture1("null");
					UpLoadUtil.uploadFile("/sdcard/JBEX/Cache/jbexinfoimages/"
			                 +"jbex_"+ owneruser.getUser_id()+"_2.png","jbex_"+ flag+"_2.png",actionUrl);
					JbexInfoService.setJbexInfo(jbexinfo);
				 }
				 else{
					 jbexinfo.setPicture2("null");
				 }
				 
				 JbexInfoService.setJbexInfo(jbexinfo);
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
				 
				Toast.makeText(JbexEditActivity.this, text, Toast.LENGTH_SHORT).show();
				
				Intent intent = new Intent(JbexEditActivity.this, JbexActivity.class);
				Bundle data = new Bundle();
				data.putSerializable("owneruser", owneruser);
				intent.putExtras(data);
				JbexEditActivity.this.startActivityForResult(intent, 222);
				JbexEditActivity.this.finish();
				
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
					progressDialog = CustomProgressDialog.createDialog(JbexEditActivity.this);
			    	progressDialog.setMessage("正在加载中...");
				}
				
		    	progressDialog.show();
			}
			
		}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jbex_edit, menu);
		return true;
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
