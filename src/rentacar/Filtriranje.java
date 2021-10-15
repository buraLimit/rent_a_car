package rentacar;

import vozniPark.GSONparser;
import vozniPark.RentiranoVozilo;
import vozniPark.Vozilo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Filtriranje {
    private static ArrayList<Vozilo> lista = new ArrayList<>();
    private static ArrayList<Vozilo> listaPoTipu;
    private static int pom; //pomocna promenljiva koja se koristi kada korisnik izabere da se vraca korak unazad
    private static int izbor;
    private static Scanner sc = new Scanner(System.in);
    private static boolean flag;

    public static ArrayList<Vozilo> getLista() {
        return lista;
    }

            //GLAVNI MENI
    public static void glavniMeni() {
        while (true) {
            try {

                System.out.println("Dobrodosli! Zelite li da vratite ili rentirate vozilo?" +
                        "\n" + "1 Rentiranje vozila" + "\n" + "2 Vracanje vozila" + "\n" + "3 Izlazak");
                izbor = sc.nextInt();
                if (izbor > 3 || izbor < 1) throw new PonudjeniBrojeviException();
                if (izbor == 1)
                    pocetak();
                if (izbor == 2)
                    RentiranoVozilo.povratakVozila();
                if (izbor == 3)
                    System.exit(0);
            } catch (InputMismatchException e) {
                System.out.println("Morate uneti broj!");
                sc.nextLine();
            } catch (PonudjeniBrojeviException e) {
            }
        }
    }


    // FUNKCIJA ZA POCETAK FILTRIRANJA

    public static void pocetak() {


        try {
            System.out.println("Izaberite opciju za filtriranje:" + '\n'
                    + "1 Automobili" + '\n' + "2 Cetvorotockasi" + '\n' + "3 Motori" + '\n' + "4 Nazad");
            izbor = sc.nextInt();
            if (izbor > 4 || izbor < 1) throw new PonudjeniBrojeviException();
            if (izbor == 4) {
                glavniMeni();
            } else
                Filtriranje.filtriranjePoTipu(izbor);
        } catch (InputMismatchException e) {
            System.out.println("Morate uneti broj!");
            sc.nextLine();
        } catch (PonudjeniBrojeviException e) {
        }

    }

    //FUNKCIJA KOJA FILTRIRA VOZILA U ZAVISNOSTI OD IZABRANOG TIPA (AUTOMOBIL, CETVOROTOCKAS, MOTOR)

    public static void filtriranjePoTipu(int izbor) {
        pom = izbor;
        lista = GSONparser.citaj("vozila.json"); //LISTA SVIH VOZILA
        listaPoTipu = new ArrayList<>(); //LISTA U KOJU SE SMESTAJU VOZILA IZABRANOG TIPA
        if (izbor == 1) {
            for (Vozilo v :
                    lista) {
                if (v.getId().contains("a")) {
                    listaPoTipu.add(v);
                    System.out.println(v);

                }
            }
        }
        if (izbor == 2) {
            for (Vozilo v :
                    lista) {
                if (v.getId().contains("c")) {
                    listaPoTipu.add(v);
                    System.out.println(v);
                }
            }
        }
        if (izbor == 3) {
            for (Vozilo v :
                    lista) {
                if (v.getId().contains("m")) {
                    listaPoTipu.add(v);
                    System.out.println(v);
                }
            }
        }


        filtriranjeOstalo();
    }

    //IZABRANI TIP FILTRIRA PO DRUGIM PARAMETRIMA, U ZAVISNOSTI OD PARAMETRA POZIVA ODGOVARAJUCU FUNKCIJU

    public static void filtriranjeOstalo() {
        while (true) {
            try {
                System.out.println("Izaberite opciju za filtriranje po drugim parametrima: " + '\n' + "1 Marka" + '\n' + "2 Godiste"
                        + '\n' + "3 Cena po danu" + '\n' + "4 Gorivo" + '\n' + "5 Rentiraj bez filtriranja" + '\n' + "0 Nazad");
                izbor = sc.nextInt();
                if (izbor > 5 || izbor < 0) throw new PonudjeniBrojeviException();
                if (izbor == 1) {

                    filtriranjeMarka();
                }
                if (izbor == 2) {
                    filtriranjeGodiste();
                }
                if (izbor == 3) {
                    filtriranjeCena();
                }
                if (izbor == 4) {
                    filtriranjeGorivo();
                }
                if (izbor == 5) {
                    Rentiranje.rentiraj();
                }
                if (izbor == 0) {
                    pocetak();
                }

            } catch (PonudjeniBrojeviException e) {
            } catch (InputMismatchException ex) {
                System.out.println("Morate uneti broj!");
                sc.nextLine();
            }
        }

    }

    //FILTRIRA ZELJENI TIP PO MARKI
    public static void filtriranjeMarka() {
        flag = false;
        System.out.println("Unesite zeljenu marku:");
        String marka = sc.next();

        for (Vozilo a : listaPoTipu
        ) {
            if (a.getMarka().equalsIgnoreCase(marka)) {
                System.out.println(a);
                flag = true;
            }
        }
        if (!flag) {
            System.out.println("Zao nam je, u ponudi nemamo vozila marke koju ste uneli.");
        }
        Filtriranje.krajFiltriranja();

    }

    //FILTRIRA ZELJENI TIP PO GODISTU OD-DO
    public static void filtriranjeGodiste() {
        flag = false;
        while (true) {
            try {

                System.out.println("Unesite pocetno i krajnje godiste sa razmakom:");
                int g1 = sc.nextInt();
                int g2 = sc.nextInt();
                if (g2 < g1) throw new Exception();
                for (Vozilo a : listaPoTipu
                ) {
                    if (a.getGodiste() > g1 && a.getGodiste() < g2) {
                        System.out.println(a);
                        flag = true;
                    }
                }
                if (!flag) System.out.println("Zao nam je, u ponudi nemamo vozila u datom opsegu godista.");
                Filtriranje.krajFiltriranja();
            } catch (InputMismatchException ex) {
                System.out.println("Morate uneti brojeve");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Krajnje godiste mora biti vece od pocetnog.");
            }

        }
    }

    //FILTRIRA ZELJENI TIP PO CENI OD-DO

    public static void filtriranjeCena() {
        flag = false;
        while (true) {
            try {

                System.out.println("Unesite pocetnu i krajnju cenu sa razmakom:");
                int c1 = sc.nextInt();
                int c2 = sc.nextInt();
                if (c2 < c1) throw new Exception();
                for (Vozilo a : listaPoTipu
                ) {
                    if (a.getCenaDan() >= c1 && a.getCenaDan() <= c2) {
                        System.out.println(a);
                        flag = true;
                    }
                }
                if (!flag) System.out.println("Zao nam je, u ponudi nemamo vozila u datom opsegu cena.");
                Filtriranje.krajFiltriranja();
            } catch (InputMismatchException ex) {
                System.out.println("Morate uneti brojeve");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Krajnja cena mora biti veca od pocetne.");
            }
        }
    }

    //FILTRIRA ZELJENI TIP PO VRSTI GORIVA

    public static void filtriranjeGorivo() {
        flag = false;
        System.out.println("Unesite zeljeni tip goriva (benzin, dizel, hibrid):");
        String gorivo = sc.next();

        for (Vozilo a : listaPoTipu
        ) {
            if (a.getGorivo().equalsIgnoreCase(gorivo)) {
                System.out.println(a);
                flag = true;
            }
        }
        if (!flag) {
            System.out.println("Zao nam je, u ponudi nemamo vozilo sa unetim tipom goriva.");
        }
        Filtriranje.krajFiltriranja();


    }

    //FILTRIRANJE SE ZAVRSAVA I PRELAZI SE NA RENTIRANJE,
    //ILI AKO KORISNIK ODLUCI DA SE VRATI NAZAD MOZE OPET FILTRIRATI IZABRANI TIP VOZILA

    public static void krajFiltriranja() {
        flag = false;
        while (true) {
            try {
                System.out.println("1 Rentiraj 0 Nazad");

                int izbor = sc.nextInt();
                if (izbor < 0 || izbor > 1) throw new PonudjeniBrojeviException();
                if (izbor == 1) {
                    Rentiranje.rentiraj();
                }
                if (izbor == 0) {
                    Filtriranje.filtriranjePoTipu(pom);
                }

            } catch (InputMismatchException e) {
                System.out.println("Morate uneti broj!");
                sc.nextLine();
            } catch (PonudjeniBrojeviException e) {

            }
        }
    }

}




