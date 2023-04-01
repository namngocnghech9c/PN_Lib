package namtdph08817.fpoly.pn_lib.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import namtdph08817.fpoly.pn_lib.DAO.ThuThuDAO;
import namtdph08817.fpoly.pn_lib.ChucNangInterface;
import namtdph08817.fpoly.pn_lib.R;
import namtdph08817.fpoly.pn_lib.entity.ThuThuModel;

public class ThuThuAdapter extends RecyclerView.Adapter<ThuThuAdapter.ThuThuViewHolder> {
    private Context context;
    private ArrayList<ThuThuModel> arrayList;
    private ThuThuDAO thuThuDAO;
    private ChucNangInterface chucNangInterface;

    public ThuThuAdapter(Context context, ChucNangInterface chucNangInterface) {
        this.context = context;
        this.chucNangInterface = chucNangInterface;
        thuThuDAO = new ThuThuDAO(context);
    }
    public void setData(ArrayList<ThuThuModel> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThuThuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thu_thu,parent,false);
        return new ThuThuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThuThuViewHolder holder, int position) {
        ThuThuModel model = arrayList.get(position);
        if(model == null)
            return;
        holder.tv_name.setText(model.getTenTT());
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Ban co muon xoa ?");
                builder.setTitle("Thong bao !");
                builder.setPositiveButton("co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(thuThuDAO.deleteThuThu(String.valueOf(model.getMaTT()))>0){
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
    }

    @Override
    public int getItemCount() {
        if (arrayList!=null){
            return arrayList.size();
        }
        return 0;
    }

    public class ThuThuViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,tvDelete;
        public ThuThuViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_tenThuThu_item);
            tvDelete = itemView.findViewById(R.id.tv_delete_thu_thu);
        }
    }
}
