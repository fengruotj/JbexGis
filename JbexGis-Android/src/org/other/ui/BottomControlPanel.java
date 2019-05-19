package org.other.ui;

import java.util.ArrayList;
import java.util.List;

import org.other.constant.Constant;

import com.basic.Activities.R;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;


public class BottomControlPanel extends RelativeLayout implements View.OnClickListener {
	private Context mContext;
	private ImageText mMsgBtn = null;
	private ImageText m_Contents = null;
	private ImageText m_Friends = null;
	private int DEFALUT_BACKGROUND_COLOR = Color.rgb(243, 243, 243); //Color.rgb(192, 192, 192)
	private BottomPanelCallback mBottomCallback = null;
	private List<ImageText> viewList = new ArrayList<ImageText>();

	public interface BottomPanelCallback{
		public void onBottomPanelClick(int itemId);
	}
	public BottomControlPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		mMsgBtn = (ImageText)findViewById(R.id.btn_message);
		m_Contents = (ImageText)findViewById(R.id.btn_contents);
		m_Friends = (ImageText)findViewById(R.id.btn_friends);
		
		setBackgroundColor(DEFALUT_BACKGROUND_COLOR);
		viewList.add(mMsgBtn);
		viewList.add(m_Friends);

	}
	public void initBottomPanel(){
		if(mMsgBtn != null){
			mMsgBtn.setImage(R.drawable.message_unselected);
			mMsgBtn.setText("消息");
		}
		
		if(m_Contents != null){
			m_Contents.setImage(R.drawable.contacts_unselected);
			m_Contents.setText("+");
		}
		if(m_Friends != null){
			m_Friends.setImage(R.drawable.news_unselected);
			m_Friends.setText("好友");
		}
		
		setBtnListener();

	}
	private void setBtnListener(){
		int num = this.getChildCount();
		for(int i = 0; i < num; i++){
			View v = getChildAt(i);
			if(v != null){
				v.setOnClickListener(this);
			}
		}
	}
	public void setBottomCallback(BottomPanelCallback bottomCallback){
		mBottomCallback = bottomCallback;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		initBottomPanel();
		int index = -1;
		switch(v.getId()){
		case R.id.btn_message:
			index = Constant.BTN_FLAG_MESSAGE;
			mMsgBtn.setChecked(Constant.BTN_FLAG_MESSAGE);
			break;
			
		case R.id.btn_contents:
			index = Constant.BTN_FLAG_Contents;
			m_Contents.setChecked(Constant.BTN_FLAG_Contents);
			break;
		case R.id.btn_friends:
			index = Constant.BTN_FLAG_Friends;
			m_Friends.setChecked(Constant.BTN_FLAG_Friends);
			break;
		default:break;
		}
		if(mBottomCallback != null){
			mBottomCallback.onBottomPanelClick(index);
		}
	}
	public void defaultBtnChecked(){
		if(m_Contents != null){
			m_Contents.setChecked(Constant.BTN_FLAG_Contents);
		}
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		layoutItems(left, top, right, bottom);
	}
	/**最左边和最右边的view由母布局的padding进行控制位置。这里需对第2、3个view的位置重新设置
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	private void layoutItems(int left, int top, int right, int bottom){
		int n = getChildCount();
		if(n == 0){
			return;
		}
//		int paddingLeft = getPaddingLeft();
//		int paddingRight = getPaddingRight();
//		Log.i("yanguoqi", "paddingLeft = " + paddingLeft + " paddingRight = " + paddingRight);
//		int width = right - left;
//		int height = bottom - top;
//		Log.i("yanguoqi", "width = " + width + " height = " + height);
//		int allViewWidth = 0;
//		for(int i = 0; i< n; i++){
//			View v = getChildAt(i);
//			Log.i("yanguoqi", "v.getWidth() = " + v.getWidth());
//			allViewWidth += v.getWidth();
//		}
//		int blankWidth = (width - allViewWidth - paddingLeft - paddingRight) / (n - 1);
//		Log.i("yanguoqi", "blankV = " + blankWidth );
//
//		LayoutParams params1 = (LayoutParams) viewList.get(1).getLayoutParams();
//		params1.leftMargin = blankWidth;
//		viewList.get(1).setLayoutParams(params1);
//
//		LayoutParams params2 = (LayoutParams) viewList.get(2).getLayoutParams();
//		params2.leftMargin = blankWidth;
//		viewList.get(2).setLayoutParams(params2);
	}



}
