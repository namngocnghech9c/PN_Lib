package namtdph08817.fpoly.pn_lib.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import namtdph08817.fpoly.pn_lib.DAO.ThuThuDAO;
import namtdph08817.fpoly.pn_lib.R;


public class ChangePassFragment extends Fragment {
    private TextInputEditText ed_oldPass,ed_newPass,ed_reNewPass;
    private Button btn_changePass;
    private ThuThuDAO thuThuDAO;
    public ChangePassFragment() {
        // Required empty public constructor
    }

    public static ChangePassFragment newInstance() {
        ChangePassFragment fragment = new ChangePassFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_pass, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ed_oldPass = view.findViewById(R.id.tied_old_passWord);
        ed_newPass = view.findViewById(R.id.tied_new_passWord);
        ed_reNewPass = view.findViewById(R.id.tied_repassWord_new);
        btn_changePass = view.findViewById(R.id.btn_doi_mat_khau);

        thuThuDAO = new ThuThuDAO(getActivity());
        SharedPreferences pref = getActivity().getSharedPreferences("thongTin", Context.MODE_PRIVATE);
        String user = pref.getString("userName","");
        String pass = pref.getString("passWord","");
        Toast.makeText(getActivity(), pass, Toast.LENGTH_SHORT).show();
        ed_newPass.requestFocus();
        ed_reNewPass.requestFocus();
        ed_oldPass.requestFocus();

        btn_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_newPass.getText().toString().trim().equals("")){
                    ed_newPass.setError("Không được để trống !");
                }
                if (ed_reNewPass.getText().toString().trim().equals("")){
                    ed_reNewPass.setError("Không được để trống !");
                }
                if (ed_oldPass.getText().toString().trim().equals("")){
                    ed_oldPass.setError("Không được để trống !");
                }else if (ed_newPass.getText().toString().trim().equals("")){
                    ed_newPass.setError("Không được để trống !");
                }else if (!ed_oldPass.getText().toString().equals(pass)){
                    ed_oldPass.setError("Mật khẩu cũ không đúng !");
                }else if (!ed_reNewPass.getText().toString().equals(ed_newPass.getText().toString())){
                    ed_reNewPass.setError("Mật khẩu mới không trùng khớp !");
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có chắc đổi mật khẩu ?");
                    String oldpass = ed_oldPass.getText().toString();
                    String newPass = ed_newPass.getText().toString();

                    builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (thuThuDAO.doiMatKhau(user,oldpass,newPass)){
                                Dialog dialog = new Dialog(getActivity());
                                dialog.setContentView(R.layout.dialog_success);
                                TextView tv_success = dialog.findViewById(R.id.tv_success);
                                tv_success.setText("Đã thay đổi mật khẩu");
                                ed_oldPass.setText("");
                                ed_newPass.setText("");
                                ed_reNewPass.setText("");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();
                                    }
                                },2000);
                                dialog.show();
                            }else {
                                Toast.makeText(getActivity(), "thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
            }
        });
    }
}