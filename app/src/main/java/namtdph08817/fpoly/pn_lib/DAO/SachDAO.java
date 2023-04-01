package namtdph08817.fpoly.pn_lib.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import namtdph08817.fpoly.pn_lib.Database.DB_Helper;
import namtdph08817.fpoly.pn_lib.entity.SachModel;

public class SachDAO {
    private SQLiteDatabase db;
    private DB_Helper db_helper;
    public SachDAO(Context context) {
        db_helper = new DB_Helper(context);
        db = db_helper.getWritableDatabase();
    }

    public long insertSach(SachModel model){
        ContentValues values = new ContentValues();
        values.put("maLoai",String.valueOf(model.getMaLoai()));
        values.put("tenSach",model.getTenSach());
        values.put("giaThue",String.valueOf(model.getGiaThue()));
        return db.insert("Sach",null,values);
    }

    public int updateSach(SachModel model, String maSach){
        ContentValues values  = new ContentValues();
        values.put("maLoai",model.getMaLoai());
        values.put("tenSach",model.getTenSach());
        values.put("giaThue",model.getGiaThue());
        return db.update("Sach",values,"maSach=?",new String[]{maSach});
    }

    public int deleteSach(String maSach){
        return db.delete("Sach","maSach=?",new String[]{maSach});
    }

    public List<SachModel> getAll(){
        String sql = "SELECT * FROM Sach";
        return getData(sql);
    }

    @SuppressLint("Range")
    public List<SachModel> getData(String sql, String...SelectArgs){
        List<SachModel> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,SelectArgs);
        while (cursor.moveToNext()){
            SachModel model = new SachModel();
            model.setMaLoai(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maLoai"))));
            model.setMaSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maSach"))));
            model.setTenSach(cursor.getString(cursor.getColumnIndex("tenSach")));
            model.setGiaThue(Integer.parseInt(cursor.getString(cursor.getColumnIndex("giaThue"))));
            list.add(model);
        }
        if(list != null || list.size() != 0){
            return list;
        }
        return null;
    }
}
