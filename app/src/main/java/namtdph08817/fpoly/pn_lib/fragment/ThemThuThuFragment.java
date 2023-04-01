package namtdph08817.fpoly.pn_lib.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import namtdph08817.fpoly.pn_lib.DAO.ThuThuDAO;
import namtdph08817.fpoly.pn_lib.DanhSachThuThuActivity;
import namtdph08817.fpoly.pn_lib.R;
import namtdph08817.fpoly.pn_lib.entity.ThuThuModel;

public class ThemThuThuFragment extends Fragment {
    private TextInputEditText ed_hoTen, ed_namSinh, ed_userName, ed_pass, ed_RepassWord;
    private Button btn_add, btn_ds_thuthu;
    private ThuThuDAO thuThuDAO;

    public ThemThuThuFragment() {
        // Required empty public constructor
    }

    public static ThemThuThuFragment newInstance() {
        ThemThuThuFragment fragment = new ThemThuThuFragment();
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
        return inflater.inflate(R.layout.fragment_them_thu_thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ed_hoTen = view.findViewById(R.id.tied_tenTT);
        ed_namSinh = view.findViewById(R.id.tied_namSinh);
        ed_userName = view.findViewById(R.id.tied_ten_dang_nhap);
        ed_pass = view.findViewById(R.id.tied_password);
        ed_RepassWord = view.findViewById(R.id.tied_Repassword);
        btn_add = view.findViewById(R.id.btn_them_thu_thu);
        btn_ds_thuthu = view.findViewById(R.id.btn_ds_thuThu);

        //them
        ed_namSinh.requestFocus();
        ed_userName.requestFocus();
        ed_pass.requestFocus();
        ed_RepassWord.requestFocus();
        ed_hoTen.requestFocus();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_namSinh.getText().toString().trim().equals("")) {
                    ed_namSinh.setError("Không được để trống !");
                }
                if (ed_userName.getText().toString().trim().equals("")) {
                    ed_userName.setError("Không được để trống !");
                }
                if (ed_pass.getText().toString().trim().equals("")) {
                    ed_pass.setError("Không được để trống !");
                }
                if (ed_RepassWord.getText().toString().trim().equals("")) {
                    ed_RepassWord.setError("Không được để trống !");
                }
                if (ed_hoTen.getText().toString().trim().equals("")) {
                    ed_hoTen.setError("Không được để trống !");
                } else if (ed_pass.getText().toString().trim().equals("")) {
                    ed_pass.setError("Không được để trống !");
                } else if (!ed_RepassWord.getText().toString().equals(ed_pass.getText().toString())) {
                    ed_RepassWord.setError("Mật khẩu không trùng khớp !");
                } else {
                    thuThuDAO = new ThuThuDAO(getContext());
                    ThuThuModel model = new ThuThuModel();
                    model.setTenTT(ed_hoTen.getText().toString());
                    model.setNamSinh(ed_namSinh.getText().toString());
                    model.setUserName(ed_userName.getText().toString());
                    model.setPassWord(ed_pass.getText().toString());
                    if (thuThuDAO.insertThuThu(model) > 0) {
                        Dialog dialog = new Dialog(getActivity());
                        dialog.setContentView(R.layout.dialog_success);
                        TextView tv_success = dialog.findViewById(R.id.tv_success);
                        tv_success.setText("Thêm thủ thư thành công");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        },2000);
                        clearForm();
                        dialog.show();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_ds_thuthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), DanhSachThuThuActivity.class);
                startActivity(i);
            }
        });
    }

    private void clearForm() {
        ed_hoTen.setText("");
        ed_namSinh.setText("");
        ed_userName.setText("");
        ed_pass.setText("");
        ed_RepassWord.setText("");
    }
}