package com.example.project_test.Emergency;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.project_test.Emergency.EmergencyActivity;
import com.example.project_test.Emergency.MsgNumList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShakeService extends Service implements SensorEventListener {

    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;

    private static final int SHAKE_THRESHOLD = 10000;    //속도가 얼마 이상일 때 흔들림 감지(민감도)
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;

    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;

    private String ID;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("svc", "서비스실행");
        //시스템 서비스를 가져와서 SensorManager 타입으로 저장
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); // 센서 접근 할 수 있는 서비스
        accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //가속도 센서

        //SharedPreferences에서 로그인한 아이디 읽어오기
        SharedPreferences preferences = getSharedPreferences("lastID", MODE_PRIVATE);
        ID = preferences.getString("IDkey", "none");
        Log.i("아이디값", ID);

        if(ID.equals("none"))
        {
            Log.d("svc", "서비스끝");
        }

        //시스템설정값 가져오기
//        Api api = Api.Factory.INSTANCE.create();
//        api.getemergency(ID).enqueue(new Callback<MsgNumList>() {
//            @Override
//            public void onResponse(Call<MsgNumList> call, Response<MsgNumList> response) {
//                MsgNumList mnl = response.body();
//                List<MsgNumData> mData = mnl.items;
//
//                int volume = mData.get(0).sysvolume;
//
//                if(boolsound) {
//                    try {
//                        float sv = volume/100.0f; //(0~1)
//                        sp = sound_pool.play(sound_beep, sv, sv, 0, -1, 1f); // (재생시킬 파일, 왼쪽 볼륨 크기, 오른쪽 볼륨 크기, 우선순위, 재생횟수, 재생속도)
//                        boolsound = false;
//                    } catch (Exception e) {
//                        Toast.makeText(getApplicationContext(), "경고음 실패", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else {
//                    sound_pool.pause(sp);
//                    boolsound = true;
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MsgNumList> call, Throwable t) {
//
//            }
//        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (accelerormeterSensor != null)
            sensorManager.registerListener(this, accelerormeterSensor, SensorManager.SENSOR_DELAY_GAME);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //센서 정보가 변하면 발생
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);
            if (gabOfTime > 100) {       //최근 측정한 시간과 현재 시간을 비교하여 0.1초 이상되었을 때
                lastTime = currentTime;
                x = event.values[SensorManager.DATA_X];
                y = event.values[SensorManager.DATA_Y];
                z = event.values[SensorManager.DATA_Z];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

                //speed가 설정한 민감도 이상
                if (speed > SHAKE_THRESHOLD) {
                    Log.d("servicecc", "흔들림");
                    //흔들리면 이벤트 발생

                    //앱 실행(반응 속도가 엄청 느림, 앱 연 상태에서 계속 열림)
                    Intent intent = new Intent(getApplicationContext(), EmergencyActivity.class);
                    intent.putExtra("code", 1); //흔들림으로 열었을 경우
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //새로운 TASK
                    getApplicationContext().startActivity(intent);

                    Toast.makeText(getApplicationContext(), "앱열림", Toast.LENGTH_SHORT).show();
                    /*Intent intent = getPackageManager().getLaunchIntentForPackage("package com.example.a2222222");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);*/


                    //문자보내기
                }

                lastX = event.values[DATA_X];
                lastY = event.values[DATA_Y];
                lastZ = event.values[DATA_Z];
            }

        }
    }
}