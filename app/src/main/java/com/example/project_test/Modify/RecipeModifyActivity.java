package com.example.project_test.Modify;

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
import com.example.project_test.DeleteImg;
import com.example.project_test.LoginActivity;
import com.example.project_test.R;
import com.example.project_test.Write;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeModifyActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button writing;
    EditText tedit, nedit, cedit, cedit2, pedit;
    TextView tv0;
    String post_title, post_con, cook_src, cook_rcp;
    int post_code;

    String[] img_data;
    ArrayList<Integer> img_code;
    ImageButton imgup;

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

    private String realPath;
    String imgString2;
    String[] imgString;
    ArrayList<String> imgs = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_recipe);


        writing = findViewById(R.id.writing);
        tedit = findViewById(R.id.tedit);
        nedit = findViewById(R.id.nedit);
        cedit = findViewById(R.id.cedit);
        cedit2 = findViewById(R.id.cedit2);
        pedit = findViewById(R.id.pedit);
        tv0 = findViewById(R.id.tv0);
        imgup = findViewById(R.id.imgup);
        imgLayout = findViewById(R.id.imgLayout);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tv0.setText("수정하기");

        //글 제목, 내용
        Intent intent = getIntent();
        post_title = intent.getStringExtra("제목");
        post_con = intent.getStringExtra("내용");
        cook_rcp = intent.getStringExtra("레시피");
        cook_src = intent.getStringExtra("재료");
        post_code = intent.getIntExtra("게시글코드", 0);
        img_data = intent.getStringArrayExtra("사진");
        img_code = intent.getIntegerArrayListExtra("사진코드");
        tedit.setText(post_title);
        nedit.setText(cook_src);
        cedit.setText(cook_rcp);
        cedit2.setText(post_con);

        final Api api = Api.Factory.INSTANCE.create();

        //올렸던 사진
        for(int i = 0; img_data.length>i; i++){
            final int position = i;
            byte[] encodeByte = Base64.decode(String.valueOf(img_data[i]), Base64.NO_WRAP);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            //bitmap;
            final ImageView iv = new ImageView(getBaseContext());
            iv.setImageBitmap(bitmap);
            imgLayout.addView(iv, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //롱클릭시 삭제
            iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    api.deleteImg(img_code.get(position)).enqueue(new Callback<DeleteImg>() {
                        @Override
                        public void onResponse(Call<DeleteImg> call, Response<DeleteImg> response) {
                            Log.i("imgdelete","성공"+img_code.get(position));
                            List<String> list = new ArrayList<>(Arrays.asList(img_data));
                            list.remove(position);
                            img_code.remove(position);
                            img_data = list.toArray(new String[list.size()]);
                            imgLayout.removeView(iv);
                        }

                        @Override
                        public void onFailure(Call<DeleteImg> call, Throwable t) {
                            Log.i("imgdelete",t.getMessage()+"실패");
                        }
                    });
                    return false;
                }
            });
        }

        imgup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RecipeModifyActivity.this);
                builder.setMessage("이미지 선택");
//                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        takePhotoAction();
//                    }
//                };
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
                //builder.setPositiveButton("사진촬영", cameraListener);
                builder.setPositiveButton("앨범선택", albumListener);
                builder.setNegativeButton("취소", cancleListener);
                builder.show();
            }
        });

        //글쓰기 _올리기
        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                post_title = tedit.getText().toString();
                post_con = cedit2.getText().toString();
                cook_src = nedit.getText().toString();
                cook_rcp = cedit.getText().toString();

                if (post_title.equals("") || cook_src.equals("") || cook_rcp.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RecipeModifyActivity.this);
                    dialog = builder.setMessage("글 작성이 완료되지 않았습니다.").setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                } else {


                    api.Modify(post_title, post_con, post_code).enqueue(new Callback<Write>() {
                        public void onResponse(Call<Write> call, Response<Write> response) {

                            Write write = response.body();
                            boolean insert = write.insert;
                            Log.i("abcdef", insert + "");

                            api.ModifyCook(cook_src, cook_rcp, post_code).enqueue(new Callback<Write>() {
                                @Override
                                public void onResponse(Call<Write> call, Response<Write> response) {
                                    Write write = response.body();
                                    boolean insert = write.insert;
                                    Log.i("abcdef", insert + "");

                                    AlertDialog.Builder builder = new AlertDialog.Builder(RecipeModifyActivity.this);
                                    dialog = builder.setMessage("수정 완료됨").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                            .create();
                                    dialog.show();
                                }

                                @Override
                                public void onFailure(Call<Write> call, Throwable t) {
                                    Log.i("수정실패", t.getMessage());
                                }
                            });

                            if(imgString2 != null)
                            {
                                api.imgmodify(LoginActivity.user_ac, imgString, post_code).enqueue(new Callback<ResponseBody>() {
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

                            else
                            {
                                api.imgmodify(LoginActivity.user_ac, img_data, post_code).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                        try {
//                                            Log.i("img" , response.body().string().trim());
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                        }

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
                                Log.i("img", "사진 없이 올림");}


                        }

                        public void onFailure(Call<Write> call, Throwable t) {
                            Log.i("수정실패", t.getMessage());
                        }

                    });
                }

            }
        });

    }

    //갤러리에서 사진선택
    private void takeAlbumAction() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(albumIntent, PICK_FROM_ALBUM);
    }

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
                intent.putExtra("aspectX", 1.67);//박스의 x축비율
                intent.putExtra("aspectY", 1);//박스의 y축비율
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
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();

                Bitmap bm = BitmapFactory.decodeFile(realPath); //크롭안된 이미지
                bm.compress(Bitmap.CompressFormat.JPEG,100,outStream);
                byte bytes[] = outStream.toByteArray();
                imgString2 = Base64.encodeToString(bytes, Base64.NO_WRAP);
                imgs.add(imgString2);
                Log.i("imgArray",imgs.get(0));
                imgString = imgs.toArray(new String[imgs.size()]);

                if (extras != null) {
                    photoBitmap = extras.getParcelable("data");//레이아웃 이미지 칸에 bitmap보여줌
                    ImageView iv = new ImageView(getBaseContext());
                    iv.setImageBitmap(photoBitmap);

                    imgLayout.addView(iv, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    imgPath = filePath;
                    saveCropImage(photoBitmap,imgPath);//자른 이미지를 외부저장소, 앨범에 저장

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
                AlertDialog.Builder logout = new AlertDialog.Builder(RecipeModifyActivity.this);
                logout.setTitle("수정취소");
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
