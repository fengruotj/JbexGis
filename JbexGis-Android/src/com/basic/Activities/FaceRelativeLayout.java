package com.basic.Activities;

import java.util.ArrayList;
import java.util.List;

import com.basic.model.ChatEmoji;
import com.basic.model.MessagePlusEndity;
import com.basic.facedemo.adapter.FaceAdapter;
import com.basic.facedemo.adapter.FacePagerAdapter;
import com.basic.facedemo.adapter.MessagePlusAdapter;
import com.basic.facedemo.adapter.ViewPagerAdapter;
import com.basic.util.FaceConversionUtil;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class FaceRelativeLayout extends RelativeLayout implements
		OnItemClickListener, OnClickListener {

	private static final String TAG = "FaceRelativeLayout";

	private Context context;

	/** 表情页的监听事件 */
	private OnCorpusSelectedListener mListener;

	/** 显示表情页的viewpager */
	private ViewPager vp_face;

	/** 表情页界面集合 */
	private ArrayList<View> pageViews;

	/** 加号页面集合 */
	List<List<MessagePlusEndity>> function_list = null;

	/** 游标显示布局 */
	private LinearLayout layout_point;

	/** 游标点集合 */
	private ArrayList<ImageView> pointViews;

	/** 表情集合 */
	private List<List<ChatEmoji>> emojis;

	/** 表情区域 */
	private View view;

	/** 输入框 */
	private EditText et_sendmessage;

	/** 表情数据填充器 */
	private List<FaceAdapter> faceAdapters;

	/** 功能数据填充器 */
	private List<MessagePlusAdapter> functionAdapter_list = null;

	/** 当前表情页 */
	private int current = 0;

	/** // 发送其他数据 */
	ImageButton faceBtn = null;
	/** // 发送其他数据 */
	ImageButton addBtn = null;

	/** 功能选中监听事件 */
	FunctionClickListener mFunctionClickListener = null;

	OnItemClickListener functionOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			MessagePlusEndity item = function_list.get(current).get(arg2);
			if (mFunctionClickListener != null) {
				mFunctionClickListener.onClick(item);
			}
		}
	};

	public void setmListener(OnCorpusSelectedListener mListener) {
		this.mListener = mListener;
	}

	public FaceRelativeLayout(Context context) {
		super(context);
		this.context = context;
	}

	public FaceRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public FaceRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	public void setOnCorpusSelectedListener(OnCorpusSelectedListener listener) {
		mListener = listener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_face:
			Init_viewPager();
			Init_Point();
			Init_Data();
			// 隐藏表情选择框
			if (view.getVisibility() == View.VISIBLE) {
				view.setVisibility(View.GONE);
			} else {
				view.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.et_sendmessage:
			// 隐藏表情选择框
			if (view.getVisibility() == View.VISIBLE) {
				view.setVisibility(View.GONE);
			}
			break;
		case R.id.btn_plus:
			//TODO 标记
			Init_functionViewPager();
			Init_Point();
			Init_functionData();
			if (view.getVisibility() == View.GONE) {
				view.setVisibility(View.VISIBLE);
			} else
				view.setVisibility(View.GONE);
			break;
		}
	}

	/**
	 * 表情选择监听
	 */
	public interface OnCorpusSelectedListener {

		void onCorpusSelected(ChatEmoji emoji);

		void onCorpusDeleted();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		emojis = FaceConversionUtil.getInstace().emojiLists;
		function_list = new ArrayList<List<MessagePlusEndity>>();
		List<MessagePlusEndity> list = new ArrayList<MessagePlusEndity>();
		int[] a = new int[] { R.drawable.chat_tool_photo,
				R.drawable.chat_tool_send_file, R.drawable.chat_tool_camera,
				R.drawable.chat_tool_funny_face };
		String[] str = new String[] { "图片", "文件", "拍照", "涂鸦" };
		for (int i = 0; i < 4; i++) {
			MessagePlusEndity item = new MessagePlusEndity();
			item.icon = a[i];
			item.name = str[i];
			item.position = i;
			list.add(item);
		}
		function_list.add(list);
		onCreate();
	}

	private void onCreate() {
		Init_View();// 初始化控件
	}

	/**
	 * 隐藏表情选择框
	 */
	public boolean hideFaceView() {
		// 隐藏表情选择框
		if (view.getVisibility() == View.VISIBLE) {
			view.setVisibility(View.GONE);
			return true;
		}
		return false;
	}

	/**
	 * 初始化控件
	 */
	private void Init_View() {
		vp_face = (ViewPager) findViewById(R.id.vp_contains);
		et_sendmessage = (EditText) findViewById(R.id.et_sendmessage);
		layout_point = (LinearLayout) findViewById(R.id.iv_image);
		et_sendmessage.setOnClickListener(this);
		findViewById(R.id.btn_face).setOnClickListener(this);
		faceBtn = (ImageButton) findViewById(R.id.btn_face);
		faceBtn.setOnClickListener(this);
		view = findViewById(R.id.ll_facechoose);
		addBtn = (ImageButton) findViewById(R.id.btn_plus);
		addBtn.setOnClickListener(this);
	}

	/**
	 * 初始化显示表情的viewpager
	 */
	public void Init_viewPager() {
		pageViews = new ArrayList<View>();
		// 左侧添加空页
		View nullView1 = new View(context);
		// 设置透明背景
		nullView1.setBackgroundColor(Color.TRANSPARENT);
		pageViews.add(nullView1);

		// 中间添加表情页

		faceAdapters = new ArrayList<FaceAdapter>();
		for (int i = 0; i < emojis.size(); i++) {
			GridView view = new GridView(context);
			FaceAdapter adapter = new FaceAdapter(context, emojis.get(i));
			view.setAdapter(adapter);
			faceAdapters.add(adapter);
			view.setOnItemClickListener(this);
			view.setNumColumns(7);
			view.setBackgroundColor(Color.TRANSPARENT);
			view.setHorizontalSpacing(1);
			view.setVerticalSpacing(1);
			view.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
			view.setCacheColorHint(0);
			view.setPadding(5, 0, 5, 0);
			view.setSelector(new ColorDrawable(Color.TRANSPARENT));
			view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			view.setGravity(Gravity.CENTER);
			pageViews.add(view);
		}

		// 右侧添加空页面
		View nullView2 = new View(context);
		// 设置透明背景
		nullView2.setBackgroundColor(Color.TRANSPARENT);
		pageViews.add(nullView2);
	}

	/**
	 * 初始化游标
	 */
	public void Init_Point() {

		pointViews = new ArrayList<ImageView>();
		layout_point.removeAllViews();
		ImageView imageView;
		for (int i = 0; i < pageViews.size(); i++) {
			imageView = new ImageView(context);
			imageView.setBackgroundResource(R.drawable.d1);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.leftMargin = 10;
			layoutParams.rightMargin = 10;
			layoutParams.width = 8;
			layoutParams.height = 8;
			layout_point.addView(imageView, layoutParams);
			if (i == 0 || i == pageViews.size() - 1) {
				imageView.setVisibility(View.GONE);
			}
			if (i == 1) {
				imageView.setBackgroundResource(R.drawable.d2);
			}
			pointViews.add(imageView);

		}
	}

	/** 表情ViewPager初始化数据 */
	private void Init_functionData() {
		vp_face.setAdapter(new FacePagerAdapter(pageViews));
		vp_face.setCurrentItem(1);
		current = 0;
		vp_face.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				current = arg0 - 1;
				draw_Point(arg0);
				if (arg0 == pointViews.size() - 1 || arg0 == 0) {
					if (arg0 == 0) {
						vp_face.setCurrentItem(arg0 + 1);
						pointViews.get(1).setBackgroundResource(R.drawable.d1);
					} else {
						vp_face.setCurrentItem(arg0 - 1);
						pointViews.get(arg0 - 1).setBackgroundResource(
								R.drawable.d2);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * 填充数据
	 */
	private void Init_Data() {
		vp_face.setAdapter(new ViewPagerAdapter(pageViews));

		vp_face.setCurrentItem(1);
		current = 0;
		vp_face.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				current = arg0 - 1;
				// 描绘分页点
				draw_Point(arg0);
				// 如果是第一屏或者是最后一屏禁止滑动，其实这里实现的是如果滑动的是第一屏则跳转至第二屏，如果是最后一屏则跳转到倒数第二屏.
				if (arg0 == pointViews.size() - 1 || arg0 == 0) {
					if (arg0 == 0) {
						vp_face.setCurrentItem(arg0 + 1);// 第二屏 会再次实现该回调方法实现跳转.
						pointViews.get(1).setBackgroundResource(R.drawable.d1);
					} else {
						vp_face.setCurrentItem(arg0 - 1);// 倒数第二屏
						pointViews.get(arg0 - 1).setBackgroundResource(
								R.drawable.d2);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	/**
	 * 发送其他数据
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void Init_functionViewPager() {
		pageViews = new ArrayList<View>();
		// 左侧添加空白页
		View nullview = new View(context);
		// 设置背景透明
		nullview.setBackgroundColor(Color.TRANSPARENT);
		pageViews.add(nullview);
		// 添加表情页
		functionAdapter_list = new ArrayList<MessagePlusAdapter>();
		for (int i = 0; i < function_list.size(); i++) {
			GridView gridView = new GridView(context);
			MessagePlusAdapter adapter = new MessagePlusAdapter(context,
					function_list.get(i));
			gridView.setAdapter(adapter);
			functionAdapter_list.add(adapter);
			gridView.setOnItemClickListener(functionOnItemClickListener);
			gridView.setNumColumns(4);
			gridView.setBackgroundColor(Color.TRANSPARENT);
			gridView.setHorizontalSpacing(1);
			gridView.setVerticalSpacing(1);
			gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
			gridView.setCacheColorHint(0);
			gridView.setPadding(5, 0, 5, 0);
			gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
			gridView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			gridView.setGravity(Gravity.CENTER);
			pageViews.add(gridView);
		}
		// 右侧添加空白页
		View nullview1 = new View(context);
		// 设置背景透明
		nullview.setBackgroundColor(Color.TRANSPARENT);
		pageViews.add(nullview1);
	}

	/**
	 * 绘制游标背景
	 */
	public void draw_Point(int index) {
		for (int i = 1; i < pointViews.size(); i++) {
			if (index == i) {
				pointViews.get(i).setBackgroundResource(R.drawable.d2);
			} else {
				pointViews.get(i).setBackgroundResource(R.drawable.d1);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		ChatEmoji emoji = (ChatEmoji) faceAdapters.get(current).getItem(arg2);
		if (emoji.getId() == R.drawable.face_del_icon) {
			int selection = et_sendmessage.getSelectionStart();
			String text = et_sendmessage.getText().toString();
			if (selection > 0) {
				String text2 = text.substring(selection - 1);
				if ("]".equals(text2)) {
					int start = text.lastIndexOf("[");
					int end = selection;
					et_sendmessage.getText().delete(start, end);
					return;
				}
				et_sendmessage.getText().delete(selection - 1, selection);
			}
		}
		if (!TextUtils.isEmpty(emoji.getCharacter())) {
			if (mListener != null)
				mListener.onCorpusSelected(emoji);
			SpannableString spannableString = FaceConversionUtil.getInstace()
					.addFace(getContext(), emoji.getId(), emoji.getCharacter());
			et_sendmessage.append(spannableString);
		}

	}

	// 设置功能点击监听器
	public void setFunctionClickListener(FunctionClickListener listener) {
		mFunctionClickListener = listener;
	}

	// 点击功能按钮监听事件
	public interface FunctionClickListener {
		void onClick(MessagePlusEndity item);
	}
}
