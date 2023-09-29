package model;

public class Menus {
    private final Object[][] namaMenu = {
            {1, "Nasi Goreng", 15000},
            {2, "Mie Goreng", 13000},
            {3, "Nasi + Ayam", 18000},
            {4, "Es TehManis", 3000},
            {5, "Es Jeruk", 5000}
    };

    public Object[][] getNamaMenu() {
        return namaMenu;
    }
}
