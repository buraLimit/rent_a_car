package korisnici;


import vozniPark.Vozilo;

import java.util.ArrayList;

public class PravnoLice extends Korisnik {
    private String ime_firme;
    private String pib;
    ArrayList<Vozilo> listaVozila;

    public PravnoLice(){
    }

    public PravnoLice(String ime, String prezime, String telefon, String ime_firme, String pib) {
        super(ime, prezime, telefon);
        this.ime_firme = ime_firme;
        this.pib = pib;
    }

    public void setIme_firme(String ime_firme) {
        this.ime_firme = ime_firme;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getIme_firme() {
        return ime_firme;
    }

    public String getPib() {
        return pib;
    }

    public ArrayList<Vozilo> getListaVozila() {
        return listaVozila;
    }

    public void setListaVozila(ArrayList<Vozilo> listaVozila) {
        this.listaVozila = listaVozila;
    }

   public void dodajListu (ArrayList<Vozilo> listaV){
        listaVozila=listaV;
    }

    @Override
    public double obracunavanjeCene() {
        double cena = 0;
        //obracunavanje popusta za cenu dana svakog vozila u zavnisnosti od broja dana rentiranja
        for (Vozilo v:
             listaVozila) {
            for (int i = 0; i < this.getBrDanaRentiranja()/10; i++) {
                v.setCenaDan(v.getCenaDan()*0.97);
            }
        }
        //obracunavanje ukupne cene
        for (Vozilo v:listaVozila
        ) {
            cena += v.getCenaDan()*this.getBrDanaRentiranja();
        }
        //obracunavanje popusta na ukupnu cenu u zavisnosti od broja rentiranih automobila
        for (int i = 0; i < listaVozila.size() / 5; i++) {
            cena += cena*0.95;
        }
        return cena;
    }

    @Override
    public String toString() {
        return "Ime firme: " + ime_firme + "\r\n" +
                "PIB: " + pib + "\r\n" +
                "Ime i prezime: " + this.getIme() + " " + this.getPrezime() + "\r\n" +
                "Telefon: " + this.getTelefon() + "\r\n" +
                "Broj dana rentiranja vozila: " + this.getBrDanaRentiranja() + "\r\n" +
                "Broj rentiranih vozila: " + listaVozila.size() + "\r\n" +
                "Vozila: " +  this.listaVozila.toString();
    }
}
