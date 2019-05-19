package com.base.jbex;

import java.util.ArrayList;
import java.util.List;

import com.base.jbex.JbexActivity.setAttentionDataAsyncTask;
import com.base.jbex.JbexActivity.setDataAsyncTask;
import com.base.jbex.JbexActivity.setFriendDataAsyncTask;
import com.basic.Activities.R;
import com.basic.Activities.ZJBEXBaseActivity;
import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageOptions;
import com.basic.ImageLoad.ImageStringUtil;
import com.basic.connectservice.JbexInfoService;
import com.basic.connectservice.PublicInfoService;
import com.basic.connectservice.dynamicInfoService;
import com.basic.service.model.DynamicInfo;
import com.basic.service.model.JbexInfo;
import com.basic.service.model.PublicInfo;
import com.basic.service.model.User;
import com.basic.ui.CustomProgressDialog;
import com.mapgis.model.DynamciInfoAnnotation;
import com.mapgis.model.JbexInfoAnnotation;
import com.mapgis.model.PublicInfoAnnotation;
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

public class DT_index extends ZJBEXBaseActivity implements MapViewAnnotationListener,MapViewLongTapListener, MapViewTapListener,MapViewRenderContextListener{
    
	private static int mdynamicuserifoID=0;
	private static int mdynamicuserifo_friend=1;
	private static int mdynamicuserifo_attention=2;
	
	private Intent intent;
	private User owneruser=new User();
	
	private ActionBar actionBar = null;
	private MapView mapView;
	private View mContents;

	AnnotationView annotationView = null;
	Annotation annotationRecord = null;
	
	  private List<DynamicInfo> mdynamicinfoList = new ArrayList<DynamicInfo>();
	   private List<DynamicInfo> mdynamicinfoList_friend = new ArrayList<DynamicInfo>();
	   private List<DynamicInfo> mdynamicinfoList_attention = new ArrayList<DynamicInfo>();
	private CustomProgressDialog progressDialog = null;
	
	//图片加载框架组件
	private DisplayImageOptions options;
	private ImageLoader mImageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	private String path = Environment.getExternalStorageDirectory().getPath();

	TextView txt = null; // actionbar的中间的字
	
	// ------------下拉---------------
	private PopupWindow popupWindow;
	private ListView lv_group;
	private View view;
	private List<String> groups;
	// ------------下拉---------------
	
	private Bitmap DynamicBitmap;             //动态标签
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dt_index);
		
		intent=getIntent();
		if ((intent.getExtras()) != null) {
			Bundle data = intent.getExtras();
			owneruser = (User) data.getSerializable("owneruser");
			Log.d("owneruser", owneruser.toString());
		}
		
		initDisplayOption();
		initActionBar(); // 加载改变actionBar
		initAnnotation();
		
		mapView = (MapView) super.findViewById(R.id.mapview5);
		mapView.loadFromFile(path + "/mapgis/map/wuhan/wuhan.xml"); // 加载地图
		mapView.setRenderContextListener(DT_index.this); // 注册四个监听
		mapView.setAnnotationListener(DT_index.this);
		mapView.setLongTapListener(DT_index.this);
		mapView.setTapListener(DT_index.this);
		
		setDataAsyncTask a=new setDataAsyncTask();
		a.execute();
		
	}

	private void initAnnotation() {
		// TODO Auto-generated method stub
		DynamicBitmap=((BitmapDrawable) getResources().getDrawable(R.drawable.dt_annotation)).getBitmap();
	}

	private void initDisplayOption() {
		// TODO 自动生成的方法存根
		options=ImageOptions.initDisplayOptions();
		mImageLoader = ImageLoader.getInstance();
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
			groups.add("附近动态");
			groups.add("我的好友");
			groups.add("我的关注");

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

		mapView.getAnnotationLayer().removeAllAnnotations();
		mapView.refresh();

		// 下拉菜单item点击事件监听======================================
		lv_group.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				//				String str = String.valueOf(id);
				int id_1 = (int) id;
				switch (id_1) {
				case 0:
					txt.setText("附近动态");
					if(mdynamicinfoList.size()==0)
					{
						setDataAsyncTask a=new setDataAsyncTask();
						a.execute();
					}
					else
					drawAnnotations(0);	
					break;

				case 1:
					txt.setText("我的好友");
					if(mdynamicinfoList_friend.size()==0)
					{
						setFriendDataAsyncTask a=new setFriendDataAsyncTask();
						a.execute();
					}
					else
						drawAnnotations(1);	
					break;

				case 2:
					txt.setText("我的关注");
					if(mdynamicinfoList_attention.size()==0)
					{
						setAttentionDataAsyncTask a=new setAttentionDataAsyncTask();
						a.execute();
					}
					else
						drawAnnotations(2);	
					break;

				default:
					Toast.makeText(DT_index.this, "未知错误", Toast.LENGTH_LONG).show();
					break;
				}
				mapView.refresh();
				if (popupWindow != null)
					popupWindow.dismiss();
			}
		});
	}

	/**
	 *  加载改变actionBar
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
		
		rightButton.setBackgroundResource(R.drawable.publish);
		
		ImageButton ibtn = (ImageButton) view.findViewById(R.id.btn_nnavigation);
		ibtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//------------------------------
				Intent intent = new Intent(DT_index.this, DT_seletedot.class);
				
				Bundle data = new Bundle();
				data.putSerializable("owneruser", owneruser);
				intent.putExtras(data);
				
				DT_index.this.startActivity(intent);
				DT_index.this.finish();
				Toast.makeText(DT_index.this, "点击进入下一个页面" , Toast.LENGTH_LONG).show();
			}
		});
		txt.setText("附近动态");
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
				DT_index.this.finish();
			}
		});
      
		backtxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				DT_index.this.finish();
			}
		});
	}

	
	
	/**
	 * 绘制函数
	 * @param type
	 */
	public void drawAnnotations(int  type) {
      mapView.getAnnotationLayer().removeAllAnnotations();
		
		String JBtitle, JBusername, JBtime;
		Dot JBposition = null ;
		String JBuserPicture;

		//List<JbexInfoAnnotation> annotationList=new ArrayList<JbexInfoAnnotation>();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
			switch (type) {
			case 0:
				for(int i=0;i<mdynamicinfoList.size();i++){
					JBtitle=mdynamicinfoList.get(i).getDetail();
					JBusername=mdynamicinfoList.get(i).getTUser().getUser_nickname();
					JBtime=sdf.format(mdynamicinfoList.get(i).getTime());
					JBuserPicture=mdynamicinfoList.get(i).getTUser().getPicture();
					JBposition=mapView.locationToMapPoint(new Dot(mdynamicinfoList.get(i).getDotX(), mdynamicinfoList.get(i).getDotY()));
					
					DynamciInfoAnnotation annotation=new DynamciInfoAnnotation(JBusername, JBtitle, JBtime,JBposition, DynamicBitmap);
					annotation.setJBuserPicture(JBuserPicture);
					annotation.setMdynamciuserifoID(mdynamicuserifoID);
					annotation.setWhich(i);
					
					mapView.getAnnotationLayer().addAnnotation(annotation);
				}
				break;
				
            case 1:
            	for(int i=0;i<mdynamicinfoList_friend.size();i++){
					JBtitle=mdynamicinfoList_friend.get(i).getDetail();
					JBusername=mdynamicinfoList_friend.get(i).getTUser().getUser_nickname();
					JBtime=sdf.format(mdynamicinfoList_friend.get(i).getTime());
					JBuserPicture=mdynamicinfoList_friend.get(i).getTUser().getPicture();
					JBposition=mapView.locationToMapPoint(new Dot(mdynamicinfoList_friend.get(i).getDotX(), mdynamicinfoList_friend.get(i).getDotY()));
					
					DynamciInfoAnnotation annotation=new DynamciInfoAnnotation(JBusername, JBtitle, JBtime,JBposition, DynamicBitmap);
					annotation.setJBuserPicture(JBuserPicture);
					annotation.setMdynamciuserifoID(mdynamicuserifo_friend);
					annotation.setWhich(i);
					
					mapView.getAnnotationLayer().addAnnotation(annotation);
				}
				break;
           case 2:
        	   for(int i=0;i<mdynamicinfoList_attention.size();i++){
					JBtitle=mdynamicinfoList_attention.get(i).getDetail();
					JBusername=mdynamicinfoList_attention.get(i).getTUser().getUser_nickname();
					JBtime=sdf.format(mdynamicinfoList_attention.get(i).getTime());
					JBuserPicture=mdynamicinfoList_attention.get(i).getTUser().getPicture();
					JBposition=mapView.locationToMapPoint(new Dot(mdynamicinfoList_attention.get(i).getDotX(), mdynamicinfoList_attention.get(i).getDotY()));
					
					DynamciInfoAnnotation annotation=new DynamciInfoAnnotation(JBusername, JBtitle, JBtime,JBposition, DynamicBitmap);
					annotation.setJBuserPicture(JBuserPicture);
					annotation.setMdynamciuserifoID(mdynamicuserifo_attention);
					annotation.setWhich(i);
					
					mapView.getAnnotationLayer().addAnnotation(annotation);
				}
				break;
			default:
				break;
			} 
			
			mapView.refresh();
	}
	
	
	
	
	@Override
	public void mapViewRenderContextCreated() {
		// TODO 自动生成的方法存根
		mapView.setShowUserLocation(true);
		mapView.zoomTo(4.0f, false);
	}

	@Override
	public void mapViewRenderContextDestroyed() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mapViewTap(PointF arg0) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public boolean mapViewLongTap(PointF arg0) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public void mapViewClickAnnotation(MapView arg0, Annotation arg1) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mapViewClickAnnotationView(MapView arg0, AnnotationView arg1) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public AnnotationView mapViewViewForAnnotation(MapView mapView,
			Annotation annotation) {
		
		DynamciInfoAnnotation dt_annotations=(DynamciInfoAnnotation)annotation;
		
		annotationView = new AnnotationView(annotation, this);
		mContents = getLayoutInflater().inflate(R.layout.dt_annotationview, null);
		annotationView.setCalloutContentView(mContents);
		
		final int Id=dt_annotations.getMdynamciuserifoID();
		final int which=dt_annotations.getWhich();
		
		Button detail_button=(Button) mContents.findViewById(R.id.btn_dt_detail);
        ImageView userimage=(ImageView) mContents.findViewById(R.id.dt_userimage);
        TextView  username=(TextView) mContents.findViewById(R.id.dt_ann_username);
        TextView  details=(TextView) mContents.findViewById(R.id.dt_details);
        
        mImageLoader.displayImage(ImageStringUtil.getImageURL(dt_annotations.getJBuserPicture()), userimage, options, animateFirstListener);
        username.setText(annotation.getUid());
        details.setText(annotation.getTitle());
        
		detail_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(DT_index.this, DT_details.class);
				Bundle data = new Bundle();
				data.putSerializable("owneruser", owneruser);
				switch (Id) {
				case 0:
					data.putSerializable("dynamicinfo", mdynamicinfoList.get(which));
					break;

				case 1:
					data.putSerializable("dynamicinfo", mdynamicinfoList_friend.get(which));
					break;
				case 2:
					data.putSerializable("dynamicinfo", mdynamicinfoList_attention.get(which));
					break;
				default:
					break;
				}
				intent.putExtras(data);
				DT_index.this.startActivity(intent);
			}
		});

		// 将annotationview平移到视图中心
		annotationView.setPanToMapViewCenter(true);
		return annotationView;
	}


	@Override
	public boolean mapViewWillHideAnnotationView(MapView arg0,
			AnnotationView arg1) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public boolean mapViewWillShowAnnotationView(MapView arg0,
			AnnotationView arg1) {
		// TODO 自动生成的方法存根
		return false;
	}

	public class setDataAsyncTask extends AsyncTask<Integer, Integer, String>{
	     
	     //后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根
				
				mdynamicinfoList=dynamicInfoService.getdynamicInfoList();
				return "success";
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				super.onPostExecute(result);
				
				drawAnnotations(0);
				
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
					progressDialog = CustomProgressDialog.createDialog(DT_index.this);
			    	progressDialog.setMessage("正在加载中...");
				}
				
		    	progressDialog.show();
			}
			
		}
	
	public class setFriendDataAsyncTask extends AsyncTask<Integer, Integer, String>{
	     
	     //后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根
				mdynamicinfoList_friend=dynamicInfoService.getFriendynamicInfoList(String.valueOf(owneruser.getUser_id()));
				return "success";
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				super.onPostExecute(result);
				
				drawAnnotations(1);
				
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
					progressDialog = CustomProgressDialog.createDialog(DT_index.this);
			    	progressDialog.setMessage("正在加载中...");
				}
				
		    	progressDialog.show();
			}
			
		}
	
	public class setAttentionDataAsyncTask extends AsyncTask<Integer, Integer, String>{
	     
	     //后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根
				mdynamicinfoList_attention=dynamicInfoService.getAttentionynamicInfoList(String.valueOf(owneruser.getUser_id()));
				return "success";
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				super.onPostExecute(result);
				
				drawAnnotations(2);
				
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
					progressDialog = CustomProgressDialog.createDialog(DT_index.this);
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
