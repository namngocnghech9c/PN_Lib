package namtdph08817.fpoly.pn_lib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import namtdph08817.fpoly.pn_lib.DAO.ThuThuDAO;
import namtdph08817.fpoly.pn_lib.fragment.ChangePassFragment;
import namtdph08817.fpoly.pn_lib.fragment.DoanhThuFragment;
import namtdph08817.fpoly.pn_lib.fragment.LoaiSachFragment;
import namtdph08817.fpoly.pn_lib.fragment.PhieuMuonFragment;
import namtdph08817.fpoly.pn_lib.fragment.SachFragment;
import namtdph08817.fpoly.pn_lib.fragment.ThanhVienFragment;
import namtdph08817.fpoly.pn_lib.fragment.ThemThuThuFragment;
import namtdph08817.fpoly.pn_lib.fragment.Top10SachFragment;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ThuThuDAO thuThuDAO;
    private TextInputEditText tv_user, tv_pass;
    private TextView tv_welcome;
    private boolean back2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.id_toolbar);
        drawerLayout = findViewById(R.id.id_drawerLayout);
        navigationView = findViewById(R.id.id_navigationDrawer);
        tv_user = findViewById(R.id.tied_user);
        tv_pass = findViewById(R.id.tied_password);
        tv_welcome = findViewById(R.id.tv_welcome);
        Toast.makeText(this, "Chào mừng đến với trang chủ", Toast.LENGTH_LONG).show();
        //set toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //cap nhat chuc nang
        thuThuDAO = new ThuThuDAO(this);
        phanLoaiUser();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                FragmentManager manager = getSupportFragmentManager();
                switch (item.getItemId()) {
                    case R.id.nav_sach:
                        fragment = new SachFragment();
                        manager.beginTransaction().replace(R.id.id_linearLayout, fragment).commit();
                        break;
                    case R.id.nav_loai_sach:
                        fragment = new LoaiSachFragment();
                        manager.beginTransaction().replace(R.id.id_linearLayout, fragment).commit();
                        break;
                    case R.id.nav_phieu_muon:
                        fragment = new PhieuMuonFragment();
                        manager.beginTransaction().replace(R.id.id_linearLayout, fragment).commit();
                        break;
                    case R.id.nav_thanh_vien:
                        fragment = new ThanhVienFragment();
                        manager.beginTransaction().replace(R.id.id_linearLayout, fragment).commit();
                        break;
                    case R.id.nav_thong_ke_top10:
                        fragment = new Top10SachFragment();
                        manager.beginTransaction().replace(R.id.id_linearLayout, fragment).commit();
                        break;
                    case R.id.nav_thong_ke_doanh_thu:
                        fragment = new DoanhThuFragment();
                        manager.beginTransaction().replace(R.id.id_linearLayout, fragment).commit();
                        break;
                    case R.id.nav_them_thu_thu:
                        fragment = new ThemThuThuFragment();
                        manager.beginTransaction().replace(R.id.id_linearLayout, fragment).commit();
                        break;
                    case R.id.nav_doi_mat_khau:
                        fragment = new ChangePassFragment();
                        manager.beginTransaction().replace(R.id.id_linearLayout, fragment).commit();
                        break;
                    case R.id.nav_dang_xuat:
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.nav_thoat:
                        finish();
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                setTitle(item.getTitle());
                return false;
            }
        });
    }

    private void phanLoaiUser() {
        SharedPreferences pref = getSharedPreferences("thongTin", MODE_PRIVATE);
        String user = pref.getString("userName", "");
        if (user.equals("admin")) {

        } else {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.nav_thong_ke).setVisible(false);
            menu.findItem(R.id.nav_them_thu_thu).setVisible(false);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (back2 == false){
                Toast.makeText(this, "Nhấn lần nữa để thoát !", Toast.LENGTH_SHORT).show();
            }
            if (back2) {
                super.onBackPressed();
            }
            back2 = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    back2 = false;
                }
            }, 3000);
        }
    }

    @Override
    protected void onStop() {
        Log.i("TEST", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("TEST", "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.i("TEST", "onPause");
        super.onPause();
    }
}