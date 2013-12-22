package com.bsodcorp.android.myfirstmapsproject;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends Activity {

	GoogleMap mMap;

	private List<String> mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpMapIfNeeded();
		
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_menu);

        // Set the adapter for the list view
        mPlanetTitles = new ArrayList<String>();
        TextView header = (TextView)getLayoutInflater().inflate(R.layout.drawer_list_item, null);
        header.setText(R.string.my_places);
        mDrawerList.addHeaderView(header);
        mDrawerList.setAdapter(new MyPlacesAdapter(this, mPlanetTitles));
        // Set the list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
	}

	@Override
	protected void onResume() {
		super.onResume();
		//Check if the Google Play Services are available in the device. If not, show a dialog with the error.
		int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		Log.d("isGooglePlayServicesAvailable", result + "");
		if (result != ConnectionResult.SUCCESS) {
			GooglePlayServicesUtil.getErrorDialog(result, this, 0);
		}
		setUpMapIfNeeded();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link MapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	/**
	 * This is where we can add markers or lines, add listeners or move the camera.
	 * In this case, we just add a marker near Africa.
	 * <p>
	 * This should only be called once and when we are sure that {@link #mMap} is not null.
	 */
	private void setUpMap() {
		//mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		//mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker").flat(true));
		mMap.setMyLocationEnabled(true);
		
		//Get my last known location and move & zoom in to it...
		Criteria cri = new Criteria();
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    String provider = lm.getBestProvider(cri, true);
	    Location myLocation = lm.getLastKnownLocation(provider);
		if (myLocation != null) {
			LatLng ll = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
			mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ll, 13));
		}
	}
	
}
