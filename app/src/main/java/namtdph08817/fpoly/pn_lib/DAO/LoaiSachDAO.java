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

public class LoaiSachDAO {
    private SQLiteDatabase db;
    private DB_Helper db_helper;
    public LoaiSachDAO(Context context) {
        db_helper = new DB_Helper(context);
        db = db_helper.getWritableDatabase();
    }

    public long insertLoaiSach(LoaiSachModel model){
        ContentValues values = new ContentValues();
        values.put("tenLoai",model.getTenLoai());
        values.put("nhaCC",model.getNha_cc());
        return db.insert("LoaiSach",null,values);
    }

    public int updateLoaiSach(LoaiSachModel model, String maLoai){
        ContentValues values  = new ContentValues();
        values.put("tenLoai",model.getTenLoai());
        values.put("nhaCC",model.getNha_cc());
        return db.update("LoaiSach",values,"maLoai=?",new String[]{maLoai});
    }

    public int deleteLoaiSach(String maloai){
        return db.delete("LoaiSach","maLoai=?",new String[]{maloai});
    }

    public List<LoaiSachModel> getAll(){
        String sql = "SELECT * FROM LoaiSach";
        return getData(sql);
    }

    @SuppressLint("Range")
    public List<LoaiSachModel> getData(String sql, String...SelectArgs){
        List<LoaiSachModel> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,SelectArgs);
        while (cursor.moveToNext()){
            LoaiSachModel model = new LoaiSachModel();
            model.setMaLoai(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maLoai"))));
            model.setTenLoai(cursor.getString(cursor.getColumnIndex("tenLoai")));
            model.setNha_cc(cursor.getString(cursor.getColumnIndex("nhaCC")));
            list.add(model);
        }
        if(list != null || list.size() != 0){
            return list;
        }
        return null;
    }
}
