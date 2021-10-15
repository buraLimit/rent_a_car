package rentacar;

public class PonudjeniBrojeviException extends Exception{
    //EXCEPTION KOJI SE BACA KADA KORISNIK UNESI NEKI OD BROJEVA KOJI NIJE PONUDJEN U MENIJU

    public PonudjeniBrojeviException(){
        System.out.println("Unesite jedan od ponudjenih brojeva!");
    }
}
