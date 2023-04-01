package namtdph08817.fpoly.pn_lib.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import namtdph08817.fpoly.pn_lib.DAO.LoaiSachDAO;
import namtdph08817.fpoly.pn_lib.ChucNangInterface;
import namtdph08817.fpoly.pn_lib.R;
import namtdph08817.fpoly.pn_lib.entity.LoaiSachModel;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.LoaiSachViewhodel> {
    private Context context;
    private ArrayList<LoaiSachModel> arrayList;
    private LoaiSachDAO loaiSachDAO;
    private ChucNangInterface chucNangInterface;

    public LoaiSachAdapter(Context context, ChucNangInterface chucNangInterface) {
        this.context = context;
        this.chucNangInterface = chucNangInterface;
        loaiSachDAO = new LoaiSachDAO(context);
    }
    public void setData(ArrayList<LoaiSachModel> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LoaiSachViewhodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_loai_sach,parent,false);
        return new LoaiSachViewhodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachViewhodel holder, int position) {
        LoaiSachModel model = arrayList.get(position);
        if(model == null)
            return;
        holder.tv_maloai.setText(String.valueOf(model.getMaLoai()));
        holder.tv_tenLoai.setText("Tên loại : "+model.getTenLoai());
        holder.tv_nhaCC.setText("Nhà cung cấp : "+model.getNha_cc());
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Ban co muon xoa ?");
                builder.setTitle("Thong bao !");
                builder.setPositiveButton("co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(loaiSachDAO.deleteLoaiSach(String.valueOf(model.getMaLoai()))>0){
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
                dialog.setContentView(R.layout.dialog_add_loai_sach);
                dialog.show();
                TextView tv_title= dialog.findViewById(R.id.txt_title_dialog_loaisach);
                TextInputEditText ed_tenLoai = dialog.findViewById(R.id.tied_add_tenLoaiSach);
                TextInputEditText ed_nhaCC = dialog.findViewById(R.id.tied_add_nha_cc);
                Button btn_add = dialog.findViewById(R.id.btn_add_loaiSach);
                Button btn_cancel = dialog.findViewById(R.id.btn_cancel_add_loaiSach);
                tv_title.setText("Sửa loại sách");
                ed_tenLoai.setText("Tên loại : "+model.getTenLoai());
                ed_nhaCC.setText("Nhà cung cấp : "+model.getNha_cc());

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ed_tenLoai.getText().toString().equals("")){
                            ed_tenLoai.setError("Không được để trống !");
                        }else {
                            LoaiSachModel loaiSachModel = new LoaiSachModel();
                            loaiSachModel.setTenLoai(ed_tenLoai.getText().toString());
                            loaiSachModel.setNha_cc(ed_nhaCC.getText().toString());

                            if (loaiSachDAO.updateLoaiSach(loaiSachModel, String.valueOf(model.getMaLoai()))>0){
                                Toast.makeText(context, "edit thành công", Toast.LENGTH_SHORT).show();
                                chucNangInterface.edit();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(context, "edit thất bại", Toast.LENGTH_SHORT).show();
                            }
                            notifyDataSetChanged();
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

    public class LoaiSachViewhodel extends RecyclerView.ViewHolder {
        private TextView tv_maloai,tv_tenLoai,tv_nhaCC,tv_delete;
        public LoaiSachViewhodel(@NonNull View itemView) {
            super(itemView);
            tv_maloai = itemView.findViewById(R.id.tv_maLoaiSach);
            tv_tenLoai = itemView.findViewById(R.id.tv_tenloaisach);
            tv_nhaCC = itemView.findViewById(R.id.tv_nha_cc);
            tv_delete = itemView.findViewById(R.id.tv_delete_loaiSach);
        }
    }
}
