package vozniPark;

import rentacar.Filtriranje;

public  class Vozilo {

    private String id;
    private int godiste;
    private String model;
    private String marka;
    private double cenaDan;
    private String gorivo;


    public Vozilo(String id, int godiste, String model, String marka, double cenaDan, String gorivo) {
        this.id = id;
        this.godiste = godiste;
        this.model = model;
        this.marka = marka;
        this.cenaDan = cenaDan;
        this.gorivo = gorivo;
    }

    public Vozilo(String id) {
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGodiste() {
        return godiste;
    }

    public void setGodiste(int godiste) {
        this.godiste = godiste;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public double getCenaDan() {
        return cenaDan;
    }

    public void setCenaDan(double cenaDan) {
        this.cenaDan = cenaDan;
    }

    public String getGorivo() {
        return gorivo;
    }

    public void setGorivo(String gorivo) {
        this.gorivo = gorivo;
    }


    @Override
    public String toString() {
        return "ID vozila: " + id + ", " +
                "Marka: " + marka + ", " +
                "Model: " + model + ", " +
                "Godiste: " + godiste + ", " +
                "Gorivo: " + gorivo + ", " +
                "Cena dana: " + cenaDan + "\r\n";
    }

    //TRAZI VOZILO IZ LISTE VOZILA PO ID-U
    public static Vozilo nadjiVoziloPoID(String id){
        for (Vozilo v: Filtriranje.getLista()
        ) {
            if(v.getId().equals(id)){
                return v;
            }
        }
        return null;
    }
}
