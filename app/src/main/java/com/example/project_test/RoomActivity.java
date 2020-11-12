package com.example.project_test;

import android.Manifest;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.project_test.Content.ContentWithPicture;
import com.example.project_test.Mypage.MyPageActivity;
import com.example.project_test.qa.qaListData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomActivity extends AppCompatActivity implements OnMapReadyCallback {

    MapFragment mf;
    GoogleMap gMap;

    Toolbar toolbar;
    TextView tabTitle;
    String tt;
    double latitude, longitude;
    ArrayList<String> room_lct = new ArrayList<>();
    public String[] roomlist;
    public List<RoomList> rooms;
    public int size;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        //권한설정
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MODE_PRIVATE);

        tabTitle =findViewById(R.id.title);
        tt = tabTitle.getText().toString();

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.mypage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mf = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mf.getMapAsync(this);
        Button write = findViewById(R.id.write);

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoomActivity.this, RoomWriting.class);
                startActivity(intent);

            }
        });
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
                Intent mypage_itnt = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(mypage_itnt);
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

        LatLng location = new LatLng(37.5830, 126.9230); //현재 위치로 바꿔보기
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));

        gMap.getUiSettings().setZoomControlsEnabled(true);

        gMap.setMyLocationEnabled(true); //현재위치를 GPS 모듈에서 받아올 수 있도록 설정
        gMap.getUiSettings().setMyLocationButtonEnabled(true); //현재위치 버튼 추가

        //보금자리 목록 가져오기
        Api api = Api.Factory.INSTANCE.create();

        api.getAllRoom().enqueue(new Callback<RoomList>() {
            @Override
            public void onResponse(Call<RoomList> call, Response<RoomList> response) {
                RoomList rlist = response.body();
                //public List<RoomList> rooms;
                rooms = rlist.items;
                roomlist = new String[rooms.size()];
                int j=0;
                for(RoomList d:rooms) {
                    room_lct.add(d.room_lct);
                    roomlist[j] = d.toString();
                    j++;
                }

                List<Address> list = null;

                for(int i=0; i<roomlist.length; i++) {
                    try {

                        Geocoder geocoder = new Geocoder(RoomActivity.this);
                        list = geocoder.getFromLocationName(roomlist[i],10);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
                    }

                    if (list != null) {
                        if (list.size() == 0) {
                            Toast.makeText(RoomActivity.this, "해당되는 주소 정보는 없습니다", Toast.LENGTH_LONG);
                        } else {
                            latitude = list.get(0).getLatitude(); //위도
                            longitude = list.get(0).getLongitude(); // 경도
                        }
                    }

                    LatLng house1 = new LatLng(latitude, longitude);
                    LatLng latLng[] = new LatLng[]{house1};
                    String titles[] = new String[]{"명전앞원룸"};

                    //방위치 마커
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng[0]);
                    markerOptions.title(titles[0]);
                    markerOptions.snippet("글쓴이");
                    markerOptions.alpha(0.5f);

                    gMap.addMarker(markerOptions);

                    //클릭하면 해당 게시판으로 넘어가기~~~
                    /*gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            Toast.makeText(RoomActivity.this, "게시글 보기", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RoomActivity.this, ContentWithPicture.class);
                            intent.putExtra("제목", marker.getTitle());
                            intent.putExtra("탭이름", tt);
                            startActivity(intent);
                            return false;
                        }
                    });*/

                } //for문 끝
            }
            @Override
            public void onFailure(Call<RoomList> call, Throwable t) {
                Log.i("실패", t.getMessage());
            }
        });
    }
}