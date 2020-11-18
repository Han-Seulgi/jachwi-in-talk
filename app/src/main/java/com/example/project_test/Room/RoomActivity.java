package com.example.project_test.Room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.project_test.Api;
import com.example.project_test.Mypage.MyPageActivity;
import com.example.project_test.NoteActivity;
import com.example.project_test.R;
import com.example.project_test.Room.RoomContent.RoomContentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
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
    public int size;
    int i=0;

    private final int WRITE_POST = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        //권한설정
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MODE_PRIVATE);

        tabTitle = findViewById(R.id.title);
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
                intent.putExtra("request", WRITE_POST);
                startActivityForResult(intent, WRITE_POST);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("roomref", "requestcode: "+requestCode);
        Log.i("roomref", "requestcode: "+requestCode+"resultcode"+resultCode);
        switch (requestCode) {
            case WRITE_POST:
                if (resultCode == RESULT_OK) {
                    gMap.clear();

                    Api api = Api.Factory.INSTANCE.create();

                    api.getAllRoom().enqueue(new Callback<RoomList>() {
                        @Override
                        public void onResponse(Call<RoomList> call, Response<RoomList> response) {
                            RoomList rlist = response.body();
                            List<RoomData> rooms = rlist.items;

                            roomlist = new String[rooms.size()];

                            final ArrayList<String> post_code = new ArrayList<>();
                            final ArrayList<String> post_title = new ArrayList<>();
                            final ArrayList<String> post_con = new ArrayList<>();
                            final ArrayList<String> id = new ArrayList<>();
                            final ArrayList<String> room_lct = new ArrayList<>();
                            final ArrayList<String> room_day = new ArrayList<>();

                            int j=0;
                            for(RoomData d:rooms) {
                                post_code.add(d.post_code);
                                post_title.add(d.post_title);
                                post_con.add(d.post_con);
                                id.add(d.id);
                                room_lct.add(d.room_lct);
                                room_day.add(d.room_day);
                                Log.e("roomlist", d.toString());
                                j++;
                            }

                            List<Address> list = null;

                            for(; i<post_code.size(); i++) {
                                try {

                                    Geocoder geocoder = new Geocoder(RoomActivity.this);
                                    list = geocoder.getFromLocationName(room_lct.get(i),10);

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

                                //방위치 마커
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(latLng[0]);
                                markerOptions.title(post_title.get(i));
                                markerOptions.snippet(id.get(i));
                                markerOptions.alpha(0.5f);

                                gMap.addMarker(markerOptions);
                                gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker) {
                                        int a=0;
                                        for(int i=0; i<post_title.size(); i++) {
                                            if(post_title.get(i).equals(marker.getTitle())) {
                                                a = i;
                                            }
                                        }
                                        Intent intent = new Intent(RoomActivity.this, RoomContentActivity.class);
                                        intent.putExtra("제목", post_title.get(a));
                                        intent.putExtra("아이디", id.get(a));
                                        intent.putExtra("만료날짜", room_day.get(a));
                                        intent.putExtra("내용", post_con.get(a));
                                        intent.putExtra("코드",post_code.get(a));
                                        startActivity(intent);
                                        return false;
                                    }
                                });


                            } //for문 끝
                        }
                        @Override
                        public void onFailure(Call<RoomList> call, Throwable t) {
                            Log.i("실패", t.getMessage());
                        }
                    });
                }
                break;
        }
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
                Intent note_itnt = new Intent(getApplicationContext(), NoteActivity.class);
                startActivity(note_itnt);
                return true;
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        gMap.getUiSettings().setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gMap.setMyLocationEnabled(true); //현재위치를 GPS 모듈에서 받아올 수 있도록 설정
        gMap.getUiSettings().setMyLocationButtonEnabled(true); //현재위치 버튼 추가

        LatLng location = new LatLng(37.5830, 126.9230); //현재 위치로 바꿔보기
        //현재위치로 카메라 이동
//        double lat = gMap.getMyLocation().getLatitude();
//        double lng = gMap.getMyLocation().getLongitude();
//        LatLng location = new LatLng(lat, lng);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));

        //보금자리 목록 가져오기
        final Api api = Api.Factory.INSTANCE.create();

        api.getAllRoom().enqueue(new Callback<RoomList>() {
            @Override
            public void onResponse(Call<RoomList> call, Response<RoomList> response) {
                RoomList rlist = response.body();
                List<RoomData> rooms = rlist.items;

                roomlist = new String[rooms.size()];

                final ArrayList<String> post_code = new ArrayList<>();
                final ArrayList<String> post_title = new ArrayList<>();
                final ArrayList<String> post_con = new ArrayList<>();
                final ArrayList<String> id = new ArrayList<>();
                final ArrayList<String> room_lct = new ArrayList<>();
                final ArrayList<String> room_p = new ArrayList<>();
                final ArrayList<String> room_day = new ArrayList<>();
                final ArrayList<String> post_day = new ArrayList<>();

                int j=0;
                for(RoomData d:rooms) {
                    post_code.add(d.post_code);
                    post_title.add(d.post_title);
                    post_con.add(d.post_con);
                    id.add(d.id);
                    room_lct.add(d.room_lct);
                    room_p.add(d.room_p);
                    room_day.add(d.room_day);
                    post_day.add(d.post_day);
                    Log.e("roomlist", d.toString());
                    j++;
                }

                List<Address> list = null;

                for(int i=0; i<post_code.size(); i++) {
                    try {

                        Geocoder geocoder = new Geocoder(RoomActivity.this);
                        list = geocoder.getFromLocationName(room_lct.get(i),10);

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

                    //방위치 마커
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng[0]);
                    markerOptions.title(post_title.get(i));
                    markerOptions.snippet(id.get(i));
                    markerOptions.alpha(0.5f);

                    gMap.addMarker(markerOptions);

                    gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            int a=0;
                            for(int i=0; i<post_title.size(); i++) {
                                if(post_title.get(i).equals(marker.getTitle())) {
                                    a = i;
                                }
                            }
                            Intent intent = new Intent(RoomActivity.this, RoomContentActivity.class);
                            intent.putExtra("제목", post_title.get(a));
                            intent.putExtra("아이디", id.get(a));
                            intent.putExtra("만료날짜", room_day.get(a));
                            intent.putExtra("내용", post_con.get(a));
                            intent.putExtra("장소",room_lct.get(a));
                            intent.putExtra("코드",post_code.get(a));
                            intent.putExtra("날짜",post_day.get(a));
                            intent.putExtra("가격",room_p.get(a));
                            startActivity(intent);
                            return false;
                        }
                    });

                } //for문 끝
            }
            @Override
            public void onFailure(Call<RoomList> call, Throwable t) {
                Log.i("실패", t.getMessage());
            }
        });
    }
}