package rentacar;

import korisnici.FizickoLice;
import korisnici.PravnoLice;
import vozniPark.GSONparser;
import vozniPark.RentiranoVozilo;
import vozniPark.Vozilo;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Rentiranje {
    private static String date1;
    private static String date2;

    private static Scanner sc = new Scanner(System.in);
    private static Pattern datePattern = Pattern.compile(
            "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");

    //korisnik unosi svoje podatke
    public static void rentiraj() {
        int izbor = 0;
        boolean flag;

        //Korisnik odabira da li je pravno ili fizicko lice
        do {
            flag = false;
            try {
                System.out.println("Rentirate kao:" + '\n' + "1 Pravno lice" + '\n' + "2 Fizicko lice");
                izbor = sc.nextInt();
                if (izbor != 1 && izbor != 2) {
                    flag = true;
                    throw new PonudjeniBrojeviException();
                }
            } catch (PonudjeniBrojeviException e) {
            } catch (InputMismatchException ex) {
                flag = true;
                System.out.println("Morate uneti broj!");
                sc.nextLine();
            }
            ;
        } while (flag);


        String pomSlova;
        int pomBrojevi = 0;

        //PRAVNO LICE

        if (izbor == 1) {
            PravnoLice p1 = new PravnoLice();

            int brojV = 0;
            do {
                flag = false;
                try {
                    System.out.println("Koliko vozila zelite da rentirate? ");
                    brojV = sc.nextInt();
                } catch (InputMismatchException ex) {
                    System.out.println("Morate uneti cifru!");
                    flag = true;
                    sc.nextLine();
                }
            } while (flag);

            String id;
            unosDatuma();

            p1.brDanaRentiranja(date1, date2);  //izracunava koliko je dana korisnik rentirao vozila,
                                                // sto ce se dalje koristiti za formiranje racuna

            ArrayList<Vozilo> listaV= new ArrayList<>();
            for (int i = 0; i < brojV; i++) {               //korisnik odabira vozila koja zeli da rentira
                Vozilo v;
                do {
                    flag = false;
                    System.out.println("Unesite id vozila broj " + (int) (i + 1) + ":");
                    id = sc.next();
                    if (Vozilo.nadjiVoziloPoID(id) == null) {
                        System.out.println("Ne postoji vozilo sa ovim id.");
                        flag = true;
                    }
                } while (!proveraZauzetosti(id, parsirajDatum(date1), parsirajDatum(date2)) || flag);

                v = Vozilo.nadjiVoziloPoID(id);
                listaV.add(v);
            }
            //Pita za nastavak

            System.out.println("Zelite li da nastavite? " + '\n' + "1 Da, predji na unos podataka" + "\n" + "2 Ne, vrati me na filtriranje");
            int nastavak = sc.nextInt();

            if(nastavak == 2){
                Filtriranje.filtriranjeOstalo();
            }

            //Pravno lice unosi svoje podatke

            if (nastavak == 1) {
                do {
                    System.out.println("Ime:");
                    pomSlova = sc.next();
                    sc.nextLine();
                } while (!proveraSlova(pomSlova));
                p1.setIme(pomSlova);

                do {
                    System.out.println("Prezime:");
                    pomSlova = sc.next();
                    sc.nextLine();
                } while (!proveraSlova(pomSlova));
                p1.setPrezime(pomSlova);

                do {
                    System.out.println("Ime firme:");
                    pomSlova = sc.next();
                    sc.nextLine();
                } while (!proveraSlova(pomSlova));
                p1.setIme_firme(pomSlova);

                do {
                    flag = false;
                    System.out.println("PIB broj:");
                    pomSlova = sc.next();
                    if (pomSlova.length() != 9) {
                        System.out.println("PIB mora sadrzati 9 cifara!");
                        flag = true;
                    }
                    if (!proveraBrojeva(pomSlova)) {
                        flag = true;
                    }
                } while (flag);
                p1.setPib(pomSlova);

                do {
                    flag = false;
                    System.out.println("Telefon:");
                    pomSlova = sc.next();
                    if (pomSlova.length() != 10) {
                        System.out.println("Telefon mora sadrzati 10 cifara!");
                        flag = true;
                    }
                    if (!proveraBrojeva(pomSlova)) {
                        flag = true;
                    }
                } while (flag);
                p1.setTelefon(pomSlova);
                String id_rentiranja = p1.getPib() + String.valueOf(new Random().nextInt(11)); //kreira se id rentiranja

                //rentirana vozila se smestaja u .JSON fajl
                for(Vozilo v : listaV){
                    GSONparser.upisiRentirano(new RentiranoVozilo(v, date1, date2, id_rentiranja, p1));
                }

                p1.dodajListu(listaV);
                p1.racun("racuni/" + p1.getIme_firme() + " - racun.txt"); //formira se racun za korisnika u folderu racuni
                System.out.println("Uspesno ste rentirali vozilo. Ovo je vas jedinstveni broj koji cete koristiti prilikom vracanja vozila: " + id_rentiranja + "\n");
            }



        }

                // FIZICKO LICE

        if (izbor == 2) {

           unosDatuma();

            String id;
            do {
                flag = false;
                System.out.println("Unesite id vozila:");  //Bira vozilo koji zeli da rentira
                id = sc.next();
                if (Vozilo.nadjiVoziloPoID(id) == null) {
                    System.out.println("Ne postoji vozilo sa ovim id.");
                    flag = true;
                }
            } while (!proveraZauzetosti(id, parsirajDatum(date1), parsirajDatum(date2)) || flag);

            Vozilo v = Vozilo.nadjiVoziloPoID(id);

            //Pita za nastavak

            System.out.println("Zelite li da nastavite? " + '\n' + "1 Da, predji na unos podataka" + "\n" + "2 Ne, vrati me na filtriranje");
            int nastavak = sc.nextInt();
            if (nastavak == 2) {
                Filtriranje.filtriranjeOstalo();
            }

            //Fizicko lice unosi svoje podatke

            if (nastavak == 1) {
                FizickoLice f1 = new FizickoLice();
                do {
                    System.out.println("Ime:");
                    pomSlova = sc.next();
                    sc.nextLine();
                } while (!proveraSlova(pomSlova));
                f1.setIme(pomSlova);

                do {
                    System.out.println("Prezime:");
                    pomSlova = sc.next();
                    sc.nextLine();
                } while (!proveraSlova(pomSlova));
                f1.setPrezime(pomSlova);

                do {
                    flag = false;
                    System.out.println("Broj licne karte:");
                    pomSlova = sc.next();
                    if (pomSlova.length() != 9) {
                        System.out.println("Broj licne karte mora sadrzati 9 cifara!");
                        flag = true;
                    }
                    if (!proveraBrojeva(pomSlova)) {
                        flag = true;
                    }
                } while (flag);
                f1.setBrLK(pomSlova);

                do {
                    flag = false;
                    System.out.println("Telefon:");
                    pomSlova = sc.next();
                    if (pomSlova.length() != 10) {
                        System.out.println("Telefon mora sadrzati 10 cifara!");
                        flag = true;
                    }
                    if (!proveraBrojeva(pomSlova)) {
                        flag = true;
                    }
                } while (flag);
                f1.setTelefon(pomSlova);

                do {
                    try {
                        flag = false;
                        System.out.println("Godina rodjenja:");
                        pomBrojevi = sc.nextInt();

                        if (pomBrojevi > Year.now().getValue() - 21 || pomBrojevi < Year.now().getValue() - 80) {
                            System.out.println("Zao nam je, morate biti stariji od 21 godine i mladji od 80 godina kako bi mogli da rentirate vozilo!");
                            Filtriranje.glavniMeni();
                            return;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Morate uneti godinu rodjenja.");
                        flag = true;
                        sc.nextLine();
                    }
                } while (flag);
                f1.setGodina_rodjenja(pomBrojevi);

                do {
                    try {
                        flag = false;
                        System.out.println("Godina izdavanja vozacke dozvole:");
                        pomBrojevi = sc.nextInt();
                        if (pomBrojevi > Year.now().getValue() || pomBrojevi < Year.now().getValue() - 63 || pomBrojevi < (f1.getGodina_rodjenja() + (int) 17)) {
                            System.out.println("Uneli ste nevazeci datum izdavanja!");
                            flag = true;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Morate uneti godinu izdavanja vozacke dozvole!");
                        flag = true;
                        sc.nextLine();
                    }
                } while (flag);

                if (!f1.proveraDozvole(pomBrojevi)) {
                    System.out.println("Zao nam je, morate imati vozacku dozvolu najmanje 4 godine kako biste rentirali vozilo.");
                    Filtriranje.glavniMeni();
                    return;
                }
                f1.setGodina_izdavanja_dozvole(pomBrojevi);

                f1.brDanaRentiranja(date1, date2);
                String id_rentiranja = f1.getBrLK() + String.valueOf(new Random().nextInt(11)); //kreira se id rentiranja
                GSONparser.upisiRentirano(new RentiranoVozilo(v, date1, date2, id_rentiranja, f1)); // Upisuje rentirano vozilo u .JSON fajl
                f1.setVozilo(v);
                f1.racun("racuni/" + f1.getIme() + " " + f1.getPrezime() + " - racun.txt"); // pravi i smesta racun u folder racuni

                System.out.println("Uspesno ste rentirali vozilo. Ovo je vas jedinstveni broj koji cete koristiti prilikom vracanja vozila: " + id_rentiranja + "\n");

            }
        }
        Filtriranje.glavniMeni();

    }

    //prima datume od korisnika
    public static void unosDatuma(){
        do {
            do {
                System.out.println("Datum preuzimanja (u formatu yyyy-mm-dd):");
                date1 = sc.next();
            } while (!datePattern.matcher(date1).matches());
            do {
                System.out.println("Datum vracanja (u formatu yyyy-mm-dd):");
                date2 = sc.next();
            } while (!datePattern.matcher(date2).matches());

        } while (!proveraIspravnostiDatuma(parsirajDatum(date1), parsirajDatum(date2)));
    }

    //provera da li je izabrano vozilo zauzeto u zeljenom terminu
    public static boolean proveraZauzetosti(String id, LocalDate date1, LocalDate date2) {
        ArrayList<RentiranoVozilo> listaR = GSONparser.citajRentirano("rentirano.json");
        if (listaR == null || listaR.isEmpty()) {
            return true;
        }
        for (RentiranoVozilo r : listaR) {
            if (r.getV().getId().equals(id)) {
                if ((date1.isAfter((parsirajDatum(r.getDatum_rentiranja()))) && date1.isBefore(parsirajDatum(r.getDatum_vracanja()))) ||
                        (date2.isAfter(parsirajDatum(r.getDatum_rentiranja())) && date2.isBefore(parsirajDatum(r.getDatum_vracanja()))) ||
                        date1.isEqual(parsirajDatum(r.getDatum_rentiranja())) || date2.isEqual(parsirajDatum(r.getDatum_vracanja()))) {
                    System.out.println("Vozilo je zauzeto u ovom terminu, unesite drugo vozilo.");
                    return false;
                }
            }
        }
        return true;
    }

    //provera da li su uneseni datumi napisani u odgovarajucem redosledu i da li je datum nakon trenutnog datuma
    public static boolean proveraIspravnostiDatuma(LocalDate date1, LocalDate date2) {
        if (date1.isBefore(java.time.LocalDate.now()) || date2.isBefore(java.time.LocalDate.now()) || date2.isBefore(date1) || date1.equals(date2)) {
            System.out.println("Neispravani datumi");
            return false;
        }
        return true;
    }

    //parsira String koji je korisnik uneo u LocalDate
    public static LocalDate parsirajDatum(String datum) {
        LocalDate localDate = LocalDate.parse(datum);
        return localDate;
    }

    //proverava da li uneti String sadrzi iskljucivo slova i da li je prazan
    public static boolean proveraSlova(String s) {
        if (s == null) {
            System.out.println("Morate uneti podatak!");
            return false;
        }
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if ((!Character.isLetter(s.charAt(i))) && s.charAt(i)!=' ') {
                System.out.println("Morate uneti slova!");
                return false;
            }
        }
        return true;
    }

    //proverava da li uneti String sadrzi samo cifre (za telefon, licnu kartu i PIB)
    public static boolean proveraBrojeva(String s) {
        if (s == null) {
            System.out.println("Morate uneti podatak!");
            return false;
        }
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if ((!Character.isDigit(s.charAt(i)))) {
                System.out.println("Morate uneti cifre!");
                return false;
            }
        }
        return true;
    }


}
