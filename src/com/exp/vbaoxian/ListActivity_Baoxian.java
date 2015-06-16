/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.exp.vbaoxian;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.app.ListFragment;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;

public final class ListActivity_Baoxian extends ListActivity {

	static final int MENU_MANUAL_REFRESH = 0;
	static final int MENU_DISABLE_SCROLL = 1;
	static final int MENU_SET_MODE = 2;
	static final int MENU_DEMO = 3;

	//private LinkedList<String> mListItems;
	private PullToRefreshListView mPullRefreshListView;
	//private ArrayAdapter<String> mAdapter;
	
	private SimpleAdapter adapter;  
    private List<Map<String,Object>> data;
    private ListView listView;
    private List<Map<String,Object>>  testdata;
    
    private List<Baoxian>  testlist;
 
    
	/** Called when the activity is first created. */
  
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ptr_list);
		mPullRefreshListView = (PullToRefreshListView)findViewById(R.id.pull_refresh_list);
		
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

		adapter  = new  SimpleAdapter(this, testdata, R.layout.list_item, new String[]{"name","age","time","group","price"}, new int[]{R.id.name,R.id.age,R.id.time,R.id.group,R.id.price});   
		
		///////////////////////////////////////////////////////////////////////////////////////////
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
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
				Toast.makeText(ListActivity_Baoxian.this, "End of List!", Toast.LENGTH_SHORT).show();
			}
		});
		///////////////////////////////////////////////////////////////////////////////////
		listView = mPullRefreshListView.getRefreshableView();

		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(listView);

		//mListItems = new LinkedList<String>();
		//mListItems.addAll(Arrays.asList(mStrings));

		//mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);

		/**
		 * Add Sound Event Listener
		 */
		SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(this);
		soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
		soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
		soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
		mPullRefreshListView.setOnPullEventListener(soundListener);

		// You can also just use setListAdapter(mAdapter) or
		// mPullRefreshListView.setAdapter(mAdapter)
		listView.setAdapter(adapter);
		listView.setClickable(true);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(ListActivity_Baoxian.this, "Start Act11!", Toast.LENGTH_SHORT).show();
				
			}
		});
	}
	
	

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		HashMap<String, Object> view = (HashMap<String, Object>) l.getItemAtPosition(position);
		
		switch(position)
		{
		default:
			Toast.makeText(ListActivity_Baoxian.this, "Start Act11!", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.setClass(this,Activity_Item_Baoxian.class);
			startActivity(intent);
			Toast.makeText(ListActivity_Baoxian.this, "Start Act12!", Toast.LENGTH_SHORT).show();
			break;
			
		}
		
		
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

	public class GetDataTask extends AsyncTask<Void, Void, List<Map<String,Object>>> {

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

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
/*
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;

		menu.setHeaderTitle("Item: " + getListView().getItemAtPosition(info.position));
		menu.add("Item 1");
		menu.add("Item 2");
		menu.add("Item 3");
		menu.add("Item 4");

		super.onCreateContextMenu(menu, v, menuInfo);
	}
	*/

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
				// view
		menu.findItem(R.id.settings).setVisible(true);
				return super.onPrepareOptionsMenu(menu);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// The action bar home/up action should open or close the drawer.
				// ActionBarDrawerToggle will take care of this.
				
				// Handle action buttons
				switch (item.getItemId()) {
				/*
				case R.id.:
					// create intent to perform web search for this planet
					Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
					intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
					// catch event that there's no activity to handle intent
					if (intent.resolveActivity(getPackageManager()) != null) {
						startActivity(intent);
					} else {
						Toast.makeText(this, R.string.app_not_available,
								Toast.LENGTH_LONG).show();
					}
					return true;
					*/
				case R.id.settings:{
					Intent intent2 = new Intent();
					intent2.setClass(this, Settings.class);
					startActivity(intent2);
				}
				default:
					return super.onOptionsItemSelected(item);
				}
	}


}
