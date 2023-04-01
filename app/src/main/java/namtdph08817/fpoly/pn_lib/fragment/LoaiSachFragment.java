package namtdph08817.fpoly.pn_lib.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import namtdph08817.fpoly.pn_lib.DAO.LoaiSachDAO;
import namtdph08817.fpoly.pn_lib.ChucNangInterface;
import namtdph08817.fpoly.pn_lib.R;
import namtdph08817.fpoly.pn_lib.adapter.LoaiSachAdapter;
import namtdph08817.fpoly.pn_lib.entity.LoaiSachModel;

public class LoaiSachFragment extends Fragment {
    private ArrayList<LoaiSachModel> arrayList = new ArrayList<>();
    private LoaiSachAdapter adapter;
    private RecyclerView rcvLoaiSach;
    private LoaiSachDAO loaiSachDAO;
    private FloatingActionButton fbtn_add;
    private LoaiSachModel loaiSachModel;

    public LoaiSachFragment() {
        // Required empty public constructor
    }

    public static LoaiSachFragment newInstance() {
        LoaiSachFragment fragment = new LoaiSachFragment();
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
        return inflater.inflate(R.layout.fragment_loai_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvLoaiSach = view.findViewById(R.id.rcv_loaiSach);
        fbtn_add = view.findViewById(R.id.fbtn_loaiSach);

        loadData();

        fbtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_add_loai_sach);
                dialog.show();
                TextView tv_title= dialog.findViewById(R.id.txt_title_dialog_loaisach);
                TextInputEditText ed_tenLoai = dialog.findViewById(R.id.tied_add_tenLoaiSach);
                TextInputEditText ed_nhaCC = dialog.findViewById(R.id.tied_add_nha_cc);
                Button btn_add = dialog.findViewById(R.id.btn_add_loaiSach);
                Button btn_cancel = dialog.findViewById(R.id.btn_cancel_add_loaiSach);
                tv_title.setText("Thêm loại sách");

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ed_tenLoai.getText().toString().equals("")){
                            ed_tenLoai.setError("Không được để trống !");
                        }else if (ed_nhaCC.getText().toString().equals("")){
                            ed_nhaCC.setError("Không được để trống !");
                        }else {
                            loaiSachModel = new LoaiSachModel();
                            loaiSachModel.setTenLoai(ed_tenLoai.getText().toString());
                            loaiSachModel.setNha_cc(ed_nhaCC.getText().toString());
                            if (loaiSachDAO.insertLoaiSach(loaiSachModel)>0){
                                Toast.makeText(getContext(), "Insert thành công", Toast.LENGTH_SHORT).show();
                                loadData();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(getContext(), "Insert thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    private void loadData() {
        adapter = new LoaiSachAdapter(getContext(), new ChucNangInterface() {
            @Override
            public void delete() {
                loadData();
            }

            @Override
            public void edit() {
                loadData();
            }
        });
        loaiSachDAO = new LoaiSachDAO(getContext());
        arrayList = (ArrayList<LoaiSachModel>) loaiSachDAO.getAll();
        adapter.setData(arrayList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        rcvLoaiSach.setLayoutManager(manager);
        rcvLoaiSach.setAdapter(adapter);
    }
}