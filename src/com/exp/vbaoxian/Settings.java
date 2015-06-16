package com.exp.vbaoxian;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Settings extends Fragment implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {
		int LayoutID = getResources().getIdentifier("settings", "layout", getActivity().getPackageName());
		View view = inflater.inflate(LayoutID, container, false);
		String title = getResources().getStringArray(R.array.pages)[3];
		getActivity().setTitle(title);
		return view;
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
