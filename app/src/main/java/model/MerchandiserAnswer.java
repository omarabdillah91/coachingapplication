package model;

/**
 * Created by omar.abdillah on 24/08/2016.
 */
public class MerchandiserAnswer {
    private String product;
    private String size;
    private String kompetitor;
    private boolean satu_a;
    private boolean satu_b;
    private boolean dua_a;
    private boolean dua_b;
    private boolean tiga_a;
    private boolean empat_a;
    private boolean lima_a;
    private String enam_a;
    private String enam_b;
    private String enam_c;
    private String enam_d;
    private String tujuh_a;
    private String tujuh_b;
    private boolean tujuh_c;
    private String rpi;
    private String indeks;

    public MerchandiserAnswer(String indeks, String product, String size, String kompetitor, boolean satu_a, boolean satu_b, boolean dua_a,
                              boolean dua_b, boolean tiga_a, boolean empat_a, boolean lima_a, String enam_a, String enam_b, String enam_c,
                              String enam_d, String tujuh_a, String tujuh_b, String rpi,boolean tujuh_c ) {
        this.indeks = indeks;
        this.product = product;
        this.size = size;
        this.kompetitor = kompetitor;
        this.satu_a = satu_a;
        this.satu_b = satu_b;
        this.dua_a = dua_a;
        this.dua_b = dua_b;
        this.tiga_a = tiga_a;
        this.empat_a = empat_a;
        this.lima_a = lima_a;
        this.enam_a = enam_a;
        this.enam_b = enam_b;
        this.enam_c = enam_c;
        this.enam_d = enam_d;
        this.tujuh_a = tujuh_a;
        this.tujuh_b = tujuh_b;
        this.tujuh_c = tujuh_c;
        this.rpi = rpi;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getKompetitor() {
        return kompetitor;
    }

    public void setKompetitor(String kompetitor) {
        this.kompetitor = kompetitor;
    }

    public boolean isSatu_a() {
        return satu_a;
    }

    public void setSatu_a(boolean satu_a) {
        this.satu_a = satu_a;
    }

    public boolean isSatu_b() {
        return satu_b;
    }

    public void setSatu_b(boolean satu_b) {
        this.satu_b = satu_b;
    }

    public boolean isDua_a() {
        return dua_a;
    }

    public void setDua_a(boolean dua_a) {
        this.dua_a = dua_a;
    }

    public boolean isDua_b() {
        return dua_b;
    }

    public void setDua_b(boolean dua_b) {
        this.dua_b = dua_b;
    }

    public boolean isTiga_a() {
        return tiga_a;
    }

    public void setTiga_a(boolean tiga_a) {
        this.tiga_a = tiga_a;
    }

    public boolean isEmpat_a() {
        return empat_a;
    }

    public void setEmpat_a(boolean empat_a) {
        this.empat_a = empat_a;
    }

    public boolean isLima_a() {
        return lima_a;
    }

    public void setLima_a(boolean lima_a) {
        this.lima_a = lima_a;
    }

    public String getEnam_a() {
        return enam_a;
    }

    public void setEnam_a(String enam_a) {
        this.enam_a = enam_a;
    }

    public String getEnam_b() {
        return enam_b;
    }

    public void setEnam_b(String enam_b) {
        this.enam_b = enam_b;
    }

    public String getEnam_c() {
        return enam_c;
    }

    public void setEnam_c(String enam_c) {
        this.enam_c = enam_c;
    }

    public String getEnam_d() {
        return enam_d;
    }

    public void setEnam_d(String enam_d) {
        this.enam_d = enam_d;
    }

    public String getTujuh_a() {
        return tujuh_a;
    }

    public void setTujuh_a(String tujuh_a) {
        this.tujuh_a = tujuh_a;
    }

    public String getTujuh_b() {
        return tujuh_b;
    }

    public void setTujuh_b(String tujuh_b) {
        this.tujuh_b = tujuh_b;
    }

    public boolean isTujuh_c() {
        return tujuh_c;
    }

    public void setTujuh_c(boolean tujuh_c) {
        this.tujuh_c = tujuh_c;
    }

    public String getRpi() {
        return rpi;
    }

    public void setRpi(String rpi) {
        this.rpi = rpi;
    }

    public String getIndeks() {
        return indeks;
    }

    public void setIndeks(String indeks) {
        this.indeks = indeks;
    }
}
