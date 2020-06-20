package com.example.project_test.Emergency;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project_test.MyPageActivity;
import com.example.project_test.R;
import com.example.project_test.ShakeNumberActivity;

import java.util.ArrayList;


public class EmergencyActivity extends AppCompatActivity {
    Activity act = this;
    Toolbar toolbar;
    GridView gv;

    ArrayList<Bitmap> picArr = new ArrayList<Bitmap>();//이미지배열
    ArrayList<String> textArr = new ArrayList<String>();//텍스트배열열
    Bitmap bm1, bm2, bm3, bm4, bm5, bm6;

    private boolean mFlashOn;//손전등 켬/끔
    private CameraManager cm;
    private String mCameraId;
    private SoundPool sound_pool;
    private int sound_beep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.mypage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //그리드뷰
        bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.flash);
        bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.message);
        bm3 = BitmapFactory.decodeResource(getResources(), R.drawable.shakemap);
        bm4 = BitmapFactory.decodeResource(getResources(), R.drawable.siren);
        bm5 = BitmapFactory.decodeResource(getResources(), R.drawable.setting);
        bm6 = BitmapFactory.decodeResource(getResources(), R.drawable.call);
        Bitmap bms[] = {bm1, bm2, bm3, bm4, bm5, bm6};
        String str[] = {"손전등", "위치전송", "흔들림횟수", "경고음", "설정", "긴급전화"};
        for (int i = 0; i < bms.length; i++) {
            picArr.add(bms[i]);
        }
        for (int i = 0; i < str.length; i++) {
            textArr.add(str[i]);
        }
        gv = findViewById(R.id.gv1);
        MyGridAdapter gAdapter = new MyGridAdapter();
        gv.setAdapter(gAdapter);

        //경고음
        sound_pool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sound_beep = sound_pool.load(EmergencyActivity.this, R.raw.sirensound, 1);

        //카메라(손전등사용)
        cm = (CameraManager) getSystemService(CAMERA_SERVICE);

        //이벤트
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                            Toast.makeText(getApplicationContext(), "핸드폰이 손전등기능을 지원하지 않아\n사용하실 수 없습니다", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        flashlight();
                        return;

                    case 1:
                        try {
                            String to = "01000000000";
                            String message = "tesetstsetstestset";

                            Uri smsUri = Uri.parse("tel:"+to);
                            Intent it = new Intent(Intent.ACTION_VIEW, smsUri);
                            it.putExtra("address", to);
                            it.putExtra("sms_body", message);
                            it.setType("vnd.android-dir/mms-sms");
                            startActivity(it);

                            /*String sms = "테스트";
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phoneNumber, null, sms, null, null);*/
                            Toast.makeText(getApplicationContext(), "전송완료", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "전송실패", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        return;

                    case 2:
                        //흔들림횟수 화면이동
                        Intent count_itnt = new Intent(EmergencyActivity.this, ShakeNumberActivity.class);
                        startActivity(count_itnt);
                        return;
                    case 3: //경고음 버튼 눌렀을 때
                        try {
                            sound_pool.play(sound_beep, 3f, 3f, 0, -1, 1f);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "경고음 실패", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    case 4:
                        Intent intent = new Intent(EmergencyActivity.this, EmergencySet.class);
                        startActivity(intent);
                        return;
                    case 5: //긴급전화 버튼 눌렀을 때
                       Intent intent2 = new Intent(Intent.ACTION_CALL);
                       intent2.setData(Uri.parse("tel:01022222222")); //마이페이지에서 설정한 전화번호
                        try {
                            startActivity(intent2);
                            Toast.makeText(EmergencyActivity.this, "전화걸기 성공", Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                            Toast.makeText(EmergencyActivity.this, "전화걸기 실패", Toast.LENGTH_SHORT).show();
                        }

                        return;

                }//switch end
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


    //어댑터
    public class MyGridAdapter extends BaseAdapter {
        LayoutInflater inflater;

        public MyGridAdapter() {
            inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        //그리드뷰에 출력할 목록 수
        @Override
        public int getCount() {
            return picArr.size();
        }

        //아이템 호츌
        @Override
        public Object getItem(int position) {
            return picArr.get(position);
        }

        //아이템ID
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.emergency_item, parent, false);
            }
            final ImageView imageView = (ImageView) convertView.findViewById(R.id.imgView1);
            TextView textView = (TextView) convertView.findViewById(R.id.tv1);
            imageView.setImageBitmap(picArr.get(position));
            textView.setText(textArr.get(position));


            return convertView;
        }
    }

    //손전등 켜기
    //@RequiresApi(api = Build.VERSION_CODES.M)
    void flashlight() {
        if (mCameraId == null) {
            try {
                for (String id : cm.getCameraIdList()) {
                    CameraCharacteristics c = cm.getCameraCharacteristics(id);
                    Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
                    if (flashAvailable != null && flashAvailable && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                        mCameraId = id;
                        break;
                    }
                }
            } catch (CameraAccessException e) {
                mCameraId = null;
                e.printStackTrace();
                return;
            }
        }
        mFlashOn = !mFlashOn;
        try {
            cm.setTorchMode(mCameraId, mFlashOn);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}



