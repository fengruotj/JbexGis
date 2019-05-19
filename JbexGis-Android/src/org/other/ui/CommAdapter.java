package org.other.ui;

import java.util.List;

import com.basic.Activities.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommAdapter extends BaseAdapter {

	private List<CommField> listField;
	private Context context;

	public int getCount() {
		if (null == listField) {
			return 0;
		} else {
			return listField.size();
		}
	}

	public Object getItem(int position) {
		if (null == listField) {
			return null;
		} else {
			return listField.get(position);
		}

	}

	public long getItemId(int position) {

		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder	holder	= null;
		
		if(null == convertView){
			
			holder = new ViewHolder();
			
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			convertView = mInflater.inflate(R.layout.common_list_item, null);
			
			holder.nameText 			= (TextView)convertView.findViewById(R.id.common_fieldname);
			holder.valueText 			= (TextView)convertView.findViewById(R.id.common_fieldvalue);
			
			convertView.setTag(holder);
			
		}else{
			
			holder = (ViewHolder)convertView.getTag();
		}
		
		Object strValue;
		String strName = "";
		CommField field = listField.get(position);

		strValue = field.getStrValue();
		strName = field.getStrName();


		holder.nameText.setText(strName);
		holder.valueText.setText((CharSequence) strValue);

		// 名字尺寸
		if (field.getNameTextSize() > 0) {
			holder.nameText.setTextSize(field.getNameTextSize());
		}

		if (field.isMore()) {
			ImageView moreimg = (ImageView) convertView.findViewById(R.id.common_little_img);
					
			moreimg.setVisibility(View.VISIBLE);

			if (field.getUserMoreImg() > 0) {
				// 使用用户自定义的图标
				moreimg.setImageResource(field.getUserMoreImg());
			}

			if (null != field.getLittleImgClickListener()) {
				// 响应用户自定义的事件
				moreimg.setOnClickListener(field.getLittleImgClickListener());
			}

			moreimg.setVisibility(View.VISIBLE);
		}

		if (field.getUserBigImg() > 1) {
			ImageView bigimg = (ImageView) convertView
					.findViewById(R.id.common_img);
			bigimg.setVisibility(View.VISIBLE);
			bigimg.setImageResource(field.getUserBigImg());
		}

		if (field.getStrLittleName().length() > 0) {
			TextView littleName = (TextView) convertView
					.findViewById(R.id.common_fieldname_s);
			littleName.setVisibility(View.VISIBLE);
			littleName.setText(field.getStrLittleName());
		}

		return convertView;
	}

	public List<CommField> getListField() {
		return listField;
	}

	public void setListField(List<CommField> listField) {
		this.listField = listField;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	class ViewHolder{
		public TextView nameText;
		public TextView valueText;
	}
	
}
