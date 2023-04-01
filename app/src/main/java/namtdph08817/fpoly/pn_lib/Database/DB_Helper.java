Spackage namtdph08817.fpoly.pn_lib.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB_Helper extends SQLiteOpenHelper {
    public DB_Helper(@Nullable Context context) {
        super(context, "QLTV.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlThuThu = "CREATE TABLE ThuThu(maTT INTEGER PRIMARY KEY AUTOINCREMENT," +
                " hoTen TEXT NOT NULL," +
                "userName TEXT NOT NULL," +
                " matKhau TEXT NOT NULL, " +
                "namSinh TEXT NOT NULL)";
        sqLiteDatabase.execSQL(sqlThuThu);
        String sqlLoaiSach =  "CREATE TABLE LoaiSach(maLoai INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenLoai TEXT NOT NULL,nhaCC TEXT NOT NULL)";
        sqLiteDatabase.execSQL(sqlLoaiSach);

        String sqlSach =  "CREATE TABLE Sach(maSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maLoai INTEGER NOT NULL," +
                "tenSach TEXT NOT NULL, " +
                "giaThue INTEGER NOT NULL)";
        sqLiteDatabase.execSQL(sqlSach);

        String sqlThanhVien =  "CREATE TABLE ThanhVien(maTV INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenTV TEXT NOT NULL, " +
                "namSinh TEXT NOT NULL)";
        sqLiteDatabase.execSQL(sqlThanhVien);
        String sqlPhieuMuon =  "CREATE TABLE PhieuMuon(maPM INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maTT INTEGER NOT NULL, " +
                "maTV INTEGER NOT NULL, " +
                "maSach INTEGER NOT NULL, " +
                "ngay TEXT NOT NULL, " +
                "trangThai INTEGER NOT NULL, " +
                "tienThue INTEGER NOT NULL)";
        sqLiteDatabase.execSQL(sqlPhieuMuon);
        sqLiteDatabase.execSQL("INSERT INTO ThuThu VALUES(0,'Trịnh Đình Nam','admin','admin','2000')");
        sqLiteDatabase.execSQL("INSERT INTO Sach VALUES(1,1,'Cậu bé bút chì',15000)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropThuThu="DROP TABLE IF EXISTS ThuThu";
        sqLiteDatabase.execSQL(dropThuThu);
        String dropLoaiSach = "DROP TABLE IF EXISTS LoaiSach";
        sqLiteDatabase.execSQL(dropLoaiSach);
        String dropSach = "DROP TABLE IF EXISTS Sach";
        sqLiteDatabase.execSQL(dropSach);
        String dropThanhVien = "DROP TABLE IF EXISTS ThanhVien";
        sqLiteDatabase.execSQL(dropThanhVien);
        String dropPhieuMuon = "DROP TABLE IF EXISTS PhieuMuon";
        sqLiteDatabase.execSQL(dropPhieuMuon);

        onCreate(sqLiteDatabase);
    }
}
