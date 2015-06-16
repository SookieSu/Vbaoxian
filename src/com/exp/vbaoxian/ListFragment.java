
package com.exp.vbaoxian;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

@SuppressLint("NewApi")
public class ListFragment extends Fragment implements OnClickListener{
	
	private static Button bt1;
	private static Button bt2;
	private static Button bt3;
	private static Button bt4;
	private static Button bt5;
	private static Button bt6;
	private static Button bt0;
	public static final int ARG_NUM = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		int LayoutID = getResources().getIdentifier("fragment_list", "layout", getActivity().getPackageName());
		View view = inflater.inflate(LayoutID, container, false);
		//View view = inflater.inflate(R.layout.listfragment_view,null);
		
		bt1 = (Button)view.findViewById(R.id.bt1);
		bt2 = (Button)view.findViewById(R.id.bt2);
		bt3 = (Button)view.findViewById(R.id.bt3);
		bt4 = (Button)view.findViewById(R.id.bt4);
		bt5 = (Button)view.findViewById(R.id.bt5);
		bt6 = (Button)view.findViewById(R.id.bt6);
		bt0 = (Button)view.findViewById(R.id.bt0);
		
		bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(),ListActivity_Baoxian.class);
				getActivity().startActivity(intent);
			}
		});
		String title = getResources().getStringArray(R.array.pages)[ARG_NUM];
		getActivity().setTitle(title);
		return view;
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}

