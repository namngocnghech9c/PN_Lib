package namtdph08817.fpoly.pn_lib.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import namtdph08817.fpoly.pn_lib.Database.DB_Helper;
import namtdph08817.fpoly.pn_lib.entity.LoaiSachModel;
import namtdph08817.fpoly.pn_lib.entity.ThanhVienModel;

public class ThanhVienDAO {
    private SQLiteDatabase db;
    private DB_Helper db_helper;
    public ThanhVienDAO(Context context) {
        db_helper = new DB_Helper(context);
        db = db_helper.getWritableDatabase();
    }

    public long insertTV(ThanhVienModel model){
        ContentValues values = new ContentValues();
        values.put("tenTV",model.getTenTV());
        values.put("namSinh",model.getNgaySinh());
        return db.insert("ThanhVien",null,values);
    }

    public int updateTV(ThanhVienModel model, String maTV){
        ContentValues values  = new ContentValues();
        values.put("tenTV",model.getTenTV());
        values.put("namSinh",model.getNgaySinh());
        return db.update("ThanhVien",values,"maTV=?",new String[]{maTV});
    }

    public int deleteThanhVien(String maTV){
        return db.delete("ThanhVien","maTV=?",new String[]{maTV});
    }

    public List<ThanhVienModel> getAll(){
        String sql = "SELECT * FROM ThanhVien";
        return getData(sql);
    }

    @SuppressLint("Range")
    public List<ThanhVienModel> getData(String sql, String...SelectArgs){
        List<ThanhVienModel> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,SelectArgs);
        while (cursor.moveToNext()){
            ThanhVienModel model = new ThanhVienModel();
            model.setMaTV(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV"))));
            model.setTenTV(cursor.getString(cursor.getColumnIndex("tenTV")));
            model.setNgaySinh(cursor.getString(cursor.getColumnIndex("namSinh")));
            list.add(model);
        }
        if(list != null || list.size() != 0){
            return list;
        }
        return null;
    }
}
