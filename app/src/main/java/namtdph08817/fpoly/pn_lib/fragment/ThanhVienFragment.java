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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import namtdph08817.fpoly.pn_lib.ChucNangInterface;
import namtdph08817.fpoly.pn_lib.DAO.LoaiSachDAO;
import namtdph08817.fpoly.pn_lib.DAO.ThanhVienDAO;
import namtdph08817.fpoly.pn_lib.R;
import namtdph08817.fpoly.pn_lib.adapter.LoaiSachAdapter;
import namtdph08817.fpoly.pn_lib.adapter.ThanhVienAdapter;
import namtdph08817.fpoly.pn_lib.entity.LoaiSachModel;
import namtdph08817.fpoly.pn_lib.entity.ThanhVienModel;

public class ThanhVienFragment extends Fragment {
    private ArrayList<ThanhVienModel> arrayList = new ArrayList<>();
    private ThanhVienAdapter adapter;
    private RecyclerView rcv_TV;
    private ThanhVienDAO thanhVienDAO;
    private FloatingActionButton fbtn_add;
    private ThanhVienModel thanhVienModel;
    public ThanhVienFragment() {
        // Required empty public constructor
    }

    public static ThanhVienFragment newInstance() {
        ThanhVienFragment fragment = new ThanhVienFragment();
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
        return inflater.inflate(R.layout.fragment_thanh_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv_TV = view.findViewById(R.id.rcv_ThanhVien);
        fbtn_add = view.findViewById(R.id.fbtn_ThanhVien);

        loadData();

        fbtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_thanh_vien);
                dialog.show();
                TextView tv_title= dialog.findViewById(R.id.txt_title_dialog_Thanh_vien);
                TextInputEditText ed_tenTV = dialog.findViewById(R.id.tied_add_tenTV);
                TextInputEditText ed_namSinh = dialog.findViewById(R.id.tied_add_namSinh);
                Button btn_add = dialog.findViewById(R.id.btn_add_thanhVien);
                Button btn_cancel = dialog.findViewById(R.id.btn_cancel_add_thanhVien);

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ed_tenTV.getText().toString().equals("")){
                            ed_tenTV.setError("Không được để trống !");
                        }else if (ed_namSinh.getText().toString().equals("")){
                            ed_namSinh.setError("Không được để trống !");
                        }else {
                            thanhVienModel = new ThanhVienModel();
                            thanhVienModel.setTenTV(ed_tenTV.getText().toString());
                            thanhVienModel.setNgaySinh(ed_namSinh.getText().toString());
                            if (thanhVienDAO.insertTV(thanhVienModel)>0){
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
        adapter = new ThanhVienAdapter(getContext(), new ChucNangInterface() {
            @Override
            public void delete() {
                loadData();
            }

            @Override
            public void edit() {
                loadData();
            }
        });
        thanhVienDAO = new ThanhVienDAO(getContext());
        arrayList = (ArrayList<ThanhVienModel>) thanhVienDAO.getAll();
        adapter.setData(arrayList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        rcv_TV.setLayoutManager(manager);
        rcv_TV.setAdapter(adapter);

    }
}