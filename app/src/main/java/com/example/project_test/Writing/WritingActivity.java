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
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WritingActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button writing;
    EditText tedit, cedit, pedit, nedit;
    TextView tv0, tv1 , title2;
    String post_title, post_con, cook_src;
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
        title2 = findViewById(R.id.title2);
        imgup = findViewById(R.id.imgup);

        tv0 = findViewById(R.id.tv0);
        tv1 = findViewById(R.id.tv1);

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
                post_con = cedit.getText().toString();
                cook_src = nedit.getText().toString();

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
                    Api api = Api.Factory.INSTANCE.create();
                    api.Write(LoginActivity.user_ac, post_title, post_con, board_code).enqueue(new Callback<Write>() {
                        public void onResponse(Call<Write> call, Response<Write> response) {

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

                    api.CookWrite(cook_src, post_con).enqueue(new Callback<CookWrite>() {
                        public void onResponse(Call<CookWrite> call, Response<CookWrite> response) {

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
                        public void onFailure(Call<CookWrite> call, Throwable t) {
                            Log.i("작성실패", t.getMessage());
                        }
                    });

                    api.imgupload(LoginActivity.user_ac, imgString2).enqueue(new Callback<Img>() {
                        @Override
                        public void onResponse(Call<Img> call, Response<Img> response) {
                            Img i = response.body();
                            boolean upload = i.insert;

                            if (upload==true){
                                Log.i("img", "업로드 성공");
                            }
                        }

                        @Override
                        public void onFailure(Call<Img> call, Throwable t) {
                                Log.i("img", "업로드 실패"+t.getMessage());
                        }
                    });

                    /*//댓글 작성
            push.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    cmt_con = editTextName1.getText().toString();

                    Api api = Api.Factory.INSTANCE.create();
                    api.Cmt(LoginActivity.user_ac, cmt_con).enqueue(new Callback<Cmt>() {
                        public void onResponse(Call<Cmt> call, Response<Cmt> response) {

                            Log.i("결과는" , response.toString());

                            AlertDialog.Builder builder = new AlertDialog.Builder(qaActivityContent.this);
                            dialog = builder.setMessage("작성 완료됨").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                                    .create();
                            dialog.show();
                        }
                        public void onFailure(Call<Cmt> call, Throwable t) {
                            Log.i("작성실패", t.getMessage());
                        }

                    });

                }
            });*/


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

                    Bitmap bm = BitmapFactory.decodeFile(realPath);
                    bm.compress(Bitmap.CompressFormat.JPEG,100,outStream);
                    byte bytes[] = outStream.toByteArray();
                    imgString2 = Base64.encodeToString(bytes, Base64.NO_WRAP);
                    Log.i("img",imgString2);

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



