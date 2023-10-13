package Controller;

import Model.Menus;
import Model.Orders;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;

public class Menu {
    Menus menuList = new Menus();
    ArrayList<Orders> orderList = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    public final String struk = "Struk_Pembayaran.txt";
    Integer pilihan, qty, totalQty, total;

    public void showMenu() {
        System.out.println(
                "==========================="
                        + "\nSelamat Datang di BinarFud"
                        + "\n==========================="
                        + "\n\nSilahkah Pilih Menu Makanan : "
        );

        for (Object[] list : menuList.namaMenu
        ) {
            System.out.println(list[0] + ". " + list[1] + "\t| " + formatNumber((Integer) list[2]));
        }
        System.out.println(
                "99. Pesan dan Bayar"
                + "\n0. Keluar Aplikasi"
        );
        System.out.print("=> ");
        pilihan = scanner.nextInt();

        route(pilihan);
    }

    public void route(Integer choice) {
        switch (choice) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                inputQuantity(choice);
                break;
            case 99:
                confirmAndPay();
                break;
            case 0:
                System.out.println("Terima kasih Sudah Memesan");
                return;
            default:
                System.out.println("Silahkan Pilih Menu yang ada.");
                showMenu();
                break;
        }
    }

    public void inputQuantity(Integer menuNumber) {
        System.out.println(
                "==========================="
                        + "\nBerapa Pesanan Anda"
                        + "\n==========================="

        );
        System.out.println(menuList.namaMenu[menuNumber - 1][1] + "\t\t| " + formatNumber((Integer) menuList.namaMenu[menuNumber - 1][2]));
        System.out.println("(Input 0 untuk kembali)");
        System.out.print("Qty => ");
        qty = scanner.nextInt();
        if (qty == 0) showMenu();
        orderList.add(new Orders(menuNumber, qty,(Integer) menuList.namaMenu[menuNumber - 1][2]));
        showMenu();
    }

    public void confirmAndPay() {
        totalQty = 0;
        total = 0;
        System.out.println(
                "==========================="
                        + "\nKonfirmasi & Pembayaran"
                        + "\n==========================="
        );

        for (Orders orders : orderList) {
            total += orders.getTotalPrice();
            totalQty += orders.getQty();
            System.out.println(
                    menuList.namaMenu[orders.getOrderId() - 1][1]
                            + "\t\t"
                            + orders.getQty()
                            + "\t"
                            + formatNumber(orders.getTotalPrice())
            );

        }
        System.out.println(
                "------------------------- +"
                        + "\nTotal\t\t\t"
                        + totalQty
                        + "\t"
                        + formatNumber(total)
        );

        System.out.println(
                "1. Konfirmasi & Bayar"
                        + "\n2. Kembali ke Menu Utama"
                        + "\n0. Keluar Aplikasi"
        );
        System.out.print("=> ");
        pilihan = scanner.nextInt();


        if (pilihan == 0) {
            System.out.println("Pemesanan Dibatalkan");
            exit(1);
        } else if (pilihan == 2) {
            showMenu();
        } else if (pilihan == 1) {
            cetakStruk();

        }
    }

    public void cetakStruk() {
        if (!fileExists(struk)) {
            createFile(struk);
        }
        totalQty = 0;
        total = 0;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(struk));
            writer.write("===========================");
            writer.newLine();
            writer.write("BinarFud");
            writer.newLine();
            writer.write("===========================");
            writer.newLine();
            writer.newLine();
            writer.write("Terimakasih sudah memesan di BinarFud");
            writer.newLine();
            writer.newLine();
            writer.write("Dibawah ini adalah pesanan anda");
            writer.newLine();
            writer.newLine();

            for (Orders orders : orderList) {
                total += orders.getTotalPrice();
                totalQty += orders.getQty();
                writer.write(menuList.namaMenu[orders.getOrderId() - 1][1].toString());
                writer.write("\t\t");
                writer.write(orders.getQty().toString());
                writer.write("\t");
                writer.write(formatNumber(orders.getTotalPrice()));
                writer.newLine();
            }
            writer.write("------------------------- +");
            writer.newLine();
            writer.write("Total");
            writer.write("\t\t\t");
            writer.write(totalQty.toString());
            writer.write("\t");
            writer.write(formatNumber(total));

            writer.newLine();
            writer.newLine();
            writer.write("Pembayaran : BinarCash");
            writer.newLine();
            writer.newLine();
            writer.write("===========================");
            writer.newLine();
            writer.write("Simpan struk ini sebagai bukti pembayaran");
            writer.newLine();
            writer.write("===========================");
            writer.close();
            System.out.println("Struk Berhasil Dicetak");
            exit(0);
        } catch (IOException e) {
            System.out.println("Gagal Mencetak Struk.");
            e.printStackTrace();
        }
    }

    public boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public static void createFile(String fileName) {
        try {
            File file = new File(fileName);
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membuat Struk Pembayaran : " + e.getMessage());
        }
    }

    public String formatNumber(Integer num) {
        String formattedNumber = new DecimalFormat("###.000").format(num / 1000);
        return formattedNumber;
    }
}
