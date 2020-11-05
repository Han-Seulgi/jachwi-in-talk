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
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project_test.Api;
import com.example.project_test.LoginActivity;
import com.example.project_test.Mypage.MyPageActivity;
import com.example.project_test.R;
import com.example.project_test.ShakeNumberActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    private SoundPool sound_pool; //사이렌
    private int sound_beep; //사이렌
    private boolean boolsound=true;
    int sp;

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


        sound_pool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0); // (동시에 재생 할 수 있는 개수, 미디어의 볼륨, 품질)
        sound_beep = sound_pool.load(EmergencyActivity.this, R.raw.sirensound, 1); // (Context, 로드하고 싶은 음원, 우선순위)

        //카메라(손전등사용)
        cm = (CameraManager) getSystemService(CAMERA_SERVICE);

        final Api api = Api.Factory.INSTANCE.create();

        //이벤트
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) { //플래시 존재 여부 체크
                            Toast.makeText(getApplicationContext(), "핸드폰이 손전등기능을 지원하지 않아\n사용하실 수 없습니다", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        flashlight();
                        return;

                    case 1:
                            //문자번호 가져오기
                            api.getmsgnum(LoginActivity.user_ac).enqueue(new Callback<MsgNumList>() {
                                @Override
                                public void onResponse(Call<MsgNumList> call, Response<MsgNumList> response) {
                                    MsgNumList mnl = response.body();
                                    List<MsgNumData> mData = mnl.items;

                                    ArrayList<String> mn = new ArrayList<>();

                                    for (MsgNumData d:mData) {
                                        mn.add(d.msgnum);
                                    }
                                    if(mn.size() == 0) {
                                        AlertDialog dialog;
                                        AlertDialog.Builder builder = new AlertDialog.Builder(EmergencyActivity.this);
                                        dialog = builder.setMessage("설정에서 연락처를 저장하세요").setNegativeButton("확인", null).create();
                                        dialog.show();
                                    }
                                    else {
                                        try {
                                            String msgnum[] = mn.toArray(new String[mn.size()]);

                                            //String to = "01000000000";
                                            String message = "테스트문자"; // 사용자의 위치

                                            for (String num : msgnum) {
                                                String to = num;  //마이페이지에서 설정한 문자 번호
                                                //문자보내기
                                                SmsManager smsManager= SmsManager.getDefault();
                                                smsManager.sendTextMessage(to, null, message, null, null);
//                                                Uri smsUri = Uri.parse("tel:" + to);
//                                                Intent it = new Intent(Intent.ACTION_VIEW, smsUri);
//                                                it.putExtra("address", to);
//                                                it.putExtra("sms_body", message);
//                                                it.setType("vnd.android-dir/mms-sms");
//                                                startActivity(it);
                                            }
                                            Toast.makeText(getApplicationContext(), "전송완료", Toast.LENGTH_SHORT).show();
                                        }
                                        catch (Exception e)  {
                                            Toast.makeText(getApplicationContext(), "전송실패", Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MsgNumList> call, Throwable t) {

                                }
                            });
                        return;

                    case 2:
                        //흔들림횟수 화면이동
                        Intent count_itnt = new Intent(EmergencyActivity.this, ShakeNumberActivity.class);
                        startActivity(count_itnt);
                        return;
                    case 3: //경고음 버튼 눌렀을 때
                        //시스템설정값 가져오기
                        api.getemergency(LoginActivity.user_ac).enqueue(new Callback<MsgNumList>() {
                            @Override
                            public void onResponse(Call<MsgNumList> call, Response<MsgNumList> response) {
                                MsgNumList mnl = response.body();
                                List<MsgNumData> mData = mnl.items;

                                int volume = mData.get(0).sysvolume;

                                if(boolsound) {
                                    try {
                                        float sv = volume/100.0f; //(0~1)
                                        sp = sound_pool.play(sound_beep, sv, sv, 0, -1, 1f); // (재생시킬 파일, 왼쪽 볼륨 크기, 오른쪽 볼륨 크기, 우선순위, 재생횟수, 재생속도)
                                        boolsound = false;
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "경고음 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    sound_pool.pause(sp);
                                    boolsound = true;
                                }
                            }

                            @Override
                            public void onFailure(Call<MsgNumList> call, Throwable t) {

                            }
                        });
                        return;

                    case 4:
                        //설정 화면
                        Intent intent = new Intent(EmergencyActivity.this, EmergencySet.class);
                        startActivity(intent);
                        return;

                    case 5: //긴급전화 버튼 눌렀을 때
                        //전화번호 가져오기
                        api.getemergency(LoginActivity.user_ac).enqueue(new Callback<MsgNumList>() {
                            @Override
                            public void onResponse(Call<MsgNumList> call, Response<MsgNumList> response) {
                                MsgNumList mnl = response.body();
                                List<MsgNumData> mData = mnl.items;

                                String callnum = mData.get(0).callnum;

                                if(callnum == null || callnum.equals(""))
                                {
                                    AlertDialog dialog;
                                    AlertDialog.Builder builder = new AlertDialog.Builder(EmergencyActivity.this);
                                    dialog = builder.setMessage("설정에서 연락처를 저장하세요").setNegativeButton("확인", null).create();
                                    dialog.show();
                                }
                                else {
                                    Intent intent2 = new Intent(Intent.ACTION_CALL);
                                    intent2.setData(Uri.parse("tel:" + callnum));
                                    try {
                                        startActivity(intent2);
                                        Toast.makeText(EmergencyActivity.this, "전화걸기 성공", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(EmergencyActivity.this, "전화걸기 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<MsgNumList> call, Throwable t) {

                            }
                        });


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


    @RequiresApi(api = Build.VERSION_CODES.M)


    void flashlight() {
        if (mCameraId == null) {
            try {
                for (String id : cm.getCameraIdList()) { // 스마트폰에 탑재된 카메라의 식별자를 얻음
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
            cm.setTorchMode(mCameraId, mFlashOn); //mFlashOn이 true라면 플래시 켜기. false라면 플래시 끄기
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}



