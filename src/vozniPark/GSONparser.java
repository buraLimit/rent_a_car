package vozniPark;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GSONparser {

    //CITA SVA VOZILA IZ JSON DOKUMENTA

    public static ArrayList citaj(String path) {

        Gson gson = new Gson();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(path));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

            Type type = new TypeToken<ArrayList<Vozilo>>() {
            }.getType();
        
            ArrayList<Vozilo> listaV = gson.fromJson(br, type);

            return listaV;
    }


    //KADA SE VOZILO RENTIRA POZIVA SE OVA FUNKCIJA KOJA TO VOZILO I DATUM KADA JE RENTIRANO (ZAUZETO)
    //UPISUJE U JSON DOKUMENT RENTIRANIH VOZILA

    public static void upisiRentirano(RentiranoVozilo r){

        ArrayList<RentiranoVozilo> listaR = new ArrayList<>();
        try {
            listaR = citajRentirano("rentirano.json");
        }catch (NullPointerException ex){};
        listaR.add(r);

        Gson gson = new Gson ();
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter("rentirano.json"));
            bw.write(gson.toJson(listaR));

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //CITA SPISAK RENTIRANIH VOZILA I DATUME KADA SU RENTIRANI

    public static ArrayList<RentiranoVozilo> citajRentirano(String path) {

        Gson gson = new Gson();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(path));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Type type = new TypeToken<ArrayList<RentiranoVozilo>>() {
        }.getType();

        ArrayList<RentiranoVozilo> listaR = gson.fromJson(br, type);

        return listaR;
    }


    //Brise rentirana vozila iz JSONa nakon vracanja vozila

    public static void obrisiRentirano( String broj){

        ArrayList<RentiranoVozilo> listaR = new ArrayList<>();
        try {
            listaR = citajRentirano("rentirano.json");
        }catch (NullPointerException ex){};


        if(listaR.removeIf(e->e.getId_rentiranja().equals(broj))){
            System.out.println("Uspesno ste vratili vozilo.");
        }
        else
            System.out.println("Jedinstveni broj ne odgovara nijednom rentiranom vozilu.");

        Gson gson = new Gson ();
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter("rentirano.json"));
            bw.write(gson.toJson(listaR));

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }

