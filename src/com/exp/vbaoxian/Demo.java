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
	private static Button btn;
	private PullToRefreshListView mPullRefreshListView;
	// private ArrayAdapter<String> mAdapter;

	private SimpleAdapter adapter;
	private List<Map<String, Object>> data;
	private ListView listView;
	private List<Map<String, Object>> testdata;
	private List<Baoxian> testlist;

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
            Baoxian music= (Baoxian)testlist.get(2);  
            map.put("name",music.getName());  
            map.put("age", music.getAge());  
            map.put("time",music.getTime());  
            map.put("group",music.getGroup());
            map.put("price", music.getPrice());
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
		
		btn = (Button) rootview.findViewById(R.id.act_shift);
		mPullRefreshListView = (PullToRefreshListView)rootview.findViewById(R.id.main_pull_refresh_list);
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
		//////////////////////

///////////////////////////////////////////////////////////////////////////////////////////
testlist = new ArrayList<Baoxian>();
Baoxian m1 = new Baoxian();  
m1.setId(0);  
m1.setName("平安旅行意外伤害保险"); 
m1.setAge("承保年龄");  
m1.setTime("保障期限");
m1.setGroup("适用人群");
m1.setPrice("5.20");

testlist.add(m1);

Baoxian m2 = new Baoxian();  
m2.setId(1);  
m2.setName("户外运动保障精英款"); 
m2.setAge("承保年龄");  
m2.setTime("保障期限");
m2.setGroup("适用人群");
m2.setPrice("6.00");

testlist.add(m2);  
Baoxian m3 = new Baoxian();  
m3.setId(2);  
m3.setName("国内旅行保障计划"); 
m3.setAge("承保年龄");  
m3.setTime("保障期限");
m3.setGroup("适用人群");
m3.setPrice("8.40");
testlist.add(m3); 

testdata = new  ArrayList<Map<String,Object>>(); 



for(int i=0;i<testlist.size();i++)
{  
Map<String,Object>  map = new HashMap<String, Object>();  
Baoxian music= (Baoxian)testlist.get(i);  
map.put("name",music.getName());  
map.put("age", music.getAge());  
map.put("time",music.getTime());  
map.put("group",music.getGroup());
map.put("price", music.getPrice());
testdata.add(map); 
}

adapter  = new  SimpleAdapter(getActivity(), testdata, R.layout.list_item, new String[]{"name","age","time","group","price"}, new int[]{R.id.name,R.id.age,R.id.time,R.id.group,R.id.price});   

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
