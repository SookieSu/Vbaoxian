package com.exp.vbaoxian;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MineFragment extends Fragment implements OnClickListener{

//public static Button bt1;	
public static final int ARG_NUM = 2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		int LayoutID = getResources().getIdentifier("fragment_mine", "layout", getActivity().getPackageName());
		View view = inflater.inflate(LayoutID, container, false);
		//View view = inflater.inflate(R.layout.listfragment_view,null);
		
		/*
		bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), Act_shift_test.class);
				getActivity().startActivity(intent);
			}
		});
		*/
		String title = getResources().getStringArray(R.array.pages)[ARG_NUM];
		getActivity().setTitle(title);
		return view;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
