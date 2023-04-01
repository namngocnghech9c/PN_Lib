package namtdph08817.fpoly.pn_lib.entity;

public class LoaiSachModel {
    private int maLoai;
    private String tenLoai,nha_cc;

    public LoaiSachModel(int maLoai, String tenLoai, String nha_cc) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.nha_cc = nha_cc;
    }

    public LoaiSachModel() {
    }

    public String getNha_cc() {
        return nha_cc;
    }

    public void setNha_cc(String nha_cc) {
        this.nha_cc = nha_cc;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
