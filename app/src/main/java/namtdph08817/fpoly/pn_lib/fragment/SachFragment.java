package namtdph08817.fpoly.pn_lib.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

import namtdph08817.fpoly.pn_lib.ChucNangInterface;
import namtdph08817.fpoly.pn_lib.DAO.LoaiSachDAO;
import namtdph08817.fpoly.pn_lib.DAO.SachDAO;
import namtdph08817.fpoly.pn_lib.R;
import namtdph08817.fpoly.pn_lib.adapter.LoaiSachAdapter;
import namtdph08817.fpoly.pn_lib.adapter.SachAdapter;
import namtdph08817.fpoly.pn_lib.entity.LoaiSachModel;
import namtdph08817.fpoly.pn_lib.entity.SachModel;


public class SachFragment extends Fragment {
    private ArrayList<SachModel> arrayList = new ArrayList<>();
    private SachAdapter adapter;
    private RecyclerView rcvSach;
    private SachDAO sachDAO;
    private FloatingActionButton fbtn_add;
    private SachModel sachModel;
    public SachFragment() {
        // Required empty public constructor
    }
    public static SachFragment newInstance() {
        SachFragment fragment = new SachFragment();
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
        return inflater.inflate(R.layout.fragment_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvSach = view.findViewById(R.id.rcv_Sach);
        fbtn_add = view.findViewById(R.id.fbtn_Sach);

        loadData();

        fbtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_sach);
                dialog.show();
                TextView tv_title= dialog.findViewById(R.id.txt_title_dialog_sach);
                TextInputEditText ed_tenSach = dialog.findViewById(R.id.tied_add_tenSach);
                TextInputEditText ed_giaThue = dialog.findViewById(R.id.tied_add_giaSach);
                Button btn_add = dialog.findViewById(R.id.btn_add_Sach);
                Button btn_cancel = dialog.findViewById(R.id.btn_cancel_add_Sach);
                tv_title.setText("Thêm sách");
                //spinner
                Spinner spinner = dialog.findViewById(R.id.spn_ma_loai_sach1);
                LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
                ArrayList<LoaiSachModel> list = (ArrayList<LoaiSachModel>) loaiSachDAO.getAll();
                ArrayList<HashMap<String, Object>> hashMaps = new ArrayList<>();
                for (LoaiSachModel model : list){
                    HashMap<String, Object> hsm= new HashMap<>();
                    hsm.put("maLoai",String.valueOf(model.getMaLoai()));
                    hashMaps.add(hsm);
                }
                SimpleAdapter simpleAdapter= new SimpleAdapter(getContext(), hashMaps,
                        android.R.layout.simple_list_item_1,new String[]{"maLoai"},new int[]{android.R.id.text1});
                spinner.setAdapter(simpleAdapter);

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ed_tenSach.getText().toString().equals("")){
                            ed_tenSach.setError("Không được để trống !");
                        }else if (ed_giaThue.getText().toString().equals("")){
                            ed_giaThue.setError("Không được để trống !");
                        }else {
                            HashMap<String, Object> hMaps = (HashMap<String, Object>) spinner.getSelectedItem();
                            String maLoai = (String) hMaps.get("maLoai");
                            sachModel = new SachModel();
                            sachDAO = new SachDAO(getContext());
                            sachModel.setMaLoai(Integer.parseInt(maLoai));
                            sachModel.setTenSach(ed_tenSach.getText().toString());
                            sachModel.setGiaThue(Integer.parseInt(ed_giaThue.getText().toString()));
                            if (sachDAO.insertSach(sachModel)>0){
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
        adapter = new SachAdapter(getContext(), new ChucNangInterface() {
            @Override
            public void delete() {
                loadData();
            }

            @Override
            public void edit() {
                loadData();
            }
        });
        sachDAO = new SachDAO(getContext());
        arrayList = (ArrayList<SachModel>) sachDAO.getAll();
        adapter.setData(arrayList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        rcvSach.setLayoutManager(manager);
        rcvSach.setAdapter(adapter);
    }
}