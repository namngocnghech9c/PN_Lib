package namtdph08817.fpoly.pn_lib.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import namtdph08817.fpoly.pn_lib.Database.DB_Helper;
import namtdph08817.fpoly.pn_lib.entity.ThuThuModel;

public class ThuThuDAO {
    private SQLiteDatabase db;
    private DB_Helper db_helper;
    public ThuThuDAO(Context context) {
        db_helper = new DB_Helper(context);
        db = db_helper.getWritableDatabase();
    }
    //insert thuthu
    public long insertThuThu(ThuThuModel model){
        ContentValues values = new ContentValues();
        values.put("hoTen",model.getTenTT());
        values.put("userName",model.getUserName());
        values.put("matKhau",model.getPassWord());
        values.put("namSinh",model.getNamSinh());
        return db.insert("ThuThu",null,values);
    }
    //xoa thuthu
    public int deleteThuThu(String id){
        return db.delete("ThuThu","maTT=?",new String[]{id});
    }

    //doi mat khau
    public boolean doiMatKhau(String userName,String oldPass,String newPass){
        Cursor cursor = db.rawQuery("SELECT * FROM ThuThu WHERE userName = ? AND matKhau = ?",new String[]{userName,oldPass});
        if (cursor.getCount() > 0){
            ContentValues values = new ContentValues();
            values.put("matKhau",newPass);
            int check = db.update("ThuThu",values,"userName=?",new String[]{userName});
            if (check < 0)
                return false;
            return true;
        }
        return false;
    }

    //getAll
    public List<ThuThuModel> getAll(){
        String sql = "SELECT * FROM ThuThu";
        return getData(sql);
    }

    public boolean getTenThuThu(int maTT,String tenTT){
        SQLiteDatabase sqldb = db_helper.getReadableDatabase();
        Cursor cursor = sqldb.rawQuery("SELECT * FROM ThuThu WHERE maTT = ? AND hoTen = ?",new String[]{String.valueOf(maTT),tenTT});
        if (cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean checkDangNhap(String userName,String matKhau){
        SQLiteDatabase sqldb = db_helper.getReadableDatabase();
        Cursor cursor = sqldb.rawQuery("SELECT * FROM ThuThu WHERE userName = ? AND matKhau = ?",new String[]{userName,matKhau});
        if (cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }

    @SuppressLint("Range")
    public List<ThuThuModel> getData(String sql, String...SelectArgs){
        List<ThuThuModel> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,SelectArgs);
        while (cursor.moveToNext()){
            ThuThuModel model = new ThuThuModel();
            model.setMaTT(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTT"))));
            model.setTenTT(cursor.getString(cursor.getColumnIndex("hoTen")));
            model.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
            model.setPassWord(cursor.getString(cursor.getColumnIndex("matKhau")));
            model.setNamSinh(cursor.getString(cursor.getColumnIndex("namSinh")));
            list.add(model);
        }
        if(list != null || list.size() != 0){
            return list;
        }
        return null;
    }
}
