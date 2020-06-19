package com.example.project_test;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.project_test.qa.qaContent.qaActivityContent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class RoomActivity extends AppCompatActivity implements OnMapReadyCallback {

    MapFragment mf;
    GoogleMap gMap;
    GroundOverlayOptions roomMark1, roomMark2, roomMark3;

    ImageButton back;
    Button write;

    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        //권한설정
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MODE_PRIVATE);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.fish);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mf = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mf.getMapAsync(this);
    }

    //상단탭 메뉴
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    //메뉴액션 --home:마이페이지 --message:쪽지함
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                //마이페이지 화면
                return true;
            case android.R.id.message:
                //쪽지함 화면
                return true;
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng location = new LatLng(37.568256, 126.897240);//--현재위치로 바꿔보기--
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));

        gMap.getUiSettings().setZoomControlsEnabled(true);

        gMap.setMyLocationEnabled(true); //현재위치를 GPS 모듈에서 받아올 수 있도록 설정
        gMap.getUiSettings().setMyLocationButtonEnabled(true); //현재위치 버튼 추가

        LatLng house1 = new LatLng(37.5830, 126.9223);
        LatLng house2 = new LatLng(37.5828, 126.9236);
        LatLng house3 = new LatLng(37.5833, 126.9236);

        LatLng latLng[] = new LatLng[] {house1,house2,house3};
        String titles[] = new String[] {"명전앞원룸","방있음","고시원"};

        //방위치 마커
        for (int idx = 0; idx<3; idx++){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng[idx]);
        markerOptions.title(titles[idx]);
        markerOptions.snippet("글쓴이");
        markerOptions.alpha(0.5f);

        gMap.addMarker(markerOptions);
        }

        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(RoomActivity.this, "게시글 보기", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RoomActivity.this, qaActivityContent.class);
                intent.putExtra("제목",marker.getTitle());
                startActivity(intent);
                return false;
            }
        });
    }
}
