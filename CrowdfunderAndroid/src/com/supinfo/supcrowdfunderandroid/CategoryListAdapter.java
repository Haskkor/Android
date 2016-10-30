package com.supinfo.supcrowdfunderandroid;

import java.util.List;

import com.supinfo.supcrowdfunderandroid.model.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoryListAdapter<T> extends BaseAdapter {

	private List<T> objects;
	private LayoutInflater inflater;
	
	public CategoryListAdapter(Context context, int resource, List<T> objects) {
		 init(context, resource, 0, objects);
	}
	
	private void init(Context context, int resource, int textViewResourceId, List<T> objectList) {
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		objects = objectList;
	}
	
	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public Object getItem(int arg0) {
		return objects.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	static class ViewHolder {
		public TextView name_txt;
		public TextView content_txt;
		public TextView completion_txt;
	}	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView; 
		ViewHolder holder;
		
		if (view == null) {
            view = inflater.inflate(R.layout.category_list_item, parent, false);
            holder= new ViewHolder();
            holder.name_txt = (TextView) view.findViewById(R.id.lv_category_name);
            view.setTag(holder);
        } else {
            holder= (ViewHolder) view.getTag();
        }

		@SuppressWarnings("unchecked")
		T item = (T) getItem(position);
		if(item instanceof Category){
			 Category category = (Category) item;
				holder.name_txt.setText(category.getCname());		
		}
		return view;
	}	
}