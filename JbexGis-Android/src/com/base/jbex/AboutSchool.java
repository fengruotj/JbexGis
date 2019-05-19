package com.base.jbex;

import java.util.ArrayList;
import java.util.List;

import com.basic.Activities.MyattentionActivity;
import com.basic.Activities.R;
import com.basic.Activities.R.layout;
import com.basic.Activities.R.menu;
import com.basic.Activities.ZJBEXBaseActivity;
import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageOptions;
import com.basic.ImageLoad.ImageStringUtil;
import com.basic.connectservice.AttentionService;
import com.basic.connectservice.PublicInfoService;
import com.basic.service.model.PublicInfo;
import com.basic.service.model.User;
import com.basic.ui.CustomProgressDialog;
import com.base.jbex.AboutSchool;
import com.base.jbex.GroupAdapter;
import com.base.jbex.Details;
import com.mapgis.model.PublicInfoAnnotation;
//import com.base.jbex.Publish;
import com.mapgis.model.PublicUserIfo;
import com.message.net.ChatMessage;
import com.message.net.Config;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zondy.mapgis.android.annotation.Annotation;
import com.zondy.mapgis.android.annotation.AnnotationLayer;
import com.zondy.mapgis.android.annotation.AnnotationView;
import com.zondy.mapgis.android.mapview.MapView;
import com.zondy.mapgis.android.mapview.MapView.MapViewAnnotationListener;
import com.zondy.mapgis.android.mapview.MapView.MapViewLongTapListener;
import com.zondy.mapgis.android.mapview.MapView.MapViewRenderContextListener;
import com.zondy.mapgis.android.mapview.MapView.MapViewTapListener;
import com.zondy.mapgis.geometry.Dot;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("SimpleDateFormat")
public class AboutSchool extends ZJBEXBaseActivity  implements MapViewAnnotationListener,MapViewLongTapListener, MapViewTapListener,MapViewRenderContextListener 
{
     
	private static int mpoublicuserifoID=0;
	private static int mpoublicuserifo_gfID=1;
	private static int mpoublicuserifo_hdID=2;
	
	private Intent intent;
	private User owneruser=new User();
	
	private ActionBar actionBar = null;
	private MapView mapView;
	private View mContents;

	AnnotationView annotationView = null;
	Annotation annotationRecord = null;
	
	private List<PublicInfo> MainpublicinfoList=new ArrayList<PublicInfo>();
	private CustomProgressDialog progressDialog = null;
	
	//图片加载框架组件
	private DisplayImageOptions options;
	private ImageLoader mImageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	private String path = Environment.getExternalStorageDirectory().getPath();
	private ArrayList<PublicUserIfo> mpoublicuserifo = new ArrayList<PublicUserIfo>();
	private ArrayList<PublicUserIfo> mpoublicuserifo_gf = new ArrayList<PublicUserIfo>();
	private ArrayList<PublicUserIfo> mpoublicuserifo_hd = new ArrayList<PublicUserIfo>();
	TextView txt = null; // actionbar的中间的字
	// ------------下拉---------------
	private PopupWindow popupWindow;
	private ListView lv_group;
	private View view;
	private List<String> groups;
	// ------------下拉---------------
	private Bitmap school_my ;
	private Bitmap school_org ;
	private Bitmap social_my ;
	private Bitmap social_org ;
	
	/**
	 * 构造函数
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_school);
		
		intent=getIntent();
		if ((intent.getExtras()) != null) {
			Bundle data = intent.getExtras();
			owneruser = (User) data.getSerializable("owneruser");
			Log.d("owneruser", owneruser.toString());
		}
		
		initActionBar(); // 加载改变actionBar
		initDisplayOption();
		
		mapView = (MapView) super.findViewById(R.id.mapview1);
		mapView.loadFromFile(path + "/mapgis/map/wuhan/wuhan.xml"); // 加载地图
		mapView.setRenderContextListener(AboutSchool.this); // 注册四个监听
		mapView.setAnnotationListener(AboutSchool.this);
		mapView.setLongTapListener(AboutSchool.this);
		mapView.setTapListener(AboutSchool.this);
		
		setDataAsyncTask a=new setDataAsyncTask();
		a.execute();
	}

	
	private void initDisplayOption() {
		// TODO 自动生成的方法存根
		options=ImageOptions.initDisplayOptions();
		mImageLoader = ImageLoader.getInstance();
	}
	
	/**
	 * 加载改变actionBar
	 */
	private void initActionBar() {
		// TODO 自动生成的方法存根
		actionBar = getActionBar();
		actionBar.setCustomView(R.layout.session_top);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayShowCustomEnabled(true);
		View view = actionBar.getCustomView();
		//ImageButton rightButton = (ImageButton) view.findViewById(R.id.btn_topright);
		ImageButton rightButton = (ImageButton) view.findViewById(R.id.btn_nnavigation);
		ImageButton back = (ImageButton) view.findViewById(R.id.btn_back);
		txt = (TextView) view.findViewById(R.id.main_Text);
		TextView backtxt=(TextView) view.findViewById(R.id.backtxt);
		
		if(owneruser.getSecurityControl()!=0)
		rightButton.setBackgroundResource(R.drawable.publish);
		else
			rightButton.setBackgroundResource(0);
		
		ImageButton ibtn = (ImageButton) view.findViewById(R.id.btn_nnavigation);
		ibtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AboutSchool.this, SelectDot.class);
				
				Bundle data = new Bundle();
				data.putSerializable("owneruser", owneruser);
				intent.putExtras(data);
				
				AboutSchool.this.startActivity(intent);
				AboutSchool.this.finish();
				//Toast.makeText(AboutSchool.this, "点击进入下一个页面" , Toast.LENGTH_LONG).show();
			}
		});
		txt.setText("社团和校园");
		txt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View view1 = findViewById(R.id.title_tt);
				showWindow(view1);                                    // popuWindow 下拉菜单显示
			}
		});

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				AboutSchool.this.finish();
			}
		});
      
		backtxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				AboutSchool.this.finish();
			}
		});
	}



	/**
	 * 弹出下拉菜单及菜单item的响应事件
	 */
	private void showWindow(View parent) {

		if (popupWindow == null) {
			/**
			 * 取得Xml中的view
			 */
//			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.group_list, null);        //加载自定义样式
			lv_group = (ListView) view.findViewById(R.id.lvGroup);
			groups = new ArrayList<String>();
			groups.add("附近消息");
			groups.add("校园官方");
			groups.add("校园活动");

			GroupAdapter groupAdapter = new GroupAdapter(this, groups);
			lv_group.setAdapter(groupAdapter);
			popupWindow = new PopupWindow(view, 300, 350); // 创建一个PopuWidow对象
		}
		popupWindow.setFocusable(true); // 使其聚集
				popupWindow.setOutsideTouchable(true); // 设置允许在外点击消失
				popupWindow.setBackgroundDrawable(new BitmapDrawable()); // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景

		WindowManager windowManager1 = getWindowManager();
		int xPos = windowManager1.getDefaultDisplay().getWidth() / 2
				- popupWindow.getWidth() / 2;
		Log.i("coder", "xPos:" + xPos);
		popupWindow.showAsDropDown(parent, xPos, 5);

		

		// 下拉菜单item点击事件监听======================================
		lv_group.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				//				String str = String.valueOf(id);
				int id_1 = (int) id;
				switch (id_1) {
				case 0:
					txt.setText("附近消息");
					drawAnnotations("0");
					break;

				case 1:
					txt.setText("校园官方");
					drawAnnotations("1");	
					break;

				case 2:
					txt.setText("校园活动");
					drawAnnotations("2");	
					break;
					
				default:
					Toast.makeText(AboutSchool.this, "未知错误", Toast.LENGTH_LONG).show();
					break;
				}
				mapView.refresh();
				if (popupWindow != null)
					popupWindow.dismiss();
			}
		});
	}


	public void setData()
	{
		mpoublicuserifo=new ArrayList<PublicUserIfo>();
		
		school_my = ((BitmapDrawable) getResources().getDrawable(R.drawable.school_my_annotation)).getBitmap();
		school_org = ((BitmapDrawable) getResources().getDrawable(R.drawable.school_org_annotation)).getBitmap();
		social_my = ((BitmapDrawable) getResources().getDrawable(R.drawable.social_my_annotation)).getBitmap();
		social_org = ((BitmapDrawable) getResources().getDrawable(R.drawable.social_org_annotation)).getBitmap();
		
		for(int i=0;i<MainpublicinfoList.size();i++){
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String type1="";
			
			switch (MainpublicinfoList.get(i).getTUser().getSecurityControl()) {
			case 1:
				type1="1";
				break;
			case 2:
				type1="0";
				break;
			default:
				break;
			}		
			PublicUserIfo publicinfo=new PublicUserIfo();
			publicinfo.setDetail(MainpublicinfoList.get(i).getDetail());
			publicinfo.setFavicon(MainpublicinfoList.get(i).getTUser().getPicture());
			publicinfo.setLabel(MainpublicinfoList.get(i).getLabel());
			publicinfo.setPicture1(MainpublicinfoList.get(i).getPicture1());
			publicinfo.setPicture2(MainpublicinfoList.get(i).getPicture2());
			publicinfo.setPosition(new Dot(MainpublicinfoList.get(i).getDotX(),MainpublicinfoList.get(i).getDotY()));
			publicinfo.setTime(sdf.format(MainpublicinfoList.get(i).getTime()));
			publicinfo.setTitle(MainpublicinfoList.get(i).getTitle());
			publicinfo.setType1(type1);
			publicinfo.setUsername(MainpublicinfoList.get(i).getTUser().getUser_nickname());
			mpoublicuserifo.add(publicinfo);
			
			for(int j = 0 ;j <mpoublicuserifo.size();j++)
			{
				if(mpoublicuserifo.get(j).getType1().equals("0"))
					mpoublicuserifo_gf.add(mpoublicuserifo.get(j));
				else if(mpoublicuserifo.get(j).getType1().equals("1"))
					mpoublicuserifo_hd.add(mpoublicuserifo.get(j));
			}
			
		}
	}

	
	
	
	
	/**
	 * 绘制函数
	 * @param type
	 */
	public void drawAnnotations(String type) {
		
		mapView.getAnnotationLayer().removeAllAnnotations();
		
		String JBtitle = null, JBusername, JBtime = null;
		Dot JBposition = null;
		String JBuserPicture="";
		PublicUserIfo publicuserinfo=new PublicUserIfo();
		
		int ii = Integer.parseInt(type);
		switch (ii) {
		case 0:
			for (int i = 0; i <mpoublicuserifo.size() ; i++) {
				publicuserinfo=mpoublicuserifo.get(i);
				JBposition = mapView.locationToMapPoint(publicuserinfo.getPosition());
				JBtitle = publicuserinfo.getTitle();
				JBtime = publicuserinfo.getTime();
				JBusername = publicuserinfo.getUsername();
				JBuserPicture=publicuserinfo.getFavicon();
				PublicInfoAnnotation annotation = null;
                if(publicuserinfo.getType1().equals("0")){ 
                	if(publicuserinfo.getUsername().equals(owneruser.getUser_nickname()))
				    annotation =new PublicInfoAnnotation(JBusername, JBtitle, JBtime,JBposition, school_my);
                	else
                    annotation =new PublicInfoAnnotation(JBusername, JBtitle, JBtime,JBposition, school_org);
                }
                else if(publicuserinfo.getType1().equals("1"))
                {
                	if(publicuserinfo.getUsername().equals(owneruser.getUser_nickname()))
    				    annotation =new PublicInfoAnnotation(JBusername, JBtitle, JBtime,JBposition, social_my);
                    	else
                        annotation =new PublicInfoAnnotation(JBusername, JBtitle, JBtime,JBposition, social_org);
                }
				annotation.setJBuserPicture(JBuserPicture);
				annotation.setMpoublicuserifoID(mpoublicuserifoID);
				annotation.setWhich(i);
					mapView.getAnnotationLayer().addAnnotation(annotation);

//				}
			}
			break;

		case 1 :
			for (int i = 0; i < mpoublicuserifo_gf.size(); i++) {
				publicuserinfo=mpoublicuserifo_gf.get(i);
				JBposition = mapView.locationToMapPoint(publicuserinfo.getPosition());
				JBtitle = publicuserinfo.getTitle();
				JBtime = publicuserinfo.getTime();
				JBusername = publicuserinfo.getUsername();
				JBuserPicture=publicuserinfo.getFavicon();

				PublicInfoAnnotation annotation;

				if(publicuserinfo.getUsername().equals(owneruser.getUser_nickname()))
					annotation =new PublicInfoAnnotation(JBusername, JBtitle, JBtime,JBposition, school_my);
				else
					annotation =new PublicInfoAnnotation(JBusername, JBtitle, JBtime,JBposition, school_org);

				annotation.setJBuserPicture(JBuserPicture);
				annotation.setMpoublicuserifoID(mpoublicuserifo_gfID);
				annotation.setWhich(i);
					mapView.getAnnotationLayer().addAnnotation(annotation);

			}
			break;

		case 2 :
			for (int i = 0; i < mpoublicuserifo_hd.size(); i++) {
				publicuserinfo=mpoublicuserifo_hd.get(i);
				JBposition = mapView.locationToMapPoint(publicuserinfo.getPosition());
				JBtitle = publicuserinfo.getTitle();
				JBtime = publicuserinfo.getTime();
				JBusername = publicuserinfo.getUsername();
				JBuserPicture=publicuserinfo.getFavicon();
				
				PublicInfoAnnotation annotation;
				if(publicuserinfo.getUsername().equals(owneruser.getUser_nickname()))
				    annotation =new PublicInfoAnnotation(JBusername, JBtitle, JBtime,JBposition, social_my);
                	else
                    annotation =new PublicInfoAnnotation(JBusername, JBtitle, JBtime,JBposition, social_org);
				
				annotation.setJBuserPicture(JBuserPicture);
				annotation.setMpoublicuserifoID(mpoublicuserifo_hdID);
				annotation.setWhich(i);
					mapView.getAnnotationLayer().addAnnotation(annotation);
			}
			break;
		default:

			break;
		}
	}
	
	@Override
	public void mapViewRenderContextCreated() {
		// TODO Auto-generated method stub
	    mapView.setShowUserLocation(true);
	    mapView.zoomTo(4.0f, false);
	}

	@Override
	public void mapViewRenderContextDestroyed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mapViewTap(PointF arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean mapViewLongTap(PointF arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void mapViewClickAnnotation(MapView arg0, Annotation arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mapViewClickAnnotationView(MapView arg0, AnnotationView arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AnnotationView mapViewViewForAnnotation(MapView mapView,
			Annotation annotation) {
		
		// TODO Auto-generated method stub
		PublicInfoAnnotation publicannotation=(PublicInfoAnnotation) annotation;
		final int Id=publicannotation.getMpoublicuserifoID();
		final int which=publicannotation.getWhich();
		
		annotationView = new AnnotationView(annotation, this);
		// 加载自定义callout的样式
		mContents = getLayoutInflater().inflate(
				R.layout.custom_calloutcontentview, null);
		annotationView.setCalloutContentView(mContents);

		String strTitle = " 标题： " + annotation.getTitle(); // 获取标签title
		TextView title = ((TextView) mContents.findViewById(R.id.title1));
		title.setText(strTitle);

		String strDesciption = " 时间： " + annotation.getDescription(); // 获取标签的的描述，在这里将其作为结伴时间
		TextView snippet = ((TextView) mContents.findViewById(R.id.snippet1));
		snippet.setText(strDesciption);

		String struid = annotation.getUid();
		TextView JBusername = (TextView) mContents.findViewById(R.id.public_username); // 获取标签的的UID，在这里将其作为结伴username
		JBusername.setText(struid);

		ImageView userimage=(ImageView) mContents.findViewById(R.id.icn);
		mImageLoader.displayImage(ImageStringUtil.getImageURL(publicannotation.getJBuserPicture()), userimage, options, animateFirstListener);
		
		Button detail_button=(Button) mContents.findViewById(R.id.btn_AS1_detail);
		
		detail_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(AboutSchool.this, Details.class);
				Bundle data = new Bundle();
				switch (Id) {
				case 0:
					data.putSerializable("Mpoublicuserifo", mpoublicuserifo.get(which));
					break;

				case 1:
					data.putSerializable("Mpoublicuserifo", mpoublicuserifo_gf.get(which));
					break;
				case 2:
					data.putSerializable("Mpoublicuserifo", mpoublicuserifo_hd.get(which));
					break;
				default:
					break;
				}
				intent.putExtras(data);
				AboutSchool.this.startActivity(intent);
			}
		});
		
		AnnotationLayer annotationLayer = mapView.getAnnotationLayer();
		int index = annotationLayer.indexOf(annotation);
		annotationLayer.moveAnnotation(index, -1);
		//mapView.refresh();
		// 将annotationview平移到视图中心
		annotationView.setPanToMapViewCenter(true);
		return annotationView;
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		switch (requestCode) {
		case 222:
			setDataAsyncTask a=new setDataAsyncTask();
			a.execute();
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	public boolean mapViewWillHideAnnotationView(MapView arg0,
			AnnotationView arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mapViewWillShowAnnotationView(MapView arg0,
			AnnotationView arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public class setDataAsyncTask extends AsyncTask<Integer, Integer, String>{
	     
	     //后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根
				
				MainpublicinfoList=PublicInfoService.getPublicInfoList();
				
				return "success";
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				super.onPostExecute(result);
				
				setData();
				drawAnnotations("0");
				mapView.refresh();
				
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
					progressDialog = CustomProgressDialog.createDialog(AboutSchool.this);
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
