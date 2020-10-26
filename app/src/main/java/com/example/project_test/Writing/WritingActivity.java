package com.example.project_test.Writing;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project_test.Api;
import com.example.project_test.CookWrite;
import com.example.project_test.LoginActivity;
import com.example.project_test.R;
import com.example.project_test.Write;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WritingActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button writing;
    EditText tedit, cedit, pedit, nedit, cedit2;
    TextView tv0, tv1 , title2;
    String post_title, post_con, cook_src, cook_rcp;
    ImageButton imgup;
    int board_code;

    private AlertDialog dialog;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE=2;

    private static Uri mImageCaptureUri;
    private String imgPath;
    private Bitmap photoBitmap;
    String folderName = "cameraTemp";
    File mediaFile = null;
    String fileName = "talkphoto.jpg";

    LinearLayout imgLayout;

    //upload test
    private String realPath;
    String imgTest;
    String imgString2;
    ArrayList<String> imgs = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_recipe);

        writing = findViewById(R.id.writing);
        tedit = findViewById(R.id.tedit);
        cedit = findViewById(R.id.cedit);
        pedit = findViewById(R.id.pedit);
        nedit = findViewById(R.id.nedit);
        cedit2 = findViewById(R.id.cedit2);
        title2 = findViewById(R.id.title2);
        imgup = findViewById(R.id.imgup);

        tv0 = findViewById(R.id.tv0);
        tv1 = findViewById(R.id.tv1);

        //imgTest = "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";

        imgLayout = findViewById(R.id.imgLayout);
        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        imgup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WritingActivity.this);
                builder.setMessage("이미지 선택");
                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        takePhotoAction();
                    }
                };
                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        takeAlbumAction();
                    }
                };
                DialogInterface.OnClickListener cancleListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                builder.setTitle("업로드할 이미지 선택");
                builder.setPositiveButton("사진촬영", cameraListener);
                builder.setNeutralButton("앨범선택", albumListener);
                builder.setNegativeButton("취소", cancleListener);
                builder.show();
            }
        });

        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post_title = tedit.getText().toString();
                post_con = cedit2.getText().toString();
                cook_src = nedit.getText().toString();
                cook_rcp = cedit.getText().toString();

                if(title2.getText().toString().equals("자취앤집밥")){
                    board_code = 11;
                }
                Log.i("결과는", LoginActivity.user_ac + post_title + post_con + board_code);

                if (post_title.equals("") || post_con.equals("") || cook_src.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WritingActivity.this);
                    dialog = builder.setMessage("글 작성이 완료되지 않았습니다.").setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                else {
                    final Api api = Api.Factory.INSTANCE.create();
                    api.Write(LoginActivity.user_ac, post_title, post_con, board_code).enqueue(new Callback<Write>() {
                        public void onResponse(Call<Write> call, Response<Write> response) {

                            api.CookWrite(cook_src, cook_rcp).enqueue(new Callback<CookWrite>() {
                                public void onResponse(Call<CookWrite> call, Response<CookWrite> response) {

                                    Log.i("결과는" , response.toString());
                                }
                                public void onFailure(Call<CookWrite> call, Throwable t) {
                                    Log.i("작성실패", t.getMessage());
                                }
                            });


                            if(imgString2 != null)
                            {
                                Log.i("writeimg", String.valueOf(imgString2.length()));
                                api.imgupload(LoginActivity.user_ac, imgString2).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        try {
                                            Log.i("img" , response.body().string().trim());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        //Img i = response.body();
                                        //String d = i.img_data;
                                        //boolean upload = i.insert;

                                        //if (upload==true){
                                            Log.i("img", "업로드 성공");
                                        //}
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Log.i("img", "업로드 실패"+t.getMessage());
                                    }
                                });
                            }
                            else Log.i("img", "사진 없이 올림");

                            Log.i("결과는" , response.toString());

                            AlertDialog.Builder builder = new AlertDialog.Builder(WritingActivity.this);
                            dialog = builder.setMessage("작성 완료됨").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).create();
                            dialog.show();
                        }
                        public void onFailure(Call<Write> call, Throwable t) {
                            Log.i("작성실패", t.getMessage());
                        }
                    });


                }
            }
        });

    }

    //사진촬영
    private void takePhotoAction() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri =  Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
        /*File photoFile = new File(Environment.getExternalStorageDirectory(),url);
        mImageCaptureUri = FileProvider.getUriForFile(getBaseContext(),"com.example.project_test", photoFile);*/

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(cameraIntent, PICK_FROM_CAMERA);

    }

    //갤러리에서 사진선택
    private void takeAlbumAction() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(albumIntent, PICK_FROM_ALBUM);
    }

    /*//파일 경로 생성
    private Uri getImageUri(String saveFile) {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()+"/download",
                folderName);
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("cameraapp","failed tot create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        if(saveFile !=null){
            mediaFile = new File(mediaStorageDir.getPath()+File.separator+saveFile);
        }
        else{
            mediaFile = new File(mediaStorageDir.getPath()+File.separator+"pic_"+timeStamp+".jpg");
        }
        mImageCaptureUri = Uri.fromFile(mediaFile);
        imgPath = mImageCaptureUri.getPath();
        return  mImageCaptureUri;
    }*/

    public String getRealPathFromURI(Uri contentUri){
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri,proj,null,null,null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return  cursor.getString(column_index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
                Log.e("앨범이미지크롭", mImageCaptureUri.getPath().toString());
                imgPath = getRealPathFromURI(mImageCaptureUri);//실제파일경로
                realPath = getRealPathFromURI(mImageCaptureUri);
                Log.e("앨범이미지경로",imgPath);
            }
            case PICK_FROM_CAMERA: {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri,"image/*");

                intent.putExtra("outputX", 200);//이미지의 x축크기
                intent.putExtra("outputY", 115);//y축크기
                /*intent.putExtra("aspectX", 1);//박스의 x축비율
                intent.putExtra("aspectY", 1);//박스의 y축비율*/
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_IMAGE);
                break;
            }
            case CROP_FROM_IMAGE: {
                //크롭한 이미지 받음
                if (resultCode != RESULT_OK) return;

                final Bundle extras = data.getExtras();

                //이미지 저장하기 위한 file 경로 설정
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/talkImg/"+
                        System.currentTimeMillis()+".jpg";
                Log.e("mImageCaptureUri: ", "Croped" + filePath);

                //base64 encoding
                //try {
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();

                    Bitmap bm = BitmapFactory.decodeFile(realPath); //크롭안된 이미지
                    bm.compress(Bitmap.CompressFormat.JPEG,100,outStream);
                    byte bytes[] = outStream.toByteArray();
                    imgString2 = Base64.encodeToString(bytes, Base64.NO_WRAP);
                    Log.i("img",imgString2);
                Log.i("siva", String.valueOf(imgString2.length()));

                imgs.add(imgString2);

                Log.i("imgArray",imgs.get(0));

                    /*File f1 = new File(imgPath);
                    FileInputStream fis = new FileInputStream(f1);
                    byte ba[] = new byte[(int) f1.length()];
                    fis.read(ba);
                    String imgString = Base64.encodeToString(ba, Base64.NO_WRAP);
                    Log.i("img",imgString);*/
                /*} catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                //encoding

                if (extras != null) {
                    photoBitmap = extras.getParcelable("data");//레이아웃 이미지 칸에 bitmap보여줌
                    ImageView iv = new ImageView(getBaseContext());
                    iv.setImageBitmap(photoBitmap);

                    imgLayout.addView(iv, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    imgPath = filePath;
                    saveCropImage(photoBitmap,imgPath);//자른 이미지를 외부저장소, 앨범에 저장

                    /*if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(mediaFile)));
                    }
                    else{
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,Uri.parse("file://"+Environment.getExternalStorageDirectory())));
                    }*/
                    break;
                }
                //임시파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) f.delete();


            }
        }
    }

    private void saveCropImage(Bitmap bitmap, String filePath) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/talkImg";
        File dir_talkImg = new File(dirPath);
        if (!dir_talkImg.exists()) dir_talkImg.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream bos = null;
        try{
            copyFile.createNewFile();
            bos = new BufferedOutputStream(new FileOutputStream((copyFile)));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));
            bos.flush();
            bos.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                AlertDialog.Builder logout = new AlertDialog.Builder(WritingActivity.this);
                logout.setTitle("작성취소");
                logout.setMessage("작성을 취소하시겠습니까?");
                logout.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                logout.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                logout.show();

                return true;
        }
        return true;
    }
}



