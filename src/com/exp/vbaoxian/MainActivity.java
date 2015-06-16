package com.exp.vbaoxian;

import com.exp.vbaoxian.Demo.DemoOnClickListener;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements DemoOnClickListener {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mAppTitle;
	private String[] mTitles;
	private LinearLayout mDrawerView;
	private Fragment mContent;
	private FragmentManager mFragmentManager;
	private Fragment fragment0, fragment1, fragment2, fragment3;

	//
	// public OnClickListener onclick = new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// switch(v.getId()){
	// case R.id.act_shift:
	// Intent intent = new Intent();
	// intent.setClass(getApplicationContext(), Act_shift_test.class);
	// startActivity(intent);
	// break;
	// default:
	// break;
	// }
	// }
	// };

	public void DemoOnClick() {
		Intent intent = new Intent();
		intent.setClass(this, ListActivity_Baoxian.class);
		startActivity(intent);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			java.lang.reflect.Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");

			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			// presumably, not relevant
		}
		mFragmentManager = getFragmentManager();
		mAppTitle = mDrawerTitle = getTitle();
		mTitles = getResources().getStringArray(R.array.pages);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_list);
		mDrawerView = (LinearLayout) findViewById(R.id.left_drawer);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mAppTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			fragment0 = new Demo();
			fragment1 = new ListFragment();
			fragment2 = new MineFragment();
			fragment3 = new Settings();
			mContent = fragment0;
			mFragmentManager.beginTransaction()
					.add(R.id.content_frame, fragment0).commit();
			mDrawerList.setItemChecked(0, true);
			setTitle(mTitles[0]);
			mDrawerLayout.closeDrawer(mDrawerView);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	// public void onClick(View v) {
	// switch(v.getId()){
	// case R.id.act_shift:
	// Intent intent = new Intent();
	// intent.setClass(this, Act_shift_test.class);
	// startActivity(intent);
	// break;
	// default:
	// break;
	// }
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerView);
		// menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {
		// case R.id.action_websearch:
		// // create intent to perform web search for this planet
		// Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
		// intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
		// // catch event that there's no activity to handle intent
		// if (intent.resolveActivity(getPackageManager()) != null) {
		// startActivity(intent);
		// } else {
		// Toast.makeText(this, R.string.app_not_available,
		// Toast.LENGTH_LONG).show();
		// }
		// return true;
		case R.id.settings: {
			Fragment fragment3 = new Settings();
			FragmentManager fragmentmanager3 = getFragmentManager();
			fragmentmanager3.beginTransaction()
					.replace(R.id.content_frame, fragment3).commit();
			setTitle(mTitles[3]);
			mDrawerLayout.closeDrawer(mDrawerView);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		switch (position) {
		case 0:
			switchContent(mContent, fragment0);
			mDrawerList.setItemChecked(position, true);
			setTitle(mTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerView);
			break;
		case 1:
			switchContent(mContent, fragment1);
			mDrawerList.setItemChecked(position, true);
			setTitle(mTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerView);
			break;
		case 2:
			switchContent(mContent, fragment2);
			mDrawerList.setItemChecked(position, true);
			setTitle(mTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerView);
			break;
		case 3:
			switchContent(mContent, fragment3);
			mDrawerList.setItemChecked(position, true);
			setTitle(mTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerView);
			break;
		}
	}

	public void switchContent(Fragment from, Fragment to) {
		if (mContent != to) {
			mContent = to;
			FragmentTransaction transaction = mFragmentManager
					.beginTransaction();
			if (!to.isAdded()) { // 先判断是否被add过
				transaction.hide(from).add(R.id.content_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else {
				transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
			}
		}
	}

	public void setTitle(CharSequence title) {
		mAppTitle = title;
		getActionBar().setTitle(title);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@SuppressWarnings("deprecation")
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@SuppressWarnings("deprecation")
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
}