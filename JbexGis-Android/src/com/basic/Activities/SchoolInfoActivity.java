package com.basic.Activities;

import com.base.jbex.JbexEditActivity;
import com.base.jbex.JbexSelectDot;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SchoolInfoActivity extends Activity {

	private ActionBar actionBar =null;
	private WebView webview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_school_info);
		
		initActionBar();
		webview=(WebView) findViewById(R.id.webview);
		webview.loadUrl("http://mp.weixin.qq.com/mp/homwepage?__biz=MjM5NjAyMzQ0MA==&hid=2&sn=2a2d6a54219c4aaa1799dea282896fff");
	}

	private void initActionBar() {
		// TODO 自动生成的方法存根
		actionBar = getActionBar();
		actionBar.setCustomView(R.layout.session_top);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);  
		actionBar.setDisplayShowCustomEnabled(true);
		View view=actionBar.getCustomView();
		ImageButton rightButton=(ImageButton) view.findViewById(R.id.btn_nnavigation);
		ImageButton back=(ImageButton) view.findViewById(R.id.btn_back);
		TextView txt = null;	
		txt=(TextView) view.findViewById(R.id.main_Text);
		rightButton.setBackgroundResource(0);	
		txt.setText("学院概括");
		TextView backtxt=(TextView) view.findViewById(R.id.backtxt);

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				SchoolInfoActivity.this.finish();
			}
		});

		backtxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				SchoolInfoActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.school_info, menu);
		return true;
	}

}
