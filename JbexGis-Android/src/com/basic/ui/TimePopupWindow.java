package com.basic.ui;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.basic.Activities.R;
import com.pickerview.lib.ScreenInfo;
import com.pickerview.lib.WheelTime;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

/**
 * 鏃堕棿閫夋嫨鍣�
 * 
 * @author Sai
 * 
 */
public class TimePopupWindow extends PopupWindow implements OnClickListener {
	public enum Type {
		ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN
	}// 四种选择模式，年月日时分，年月日，时分，月日时分

	private View rootView;// 总的布局
	WheelTime wheelTime;
	private View btnSubmit, btnCancel;
	private static final String TAG_SUBMIT = "submit";
	private static final String TAG_CANCEL = "cancel";
	private OnTimeSelectListener timeSelectListener;

	public TimePopupWindow(Context context, Type type) {
		super(context);
		this.setWidth(LayoutParams.FILL_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setBackgroundDrawable(new BitmapDrawable());// 这样设置才能点击屏幕外dismiss窗口
		this.setOutsideTouchable(true);
		this.setAnimationStyle(R.style.timepopwindow_anim_style);

		LayoutInflater mLayoutInflater = LayoutInflater.from(context);
		rootView = mLayoutInflater.inflate(R.layout.pw_time, null);
		// -----确定和取消按钮
		btnSubmit = rootView.findViewById(R.id.btnSubmit);
		btnSubmit.setTag(TAG_SUBMIT);
		btnCancel = rootView.findViewById(R.id.btnCancel);
		btnCancel.setTag(TAG_CANCEL);
		btnSubmit.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		// ----时间转轮
		final View timepickerview = rootView.findViewById(R.id.timepicker);
		ScreenInfo screenInfo = new ScreenInfo((Activity) context);
		wheelTime = new WheelTime(timepickerview, type);

		wheelTime.screenheight = screenInfo.getHeight();

		//榛樿閫変腑褰撳墠鏃堕棿
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		wheelTime.setPicker(year, month, day, hours, minute);
		
		setContentView(rootView);
	}

	/**
	 * 璁剧疆鍙互閫夋嫨鐨勬椂闂磋寖鍥�
	 * 
	 * @param START_YEAR
	 * @param END_YEAR
	 */
	public void setRange(int START_YEAR, int END_YEAR) {
		WheelTime.setSTART_YEAR(START_YEAR);
		WheelTime.setEND_YEAR(END_YEAR);
	}

	/**
	 * 璁剧疆閫変腑鏃堕棿
	 * @param date
	 */
	public void setTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		if (date == null)
			calendar.setTimeInMillis(System.currentTimeMillis());
		else
			calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		wheelTime.setPicker(year, month, day, hours, minute);
	}

	/**
	 * 鎸囧畾閫変腑鐨勬椂闂达紝鏄剧ず閫夋嫨鍣�
	 * 
	 * @param parent
	 * @param gravity
	 * @param x
	 * @param y
	 * @param date
	 */
	public void showAtLocation(View parent, int gravity, int x, int y, Date date) {
		Calendar calendar = Calendar.getInstance();
		if (date == null)
			calendar.setTimeInMillis(System.currentTimeMillis());
		else
			calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		wheelTime.setPicker(year, month, day, hours, minute);
		update();
		super.showAtLocation(parent, gravity, x, y);
	}

	/**
	 * 璁剧疆鏄惁寰幆婊氬姩
	 * 
	 * @param cyclic
	 */
	public void setCyclic(boolean cyclic) {
		wheelTime.setCyclic(cyclic);
	}

	@Override
	public void onClick(View v) {
		String tag = (String) v.getTag();
		if (tag.equals(TAG_CANCEL)) {
			dismiss();
			return;
		} else {
			if (timeSelectListener != null) {
				try {
					Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
					timeSelectListener.onTimeSelect(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			dismiss();
			return;
		}
	}

	public interface OnTimeSelectListener {
		public void onTimeSelect(Date date);
	}

	public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
		this.timeSelectListener = timeSelectListener;
	}

}
