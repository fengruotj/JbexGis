package com.base.jbex;
import java.util.ArrayList;
import java.util.List;

import com.basic.Activities.R;
import com.basic.Activities.ZJBEXBaseActivity;
import com.basic.Activities.R.drawable;
import com.basic.Activities.R.id;
import com.basic.Activities.R.layout;
import com.basic.Activities.R.menu;
import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageOptions;
import com.basic.ImageLoad.ImageStringUtil;
import com.basic.connectservice.MyJbexRequestService;
import com.basic.service.model.JbexInfo;
import com.basic.service.model.MyJbexRequest;
import com.basic.service.model.User;
import com.basic.ui.CustomProgressDialog;
import com.mapgis.model.JbexInfoAnnotation;
import com.message.net.ChatMessage;
import com.message.net.Config;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zondy.mapgis.android.annotation.Annotation;
import com.zondy.mapgis.android.annotation.AnnotationLayer;
import com.zondy.mapgis.android.annotation.AnnotationView;
import com.zondy.mapgis.android.graphic.GraphicCircle;
import com.zondy.mapgis.android.mapview.MapView;
import com.zondy.mapgis.android.mapview.MapView.MapViewAnnotationListener;
import com.zondy.mapgis.android.mapview.MapView.MapViewRenderContextListener;
import com.zondy.mapgis.geometry.Dot;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class FootRoutActivity extends ZJBEXBaseActivity implements
MapViewAnnotationListener, MapViewRenderContextListener{
	private static int mjbexuserifoID=0;
	private User owneruser=new User();
	private Intent intent;
	private ActionBar actionBar ;
	
	private MapView mapView;
	private String path = Environment.getExternalStorageDirectory().getPath();
	private List<MyJbexRequest> mjbexinfoList = new ArrayList<MyJbexRequest>();
	
	private CustomProgressDialog progressDialog = null;
	private java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
	
	//图片加载框架组件
	private DisplayImageOptions options;
	private ImageLoader mImageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	private Bitmap studyBmp;             //学习标签
	private Bitmap eatBmp;                //吃饭标签
	private Bitmap KTVBmp;                //KTV标签
	private Bitmap walkBmp;                //散步标签
	private Bitmap basketballBmp;           // 篮球标签
	private Bitmap RunBmp;                  //跑步标签
	private Bitmap footballBmp;              //足球标签
	private Bitmap sportsBmp;               //运动标签
	private Bitmap bodyBmp;                 //健身标签
	private Bitmap milkBmp;                 //奶茶标签
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foot_rout);
	
		intent=getIntent();
		if ((intent.getExtras()) != null) {
			Bundle data = intent.getExtras();
			owneruser = (User) data.getSerializable("owneruser");
			Log.d("owneruser", owneruser.toString());
		}
		
	   initDisplayOption();
       initactionBar();
       initView();
       initBitMap();
       
       mapView = (MapView) super.findViewById(R.id.mapview);
	   mapView.loadFromFile(path + "/mapgis/map/wuhan/wuhan.xml"); // 加载地图
	   mapView.setRenderContextListener(FootRoutActivity.this); // 注册四个监听
	   mapView.setAnnotationListener(FootRoutActivity.this);
 
       setDataAsyncTask a=new setDataAsyncTask();
	   a.execute();
	   
	}

	private void initDisplayOption() {
		// TODO 自动生成的方法存根
		options=ImageOptions.initDisplayOptions();
		mImageLoader = ImageLoader.getInstance();
	}

	private void initBitMap() {
		// TODO 自动生成的方法存根
		studyBmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.annotation_study)).getBitmap();
		 eatBmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.eat_annotation)).getBitmap();
		 KTVBmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.ktv_annotation)).getBitmap();
		 walkBmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.run_annation)).getBitmap();
		 basketballBmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.basketball_annotation)).getBitmap();
		 RunBmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.run_annation)).getBitmap();
		 footballBmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.football_annotation)).getBitmap();
		 sportsBmp= ((BitmapDrawable) getResources().getDrawable(R.drawable.run_annation)).getBitmap();
		 bodyBmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.body_annotation)).getBitmap();
		 milkBmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.milk_annotation)).getBitmap();
	}

	private void initView() {
		// TODO 自动生成的方法存根
		
	}

	private void initactionBar() {
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

		nnavigation.setBackgroundResource(0);

		txt.setText("我的足迹");

		backtxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				FootRoutActivity.this.finish();
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				FootRoutActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.foot_rout, menu);
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
	public void mapViewClickAnnotation(MapView arg0, Annotation arg1) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mapViewClickAnnotationView(MapView arg0, AnnotationView arg1) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public AnnotationView mapViewViewForAnnotation(MapView arg0, Annotation annotation) {
		// TODO 自动生成的方法存根
	     View mContents;
		AnnotationView annotationView = null;
		JbexInfoAnnotation jbexannotation=(JbexInfoAnnotation) annotation;
		final int Id=jbexannotation.getMpoublicuserifoID();
		final int which=jbexannotation.getWhich();
		
		annotationView = new AnnotationView(annotation, this);
		// 加载自定义callout的样式
		mContents = getLayoutInflater().inflate(
				R.layout.jbexcontentview, null);
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
		mImageLoader.displayImage(ImageStringUtil.getImageURL(jbexannotation.getJBuserPicture()), userimage, options, animateFirstListener);
		
		Button detail_button=(Button) mContents.findViewById(R.id.btn_AS1_detail);
		
		detail_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(FootRoutActivity.this, JbexDetailsActivity.class);
				Bundle data = new Bundle();
				data.putSerializable("owneruser", owneruser);
				switch (Id) {
				case 0:
					data.putSerializable("jbexuserifo", mjbexinfoList.get(which).getJbexInfo());
					break;
				}
				intent.putExtras(data);
				FootRoutActivity.this.startActivity(intent);
			}
		});
		
		AnnotationLayer annotationLayer = mapView.getAnnotationLayer();
		int index = annotationLayer.indexOf(annotation);
		annotationLayer.moveAnnotation(index, -1);
		mapView.refresh();
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
	
	public void draw_cir(Dot dot,int size)
	{
		int level[]=new int[10];
		level[0]=Color.parseColor("#fcf161");
		level[1]=Color.parseColor("#f0dc70");
		level[2]=Color.parseColor("#cbc547");
		level[3]=Color.parseColor("#ffe600");
		level[4]=Color.parseColor("#ffd400");
		level[5]=Color.parseColor("#f8aba6");
		level[6]=Color.parseColor("#f69c9f");
		level[7]=Color.parseColor("#f58f98");
		level[8]=Color.parseColor("#f391a9");
		level[9]=Color.parseColor("#d71345");
		
		 int num=size/10;
	      double radius = (num+1) * 50;
	      GraphicCircle graphicCircle = new GraphicCircle(dot,radius);
	      if(num>=10)
	    	  graphicCircle.setColor(level[9]);
	      else
	    	  graphicCircle.setColor(level[num]);
	      graphicCircle.setBorderlineWidth(5);		
	      mapView.getGraphicLayer().addGraphic(graphicCircle);
		}
	
	
	 private void drawAnnotations() {
			// TODO 自动生成的方法存根
		 String JBtitle, JBusername, JBtime;
		 Dot JBposition = null ;
		 String JBuserPicture;
		 Bitmap bmp;
        for(int i=0;i<mjbexinfoList.size();i++){
        	JbexInfo jbexinfo=mjbexinfoList.get(i).getJbexInfo();
        	JBtitle=jbexinfo.getTitle();
			JBusername=jbexinfo.getTUser().getUser_nickname();
			JBtime=sdf.format(jbexinfo.getTime());
			JBuserPicture=jbexinfo.getTUser().getPicture();
			bmp=selectAnnotationByLabel(jbexinfo.getLabel());
			JBposition=mapView.locationToMapPoint(new Dot(jbexinfo.getDotX(), jbexinfo.getDotY()));
			
			JbexInfoAnnotation annotation=new JbexInfoAnnotation(JBusername, JBtitle, JBtime,JBposition, bmp);
			annotation.setJBuserPicture(JBuserPicture);
			annotation.setMpoublicuserifoID(mjbexuserifoID);
			annotation.setWhich(i);
			
			draw_cir(JBposition,jbexinfo.getSize());
			mapView.getAnnotationLayer().addAnnotation(annotation);
			
			//Toast.makeText(FootRoutActivity.this, mjbexinfoList.get(i).getJbexInfo().getSize(), Toast.LENGTH_SHORT).show();
        }
		}
	
	 private Bitmap selectAnnotationByLabel(String label) {
			// TODO 自动生成的方法存根
			if(label.equals("学习"))
			return studyBmp;
			else if(label.equals("吃饭"))
			return eatBmp;
			else if(label.equals("KTV"))
			return KTVBmp;
			else if(label.equals("散步"))
				return walkBmp;
			else if(label.equals("篮球"))
				return basketballBmp;
			else if(label.equals("跑步"))
				return RunBmp;
			else if(label.equals("足球"))
				return footballBmp;
			else if(label.equals("运动"))
				return sportsBmp;
			else if(label.equals("健身"))
				return bodyBmp;
			else if(label.equals("奶茶"))
				return milkBmp;
			else return null;
		}


	public class setDataAsyncTask extends AsyncTask<Integer, Integer, String>{
	     
	     //后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根
				mjbexinfoList=MyJbexRequestService.getMyJbexRequestList(owneruser.getEmail());
				return "success";
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				super.onPostExecute(result);
				
				drawAnnotations();
				
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
					progressDialog = CustomProgressDialog.createDialog(FootRoutActivity.this);
			    	progressDialog.setMessage("正在加载中...");
				}
				
		    	progressDialog.show();
			}
			
		}

}
