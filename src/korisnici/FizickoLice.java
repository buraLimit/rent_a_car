package korisnici;


import vozniPark.Vozilo;

import java.time.Year;

public class FizickoLice extends Korisnik {

    private Vozilo vozilo;
    private int godina_rodjenja;
    private String brLK;
    private int godina_izdavanja_dozvole;

    public FizickoLice() {
    }

    public FizickoLice(int godina_rodjenja, String brLK, int godina_izdavanja_dozvole, String ime, String prezime, String telefon) {
        super(ime, prezime, telefon);
        this.godina_rodjenja = godina_rodjenja;
        this.brLK = brLK;
        this.godina_izdavanja_dozvole = godina_izdavanja_dozvole;
    }

    public Vozilo getVozilo() {
        return vozilo;
    }

    public void setVozilo(Vozilo vozilo) {
        this.vozilo = vozilo;
    }

    public int getGodina_rodjenja() {
        return godina_rodjenja;
    }

    public void setGodina_rodjenja(int godina_rodjenja) {
        this.godina_rodjenja = godina_rodjenja;
    }

    public String getBrLK() {
        return brLK;
    }

    public void setBrLK(String brLK) {
        this.brLK = brLK;
    }

    public int getGodina_izdavanja_dozvole() {
        return godina_izdavanja_dozvole;
    }

    public void setGodina_izdavanja_dozvole(int godina_izdavanja_dozvole) {
        this.godina_izdavanja_dozvole = godina_izdavanja_dozvole;
    }


    @Override
    public double obracunavanjeCene() {
        //obracunavanje popusta za cenu dana svakog vozila u zavnisnosti od broja dana rentiranja

        for (int i = 0; i < this.getBrDanaRentiranja() / 10; i++) {
            vozilo.setCenaDan(vozilo.getCenaDan() * 0.95);
        }
        //obracunavanje ukupne cene
        double cena = vozilo.getCenaDan() * this.getBrDanaRentiranja();

        return cena;
    }

    //PROVERA KADA JE KORISNIK POLOZIO VOZNJU, USLOV JE DA MORA IMATI MINIMUM 4 GODINE DOZVOLU KAKO BI RENTIRAO VOZILO

    public boolean proveraDozvole(int godina_izdavanja) {
        if (Year.now().getValue() - godina_izdavanja < 4) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ime i prezime: " + this.getIme() + " " + this.getPrezime() + "\r\n" +
                "Godina rodjenja: " + godina_rodjenja + "\r\n" +
                "Telefon: " + this.getTelefon() + "\r\n" +
                "Broj licne karte: " + brLK + "\r\n" +
                "Godina izdavanja vozacke dozvole: " + godina_izdavanja_dozvole + "\r\n" +
                "Rentirano vozilo: " + this.vozilo + "\r\n" +
                "Broj dana rentiranja vozila: " + this.getBrDanaRentiranja();
    }
}
