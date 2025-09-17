package simu.model;

import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

public class OmaMoottori extends Moottori{
	
	private Saapumisprosessi saapumisprosessi1;
    private Saapumisprosessi saapumisprosessi2;
    private Saapumisprosessi saapumisprosessi3;

	private Palvelupiste[] palvelupisteet;

	public OmaMoottori(){

		palvelupisteet = new Palvelupiste[8];

        // Tilauksen teko:
		palvelupisteet[0]=new Palvelupiste(new Normal(7,6), tapahtumalista, TapahtumanTyyppi.FIN1); // Autokaista
        palvelupisteet[1]=new Palvelupiste(new Normal(7,6), tapahtumalista, TapahtumanTyyppi.FIN2); // Palvelutiski
		palvelupisteet[2]=new Palvelupiste(new Normal(6,7), tapahtumalista, TapahtumanTyyppi.FIN3); // Tilausautomaatti

        // Tilauksen valmistus:
		palvelupisteet[3]=new Palvelupiste(new Normal(15,3), tapahtumalista, TapahtumanTyyppi.FIN4); // Keittiö

        // Tilauksen vastaanottaminen:
        palvelupisteet[4]=new Palvelupiste(new Normal(3,1), tapahtumalista, TapahtumanTyyppi.DEP1); // Toimitus
        palvelupisteet[5]=new Palvelupiste(new Normal(2,1), tapahtumalista, TapahtumanTyyppi.DEP2); // Nouto

        // Tilauksen syöminen tai uudelleenpakkaus:
        palvelupisteet[6]=new Palvelupiste(new Normal(30,8), tapahtumalista, TapahtumanTyyppi.DEP3); // Syöminen
        palvelupisteet[7]=new Palvelupiste(new Normal(4,2), tapahtumalista, TapahtumanTyyppi.DEP4); // Uudelleenpakkaus

        // Saapumisprosessit:
		saapumisprosessi1 = new Saapumisprosessi(new Negexp(15,5), tapahtumalista, TapahtumanTyyppi.ARR1); // Autokaistalle saapuu asiakas
        saapumisprosessi2 = new Saapumisprosessi(new Negexp(11,5), tapahtumalista, TapahtumanTyyppi.ARR2); // Palvelutiskille saapuu asiakas
        saapumisprosessi3 = new Saapumisprosessi(new Negexp(9,5), tapahtumalista, TapahtumanTyyppi.ARR3); // Tilausautomaatille saapuu asiakas

	}


	@Override
	protected void alustukset() {
		saapumisprosessi1.generoiSeuraava(); saapumisprosessi2.generoiSeuraava(); saapumisprosessi3.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat (vielä puuttuu muutama)

		Asiakas a;
		switch ((TapahtumanTyyppi)t.getTyyppi()){

			case ARR1: palvelupisteet[0].lisaaJonoon(new Asiakas());
				       saapumisprosessi1.generoiSeuraava();
				break;
            case ARR2: palvelupisteet[1].lisaaJonoon(new Asiakas());
                saapumisprosessi2.generoiSeuraava();
                break;
            case ARR3: palvelupisteet[2].lisaaJonoon(new Asiakas());
                saapumisprosessi3.generoiSeuraava();
                break;

            case FIN1: a = (Asiakas)palvelupisteet[0].otaJonosta();
                palvelupisteet[3].lisaaJonoon(a);
                break;
            case FIN2: a = (Asiakas)palvelupisteet[1].otaJonosta();
                palvelupisteet[3].lisaaJonoon(a);
                break;
            case FIN3: a = (Asiakas)palvelupisteet[2].otaJonosta();
                palvelupisteet[3].lisaaJonoon(a);
                break;

            case FIN4: a = (Asiakas)palvelupisteet[3].otaJonosta();
				   	   palvelupisteet[5].lisaaJonoon(a);
				break;

			case DEP2: a = (Asiakas)palvelupisteet[5].otaJonosta();
				   	   palvelupisteet[6].lisaaJonoon(a);
				break;
			case DEP3:
				       a = (Asiakas)palvelupisteet[6].otaJonosta();
					   a.setPoistumisaika(Kello.getInstance().getAika());
			           a.raportti();
		}
	}

	@Override
	protected void yritaCTapahtumat(){
		for (Palvelupiste p: palvelupisteet){
			if (!p.onVarattu() && p.onJonossa()){
				p.aloitaPalvelu();
			}
		}
	}

	@Override
	protected void tulokset() {
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset ... puuttuvat vielä");
	}

	
}
