package com.exp.vbaoxian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exp.vbaoxian.ListActivity_Baoxian.GetDataTask;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;

import android.widget.SimpleAdapter;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Demo extends ListFragment implements OnClickListener {

	public static final int ARG_NUM = 0;
	private PullToRefreshListView mPullRefreshListView;
	// private ArrayAdapter<String> mAdapter;

	private SimpleAdapter adapter;
	private List<Map<String, Object>> data;
	private ListView listView;
	private List<Map<String, Object>> testdata;
	private List<String> testlist;

	public interface DemoOnClickListener {
		void DemoOnClick();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	}

	private List<Map<String,Object>>  getAdapterData(List  list){  
        List<Map<String,Object>>  data = new  ArrayList<Map<String,Object>>();  
        for(int i=0;i<list.size();i++){  
            Map<String,Object>  map = new HashMap<String, Object>();  
            Baoxian music= (Baoxian)list.get(i);  
            map.put("name",music.getName());  
            map.put("age", music.getAge());  
            map.put("time",music.getTime());  
            map.put("group",music.getGroup());
            map.put("price", music.getPrice());
            data.add(map);  
            
     
        }  
        return   data;  
  }  

	private class GetDataTask extends AsyncTask<Void, Void, List<Map<String,Object>>> {

		@Override
		protected List<Map<String,Object>> doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return testdata;
		}

		@Override
		protected void onPostExecute(List<Map<String,Object>> data) {
			super.onPostExecute(data);
			
	          
	        Map<String,Object>  map = new HashMap<String, Object>();  
	        String music= "哈哈哈哈哈";
	        map.put("text_title",music.toString());  
	        testdata.add(map); 
	        
            
	        ////////////////////////
			adapter.notifyDataSetChanged();

			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			
		}
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("demo start!");
		int LayoutID = getResources().getIdentifier("demo", "layout",
				getActivity().getPackageName());
		View rootview = inflater.inflate(LayoutID, container, false);
		
	
		mPullRefreshListView = (PullToRefreshListView)rootview.findViewById(R.id.main_pull_refresh_list);
		

		String title = getResources().getStringArray(R.array.pages)[ARG_NUM];
		getActivity().setTitle(title);
		//////////////////////

///////////////////////////////////////////////////////////////////////////////////////////
testlist = new ArrayList<String>();
String m1 = "买这个没错！";

testlist.add(m1);
String m2 = "走吧！少年！";

testlist.add(m2);  
String m3 = "你的宝贝还在裸奔吗！";
testlist.add(m3); 

testdata = new  ArrayList<Map<String,Object>>(); 



for(int i=0;i<testlist.size();i++)
{  
Map<String,Object>  map = new HashMap<String, Object>();  
String music= (String)testlist.get(i);
map.put("text_title",music);  

testdata.add(map); 
}

adapter  = new  SimpleAdapter(getActivity(), testdata, R.layout.fragment_main_list_item, new String[]{"text_title"}, new int[]{R.id.main_list_item_text});   

///////////////////////////////////////////////////////////////////////////////////////////
// Set a listener to be invoked when the list should be refreshed.
mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
@Override
public void onRefresh(PullToRefreshBase<ListView> refreshView) {
String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

// Update the LastUpdatedLabel
refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

// Do work to refresh the list here.
new GetDataTask().execute();
}
});

// Add an end-of-list listener
mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

@Override
public void onLastItemVisible() {
Toast.makeText(getActivity(), "End of List!", Toast.LENGTH_SHORT).show();
}
});
///////////////////////////////////////////////////////////////////////////////////
listView = mPullRefreshListView.getRefreshableView();

// Need to use the Actual ListView when registering for Context Menu
registerForContextMenu(listView);

//mListItems = new LinkedList<String>();
//mListItems.addAll(Arrays.asList(mStrings));

//mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);

/*
* Add Sound Event Listener
*/
/*
SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(getActivity());
soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
mPullRefreshListView.setOnPullEventListener(soundListener);
*/
// You can also just use setListAdapter(mAdapter) or
// mPullRefreshListView.setAdapter(mAdapter)
listView.setAdapter(adapter);
		///////////////////////////
		
		return rootview;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// ((DemoOnClickListener) getActivity()).DemoOnClick();
	}

}
