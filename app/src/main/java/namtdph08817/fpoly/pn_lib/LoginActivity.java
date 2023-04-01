package namtdph08817.fpoly.pn_lib;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import namtdph08817.fpoly.pn_lib.DAO.ThuThuDAO;

public class LoginActivity extends AppCompatActivity {
    private Button btnlogin;
    private TextInputEditText ed_user,ed_pass;
    private ThuThuDAO thuThuDAO;
    private CheckBox chkRemember;
    private LinearLayout imgMNChao;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("aaa","onCreate");

        setContentView(R.layout.activity_login);
        btnlogin = findViewById(R.id.btnLogin);
        ed_user = findViewById(R.id.tied_user);
        ed_pass = findViewById(R.id.tied_password1);
        chkRemember = findViewById(R.id.id_checkBox);
        imgMNChao = findViewById(R.id.img_chao);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imgMNChao.setVisibility(View.INVISIBLE);
            }
        },2000);

        showRemember();
        thuThuDAO = new ThuThuDAO(this);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed_user.getText().toString().trim().equals("") || ed_pass.getText().toString().trim().equals("")){
                    Toast.makeText(LoginActivity.this, "Không được để trống tên tài khoản hoặc mật khẩu !!!", Toast.LENGTH_SHORT).show();
                }else if (thuThuDAO.checkDangNhap(ed_user.getText().toString(),ed_pass.getText().toString())){
                    saveRemember();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "Tên tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void saveRemember() {
        SharedPreferences pref = getSharedPreferences("thongTin",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        boolean chk = chkRemember.isChecked();
        if (chk){
            editor.putString("userName",ed_user.getText().toString());
            editor.putString("passWord",ed_pass.getText().toString());
            editor.putBoolean("chkStatus",chk);
        }else {
            editor.clear();
        }
        editor.commit();
    }


    private void showRemember() {
        SharedPreferences pref = getSharedPreferences("thongTin",MODE_PRIVATE);
        boolean check = pref.getBoolean("chkStatus",false);
        if (check){
            String user = pref.getString("userName","");
            String pass= pref.getString("passWord","");
            ed_user.setText(user);
            ed_pass.setText(pass);
        }
        chkRemember.setChecked(check);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("aaa","onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("aaa","onStop" );
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("aaa","onReStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("aaa","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("aaa","onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("aaa","onDestroy");

    }
}