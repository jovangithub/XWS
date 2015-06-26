
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package banka;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import xml.banka.Banka;
import xml.globals.TFirma;
import xml.izvod.StavkaPreseka;
import xml.izvod.TIzvod;
import xml.izvod.TIzvod.Zaglavlje;
import xml.mt102.MT102;
import xml.mt102.Placanje;
import xml.mt103.MT103;
import xml.racunfirme.RacunFirme;
import centralnabanka.CentralnaBankaPort;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2015-06-21T00:00:45.895+02:00
 * Generated source version: 2.6.5
 * 
 */

@javax.jws.WebService(
		serviceName = "bankaService",
		portName = "bankaPort",
		targetNamespace = "http://banka",
		wsdlLocation = "WEB-INF/wsdl/banka.wsdl",
		endpointInterface = "banka.BankaPort")

public class BankaPortImpl implements BankaPort {

	private static final Banka banka = new Banka("012", "000000000000000000", "AAAAAAAA");
	private static CentralnaBankaPort centralnaBanka;
	private static HashMap<String, RacunFirme> racuni = new HashMap<String, RacunFirme>();
	private static HashMap<String, Banka> banke = new HashMap<String, Banka>();

	public static void main(String[] args) {
		init();
		clearingAndSettlement(banka);
	}

	private static void init() {
		try {
			URL wsdlLocation = new URL("http://localhost:8080/centralna_banka/services/CentralnaBanka?wsdl");
			QName serviceName = new QName("http://centralnabanka", "centralnaBankaService");
			QName portName = new QName("http://centralnabanka", "centralnaBankaPort");

			Service service = Service.create(wsdlLocation, serviceName);

			centralnaBanka = service.getPort(portName, CentralnaBankaPort.class); 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		racuni.put("012000000000000000", new RacunFirme("012000000000000000", new BigDecimal("300000")));
		racuni.put("012000000000000001", new RacunFirme("012000000000000001", new BigDecimal("0")));
		banke.put("345", new Banka("345", "000000000000000001", "BBBBBBBB"));
		banke.put("678", new Banka("012", "000000000000000002", "CCCCCCCC"));
		System.out.println("Bank init done.");
	}

	private static void clearingAndSettlement(Banka b) {
		try {
			GregorianCalendar c = new GregorianCalendar();
			XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			clearingAndSettlementConstruct(b, date);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
	}

	private static void clearingAndSettlementConstruct(Banka b, XMLGregorianCalendar date) {
		MT102 mt102 = new MT102();
		mt102.setUID("");/////
		mt102.setSWIFTDuznika(banka.getSwiftKod());
		mt102.setObrRacunBankeDuznika(banka.getObracunskiRacun());
		mt102.setSWIFTPoverioca(b.getSwiftKod());
		mt102.setObrRacunBankePoverioca(b.getObracunskiRacun());
		BigDecimal sum = new BigDecimal("0");
		for (Placanje p : b.getPlacanja()) {
			sum.add(p.getIznos());
			mt102.getPlacanja().add(p);
		}
		mt102.setUkupanIznos(sum);
		mt102.setSifraValute("RSD");
		mt102.setDatumValute(date);
		mt102.setDatum(date);
		if (centralnaBanka.mt102(mt102)) {
			for (Placanje p : mt102.getPlacanja()) {
				StavkaPreseka stavka = new StavkaPreseka();
				stavka.setDuznikNalogodavac(p.getDuznikNalogodavac().getNaziv());
				stavka.setSvrhaPlacanja(p.getSvrhaPlacanja());
				stavka.setPrimalacPoverilac(p.getPrimalacPoverilac().getNaziv());
				stavka.setDatumNaloga(p.getDatumNaloga());
				stavka.setDatumValute(p.getDatumValute());
				stavka.setRacunDuznika(p.getDuznikNalogodavac().getRacun());
				stavka.setModelZaduzenja(p.getDuznikNalogodavac().getModel());
				stavka.setPozivNaBrojZaduzenja(p.getDuznikNalogodavac().getPozivNaBroj());
				stavka.setRacunPoverioca(p.getPrimalacPoverilac().getRacun());
				stavka.setModelOdobrenja(p.getPrimalacPoverilac().getModel());
				stavka.setPozivNaBrojOdobrenja(p.getPrimalacPoverilac().getPozivNaBroj());
				stavka.setIznos(p.getIznos());
				stavka.setSmer("");/////
				racuni.get(stavka.getRacunDuznika()).getStavke().add(stavka);
			}
		}
	}

	private static final Logger LOG = Logger.getLogger(BankaPortImpl.class.getName());

	/* (non-Javadoc)
	 * @see banka.BankaPort#zahtevZaIzvod(xml.zahtevzaizvod.TZahtevZaIzvod  parameters )*
	 */
	public xml.izvod.TIzvod zahtevZaIzvod(xml.zahtevzaizvod.TZahtevZaIzvod parameters) { 
		LOG.info("Executing operation zahtevZaIzvod");
		System.out.println(parameters);
//		try {
//			xml.izvod.TIzvod _return = new xml.izvod.TIzvod();
//			return _return;
//		} catch (java.lang.Exception ex) {
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
		String brojRacuna = parameters.getBrojRacuna();
		RacunFirme racunFirme = racuni.get(brojRacuna);
		if (racunFirme == null) {
			return null;
		}
		ArrayList<StavkaPreseka> stavke = new ArrayList<StavkaPreseka>();
		for (StavkaPreseka sp : racunFirme.getStavke()) {
			if (sp.getDatumNaloga().equals(parameters.getDatum())) {
				stavke.add(sp);
			}
		}
		int rbPreseka = parameters.getRadniBrojPreseka().intValue();
		if (rbPreseka * 5 <= stavke.size()) {
			TIzvod izvod = new TIzvod();
			int curr = rbPreseka * 5;
			while (true) {
				try {
					StavkaPreseka sp = stavke.get(curr++);
					izvod.getStavkePreseka().add(sp);
				} catch (IndexOutOfBoundsException e) {
					break;
				}
			}
			Zaglavlje zaglavlje = new Zaglavlje();
			zaglavlje.setBrojRacuna(parameters.getBrojRacuna());
			zaglavlje.setDatumNaloga(parameters.getDatum());
			zaglavlje.setBrojPreseka(parameters.getRadniBrojPreseka());
			BigDecimal prethodnoStanje = new BigDecimal("0");
			Integer uKorist = 0;
			BigDecimal ukupnoKorist = new BigDecimal("0");
			Integer naTeret = 0;
			BigDecimal ukupnoTeret = new BigDecimal("0");
			for (Object o : izvod.getStavkePreseka()) {
				StavkaPreseka sp = (StavkaPreseka) o;
				if (sp.getRacunDuznika().equals(parameters.getBrojRacuna())) {
					naTeret++;
					ukupnoTeret = ukupnoTeret.add(sp.getIznos());
				} else {
					uKorist++;
					ukupnoKorist = ukupnoKorist.add(sp.getIznos());
				}
			}
			BigDecimal novoStanje = prethodnoStanje.add(ukupnoKorist).subtract(ukupnoTeret);
			zaglavlje.setPrethodnoStanje(prethodnoStanje);
			zaglavlje.setBrojPromenaUKorist(new BigInteger(uKorist.toString()));
			zaglavlje.setUkupnoUKorist(ukupnoKorist);
			zaglavlje.setBrojPromenaNaTeret(new BigInteger(naTeret.toString()));
			zaglavlje.setUkupnoNaTeret(ukupnoTeret);
			zaglavlje.setNovoStanje(novoStanje);
			izvod.setZaglavlje(zaglavlje);
			return izvod;
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see banka.BankaPort#mt103(xml.mt103.TMT103  parameters )*
	 */
	public boolean mt103(xml.mt103.MT103 parameters) { 
		LOG.info("Executing operation mt103");
		System.out.println(parameters);
		//		try {
		//			boolean _return = false;
		//			return _return;
		//		} catch (java.lang.Exception ex) {
		//			ex.printStackTrace();
		//			throw new RuntimeException(ex);
		//		}
		racuni.put("345000000000000000", new RacunFirme("345000000000000000", new BigDecimal("300000")));//////
		racuni.put("345000000000000001", new RacunFirme("345000000000000001", new BigDecimal("0")));/////
		String racunPrimaoca = parameters.getPrimalacPoverilac().getRacun();
		if (racuni.containsKey(racunPrimaoca)) {
			RacunFirme trenutno = racuni.get(racunPrimaoca);
			trenutno.setStanje(trenutno.getStanje().add(parameters.getIznos()));
			racuni.put(racunPrimaoca, trenutno);
		}
		if (racuni.containsKey(racunPrimaoca)) {
			BigDecimal trenutno = racuni.get(racunPrimaoca).getStanje();
			racuni.get(racunPrimaoca).setStanje(trenutno.add(parameters.getIznos()));
		}
		StavkaPreseka stavka = new StavkaPreseka();
		stavka.setDuznikNalogodavac(parameters.getDuznikNalogodavac().getNaziv());
		stavka.setSvrhaPlacanja(parameters.getSvrhaPlacanja());
		stavka.setPrimalacPoverilac(parameters.getPrimalacPoverilac().getNaziv());
		stavka.setDatumNaloga(parameters.getDatumNaloga());
		stavka.setDatumValute(parameters.getDatumValute());
		stavka.setRacunDuznika(parameters.getDuznikNalogodavac().getRacun());
		stavka.setModelZaduzenja(parameters.getDuznikNalogodavac().getModel());
		stavka.setPozivNaBrojZaduzenja(parameters.getDuznikNalogodavac().getPozivNaBroj());
		stavka.setRacunPoverioca(parameters.getPrimalacPoverilac().getRacun());
		stavka.setModelOdobrenja(parameters.getPrimalacPoverilac().getModel());
		stavka.setPozivNaBrojOdobrenja(parameters.getPrimalacPoverilac().getPozivNaBroj());
		stavka.setIznos(parameters.getIznos());
		stavka.setSmer("");/////
		racuni.get(racunPrimaoca).getStavke().add(stavka);
		return true;
	}

	/* (non-Javadoc)
	 * @see banka.BankaPort#mt900(xml.mt900.TMT900  parameters )*
	 */
	public boolean mt900(xml.mt900.TMT900 parameters) { 
		LOG.info("Executing operation mt900");
		System.out.println(parameters);
		try {
			boolean _return = false;
			return _return;
		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	/* (non-Javadoc)
	 * @see banka.BankaPort#nalogZaPlacanje(xml.nalogzaplacanje.TNalogZaPlacanje  parameters )*
	 */
	public boolean nalogZaPlacanje(xml.nalogzaplacanje.TNalogZaPlacanje parameters) { 
		LOG.info("Executing operation nalogZaPlacanje");
		System.out.println(parameters);
		//		try {
		//			boolean _return = false;
		//			return _return;
		//		} catch (java.lang.Exception ex) {
		//			ex.printStackTrace();
		//			throw new RuntimeException(ex);
		//		}

		init();

		BigDecimal iznos = parameters.getIznos();
		String nalogodavac = parameters.getRacunDuznika();
		if (racuni.get(nalogodavac).getStanje().compareTo(iznos) < 0) {
			return false;
		}
		if (racuni.containsKey(parameters.getRacunPoverioca())) {
			System.out.println("Loklani transfer.");
			String primalac = parameters.getRacunPoverioca();
			BigDecimal staroNalogodavac = racuni.get(nalogodavac).getStanje();
			BigDecimal staroPrimalac = racuni.get(primalac).getStanje();
			racuni.get(nalogodavac).setStanje(staroNalogodavac.subtract(iznos));
			racuni.get(primalac).setStanje(staroPrimalac.add(iznos));
			return true;
		}
		if (parameters.isHitno() || parameters.getIznos().compareTo(new BigDecimal("250000")) >= 0) {
			//RTGS model
			System.out.println("Globalni transfer; RTGS.");
			MT103 mt103 = new MT103();
			mt103.setUID("");
			mt103.setSWIFTDuznika(banka.getSwiftKod());
			mt103.setObrRacunBankeDuznika(banka.getObracunskiRacun());
			Banka b = banke.get(parameters.getRacunPoverioca().subSequence(0, 3));
			if (b == null) {
				return false;
			}
			mt103.setSWIFTPoverioca(b.getSwiftKod());
			mt103.setObrRacunBankePoverioca(b.getObracunskiRacun());
			TFirma duznikNalogodavac = new TFirma();
			duznikNalogodavac.setModel(parameters.getModelZaduzenja());
			duznikNalogodavac.setNaziv(parameters.getDuznikNalogodavac());
			duznikNalogodavac.setPozivNaBroj(parameters.getPozivNaBrojZaduzenja());
			duznikNalogodavac.setRacun(parameters.getRacunDuznika());
			mt103.setDuznikNalogodavac(duznikNalogodavac);
			TFirma primalacPoverilac = new TFirma();
			primalacPoverilac.setModel(parameters.getModelOdobrenja());
			primalacPoverilac.setNaziv(parameters.getPrimalacPoverilac());
			primalacPoverilac.setPozivNaBroj(parameters.getPozivNaBrojOdobrenja());
			primalacPoverilac.setRacun(parameters.getRacunPoverioca());
			mt103.setPrimalacPoverilac(primalacPoverilac);
			mt103.setSvrhaPlacanja(parameters.getSvrhaPlacanja());
			mt103.setDatumNaloga(parameters.getDatumNaloga());
			mt103.setDatumValute(parameters.getDatumValute());
			mt103.setIznos(parameters.getIznos());
			mt103.setSifraValute(parameters.getOznakaValute());
			if (centralnaBanka.mt103(mt103)) {
				nalogodavac = parameters.getRacunDuznika();
				BigDecimal staroNalogodavac = racuni.get(nalogodavac).getStanje();
				racuni.get(nalogodavac).setStanje(staroNalogodavac.subtract(parameters.getIznos()));
				StavkaPreseka stavka = new StavkaPreseka();
				stavka.setDuznikNalogodavac(mt103.getDuznikNalogodavac().getNaziv());
				stavka.setSvrhaPlacanja(mt103.getSvrhaPlacanja());
				stavka.setPrimalacPoverilac(mt103.getPrimalacPoverilac().getNaziv());
				stavka.setDatumNaloga(mt103.getDatumNaloga());
				stavka.setDatumValute(mt103.getDatumValute());
				stavka.setRacunDuznika(mt103.getDuznikNalogodavac().getRacun());
				stavka.setModelZaduzenja(mt103.getDuznikNalogodavac().getModel());
				stavka.setPozivNaBrojZaduzenja(mt103.getDuznikNalogodavac().getPozivNaBroj());
				stavka.setRacunPoverioca(mt103.getPrimalacPoverilac().getRacun());
				stavka.setModelOdobrenja(mt103.getPrimalacPoverilac().getModel());
				stavka.setPozivNaBrojOdobrenja(mt103.getPrimalacPoverilac().getPozivNaBroj());
				stavka.setIznos(mt103.getIznos());
				stavka.setSmer("");/////
				racuni.get(stavka.getRacunDuznika()).getStavke().add(stavka);
			}
		} else {
			System.out.println("Globalni transfer; ne-RTGS.");
			Placanje placanje = new Placanje();
			placanje.setIDNalogaZaPlacanje("");/////
			TFirma duznikNalogodavac = new TFirma();
			duznikNalogodavac.setModel(parameters.getModelZaduzenja());
			duznikNalogodavac.setNaziv(parameters.getDuznikNalogodavac());
			duznikNalogodavac.setPozivNaBroj(parameters.getPozivNaBrojZaduzenja());
			duznikNalogodavac.setRacun(parameters.getRacunDuznika());
			placanje.setDuznikNalogodavac(duznikNalogodavac);
			TFirma primalacPoverilac = new TFirma();
			primalacPoverilac.setModel(parameters.getModelOdobrenja());
			primalacPoverilac.setNaziv(parameters.getPrimalacPoverilac());
			primalacPoverilac.setPozivNaBroj(parameters.getPozivNaBrojOdobrenja());
			primalacPoverilac.setRacun(parameters.getRacunPoverioca());
			placanje.setPrimalacPoverilac(primalacPoverilac);
			placanje.setSvrhaPlacanja(parameters.getSvrhaPlacanja());
			placanje.setDatumNaloga(parameters.getDatumNaloga());
			placanje.setDatumValute(parameters.getDatumValute());
			placanje.setIznos(parameters.getIznos());
			placanje.setSifraValute(parameters.getOznakaValute());
			String primalac = placanje.getPrimalacPoverilac().getRacun();
			Banka bPrimalac = banke.get(primalac.subSequence(0, 3));
			if (bPrimalac == null) {
				return false;
			} else {
				nalogodavac = placanje.getDuznikNalogodavac().getRacun();
				BigDecimal stanje = racuni.get(nalogodavac).getStanje();
				racuni.get(nalogodavac).setStanje(stanje.subtract(placanje.getIznos()));
			}
			placanje.setSWIFTDuznika(banka.getSwiftKod());
			placanje.setSWIFTPoverioca(bPrimalac.getSwiftKod());
			bPrimalac.getPlacanja().add(placanje);
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see banka.BankaPort#mt910(xml.mt910.TMT910  parameters )*
	 */
	public boolean mt910(xml.mt910.TMT910 parameters) { 
		LOG.info("Executing operation mt910");
		System.out.println(parameters);
		try {
			boolean _return = false;
			return _return;
		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	/* (non-Javadoc)
	 * @see banka.BankaPort#mt102(xml.mt102.TMT102  parameters )*
	 */
	public boolean mt102(xml.mt102.MT102 parameters) { 
		LOG.info("Executing operation mt102");
		System.out.println(parameters);
//		try {
//			boolean _return = false;
//			return _return;
//		} catch (java.lang.Exception ex) {
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}		
		racuni.put("345000000000000000", new RacunFirme("345000000000000000", new BigDecimal("300000")));//////
		racuni.put("345000000000000001", new RacunFirme("345000000000000001", new BigDecimal("0")));/////
		for (Placanje p : parameters.getPlacanja()) {
			String racunPrimaoca = p.getPrimalacPoverilac().getRacun();
			if (racuni.containsKey(racunPrimaoca)) {
				BigDecimal trenutno = racuni.get(racunPrimaoca).getStanje();
				racuni.get(racunPrimaoca).setStanje(trenutno.add(p.getIznos()));
			}
			StavkaPreseka stavka = new StavkaPreseka();
			stavka.setDuznikNalogodavac(p.getDuznikNalogodavac().getNaziv());
			stavka.setSvrhaPlacanja(p.getSvrhaPlacanja());
			stavka.setPrimalacPoverilac(p.getPrimalacPoverilac().getNaziv());
			stavka.setDatumNaloga(p.getDatumNaloga());
			stavka.setDatumValute(p.getDatumValute());
			stavka.setRacunDuznika(p.getDuznikNalogodavac().getRacun());
			stavka.setModelZaduzenja(p.getDuznikNalogodavac().getModel());
			stavka.setPozivNaBrojZaduzenja(p.getDuznikNalogodavac().getPozivNaBroj());
			stavka.setRacunPoverioca(p.getPrimalacPoverilac().getRacun());
			stavka.setModelOdobrenja(p.getPrimalacPoverilac().getModel());
			stavka.setPozivNaBrojOdobrenja(p.getPrimalacPoverilac().getPozivNaBroj());
			stavka.setIznos(p.getIznos());
			stavka.setSmer("");/////
			racuni.get(racunPrimaoca).getStavke().add(stavka);
		}
		return true;
	}

}
