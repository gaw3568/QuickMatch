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

import com.QuickMatch.quickmatchv110.Teampage.Team;
import com.QuickMatch.quickmatchv110.Teampage.Teaminfo;
import com.QuickMatch.quickmatchv110.USER.UserID;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Teampage_AddTeam extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private Toolbar t_AddTeam_toolbar;
    private Button t_City, t_State, t_Level;
    private EditText t_Add_TeamName, t_Add_intro;
    private ImageView t_Add_TeamLogo;

    private Uri addimageUri;

    private int TAKE_IMAGE_CODE = 10001;

    private String nickname;
    private String teamID;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teampage_add_team);

        t_AddTeam_toolbar = findViewById(R.id.m_Toolbar);
        t_AddTeam_toolbar.setTitle(R.string.title);
        setSupportActionBar(t_AddTeam_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        addimageUri = Uri.parse("android.resource://" + getPackageName() + "/drawable/basiclogo");
        t_Add_TeamLogo = findViewById(R.id.t_Add_TeamLogo);


        t_City = findViewById(R.id.t_City);
        t_State = findViewById(R.id.t_State);
        t_Level = findViewById(R.id.t_Level);

        t_Add_TeamName = findViewById(R.id.t_Add_TeamName);
        t_Add_intro = findViewById(R.id.t_Add_intro);


        t_City.setOnClickListener(this);
        t_State.setOnClickListener(this);
        t_Level.setOnClickListener(this);


        if (mAuth.getCurrentUser() != null) {
            DocumentReference docRef = mStore.collection(UserID.user).document(mAuth.getCurrentUser().getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    nickname = (String) documentSnapshot.getData().get(UserID.nickname);//팀 추가할때 주장 닉네임 추가
                }
            });
        }

        t_Add_TeamLogo.setOnClickListener(this);

    }


    private String uploadImageToFirebase(Uri imageUri) {//firebase에 이미지 업로드

        final StorageReference fileRef = storageRef.child("teams/" + teamID + "/teamlogo.jpg");//firebase에 저장할 주소(경로) 사용자 id로 나눔
        UploadTask uploadTask = fileRef.putFile(imageUri);
        String s = "";
        Log.d("asdf", "업로드 실행중");
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
                    String logouri = String.valueOf(downloadUri);

                    Map<String, Object> imageMap = new HashMap<>();
                    imageMap.put(Teaminfo.logouri, logouri);
                    mStore.collection(Teaminfo.team).document(teamID).set(imageMap, SetOptions.merge());
                    dialog.dismiss();
                    finish();

                    Toast.makeText(Teampage_AddTeam.this, "완료~~~~~", Toast.LENGTH_SHORT).show();
                    //데이터 베이스 처리 같게함
                } else {
                    dialog.dismiss();
                    finish();

                    Toast.makeText(Teampage_AddTeam.this, "Failed. ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return s;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.teampage_addteam_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) { //toolbar의 back키 눌렀을 때 동작
            finish();
            return true;
        } else if (id == R.id.teampage_addteam_success) {
            if (t_Add_TeamName.getText().toString().equals("")) {
                Toast.makeText(Teampage_AddTeam.this, "팀 명을 적어주세요!", Toast.LENGTH_SHORT).show();
            } else if (t_Add_intro.getText().toString().equals("")) {
                Toast.makeText(Teampage_AddTeam.this, "팀 소개를 적어주세요!", Toast.LENGTH_SHORT).show();
            } else {
                if (mAuth.getCurrentUser() != null) {
                    builder = new AlertDialog.Builder(Teampage_AddTeam.this);
                    builder.setCancelable(false); // if you want user to wait for some process to finish,
                    builder.setView(R.layout.loding_progress);
                    dialog = builder.create();
                    dialog.show();

                    teamID = mStore.collection(Teaminfo.team).document().getId();
                    String teamName = t_Add_TeamName.getText().toString();
                    String introduce = t_Add_intro.getText().toString();
                    String city = t_City.getText().toString();
                    String state = t_State.getText().toString();
                    String teamlevel = t_Level.getText().toString();
                    String captainID = mAuth.getCurrentUser().getUid(); //만든사람ID
                    ArrayList memberlist = new ArrayList();
                    ArrayList managerlist = new ArrayList();
                    memberlist.add(captainID);//멤버 목록
                    managerlist.add(captainID);
                    Team team = new Team(teamID, teamName, introduce, city, state, teamlevel,
                            captainID, nickname, memberlist, managerlist, 1);//team 클래스 생성
                    mStore.collection(Teaminfo.team).document(teamID).set(team, SetOptions.merge());//파이어베이스에 팀 추가

                    mStore.collection(UserID.user).document(mAuth.getCurrentUser().getUid()).update(UserID.temalist, FieldValue.arrayUnion(teamID));

                    uploadImageToFirebase(addimageUri);
//                    LodingTask task = new LodingTask();
//                    task.execute();
                }
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {//뷰들의 아이디를 가져옴
            case R.id.t_City://첫번째 지역 버튼을 눌렀을때(서울 경기 등등)
                AlertDialog.Builder citydialog = new AlertDialog.Builder(Teampage_AddTeam.this);
                final String city[] = {"서울", "경기"};
                // , "강원", "인천", "대전", "울산", "광주", "세종", "부산", "대구", "충북", "충남", "전북", "전남", "경북", "경남", "제주"

                citydialog.setSingleChoiceItems(city, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        t_City.setText(city[i]);

                        if (city[i].equals("서울")) {
                            t_State.setText("중랑");
                        } else if (city[i].equals("경기")) {
                            t_State.setText("남양주");
                        }

                        dialogInterface.dismiss();
                    }
                });

                citydialog.show();
                break;
            case R.id.t_State://두번째 지역 버튼을 눌렀을때(중랑구, 남양주시)
                AlertDialog.Builder statedialog = new AlertDialog.Builder(Teampage_AddTeam.this);
                final String seoul[] = {"중랑", "강남", "강북"};
                final String gyeonggi[] = {"가평", "고양", "과천", "광명", "광주", "구리", "군포", "김포", "남양주"
                        , "동두천", "부천", "성남", "수원", "시흥", "안산", "안성", "안양", "양주", "양평"
                        , "여주", "연쳔", "오산", "용인", "의왕", "의정부", "이천", "파주"
                        , "평택", "포천", "하남", "화성"};
                String[] state = seoul;
                switch (t_City.getText().toString()) {
                    case "경기":
                        state = gyeonggi;
                        break;
                }

                final String[] finalState = state;
                statedialog.setSingleChoiceItems(state, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        t_State.setText(finalState[i]);
                        dialogInterface.dismiss();
                    }
                });

                statedialog.show();
                break;

            case R.id.t_Level://팀 수준 버튼을 눌렀을때
                AlertDialog.Builder leveldialog = new AlertDialog.Builder(Teampage_AddTeam.this);
                final String level[] = {"최상", "상", "중", "하", "최하"};
                leveldialog.setSingleChoiceItems(level, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        t_Level.setText(level[i]);

                        dialogInterface.dismiss();
                    }
                });
                leveldialog.show();
                break;

            case R.id.t_Add_TeamLogo:
                int cameraPermissionCheck = ContextCompat.checkSelfPermission(
                        Teampage_AddTeam.this, Manifest.permission.CAMERA
                );
                if (cameraPermissionCheck != PackageManager.PERMISSION_GRANTED) {
                    //권한이 없는경우
                    ActivityCompat.requestPermissions(Teampage_AddTeam.this, new String[]{Manifest.permission.CAMERA}, 1000);
                    if (cameraPermissionCheck == PackageManager.PERMISSION_GRANTED) {
                        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(openGalleryIntent, TAKE_IMAGE_CODE);//선택한 이미지 넘겨주기
                    } else {

                    }
                } else {
                    Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(openGalleryIntent, TAKE_IMAGE_CODE);//선택한 이미지 넘겨주기
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_IMAGE_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                addimageUri = data.getData();

                Log.d("asdf", String.valueOf(addimageUri));
                Glide.with(Teampage_AddTeam.this)//프로필 사진칸에 사진 업데이트
                        .load(addimageUri)
                        .into(t_Add_TeamLogo);
            }
        }
    }

}
