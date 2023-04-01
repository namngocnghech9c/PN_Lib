package namtdph08817.fpoly.pn_lib.entity;

public class PhieuMuonModel {
    private String date;
    private int maPM,maTV,maTT,maSach,giaThue,trangThai;

    public PhieuMuonModel(String date, int maPM, int maTV, int maTT, int maSach, int giaThue, int trangThai) {
        this.date = date;
        this.maPM = maPM;
        this.maTV = maTV;
        this.maTT = maTT;
        this.maSach = maSach;
        this.giaThue = giaThue;
        this.trangThai = trangThai;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaTT() {
        return maTT;
    }

    public void setMaTT(int maTT) {
        this.maTT = maTT;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
