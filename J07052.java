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

class THISINH {
    private String ma, ten;
    private double toan, ly, hoa, diemXT, diemUT;

    public double getDiemXT() {
        return diemXT;
    }

    public String getMa() {
        return ma;
    }

    public void nhap(Scanner in) {
        ma = in.nextLine();
        ten = iso_name(in.nextLine());
        toan = in.nextDouble();
        ly = in.nextDouble();
        hoa = in.nextDouble();
        if (ma.substring(0, 3).compareTo("KV1") == 0) {
            diemUT = 0.5;
        } else if (ma.substring(0, 3).compareTo("KV2") == 0) {
            diemUT = 1;
        } else {
            diemUT = 2.5;
        }
        diemXT = toan * 2 + ly + hoa + diemUT;
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

    public static String customizePoint(double x) {
        if ((int) x == x) {
            return String.valueOf((int) x);
        } else {
            return String.valueOf(x);
        }
    }

    public String toString() {
        return ma + " " + ten + " " + customizePoint(diemUT) + " " + customizePoint(diemXT);
    }
}

public class J07052 {
    public static void main(String[] args) {
        try {
            File file = new File("THISINH.in");
            Scanner in = new Scanner(file);
            int n = in.nextInt();
            in.nextLine();
            List<THISINH> ds = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                THISINH p = new THISINH();
                p.nhap(in);
                ds.add(p);
            }
            int chiTieu = in.nextInt();
            Collections.sort(ds, new Comparator<THISINH>() {
                public int compare(THISINH a, THISINH b) {
                    if (Double.compare(b.getDiemXT(), a.getDiemXT()) == 0) {
                        return a.getMa().compareTo(b.getMa());
                    }
                    return Double.compare(b.getDiemXT(), a.getDiemXT());
                }
            });
            double diemChuan = Double.MAX_VALUE;
            for (int i = 0; i < chiTieu; i++) {
                if (ds.get(i).getDiemXT() <= diemChuan) {
                    diemChuan = ds.get(i).getDiemXT();
                }
            }
            System.out.println(diemChuan);
            for (THISINH ts : ds) {
                System.out.print(ts + " ");
                if (ts.getDiemXT() >= diemChuan) {
                    System.out.println("TRUNG TUYEN");
                } else {
                    System.out.println("TRUOT");
                }
            }
        } catch (FileNotFoundException e) {

        }
    }
}
