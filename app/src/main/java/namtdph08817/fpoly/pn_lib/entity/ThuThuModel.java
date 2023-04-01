package namtdph08817.fpoly.pn_lib.entity;

public class ThuThuModel {
    private int maTT;
    private String tenTT,userName,passWord,namSinh;

    public ThuThuModel() {
    }

    public ThuThuModel(String tenTT, String userName, String passWord, String namSinh) {
        this.tenTT = tenTT;
        this.userName = userName;
        this.passWord = passWord;
        this.namSinh = namSinh;
    }

    public int getMaTT() {
        return maTT;
    }

    public void setMaTT(int maTT) {
        this.maTT = maTT;
    }

    public String getTenTT() {
        return tenTT;
    }

    public void setTenTT(String tenTT) {
        this.tenTT = tenTT;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }
}
