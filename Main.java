import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.io.File;

class Customers {
    private String id, tenKH, ngayNhanPhong, ngayTraPhong;
    private int soPhong, tienPhatSinh;
    private long tongTien, soNgayO;

    public long getTongTien() {
        return tongTien;
    }

    public void nhap(Scanner in, int dem) {
        String tmp = String.valueOf(dem);
        this.id = "KH" + String.join("", Collections.nCopies(2 - tmp.length(), "0")) + tmp;
        tenKH = in.nextLine();
        soPhong = in.nextInt();
        in.nextLine();
        ngayNhanPhong = iso_date(in.nextLine());
        ngayTraPhong = iso_date(in.nextLine());
        tienPhatSinh = in.nextInt();

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate starDate = LocalDate.parse(ngayNhanPhong, formatter1);
        LocalDate endDate = LocalDate.parse(ngayTraPhong, formatter2);
        soNgayO = ChronoUnit.DAYS.between(starDate, endDate) + 1;
        int floor = Integer.parseInt(String.valueOf(soPhong).substring(0, 1));
        if (floor == 1) {
            tongTien = soNgayO * 25 + tienPhatSinh;
        } else if (floor == 2) {
            tongTien = soNgayO * 34 + tienPhatSinh;
        } else if (floor == 3) {
            tongTien = soNgayO * 50 + tienPhatSinh;
        } else {
            tongTien = soNgayO * 80 + tienPhatSinh;
        }

        if (in.hasNext())
            in.nextLine();
    }

    public static String iso_name(String name) {
        String[] words = name.split("\\s+");
        String tmp = "";
        for (String word : words) {
            if (!word.isEmpty()) {
                word = (word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase());
                tmp += (word + " ");
            }
        }
        return tmp.substring(0, tmp.length() - 1);
    }

    public static String iso_date(String date) {
        if (date.charAt(1) == '/') {
            date = "0" + date;
        }
        if (date.charAt(4) == '/') {
            date = date.substring(0, 3) + "0" + date.substring(3);
        }
        return date.trim();
    }

    public String toString() {
        return id + " " + iso_name(tenKH) + " " + soPhong + " " + soNgayO + " " + tongTien;
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("KHACHHANG.in");
            Scanner in = new Scanner(file);
            int n = in.nextInt();
            in.nextLine();
            List<Customers> ds = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                Customers p = new Customers();
                p.nhap(in, i + 1);
                ds.add(p);
            }
            Collections.sort(ds, new Comparator<Customers>() {
                public int compare(Customers a, Customers b) {
                    return Long.compare(b.getTongTien(), a.getTongTien());
                }
            });
            ds.forEach(p -> {
                System.out.println(p);
            });
        } catch (FileNotFoundException e) {

        }
    }
}
