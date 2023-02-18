import java.util.Date;
import java.util.HashSet;
import java.util.Set;
public class Post implements Cloneable{
	/*Overview:Il tipo Post è un tipo di dato modificabile rappresentato da una quintupla di attributi
	 *Abstract Function:
	 * 						F(p)=(p.identificatore,p.utente,p.testo,p.data,p.like(set di stringhe))
	 *Typical Element:
	 *						(identificatore,utente,testo,data,[likeby1,...,likebyN]
	 *
	 *Representation Invariant:		
	 *						Per tutti le stringhe in like ->(string!=null && string.isEmpty()!=true)
	 *						&& data!=null && testo!=null && testo.isEmpty()!=true && utente != null && utente.isEmpty()!=true
	 * 								 
	 */
	static private int ide=0;
	 private int id;
	 private String author;
	 private String text;
	 private Date timestamp;
	 private Set<String> likes;
	/*Overview:Il tipo Post è un tipo di dato modificabile rappresentato da una quintupla di attributi
	 *Abstract Function:
	 * 						F(p)=(p.identificatore,p.utente,p.testo,p.data,p.like(set di stringhe))
	 *Typical Element:
	 *						(identificatore,utente,testo,data,[likeby1,...,likebyN]
	 *
	 *Representation Invariant:		
	 *						Per tutti le stringhe in like ->(string!=null && string.isEmpty()!=true && this.author!=string)
	 *						&& data!=null && testo!=null && testo.isEmpty()!=true && utente != null && utente.isEmpty()!=true				 
	 *Requires: utente!=null, testo.lenght<140 , testo.isEmpty()!=true, utente.isEmpty()!=true,identificatore>=0							
	  *Throws:NullPointerException,NewException
	  *Effects: restituisce un nuovo post
	  */ 
	//costruttore usato per creare post all'interno della rete sociale
	Post(int identificatore,String utente, String testo)throws NullPointerException,NewException{
		if(utente==null) {
			throw new NullPointerException();
		}
		if(testo==null) {
			throw new NullPointerException();
		}
		if(utente.isEmpty()) {
			throw new NewException("Il campo utente non può essere vuoto");
		}
		if(testo.length()>140) {
			throw new NewException("Il campo testo contiene può contenere piu'  di 140 caratteri");
		}
		if(testo.isEmpty()) {
			throw new NewException("Il campo testo non può essere vuoto");
		}
		this.author=utente;
		this.text=testo;
		this.id=identificatore;
		this.likes=new HashSet<String>();
		this.timestamp=new Date(System.currentTimeMillis());
	}
	Post(String utente,String testo)throws NullPointerException, NewException{
		if(utente==null) {
			throw new NullPointerException();
		}
		if(testo==null) {
			throw new NullPointerException();
		}
		if(utente.isEmpty()) {
			throw new NewException("Il campo utente non può essere vuoto");
		}
		if(testo.length()>140) {
			throw new NewException("Il campo testo contiene può contenere piu'  di 140 caratteri");
		}
		if(testo.isEmpty()) {
			throw new NewException("Il campo testo non può essere vuoto");
		}
		this.id=ide++;
		this.author=utente;
		this.text=testo;
		this.likes=new HashSet<String>();
		this.timestamp=new Date(System.currentTimeMillis());
	}
	//effects:restituisce il  set like del post
	public Set<String> getLike(){	
		return this.likes;
	}
	//effects:restituisce la stringa utente del post
	public String getUtente() {
		return this.author;
	}
	//effects: restituisce il valore dell'identificatore del post
	public int getIdentificatore() {	
		return this.id;
	}
	//effects: restituisce la stringa del testo del post
	public String getTesto() {
			return this.text;
	}
	public Date getData() {
		return this.timestamp;
	}
	// effects: stampa il nome dell'utente che ha creato il post e il testo  del post
	public String toString() {
		return "Post creato da: "+this.author+"\nche ha scritto: "+ this.text+"\n";
	}
	/*Requires: utente!=null, utente.isempty()!=true
	 *Throws: NullPointerException,NewException
	 *Effects: aggiunge un like al post se utente!=null e utente.isEmpty!=true
	 */
	public void setLike(String utente)throws NullPointerException,NewException{
			if(utente==null) {
				throw new NullPointerException();
			}
			if(utente.isEmpty()) {
				throw new NewException("Il campo utente non può essere vuoto");
			}
			if(this.author==utente) {
				throw new NewException("Impossibile mettere like a se stessi");
			}
			this.likes.add(utente);
	}
	/*Requires: utente!=null, utente.isempty()!=true
	 *Throws: NullPointerException,NewException
	 *Effects: rimuove un like al post se utente!=null e utente.isEmpty!=true
	 */
	public void removeLike(String utente)throws NullPointerException,NewException {
			if(utente==null) {
				throw new NullPointerException();
			}
			if(utente.isEmpty()) {
				throw new NewException("Il campo utente non può essere vuoto");
			}
			if(!this.likes.contains(utente)) {
				throw new NewException("Impossibile togliere like ad un post in cui non era presente il like in precedenza");
			}
			this.likes.remove(utente);
	}
	/*
	 * Requires: testo!=null, testo.isempty()!=true
	 * Throws: NullPointerException,NewException
	 * Effects: Setta this.testo con la stringa testo 
	 */
	public void SetTesto(String testo)throws NewException{
		if(testo==null) {
			throw new NullPointerException();
		}
		if(testo.isEmpty()) {
			throw new NewException("Il testo non può essere un campo vuoto");
		}
		this.text=testo;
	}

}
