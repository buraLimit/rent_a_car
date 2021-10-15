package vozniPark;

import korisnici.FizickoLice;;
import korisnici.PravnoLice;
import rentacar.Filtriranje;
import java.util.Scanner;

public class RentiranoVozilo{
    private Vozilo v ;
    private String datum_rentiranja;
    private String datum_vracanja;
    private String id_rentiranja;
    private FizickoLice f;
    private PravnoLice p;


    public RentiranoVozilo(Vozilo v, String datum_rentiranja, String datum_vracanja,String id_rentiranja, FizickoLice f) {
        this.v=v;
        this.datum_rentiranja = datum_rentiranja;
        this.datum_vracanja = datum_vracanja;
        this.id_rentiranja=id_rentiranja;
        this.f=f;
    }

    public RentiranoVozilo(Vozilo v, String datum_rentiranja, String datum_vracanja,String id_rentiranja, PravnoLice p) {
        this.v=v;
        this.datum_rentiranja = datum_rentiranja;
        this.datum_vracanja = datum_vracanja;
        this.id_rentiranja=id_rentiranja;
        this.p=p;
    }

    public String getDatum_rentiranja() {
        return datum_rentiranja;
    }

    public void setDatum_rentiranja(String datum_rentiranja) {
        this.datum_rentiranja = datum_rentiranja;
    }

    public String getDatum_vracanja() {
        return datum_vracanja;
    }

    public void setDatum_vracanja(String datum_vracanja) {
        this.datum_vracanja = datum_vracanja;
    }

    public Vozilo getV() {
        return v;
    }

    public void setV(Vozilo v) {
        this.v = v;
    }

    public String getId_rentiranja() {
        return id_rentiranja;
    }

    public void setId_rentiranja(String id_rentiranja) {
        this.id_rentiranja = id_rentiranja;
    }

        //POZIVA SE KADA KORISNIK ZELI DA VRATI RENTIRANO VOZILO/LA

    public static void povratakVozila(){
        Scanner sc = new Scanner(System.in);
        String id;

        System.out.println("Unesite vas jedinstveni broj koji ste dobili prilikom rentiranja: ");
        id=sc.next();

       GSONparser.obrisiRentirano(id);

       Filtriranje.glavniMeni();
   }


}
