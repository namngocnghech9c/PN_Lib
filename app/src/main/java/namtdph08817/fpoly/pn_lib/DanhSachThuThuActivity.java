package namtdph08817.fpoly.pn_lib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import namtdph08817.fpoly.pn_lib.DAO.ThuThuDAO;
import namtdph08817.fpoly.pn_lib.adapter.ThuThuAdapter;
import namtdph08817.fpoly.pn_lib.entity.ThuThuModel;

public class DanhSachThuThuActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ThuThuAdapter adapter;
    private ThuThuDAO thuThuDAO;
    private ArrayList<ThuThuModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_thu_thu);
        recyclerView = findViewById(R.id.reV_thu_thu);
        loadData();
    }

    private void loadData() {
        adapter = new ThuThuAdapter(this, new ChucNangInterface() {
            @Override
            public void delete() {
                loadData();
            }

            @Override
            public void edit() {

            }
        });
        thuThuDAO = new ThuThuDAO(this);
        arrayList = (ArrayList<ThuThuModel>) thuThuDAO.getAll();
        adapter.setData(arrayList);
        LinearLayoutManager manager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

}