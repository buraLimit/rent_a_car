package korisnici;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Korisnik  implements Obracun {
    private String ime;
    private String prezime;
    private String telefon;
    private int brDanaRentiranja;


    public int getBrDanaRentiranja() {
        return brDanaRentiranja;
    }

    public void setBrDanaRentiranja(int brDanaRentiranja) {
        this.brDanaRentiranja = brDanaRentiranja;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Korisnik(){
    }

    public Korisnik(String ime, String prezime, String telefon) {
        this.ime = ime;
        this.prezime = prezime;
        this.telefon = telefon;
    }

    //NA OSNOVU DATUMA PREUZIMANJA I DATUMA VRACANJA VOZILA KOJE KORISNIK UNOSI, FUNKCIJA RACUNA BROJ DANA RENTIRANJA

    public void brDanaRentiranja(String datum_rentiranja, String datum_vracanja){
        LocalDate d1 = LocalDate.parse(datum_rentiranja);
        LocalDate d2 = LocalDate.parse(datum_vracanja);
        setBrDanaRentiranja((int ) d1.until(d2, ChronoUnit.DAYS));
    }


    //STAMPA RACUN U TXT FAJL

    public void racun(String path) {
        String racun = "-------Vaš racun-------" + "\r\n"   +
                "Vasi podaci: " + "\r\n" + this.toString() + "\r\n"+
                "Ukupno za placanje: " + this.obracunavanjeCene() + "\r\n" ;
        BufferedWriter bw = null;
        try {

            bw = new BufferedWriter(new FileWriter(path));
            bw.write(racun);
            bw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(racun);
    }



}
