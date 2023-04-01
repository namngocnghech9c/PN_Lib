package namtdph08817.fpoly.pn_lib.entity;

public class ThanhVienModel {
    private int maTV;
    private String tenTV,ngaySinh;

    public ThanhVienModel() {
    }

    public ThanhVienModel(int maTV, String tenTV, String ngaySinh) {
        this.maTV = maTV;
        this.tenTV = tenTV;
        this.ngaySinh = ngaySinh;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getTenTV() {
        return tenTV;
    }

    public void setTenTV(String tenTV) {
        this.tenTV = tenTV;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
}
