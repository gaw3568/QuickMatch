package com.QuickMatch.quickmatchv110;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.QuickMatch.quickmatchv110.USER.Weather;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    //MainFragment에 onFragmentBtnSelected를 상속 ,NavigationView.OnNavigationItemSelectedListener 이거때문에 메뉴 아이콘을 못 바꾸는듯?
    private DrawerLayout m_Drawer;//
//
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private Toolbar m_Toolbar;//appcompat이랑 그냥이랑 뭐가 다른지 찾아보기
    private NavigationView m_NavigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Button logout;

    Uri imageuri;
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private View m_HeadView;

    private ImageView n_Profile_ProfileImage;

    private SharedPreferences lp;
    private SharedPreferences.Editor lEdit;

    private Fragment homefragment;
    private Home_fragment hf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        homefragment = new Home_fragment();

        m_Toolbar = findViewById(R.id.m_Toolbar);
        m_Toolbar.setTitleTextColor(Color.parseColor("#ffff33")); //제목의 칼라
        m_Toolbar.setTitle(R.string.title);
        m_Toolbar.setSubtitle(""); //부제목 넣기
        setSupportActionBar(m_Toolbar);

        m_Drawer = findViewById(R.id.m_Drawer);//
        m_NavigationView = findViewById(R.id.m_NavigationView);
        m_NavigationView.setNavigationItemSelectedListener(this);

        //자동 로그인 정보를 담기위한 SharedPreferences
        lp = getSharedPreferences("login", MODE_PRIVATE);
        lEdit = lp.edit();

        m_HeadView = m_NavigationView.getHeaderView(0);
        logout = m_HeadView.findViewById(R.id.navi_logout);
        logout.setOnClickListener(this);


        //분석
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, m_Drawer, m_Toolbar, (R.string.open), (R.string.close));
        m_Drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        //네비게이션바 부르는 아이콘 클릭시 모양이 바뀜


        //load default fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_contant_fragment, homefragment);
        fragmentTransaction.commit();


        //기본 프레그먼트
        readRss();

    }

    void readRss() {
        try {
            URL url = new URL("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1126065500");

            RssFeedTask task = new RssFeedTask();
            task.execute(url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (lp.getBoolean("auto_login", false) == false) {
            if (mAuth.getCurrentUser() != null) {
                mAuth.signOut();
                lEdit.commit();
            }
        }
    }

    @Override
    protected void onResume() {//프로필 사진이 변경 됐을 떄 새로고침하기 위함 (보완필요할듯)
        super.onResume();
        m_HeadView = m_NavigationView.getHeaderView(0);
        n_Profile_ProfileImage = m_HeadView.findViewById(R.id.n_Profile_ProfileImage);
        imageuri = Uri.parse("android.resource://" + getPackageName() + "/drawable/basicprofile");
        if (mAuth.getCurrentUser() != null) {
            StorageReference profileRef = storageRef.child("users/" + mAuth.getCurrentUser().getUid() + "/profile.jpg");
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(MainActivity.this)
                            .load(uri)
                            .into(n_Profile_ProfileImage);
                }
            });
        } else {
            Glide.with(MainActivity.this)
                    .load(imageuri)
                    .into(n_Profile_ProfileImage);
        }
    }

    /*public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }//앱을 시작할때 옵션메뉴 생성(정렬, 세팅)*/


    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.m_Drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }//뒤로가기 버튼을 눌렀을때 Drawerlayout이 열려있다면 닫아줘라. 안열려있다면 뒤로가라
    //이 함수가 없으면 네비게이션이 열려 있어도 앱이 나가짐 이 함수가 있어야 네비게이션만 없어짐


    //----------------------------------네비게이션 버튼 클릭 fragment 화면전환 ----------------------------------------------
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {//아마 네비게이션에서 아이템을 선택할때인듯?
        m_Drawer.closeDrawer(GravityCompat.START);//아이템클릭을 하면 화면이 바뀌고 네비게이션 바가 들어감
        if (menuItem.getItemId() == R.id.n_Menu_Home) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_contant_fragment, homefragment);
            fragmentTransaction.commit();
        }
        if (menuItem.getItemId() == R.id.n_Menu_Matching) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_contant_fragment, new Matching_Fragment());
            fragmentTransaction.commit();
        }

        if (menuItem.getItemId() == R.id.n_Menu_Team) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_contant_fragment, new Teampage_Fragment());
            fragmentTransaction.commit();
        }

        /*
            구인, 구직 페이지 부분
         */
        if (menuItem.getItemId() == R.id.n_Menu_Job) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_contant_fragment, new Job_Fragment());
            fragmentTransaction.commit();
        }

        if (menuItem.getItemId() == R.id.n_Menu_Profile) {
            Intent intent = new Intent(MainActivity.this, Profile.class);
            startActivity(intent);
        }

        if (menuItem.getItemId() == R.id.n_Menu_invite) {
            Intent intent = new Intent(MainActivity.this, Invite_Request.class);
            startActivity(intent);
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.navi_logout:
                if (mAuth.getCurrentUser() != null) {
                    mAuth.signOut();
                    intent = new Intent(MainActivity.this, Login_Main.class);
                    finish();
                    startActivity(intent);
                    lEdit.putBoolean("auto_login", false);
                    lEdit.commit();
                    break;
                } else {
                    Toast.makeText(MainActivity.this, "로그인 되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    //----------------------------------fragment를 이용한 화면전환 공간 끝----------------------------------------------

    class RssFeedTask extends AsyncTask<URL, Void, Weather> {


        @Override
        protected Weather doInBackground(URL... urls) {
            URL url = urls[0];
            Weather data = null;
            InputStream is = null;
            Log.d("asdf", String.valueOf(url));
            //URL에게 Stream 열도록..
            try {

                is = url.openStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(is , "utf-8");
                int eventType = xpp.getEventType();


                String tagName = null;
                int a = 0;

                while (a == 0) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            tagName = xpp.getName();
                            if (tagName.equals("channel")) {
                                data = new Weather();
                            } else if (tagName.equals("pubDate")) {
                                xpp.next();
                                if (data != null) data.setUpdatedata(xpp.getText());
                            } else if (tagName.equals("category")) {
                                xpp.next();
                                if (data != null) data.setLocal(xpp.getText());
                            } else if (tagName.equals("hour")) {
                                xpp.next();
                                if (data != null) data.setHour(xpp.getText());
                            } else if (tagName.equals("temp")) {
                                xpp.next();
                                if (data != null) data.setTemp(xpp.getText());
                            } else if (tagName.equals("wfKor")) {
                                xpp.next();
                                if (data != null) data.setWeather(xpp.getText());
                            }
                            break;

                        case XmlPullParser.TEXT:
                            break;

                        case XmlPullParser.END_TAG:
                            tagName = xpp.getName();
                            if (tagName.equals("data")) {
                                a = 1;
                            }
                            break;
                    }
                    eventType = xpp.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        //doInBackground메소드가 종료된 후
        //UI작업을 위해 자동 실행되는 메소드
        //runOnUiThread()와 비슷한 역할
        @Override
        protected void onPostExecute(Weather data) {
            super.onPostExecute(data);

            if (data == null) {
                Log.d("asdf", "널");
            } else {
                String local = data.getLocal();
                String update = data.getUpdatedata();
                String time = data.getHour();
                int timestart = Integer.parseInt(time) - 3;

                String data1 = "지역: " + local + " " + update + "에 업데이트 됨" + "( " + timestart + "시부터 ~ " + time + "시 )";
                String data2 = data.getTemp() + "도 " + data.getWeather();

                Log.d("asdf", "지역: " + local + " " + update + "에 업데이트 됨" + "( " + timestart + " ~ " + time + " )");
                hf = (Home_fragment) getSupportFragmentManager().findFragmentById(R.id.main_contant_fragment);
                if(hf == null){
                    Log.d("asdf", "널");
                }else {
                    hf.setDay(data1);
                    hf.setTemp(data2);
                }
            }
        }
    }


}