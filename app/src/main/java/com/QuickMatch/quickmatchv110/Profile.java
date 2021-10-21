package com.QuickMatch.quickmatchv110;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.QuickMatch.quickmatchv110.USER.UserID;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;


public class Profile extends AppCompatActivity implements View.OnClickListener {
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();;
    private FirebaseAuth  mAuth = FirebaseAuth.getInstance();;
    private ImageView profile_image;
    private Toolbar P_toolbar;
    private FirebaseFirestore  mStore = FirebaseFirestore.getInstance();;

    private Uri addimageUri;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private EditText profile_height;
    private Button profile_foot, profile_position, profile_state, profile_city;

    int TAKE_IMAGE_CODE = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        profile_image = findViewById(R.id.profile_profileImage);

        profile_height = findViewById(R.id.profile_height);
        profile_foot = findViewById(R.id.profile_foot);
        profile_position = findViewById(R.id.profile_position);
        profile_city = findViewById(R.id.profile_city);
        profile_state = findViewById(R.id.profile_state);

        profile_foot.setOnClickListener(this);
        profile_position.setOnClickListener(this);
        profile_city.setOnClickListener(this);
        profile_state.setOnClickListener(this);

        addimageUri = Uri.parse("android.resource://" + getPackageName() + "/drawable/basicprofile");


        P_toolbar = findViewById(R.id.m_Toolbar);
        setSupportActionBar(P_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(storageRef != null) {
            StorageReference profileRef = storageRef.child("users/" + mAuth.getCurrentUser().getUid() + "/profile.jpg");//화면보일때 firebase에 있는 이미지 가져오기

            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    Glide.with(Profile.this)
                            .load(uri)
                            .into(profile_image);
                    addimageUri = uri;
                }
            });
        }
        profile_image.setOnClickListener(new View.OnClickListener() {//카메라 권한 받아오기
            @Override
            public void onClick(View view) {
                int cameraPermissionCheck;
                cameraPermissionCheck = ContextCompat.checkSelfPermission(
                        Profile.this, Manifest.permission.CAMERA
                );

                if (cameraPermissionCheck != PackageManager.PERMISSION_GRANTED) {
                    //권한이 없는경우
                    ActivityCompat.requestPermissions(Profile.this, new String[]{Manifest.permission.CAMERA}, 1000);
                    if (cameraPermissionCheck == PackageManager.PERMISSION_GRANTED) {
                        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(openGalleryIntent, TAKE_IMAGE_CODE);//선택한 이미지 넘겨주기
                    }else{

                    }

                } else {
                    Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(openGalleryIntent, TAKE_IMAGE_CODE);//선택한 이미지 넘겨주기

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//선택한 이미지 받아옴
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_IMAGE_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                addimageUri = data.getData();
                Glide.with(Profile.this)//프로필 사진칸에 사진 업데이트
                        .load(addimageUri)
                        .into(profile_image);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {//firebase에 이미지 업로드
        final StorageReference fileRef = storageRef.child("users/" + mAuth.getCurrentUser().getUid() + "/profile.jpg");//firebase에 저장할 주소(경로) 사용자 id로 나눔
        UploadTask uploadTask = fileRef.putFile(imageUri);

        Log.d("asdf","업로드 uri"+String.valueOf(imageUri));
        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                return fileRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Map<String, Object> imageMap = new HashMap<>();
                    imageMap.put(UserID.imageuri, String.valueOf(downloadUri));
                    mStore.collection(UserID.user).document(mAuth.getCurrentUser().getUid()).set(imageMap, SetOptions.merge());
                    Toast.makeText(Profile.this, "변경 완료", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    finish();
                    //데이터 베이스 처리 같게함
                }else{

                    //데이터 베이스 처리
                    dialog.dismiss();
                    Toast.makeText(Profile.this, "Failed. ", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.profile_edit_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) { //toolbar의 back키 눌렀을 때 동작
            finish();
            return true;
        }
        if (id == R.id.profile_edit) {
            FirebaseUser user = mAuth.getCurrentUser();
            if(user != null) {         // 게시글 작성
                Map<String, Object> profile_data = new HashMap<>();
                profile_data.put(UserID.height, profile_height.getText().toString());
                profile_data.put(UserID.foot, profile_foot.getText().toString());
                profile_data.put(UserID.position, profile_position.getText().toString());
                profile_data.put(UserID.city, profile_city.getText().toString());
                profile_data.put(UserID.state, profile_state.getText().toString());
                mStore.collection(UserID.user).document(user.getUid()).set(profile_data, SetOptions.merge());
                Toast.makeText(Profile.this, "작성 완료", Toast.LENGTH_SHORT).show();
            }
            builder = new AlertDialog.Builder(Profile.this);
            builder.setCancelable(false); // if you want user to wait for some process to finish,
            builder.setView(R.layout.loding_progress);
            dialog = builder.create();
            dialog.show();

            uploadImageToFirebase(addimageUri);//upload 실행

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {//뷰들의 아이디를 가져옴
            case R.id.profile_city://첫번째 지역 버튼을 눌렀을때(서울 경기 등등)
                AlertDialog.Builder citydialog = new AlertDialog.Builder(Profile.this);
                final String city[] = {"서울", "경기"};
                // , "강원", "인천", "대전", "울산", "광주", "세종", "부산", "대구", "충북", "충남", "전북", "전남", "경북", "경남", "제주"

                citydialog.setSingleChoiceItems(city, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        profile_city.setText(city[i]);

                        if (city[i].equals("서울")) {
                            profile_state.setText("중랑");
                        } else if (city[i].equals("경기")) {
                            profile_state.setText("남양주");
                        }

                        dialogInterface.dismiss();
                    }
                });

                citydialog.show();
                break;
            case R.id.profile_state:
                AlertDialog.Builder statedialog = new AlertDialog.Builder(Profile.this);
                final String seoul[] = {"중랑", "강남", "강북"};
                final String gyeonggi[] = {"가평", "고양", "과천", "광명", "광주", "구리", "군포", "김포", "남양주"
                        , "동두천", "부천", "성남", "수원", "시흥", "안산", "안성", "안양", "양주", "양평"
                        , "여주", "연쳔", "오산", "용인", "의왕", "의정부", "이천", "파주"
                        , "평택", "포천", "하남", "화성"};
                String[] state = seoul;
                switch (profile_city.getText().toString()) {
                    case "경기":
                        state = gyeonggi;
                        break;
                }

                final String[] finalState = state;
                statedialog.setSingleChoiceItems(state, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        profile_state.setText(finalState[i]);
                        dialogInterface.dismiss();
                    }
                });

                statedialog.show();
                break;

            case R.id.profile_position:
                AlertDialog.Builder positionDialog = new AlertDialog.Builder(Profile.this);
                final String position[] = {"FW", "MF", "DF", "GK"};
                positionDialog.setSingleChoiceItems(position, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        profile_position.setText(position[i]);

                        dialogInterface.dismiss();
                    }
                });
                positionDialog.show();
                break;

            case R.id.profile_foot:
                AlertDialog.Builder footDialog = new AlertDialog.Builder(Profile.this);
                final String foot[] = {"양발", "왼발", "오른발"};
                footDialog.setSingleChoiceItems(foot, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        profile_foot.setText(foot[i]);

                        dialogInterface.dismiss();
                    }
                });
                footDialog.show();
                break;
        }
    }
}
