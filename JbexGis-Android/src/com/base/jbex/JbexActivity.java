package com.base.jbex;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.base.jbex.AboutSchool.setDataAsyncTask;
import com.basic.Activities.R;
import com.basic.Activities.R.drawable;
import com.basic.Activities.R.id;
import com.basic.Activities.R.layout;
import com.basic.Activities.ZJBEXBaseActivity;
import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageOptions;
import com.basic.ImageLoad.ImageStringUtil;
import com.basic.connectservice.JbexInfoService;
import com.basic.connectservice.PublicInfoService;
import com.basic.service.model.JbexInfo;
import com.basic.service.model.User;
import com.basic.ui.CustomProgressDialog;
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
import com.zondy.mapgis.android.graphic.GraphicCircle;
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
import android.graphics.Color;
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

public class JbexActivity extends ZJBEXBaseActivity implements MapViewAnnotationListener,MapViewLongTapListener, MapViewTapListener,MapViewRenderContextListener{
    
	private static int mjbexuserifoID=0;
	private static int mjbexuserifoID_friend=1;
	private static int mjbexuserifoID_attention=2;
	
	private Intent intent;
	private User owneruser=new User();
	
    private CustomProgressDialog progressDialog = null;
	//图片加载框架组件
	private DisplayImageOptions options;
	private ImageLoader mImageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	private String path = Environment.getExternalStorageDirectory().getPath();
	
	private ActionBar actionBar = null;
	private MapView mapView;
	private View mContents;
	
	TextView main_txt = null; // actionbar的中间的字
	
	// ------------下拉---------------
		private PopupWindow popupWindow;
		private ListView lv_group;
		private View view;
		private List<String> groups;
		// ------------下拉---------------
		
    private ArrayList<JbexInfo> mjbexinfoList = new ArrayList<JbexInfo>();
    private ArrayList<JbexInfo> mjbexinfoList_friend = new ArrayList<JbexInfo>();
    private ArrayList<JbexInfo> mjbexinfoList_attention = new ArrayList<JbexInfo>();
    
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
    
    private java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jbex);
		
		intent=getIntent();
		if ((intent.getExtras()) != null) {
			Bundle data = intent.getExtras();
			owneruser = (User) data.getSerializable("owneruser");
			Log.d("owneruser", owneruser.toString());
		}
		
		
		initDisplayOption();
		initActionBar(); // 加载改变actionBar
		initAnnotation();
		
		mapView = (MapView) super.findViewById(R.id.mapview);
		mapView.loadFromFile(path + "/mapgis/map/wuhan/wuhan.xml"); // 加载地图
		mapView.setRenderContextListener(JbexActivity.this); // 注册四个监听
		mapView.setAnnotationListener(JbexActivity.this);
		mapView.setLongTapListener(JbexActivity.this);
		mapView.setTapListener(JbexActivity.this);
		
		setDataAsyncTask a=new setDataAsyncTask();
		a.execute();
		
		mapView.refresh();
	}
   
	 private void initAnnotation() {
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
		
	 
	private void initDisplayOption() {
		// TODO 自动生成的方法存根
		options=ImageOptions.initDisplayOptions();
		mImageLoader = ImageLoader.getInstance();
	}

	@SuppressWarnings("null")
	private void drawAnnotations(int ii) {
			// TODO 自动生成的方法存根
		
		mapView.getAnnotationLayer().removeAllAnnotations();
		mapView.getGraphicLayer().removeAllGraphics();
		
		String JBtitle, JBusername, JBtime;
		Dot JBposition = null ;
		String JBuserPicture;
		Bitmap bmp;
		//List<JbexInfoAnnotation> annotationList=new ArrayList<JbexInfoAnnotation>();
			switch (ii) {
			case 0:
				for(int i=0;i<mjbexinfoList.size();i++){
					JBtitle=mjbexinfoList.get(i).getTitle();
					JBusername=mjbexinfoList.get(i).getTUser().getUser_nickname();
					JBtime=sdf.format(mjbexinfoList.get(i).getTime());
					JBuserPicture=mjbexinfoList.get(i).getTUser().getPicture();
					bmp=selectAnnotationByLabel(mjbexinfoList.get(i).getLabel());
					JBposition=mapView.locationToMapPoint(new Dot(mjbexinfoList.get(i).getDotX(), mjbexinfoList.get(i).getDotY()));
					
					JbexInfoAnnotation annotation=new JbexInfoAnnotation(JBusername, JBtitle, JBtime,JBposition, bmp);
					annotation.setJBuserPicture(JBuserPicture);
					annotation.setMpoublicuserifoID(mjbexuserifoID);
					annotation.setWhich(i);
					
					draw_cir(JBposition,mjbexinfoList.get(i).getSize());
					mapView.getAnnotationLayer().addAnnotation(annotation);
				}
				break;
				
            case 1:
            	for(int i=0;i<mjbexinfoList_friend.size();i++){
					JBtitle=mjbexinfoList_friend.get(i).getTitle();
					JBusername=mjbexinfoList_friend.get(i).getTUser().getUser_nickname();
					JBtime=sdf.format(mjbexinfoList_friend.get(i).getTime());
					JBuserPicture=mjbexinfoList_friend.get(i).getTUser().getPicture();
					bmp=selectAnnotationByLabel(mjbexinfoList_friend.get(i).getLabel());
					JBposition=mapView.locationToMapPoint(new Dot(mjbexinfoList_friend.get(i).getDotX(), mjbexinfoList_friend.get(i).getDotY()));
					
					JbexInfoAnnotation annotation=new JbexInfoAnnotation(JBusername, JBtitle, JBtime,JBposition, bmp);
					annotation.setJBuserPicture(JBuserPicture);
					annotation.setMpoublicuserifoID(mjbexuserifoID_friend);
					annotation.setWhich(i);
					
					draw_cir(JBposition,mjbexinfoList_friend.get(i).getSize());
					mapView.getAnnotationLayer().addAnnotation(annotation);
				}
				break;
				
           case 2:
        	   for(int i=0;i<mjbexinfoList_attention.size();i++){
					JBtitle=mjbexinfoList_attention.get(i).getTitle();
					JBusername=mjbexinfoList_attention.get(i).getTUser().getUser_nickname();
					JBtime=sdf.format(mjbexinfoList_attention.get(i).getTime());
					JBuserPicture=mjbexinfoList_attention.get(i).getTUser().getPicture();
					bmp=selectAnnotationByLabel(mjbexinfoList_attention.get(i).getLabel());
					JBposition=mapView.locationToMapPoint(new Dot(mjbexinfoList_attention.get(i).getDotX(), mjbexinfoList_attention.get(i).getDotY()));
					
					JbexInfoAnnotation annotation=new JbexInfoAnnotation(JBusername, JBtitle, JBtime,JBposition, bmp);
					annotation.setJBuserPicture(JBuserPicture);
					annotation.setMpoublicuserifoID(mjbexuserifoID_attention);
					annotation.setWhich(i);
					
					draw_cir(JBposition,mjbexinfoList_attention.get(i).getSize());
					mapView.getAnnotationLayer().addAnnotation(annotation);
				}
  				break;
  				
			default:
				break;
			} 
			
			mapView.refresh();
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

	private void initData() {
			// TODO 自动生成的方法存根
			
		}
	
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
		main_txt = (TextView) view.findViewById(R.id.main_Text);
		TextView backtxt=(TextView) view.findViewById(R.id.backtxt);
		
		rightButton.setBackgroundResource(R.drawable.publish);
		
		ImageButton ibtn = (ImageButton) view.findViewById(R.id.btn_nnavigation);
		ibtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(JbexActivity.this, JbexSelectDot.class);
				
				Bundle data = new Bundle();
				data.putSerializable("owneruser", owneruser);
				intent.putExtras(data);
				
				JbexActivity.this.startActivity(intent);
				JbexActivity.this.finish();
				//Toast.makeText(AboutSchool.this, "点击进入下一个页面" , Toast.LENGTH_LONG).show();
			}
		});
		main_txt.setText("结伴而行");
		main_txt.setOnClickListener(new OnClickListener() {
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
				JbexActivity.this.finish();
			}
		});
      
		backtxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				JbexActivity.this.finish();
			}
		});
		}
	
	protected void showWindow(View parent) {
		// TODO 自动生成的方法存根
		if (popupWindow == null) {
			/**
			 * 取得Xml中的view
			 */
//			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			view = getLayoutInflater().inflate(R.layout.group_list, null);        //加载自定义样式
			lv_group = (ListView) view.findViewById(R.id.lvGroup);
			groups = new ArrayList<String>();
			groups.add("附近结伴"); 
			groups.add("我的好友");
			groups.add("我的关注");

			GroupAdapter groupAdapter = new GroupAdapter(this, groups);
			lv_group.setAdapter(groupAdapter);
			popupWindow = new PopupWindow(view, 300, 350); // 创建一个PopuWidow对象
			
			// 下拉菜单item点击事件监听======================================
			lv_group.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapterView, View view,
						int position, long id) {
					//				String str = String.valueOf(id);
					int id_1 = (int) id;
					switch (id_1) {
					case 0:
						main_txt.setText("附近结伴");
						if(mjbexinfoList.size()==0)
						{
							setDataAsyncTask a=new setDataAsyncTask();
							a.execute();
						}
						else
						drawAnnotations(0);	
						break;

					case 1:
						main_txt.setText("我的好友");
						if(mjbexinfoList_friend.size()==0)
						{
							setFriendDataAsyncTask a=new setFriendDataAsyncTask();
							a.execute();
						}
						else
						drawAnnotations(1);
						break;

					case 2:
						main_txt.setText("我的关注");
						if(mjbexinfoList_attention.size()==0)
						{
							setAttentionDataAsyncTask a=new setAttentionDataAsyncTask();
							a.execute();
						}
						else
						drawAnnotations(2);	
						break;

					default:
						Toast.makeText(JbexActivity.this, "未知错误", Toast.LENGTH_LONG).show();
						break;
					}
					mapView.refresh();
					if (popupWindow != null)
						popupWindow.dismiss();
				}
			});
			
		}
		popupWindow.setFocusable(true); // 使其聚集
				popupWindow.setOutsideTouchable(true); // 设置允许在外点击消失
				popupWindow.setBackgroundDrawable(new BitmapDrawable()); // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景

		WindowManager windowManager1 = getWindowManager();
		int xPos = windowManager1.getDefaultDisplay().getWidth() / 2
				- popupWindow.getWidth() / 2;
		Log.i("coder", "xPos:" + xPos);
		popupWindow.showAsDropDown(parent, xPos, 5);

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
		AnnotationView annotationView = null;
		// TODO Auto-generated method stub
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
				Intent intent = new Intent(JbexActivity.this, JbexDetailsActivity.class);
				Bundle data = new Bundle();
				data.putSerializable("owneruser", owneruser);
				switch (Id) {
				case 0:
					data.putSerializable("jbexuserifo", mjbexinfoList.get(which));
					break;

				case 1:
					data.putSerializable("jbexuserifo", mjbexinfoList_friend.get(which));
					break;
				case 2:
					data.putSerializable("jbexuserifo", mjbexinfoList_attention.get(which));
					break;
				default:
					break;
				}
				intent.putExtras(data);
				JbexActivity.this.startActivity(intent);
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
	
	public class setDataAsyncTask extends AsyncTask<Integer, Integer, String>{
	     
	     //后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根
				mjbexinfoList=(ArrayList<JbexInfo>) JbexInfoService.getJbexInfoList(sdf.format(new Date()));
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
					progressDialog = CustomProgressDialog.createDialog(JbexActivity.this);
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
				mjbexinfoList_friend=(ArrayList<JbexInfo>) JbexInfoService.getFrienJbexInfoListByUserid(String.valueOf(owneruser.getUser_id()),sdf.format(new Date()));
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
					progressDialog = CustomProgressDialog.createDialog(JbexActivity.this);
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
				mjbexinfoList_attention=(ArrayList<JbexInfo>) JbexInfoService.getAttentionjbexInfoListByUserid(String.valueOf(owneruser.getUser_id()),sdf.format(new Date()));
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
					progressDialog = CustomProgressDialog.createDialog(JbexActivity.this);
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
