import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class MicroBlog implements SocialNetwork1 {
	final private Map<String, Set<String>> Follows;//Map[a]=Set di utenti seguiti da a
	final private Map<String, Set<Post>> PostCaricati;//Map[a]=Set di post caricati da a
	final private Map<String, Set<String>> Followers;//Map[a]=Set di utenti che seguono a
	final private Set<String> Utenti;//Set di utenti iscritti al rete sociale
	final private Set<Post> Posts;//Set di post nelle rete sociale;
	/*OVERVIEW:MicroBlog è un tipo di dato modificabile che permette la registrazione di utente, la creazione di post
	 * la possibilita di seguire altri utenti e mettere like ai post di altri utenti.
	 *ABSTRACT FUNCTION:f(utente_A)->{{Follows.get(utente_A)},{Followers.get(utente_A)},{PostCaricati.get(utente_A)}}
	 * il dominio della funzione è l'insieme degli utenti iscritti alla rete sociale e il codominio
	 * della funzione è una tripla di insiemi:utenti seguiti da utente_A,utenti che seguono utente_A e post scritti da utente_A
	 * TYPICAL ELEMENT:Un elemento tipico del tipo di dato MicroBlog è l'associazione di ogni utente_A
	 * che è registrato nella rete socialo con: gli utenti che A  segue, gli utenti che seguono A e i post scritti da A
	 * INVARIANT RAPPRESENTATION:Follows!=null && Followers!=null && PostCaricati!=null &&
	 * ∀(Follows.keySet()//li chiamiamo utenti)->utenti.isEmpty!=true,Follows.get(utenti)!=null &&
	 * ∀(follows in Follows.get(utenti))->Utenti.contains(follows)=true(ogni utente può seguire solamente utenti
	 * iscritti alla rete sociale) && Follows.get(utenti).contain(utenti)!=true(un utente non può seguire se stesso)
	 * ∀(Followers.keySet()//li chiamiamo utenti2)->utenti2.isEmpty!=true, Followers.get(utenti2)!=null &&
	 * ∀(followers in Followers.get(utenti2))->Utenti.contains(follows)=true(ogni utente può essere seguito solamente
	 * dagli utetni iscritti alla rete sociale && followers.get(utenti2).contains(utenti2)!=true(ogni utente non può essere seguito da se stesso)
	 * ∀(PostCaricati.keySet()//li chiamiamo utenti3)->utenti3.isEmpty()!=true,PostCaricati.get(utenti3)!=null,
	 * Utenti.add(utenti3) (ogni utente che scrive un post è automaticamente iscritto alla rete sociale 
	 * (∀(post1 di Posts ) && ∀(post2 di Posts))->post1.getIdentifcatore!=post2.getIdentificatore (significa che per ogni coppia
	 * di post nel set Posts l'identificatore di post1 è diverso dall'identificatore di post2, quindi ogni post ha un identificatore
	 * univoco.)
	 *   
	 */
	public MicroBlog() {	
		this.Follows=new HashMap<String,Set<String>>();
		this.PostCaricati=new HashMap<String, Set<Post>>();
		this.Utenti=new HashSet<String>();
		this.Followers=new HashMap<String,Set<String>>();
		this.Posts=new HashSet<Post>();
	}
	/*REQUIRES:Post != null;
	 *TRHOWS: se Post==null lancia l'eccezione NullPointerException;
	 *MODIFIES:This.PostCaricati, this.Followers,this.Follows;
	 *EFFECTS:Prende come parametro il post da inserire nella rete sociale, IF nelle chiavi di  PostCaricati esiste il creatore di questo post
	 *		  PostCaricati[creatorepost].add(post). ELSE aggiungiamo il creatore del post come chiave in followers e follows  che avranno un set di 
	 *        seguaci/seguiti vuoti, e in PostCaricati che avra' come unico valore del set di post this.post   
	 * 
	 */
	public void InserisciPost(Post post)throws NullPointerException,BlogException{
		if(post==null) {
			throw new NullPointerException();
		}
		if(PostCaricati.containsKey(post.getUtente())) {
				PostCaricati.get(post.getUtente()).add(post);
		}
		
		else {
			Followers.put(post.getUtente(), new HashSet<String>());
			Follows.put(post.getUtente(), new HashSet<String>());
			PostCaricati.put(post.getUtente(), new HashSet<Post>());
			PostCaricati.get(post.getUtente()).add(post);
			if(!Utenti.contains(post.getUtente())) {
				Utenti.add(post.getUtente());
			}
		}
		if(!post.getLike().isEmpty()) {
			for(String like: post.getLike()) {
				if(!Utenti.contains(like)) {
					throw new BlogException("Un utente non registrato non può mettere like");
				}
				if(Utenti.contains(like)) {
					try {
						this.InserisciLike(post.getIdentificatore(), like);
					}catch(BlogException e) {
						System.out.println(e);
					}
				} 
			}
		}
		Posts.add(post);
	}
	/*
	 * REQUIRES: Utenti.contains(Username)==true,Username.isempty()!=true, ∀(post in Posts)->post.getUtente!=Username
	 * TRHOWS:   se Username==null->NullPointerException, se (Utenti.contains(Username)==false ||  Username.isempty()==true || post.getUtente==Username)->BlogException 
	 * MODIFIES: This.Follows, this.Followers
	 * EFFECTS:  inserisce il like di username al post passato come parametro. Aggiungiamo alla map Follows[username]
	 *           il creatore del post(username segue il creatore del post)(Se è il primo like che username mette al creatore del post)
	 *            e alla map di followers[CreatorePost] username(Se per il creatore del post è il primo like che riceve da username)
	 */
	public void InserisciLike(int ide,String Username)throws NullPointerException,BlogException {
			if(Username==null) {
				throw new NullPointerException();
			}
			if(Username.isEmpty()) {
				throw new BlogException("L'username dell'utente che vuole mettere like non può  essere vuoto");
			}
			if(!Utenti.contains(Username)) {
				throw new BlogException("Un utente non registrato non può inserire like");
			}
			Iterator<Post> ricerca;
			ricerca = Posts.iterator();
			Post prendi;
			while(ricerca.hasNext()) {
				try {
					prendi=ricerca.next();
					if(prendi.getUtente()==Username && prendi.getIdentificatore()==ide) {
						throw new BlogException("Impossibile mettere like a se stessi.");
					}
					if(ide==prendi.getIdentificatore()){
						prendi.setLike(Username);	
						if(Follows.containsKey(Username))
								Follows.get(Username).add(prendi.getUtente());
						else {
								Follows.put(Username, new HashSet<String>());
								Follows.get(Username).add(prendi.getUtente());
						}
						if(Followers.containsKey(prendi.getUtente())) {
							if(!Followers.containsValue(Username)) {
								Followers.get(prendi.getUtente()).add(Username);
							}
						}
						return;
					}
				}
				catch(NewException e) {
					System.out.println(e);
				}
			}
	}
	/*Requires: Username!=null, Username.isempty()!=true,Utenti.contains(Username)==true
	 *Throws: se Username==null->NullPointerException,Usernam.isempty()->BlogException,Utenti.contains(Username)==false->BlogException 
	 *Modifies:this.Follows, this.Followers
	 *Effects:Rimuove il like dell'utente Username al post con id==ide. Se in era l'unico like che Username aveva messa a creatore
	 *	      post allora si modifica Follows(togliendo creatore post) e Followers(togliendo l'username)
	 * 
	 */
	public void TogliLike(int ide,String Username)throws NullPointerException,BlogException {
		if(Username==null) {
			throw new NullPointerException();
		}
		if(Username.isEmpty()) {
			throw new BlogException("L'username dell'utente che vuole mettere like non può  essere vuoto");
		}
		if(!Utenti.contains(Username)) {
			throw new BlogException("Un utente non registrato non può inserire like");
		}
		Iterator<Post> ricerca;
		ricerca = Posts.iterator();
		Post prendi;
		while(ricerca.hasNext()) {
			try {
				prendi=ricerca.next();
				if(ide==prendi.getIdentificatore()) {
					prendi.removeLike(Username);
					if(this.QuantiLike(prendi.getUtente(),Username)==0) {	
						this.Follows.get(prendi.getUtente()).remove(Username);
						this.Followers.get(prendi.getUtente()).remove(Username);
					}
				}
			}
			catch(NewException e) {
				System.out.println(e);
			}
		}
		
	}
	/*
	 *effects:ritorna il numero di like che  username ha messo a utente_creatore
	 */
	public int QuantiLike(String utente_creatore,String username) {
			Set<Post> NumeroPost=PostCaricati.get(utente_creatore);
			int conta=0;
			for(Post post: NumeroPost) {
				if(post.getLike().contains(username)) {
						conta++;
				}
			}
			return conta;
	}
	/*Requires:Username!=null,testo!=null,Username.isempty()!=true,testo.isempty()!=true,testo.lenght>140==true
	 *Trhows:se(Username==null||testo==null)->NullPointerExcetion,se(Username.isempty()==true || testo.isempty()==true || testo.lenght()>140)->BlogException
	 *Effects:Crea un nuovo post: NuovoPost. Chiama il metodo this.InserisciPost per inserire il post nella rete sociale.
	 */
	public void CreaPost(String Username,String testo)throws NullPointerException,BlogException{
			if(Username==null) {
				throw new NullPointerException();
			}
			if(testo==null) {
				throw new NullPointerException();
			}
			if(Username.isEmpty()) {
				throw new BlogException("Il campo Username del creatore del post non può essere vuoto");
			}
			if(testo.isEmpty()) {
				throw new BlogException("Il campo testo di un post non può esser vuoto");
			}
			if(testo.length()>140) {
				throw new BlogException("Il campo testo di un post non può contenere piu' di 140 caratteri");
			}
			//Non controlliamo se Username appartiene ad Utenti perchè ogni utente che scrive un nuovo post è automaticamente iscritto alla rete sociale
			try {
				Post NuovoPost= new Post(Username,testo);
				this.InserisciPost(NuovoPost);
			}
			catch(NewException e) {
				System.out.println(e);
			}
	}
	/*Requires:Username!=null, Username.isempty()!=true
	 * Throws:se Username==null->NullPointerException, se Username.isempty()==true->BlogException
	 * MODIFIES: this.Utenti
	 * EFFECTS: se  Utenti non contine già  Username lo si aggiunge al set di utenti registrati nella rete sociale 
	 */
	public void  InserisciUtente(String Username)throws NullPointerException,BlogException{
		if(Username==null) {
			throw new NullPointerException();
		}
		if(Username.isEmpty()) {
			throw new BlogException("L'username dell'utente che deve essere inserito nella rete sociale non può  essere vuoto");
		}
		Utenti.add(Username);
	}
	//Specifica data nell'interfaccia SocialNetwork1
	public Map<String, Set<String>> guessFollowers(List<Post> ps)throws NullPointerException{
		if(ps==null) {
			throw new NullPointerException();
		}
		MicroBlog newMicroBlog=new MicroBlog();
		try {
			for(Post post: ps) {
				for(String like: post.getLike()) {
					newMicroBlog.InserisciUtente(like);//inserisco nella nuova rete sociale tutti gli utenti che hanno messo like al post 
				}
				newMicroBlog.InserisciPost(post);//Inserisco il post nella rete sociale, il metodo InserisciPost se il post passato come parametro ha dei like, inserisce pure quelli
			}
		}catch(BlogException e) {
			System.out.println(e);
		}
		return  newMicroBlog.Follows;
	}
	//Specifica data nell'interfaccia SocialNetwork1
	public Set<String>getMentionedUsers(List<Post> ps)throws NullPointerException{
		if(ps==null) {
			throw new NullPointerException();
		}
		MicroBlog NuovoBlog=new MicroBlog();
		try {
			for(Post post: ps) {
				for(String like: post.getLike()){
					NuovoBlog.InserisciUtente(like);
				}
				NuovoBlog.InserisciPost(post);
			}
		}catch(BlogException e) {
			System.out.println(e);
		}
		return NuovoBlog.PostCaricati.keySet();
	}
	//Specifica data nell'interfaccia SocialNetwork1
	public Set<String>getMentionedUsers(){
		return this.PostCaricati.keySet();
	}
	//Specifica data nell'interfaccia SocialNetwork1
	public List<Post> writtenBy(String username)throws NullPointerException,BlogException{
		if(username==null) {
			throw new NullPointerException();
		}
		if(username.isEmpty()) {
			throw new BlogException("Il campo username non può essere vuoto");
		}
		if(!Utenti.contains(username)) {
			throw new BlogException("Non è possibile trovare i post scritti da un utente che non è iscritto alla rete sociale");
		}
		List<Post> NuovaLista= new ArrayList<Post>();
		Iterator<Post> prendi= PostCaricati.get(username).iterator();
		while(prendi.hasNext()) {
			NuovaLista.add(prendi.next());
		}
		return NuovaLista;
	}
	//Specifica data nell'interfaccia SocialNetwork1
	public List<Post> writtenBy(List<Post> ps, String username)throws NullPointerException,BlogException{
			if(ps==null) {
				throw new NullPointerException();
			}
			if(username==null) {
				throw new NullPointerException();
			}
			if(username.isEmpty()) {
				throw new BlogException("Il campo username non può essere vuoto");
			}
			MicroBlog NuovoBlog= new MicroBlog();
			List<Post> NuovaLista=new ArrayList<Post>();
			try {
				for(Post post: ps) {
					for(String like: post.getLike()) {
						NuovoBlog.InserisciUtente(like);
					}
					NuovoBlog.InserisciPost(post);
				}
			}catch(BlogException e) {
				System.out.println(e);
			}
			if(!NuovoBlog.Utenti.contains(username)) {
				throw new BlogException("Non è possibile trovare i post scritti da un utente che non è iscritto alla rete sociale");
			}
			Iterator<Post> prendi= NuovoBlog.PostCaricati.get(username).iterator();
			while(prendi.hasNext()) {
				NuovaLista.add(prendi.next());
			}
			return NuovaLista;
	}
	//Specifica data nell'interfaccia SocialNetwork1
	public List<Post> containing(List<String> words)throws NullPointerException{
		if(words==null) {
			throw new NullPointerException();
		}
		Iterator<String> NuovoIterator=PostCaricati.keySet().iterator();
		Iterator<String> NuovoIterator2=words.iterator();
		List<Post> PostValidi=new ArrayList<Post>();
		boolean contiene=false;
		while(NuovoIterator.hasNext()) {
			String username=NuovoIterator.next();
			for(Post post: PostCaricati.get(username)) {
				if(post==null) {
					throw new NullPointerException();
				}
				while(NuovoIterator2.hasNext() && contiene==false) {
					contiene=checkword(post.getTesto(),NuovoIterator2.next());
					if(contiene==true) {
							PostValidi.add(post);
							
					}
				}
				NuovoIterator2=words.iterator();
				contiene=false;
			}
		}
		return PostValidi;
	}
	//Effects:funzione chiamata dal metodo containing che restituisce true se  la string parola è contenuta nella stringa testo
	public boolean checkword(String testo, String parola){
		
			if(testo.contains(parola)) {
				return true;
			}
			else {
					return false;
			}
	}
	//Specifica data nell'interfaccia SocialNetwork
	public List<String> influencers(Map<String, Set<String>> followers)throws NullPointerException{
		if(followers==null) {
			throw new NullPointerException();
		}
		List<String> Bestinfluencer=new ArrayList<String>();
		Iterator<String> Utenti=followers.keySet().iterator();
		int massimo1=0;
		int massimo2=0;
		int massimo3=0;
		String utente;
		String influencer1=null;
		String influencer2=null;
		String influencer3=null;
		while(Utenti.hasNext()) {
			utente=Utenti.next();
			int size=followers.get(utente).size();
			if(size>massimo1) {
				massimo2=massimo1;
				influencer2=influencer1;
				massimo1=size;
				influencer1=utente;
			}
			else if(size>massimo2) {
				massimo3=massimo2;
				influencer3=influencer2;
				massimo2=size;
				influencer2=utente;
			}
			else if(size>massimo3) {
				massimo3=size;
				influencer3=utente;
			}
		}
		Bestinfluencer.add(influencer1);
		Bestinfluencer.add(influencer2);
		Bestinfluencer.add(influencer3);
		return Bestinfluencer;
	}
	//Effects:restituisce la Mapp Follows del MicroBlog
	public Map<String, Set<String>> getFollows(){
		return this.Follows;
	}
	//Effects: restituisce la Map Follower del MicroBlog
	public Map<String, Set<String>> getFollowers(){
		return this.Followers;
	}
	public Set<String> getUtentiRegistrati(){
		return this.Utenti;
	}
	//Effects: Stampa tutti gli utenti registrati nel MicroBlog
	public void StampaRegistrati() {
		Iterator<String> utenti=Utenti.iterator();
		while(utenti.hasNext()) {
			System.out.println(utenti.next());
		}
	}
	/*Requires: Username!=null, Username.isempty()!=true
	 *Trhows: se Username==null->NullPointerException, se Username.isempty()==true->BlogException
	 *Effects:Stampa gli utenti seguiti da Username
	 */
	public void StampaFollows(String Username)throws NullPointerException,BlogException {
		if(Username==null) {
			throw new NullPointerException();
		}
		if(Username.isEmpty()){
			throw new BlogException("Impossibile trovare gli utenti seguiti da  Username, perchè la stringa è vuota");
		}
		
		System.out.println(this.Follows.get(Username));
	}
	/*Requires: Username!=null, Username.isempty()!=true
	 *Trhows: se Username==null->NullPointerException, se Username.isempty()==true->BlogException
	 *Effects:Stampa gli utenti che seguono Username
	 */
	public void StampaFollowers(String Username)throws NullPointerException,BlogException {
		if(Username==null) {
			throw new NullPointerException();
		}
		if(Username.isEmpty()){
			throw new BlogException("Impossibile trovare gli utenti che seguono Username, perchè la stringa è vuota");
		}
		System.out.println(this.Followers.get(Username));
	}
}
