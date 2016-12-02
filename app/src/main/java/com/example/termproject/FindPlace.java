package com.example.termproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import static com.example.termproject.R.id.map;

public class FindPlace extends AppCompatActivity implements OnMapReadyCallback {

    final private String TAG = "LocationServicesTest";
    final private int MY_PERMISSION_REQUEST_LOCATION = 100;

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    static double latitude= 0.0;
    static double longitude = 0.0;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private GoogleMap googleMap;

    private TextView resultText;
    private EditText editText;
    private Button findBtn;
    private Geocoder geocoder;
    private List<Address> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        Log.i("practice8","onStart ,test");

        resultText = (TextView) findViewById(R.id.map);
        editText = (EditText) findViewById(R.id.edit);
        findBtn = (Button)findViewById(R.id.button);
        geocoder = new Geocoder(this);

        if (isPermissionGranted()) { // 위치 접근 권한이 허가된 상태
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(new MyConnectionCallBack())
                    .addOnConnectionFailedListener(new MyOnConnectionFailedListener())
                    .addApi(LocationServices.API)
                    .build();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private class FindButtonClickListener implements View.OnClickListener {
        public void onClick(View v) {
            list = null;
            String str = editText.getText().toString();
            try {
                list = geocoder.getFromLocationName(str, 10);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
            }

            if (list != null) {
                if (list.size() == 0) {
                    resultText.setText("해당되는 주소 정보는 없습니다");
                } else {
                    // 해당되는 주소로 인텐트 날리기
                    Address addr = list.get(0);
                    latitude = addr.getLatitude();
                    longitude = addr.getLongitude();

                    resultText.setText("검색위치 : 위도 - " + latitude + ", 경도 - " + longitude);

                }
                moveCamera(latitude, longitude);
            }
        }
    }


    private boolean isPermissionGranted() {
        String[] PERMISSIONS_STORAGE = { // 요청할 권한 목록을 설정
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        if (ActivityCompat.checkSelfPermission(FindPlace.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    FindPlace.this, // MainActivity 액티비티의 객체 인스턴스를 나타냄
                    PERMISSIONS_STORAGE, // 요청할 권한 목록을 설정한 String 배열
                    MY_PERMISSION_REQUEST_LOCATION ); // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
            return false;
        } else
            return true;
    }

    private class MyConnectionCallBack implements GoogleApiClient.ConnectionCallbacks {
        public void onConnected(Bundle bundle) {
            //mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            updateUI();
            Log.i("practice8","location" + latitude + "," + longitude);
            resultText.setText("현재 위치 : 위도-" + latitude + ", 경도-" + longitude);
        } // 연결 성공시 호출

        public void onConnectionSuspended(int i) {
            Log.i(TAG,"unConnection");
        } // 일시 연결 해제시 호출
    }
    private class MyOnConnectionFailedListener implements
            GoogleApiClient.OnConnectionFailedListener {
        public void onConnectionFailed(ConnectionResult connectionResult) {
            Log.i(TAG, "onConnectionFailed");
        }
    } // 연결 실패시 호출

    protected void onStart() {
        Log.i("practice8","onStart , connect request");
        super.onStart();
        mGoogleApiClient.connect(); //연결 요청
    }
    protected void onStop() {
        Log.i("practice8","onStop" + latitude + "," + longitude);
        mGoogleApiClient.disconnect();
        super.onStop(); //연결 해제 요청
    }
    private void updateUI() {
        if(mCurrentLocation != null) {
            latitude = mCurrentLocation.getLatitude();
            longitude = mCurrentLocation.getLongitude();
        }
    }


    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;
    }

    private void moveCamera(double x, double y){
        LatLng latLng = new LatLng(x, y);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.addMarker(new MarkerOptions().position(latLng).title("낙산공원"));
    }
}