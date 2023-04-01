package namtdph08817.fpoly.pn_lib.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

import namtdph08817.fpoly.pn_lib.ChucNangInterface;
import namtdph08817.fpoly.pn_lib.DAO.LoaiSachDAO;
import namtdph08817.fpoly.pn_lib.DAO.SachDAO;
import namtdph08817.fpoly.pn_lib.R;
import namtdph08817.fpoly.pn_lib.entity.LoaiSachModel;
import namtdph08817.fpoly.pn_lib.entity.SachModel;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHolder> {
    private Context context;
    private ArrayList<SachModel> arrayList;
    private SachDAO sachDAO;
    private ChucNangInterface chucNangInterface;

    public SachAdapter(Context context, ChucNangInterface chucNangInterface) {
        this.context = context;
        this.chucNangInterface = chucNangInterface;
        sachDAO = new SachDAO(context);
    }
    public void setData(ArrayList<SachModel> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sach,parent,false);
        return new SachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachViewHolder holder, int position) {
        SachModel model = arrayList.get(position);
        if(model == null)
            return;
        holder.tv_maSach.setText(String.valueOf(model.getMaSach()));
        holder.tv_maloai.setText("Mã loại : "+model.getMaLoai());
        holder.tv_tenSach.setText("Tên sách : "+model.getTenSach());
        holder.tv_giaThue.setText("Giá thuê : "+model.getGiaThue());

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Ban co muon xoa ?");
                builder.setTitle("Thong bao !");
                builder.setPositiveButton("co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(sachDAO.deleteSach(String.valueOf(model.getMaLoai()))>0){
                            Toast.makeText(context, "da xoa", Toast.LENGTH_SHORT).show();
                            chucNangInterface.delete();
                            notifyDataSetChanged();
                        }else {
                            Toast.makeText(context, "chua xoa", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_sach);
                dialog.show();
                TextView tv_title= dialog.findViewById(R.id.txt_title_dialog_sach);
                TextInputEditText ed_tenSach = dialog.findViewById(R.id.tied_add_tenSach);
                TextInputEditText ed_giaThue = dialog.findViewById(R.id.tied_add_giaSach);
                Button btn_add = dialog.findViewById(R.id.btn_add_Sach);
                Button btn_cancel = dialog.findViewById(R.id.btn_cancel_add_Sach);
                tv_title.setText("Sửa sách");
                ed_tenSach.setText(model.getTenSach());
                ed_giaThue.setText(String.valueOf(model.getGiaThue()));
                //spinner
                Spinner spinner = dialog.findViewById(R.id.spn_ma_loai_sach1);
                LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
                ArrayList<LoaiSachModel> list = (ArrayList<LoaiSachModel>) loaiSachDAO.getAll();
                ArrayList<HashMap<String, Object>> hashMaps = new ArrayList<>();
                for (LoaiSachModel model : list){
                    HashMap<String, Object> hsm= new HashMap<>();
                    hsm.put("maLoai",String.valueOf(model.getMaLoai()));
                    hashMaps.add(hsm);
                }
                SimpleAdapter simpleAdapter= new SimpleAdapter(context, hashMaps,
                        android.R.layout.simple_list_item_1,new String[]{"maLoai"},new int[]{android.R.id.text1});
                spinner.setAdapter(simpleAdapter);
                //set spinner selection
                for (int i = 0; i < spinner.getCount(); i++){
                    if (spinner.getItemAtPosition(i).toString().substring(9).equals(model.getMaSach()+"}")){
                        spinner.setSelection(i);
                    }
                }

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
                            SachModel sachModel = new SachModel();
                            sachDAO = new SachDAO(context);
                            sachModel.setMaLoai(Integer.parseInt(maLoai));
                            sachModel.setTenSach(ed_tenSach.getText().toString());
                            sachModel.setGiaThue(Integer.parseInt(ed_giaThue.getText().toString()));
                            if (sachDAO.updateSach(sachModel,String.valueOf(model.getMaSach()))>0){
                                Toast.makeText(context, "edit thành công", Toast.LENGTH_SHORT).show();
                                chucNangInterface.edit();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(context, "edit thất bại", Toast.LENGTH_SHORT).show();
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
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrayList!=null){
            return arrayList.size();
        }
        return 0;
    }

    public class SachViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_maSach,tv_maloai,tv_tenSach,tv_giaThue,tv_delete;
        public SachViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_maSach = itemView.findViewById(R.id.tv_maSach);
            tv_maloai = itemView.findViewById(R.id.tv_ma_loaisach);
            tv_tenSach = itemView.findViewById(R.id.tv_tensach);
            tv_giaThue = itemView.findViewById(R.id.tv_gia_sach);
            tv_delete = itemView.findViewById(R.id.tv_delete_Sach);
        }
    }
}
