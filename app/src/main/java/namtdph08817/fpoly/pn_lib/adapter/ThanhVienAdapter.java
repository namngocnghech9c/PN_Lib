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

import namtdph08817.fpoly.pn_lib.ChucNangInterface;
import namtdph08817.fpoly.pn_lib.DAO.LoaiSachDAO;
import namtdph08817.fpoly.pn_lib.DAO.ThanhVienDAO;
import namtdph08817.fpoly.pn_lib.R;
import namtdph08817.fpoly.pn_lib.entity.LoaiSachModel;
import namtdph08817.fpoly.pn_lib.entity.ThanhVienModel;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienHolder> {
    private Context context;
    private ArrayList<ThanhVienModel> arrayList;
    private ThanhVienDAO thanhVienDAO;
    private ChucNangInterface chucNangInterface;

    public ThanhVienAdapter(Context context, ChucNangInterface chucNangInterface) {
        this.context = context;
        this.chucNangInterface = chucNangInterface;
        thanhVienDAO = new ThanhVienDAO(context);
    }
    public void setData(ArrayList<ThanhVienModel> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThanhVienHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thanh_vien,parent,false);
        return new ThanhVienHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienHolder holder, int position) {
        ThanhVienModel model = arrayList.get(position);
        if(model == null)
            return;
        holder.tv_maTV.setText("Mã TV : "+model.getMaTV());
        holder.tv_tenTV.setText("Tên TV : "+model.getTenTV());
        holder.tv_date.setText("Năm sinh : "+model.getNgaySinh());
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Ban co muon xoa ?");
                builder.setTitle("Thong bao !");
                builder.setPositiveButton("co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(thanhVienDAO.deleteThanhVien(String.valueOf(model.getMaTV()))>0){
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
                dialog.setContentView(R.layout.dialog_thanh_vien);
                dialog.show();
                TextView tv_title= dialog.findViewById(R.id.txt_title_dialog_Thanh_vien);
                TextInputEditText ed_tenTV = dialog.findViewById(R.id.tied_add_tenTV);
                TextInputEditText ed_namSinh = dialog.findViewById(R.id.tied_add_namSinh);
                Button btn_add = dialog.findViewById(R.id.btn_add_thanhVien);
                Button btn_cancel = dialog.findViewById(R.id.btn_cancel_add_thanhVien);
                tv_title.setText("Sửa thành viên");
                ed_tenTV.setText(model.getTenTV());
                ed_namSinh.setText(model.getNgaySinh());

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ed_tenTV.getText().toString().equals("")){
                            ed_tenTV.setError("Không được để trống !");
                        }else if (ed_namSinh.getText().toString().equals("")){
                            ed_namSinh.setError("Không được để trống !");
                        }else {
                            ThanhVienModel thanhVienModel = new ThanhVienModel();
                            thanhVienModel.setTenTV(ed_tenTV.getText().toString());
                            thanhVienModel.setNgaySinh(ed_namSinh.getText().toString());
                            if (thanhVienDAO.updateTV(thanhVienModel, String.valueOf(model.getMaTV()))>0){
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

    public class ThanhVienHolder extends RecyclerView.ViewHolder {
        private TextView tv_maTV,tv_tenTV,tv_date,tv_delete;
        public ThanhVienHolder(@NonNull View itemView) {
            super(itemView);
            tv_maTV = itemView.findViewById(R.id.tv_ma_TV);
            tv_tenTV = itemView.findViewById(R.id.tv_tenTV);
            tv_date = itemView.findViewById(R.id.tv_nam_sinh);
            tv_delete = itemView.findViewById(R.id.tv_delete_Thanh_vien);
        }
    }
}
