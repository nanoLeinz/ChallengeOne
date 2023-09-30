package controller;

import model.Menus;
import model.Orders;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Menu {
    Menus menuList = new Menus();
    ArrayList<Orders> orderList = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    Integer pilihan;
    Integer qty;
    Integer totalQty;
    Integer total;
    String input;

    public void showMenu() {
        Header.printHeader("Selamat Datang di BinarFud", "Silahkah Pilih Menu Makanan : ");

        for (Object[] list : menuList.getNamaMenu()
        ) {
            System.out.println(list[0] + ". " + list[1] + "\t| " + formatNumber((Integer) list[2]));
        }
        System.out.println(
                        "6. Hapus Pesanan"
                        + "\n99. Pesan dan Bayar"
                        + "\n0. Keluar Aplikasi"
        );
        try {
            System.out.print("=> ");
            pilihan = scanner.nextInt();
            route(pilihan);
        } catch (InputMismatchException e) {
            scanner.next(); // Consume the invalid input
            Header.printHeader("Input harus berupa angka!");
            showMenu(); // Prompt the user again
        }
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
            case 6:
                deleteChoice();
                showMenu();
                break;
            case 99:
                confirmAndPay();
                break;
            case 0:
                System.out.println("Terima kasih Sudah Memesan");
                return;
            default:
                Header.printHeader("Mohon masukkan input  pilihan anda");
                System.out.println(
                        "(y) untuk lanjut"
                        + "\n(n) untuk keluar"
                );
                System.out.print("=> ");
                input = scanner.next();
                if (input.equals("y")) {
                    showMenu();
                } else if (input.equals("n")){
                    exit(0);
                }
                showMenu();
                break;
        }
    }

    public void inputQuantity(Integer menuNumber) {
        Header.printHeader("Berapa Pesanan Anda");
        System.out.println(menuList.getNamaMenu()[menuNumber - 1][1] + "\t\t| " + formatNumber((Integer) menuList.getNamaMenu()[menuNumber - 1][2]));
        System.out.println("(Input 0 untuk kembali)");
        System.out.print("Qty => ");
        try {
            qty = scanner.nextInt();
            if (qty == 0) {
                Header.printHeader("Minimal 1 Jumlah Pesanan!");
                inputQuantity(menuNumber);
            }
            orderList.add(new Orders(menuNumber, qty));
            showMenu();
        } catch (InputMismatchException e) {
            scanner.next();
            Header.printHeader("Input harus berupa angka!");
            inputQuantity(menuNumber);
        }
    }
    public void deleteChoice() {
        if (orderList.isEmpty()) {
            System.out.println("Anda belum memesan apapun.");
            return;
        }
        System.out.println("Pilih nomor pesanan yang ingin dihapus:");
        for (int i = 0; i < orderList.size(); i++) {
            Orders order = orderList.get(i);
            System.out.println(i + 1 + ". " + menuList.getNamaMenu()[order.getOrderId() - 1][1]
                    + "\t\t" + order.getQty() + "\t" + formatNumber(order.getTotalPrice()));
        }
        System.out.print("=> ");
        int choice = scanner.nextInt();

        if (choice >= 1 && choice <= orderList.size()) {
            orderList.remove(choice - 1);
            System.out.println("Pesanan berhasil dihapus.");
        } else {
            System.out.println("Nomor pesanan tidak valid.");
        }
    }
    public void confirmAndPay() {
        totalQty = 0;
        total = 0;
        Header.printHeader("Konfirmasi & Pembayaran");

        for (Orders orders : orderList) {
            total += orders.getTotalPrice();
            totalQty += orders.getQty();
            System.out.println(
                    menuList.getNamaMenu()[orders.getOrderId() - 1][1]
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
        try {
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
        } catch (InputMismatchException e) {
            scanner.next(); // Consume the invalid input
            Header.printHeader("Masukkan Menu yang tersedia");
            confirmAndPay(); // Prompt the user again
        }
    }

    public void cetakStruk() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDate = dateFormat.format(new Date());
        String struk = "Struk_Pembayaran_" + currentDate + ".txt";
        totalQty = 0;
        total = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(struk))){
            writer.write(Header.lines);
            writer.newLine();
            writer.write("BinarFud");
            writer.newLine();
            writer.write(Header.lines);
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
                writer.write(menuList.getNamaMenu()[orders.getOrderId() - 1][1].toString());
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
            writer.write(Header.lines);
            writer.newLine();
            writer.write("Simpan struk ini sebagai bukti pembayaran");
            writer.newLine();
            writer.write(Header.lines);

        } catch (IOException e) {
            System.out.println("Gagal Mencetak Struk.");
            e.printStackTrace();
        } finally {
            System.out.println("Struk Berhasil Dicetak");
            exit(0);
        }
    }

    public String formatNumber(Integer num) {
        return new DecimalFormat("###.000").format(num / 1000);
    }
}
