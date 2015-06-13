package com.exp.vbaoxian;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.TextView;

public class MainFragment extends Fragment implements OnClickListener{

	public static final int ARG_NUM = 0;
	private static Button btn;
	public interface DemoOnClickListener{
		void DemoOnClick();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		int LayoutID = getResources().getIdentifier("demo", "layout", getActivity().getPackageName());
		View rootview = inflater.inflate(LayoutID, container, false);
		btn = (Button)rootview.findViewById(R.id.act_shift);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), Act_shift_test.class);
				getActivity().startActivity(intent);
			}
		});
		String title = getResources().getStringArray(R.array.pages)[ARG_NUM];
		getActivity().setTitle(title);
		return rootview;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		((DemoOnClickListener) getActivity()).DemoOnClick();
	}

}
