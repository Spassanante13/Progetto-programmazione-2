import java.util.ArrayList;
import java.util.List;
import java.util.*;
public class MicroBlogFiltrato extends MicroBlog  {
	 private List<String> listaparole;
		/*Overview:MicroBlogFiltrato è un tipo di dato modificabile che permette la creazione di post i cui testi verrano filtrati, per controllare
		 *		   se ci sono parole che possono essere offensive,oltre al creare post, possiamo mettere like a post di altri utenti e seguire altri utenti. 
		 *  ABSTRACT FUNCTION:f(utente_A)->{{Follows.get(utente_A)},{Followers.get(utente_A)},{PostCaricati.get(utente_A)}}il dominio della funzione è l'insieme degli utenti iscritti alla rete sociale e il codominio
			   				  della funzione è una tripla di insiemi:utenti seguiti da utente_A,utenti che seguono utente_A e post scritti da utente_A
		    TYPICAL ELEMENT:Un elemento tipico del tipo di dato MicroBlog è l'associazione di ogni utente_A
			   				che è registrato nella rete socialo con: gli utenti che A  segue, gli utenti che seguono A e i post scritti da A
		    INVARIANT RAPPRESENTATION: Invariante di rappresentazione della classe MicrboBlog && listaparole!=null &&  ∀(parola in listaparole)->(parola!=null && parola.isempty()!=true)
		    						   && ∀(PostCaricati.values)->not exist(post.getTesto().contains(parola))
		 */
		/*Requires: parole!=null,∀(parola in parole)->(parola!=null && parola.isempty()!=true)
		 *Throws: se(parole==null || parola==null)->NullPointerException, se parola.isempty()==true->IllegalArgumentException
		 *Effects:Dopo aver chiamata il costruttore della classe padre e essersi accertati che non vengono lanciate eccezioni,
		 *		  aggiunge le parole che devono essere censurate in listaparole.
		 */
		public MicroBlogFiltrato(List<String> parole)throws NullPointerException,IllegalArgumentException {
			super();
			if(parole==null) {
				throw new NullPointerException();
			}
			listaparole=new ArrayList<>();
			for(String parola: parole) {
				if(parola==null) {
					throw new NullPointerException();
				}
				if(parola.isEmpty()) {
					throw new IllegalArgumentException();
				}
				listaparole.add(parola);
			}
		}
		@Override
		/*Requires: identico al supertipo 
		 *Throws: identico al supertipo
		 * Effects: Avviene un controllo sul testo del post passato come parametro, se ci sono parole presenti anche in listaparole
		 *          vengono censurata da "****" e poi si chiama il metodo InserisciPost del supertipo
		 * 
		 */
		public void  InserisciPost(Post post)throws NullPointerException,BlogException{
			if(post==null) {
				throw new NullPointerException();
			}
			String Testomod=post.getTesto();
			for(String parola: listaparole) {
					Testomod=Testomod.replaceAll(parola,"****");
					try {
						post.SetTesto(Testomod);
					}
					catch(NewException e) {
						System.out.println(e);
					}
			}
			super.InserisciPost(post);
			if(!post.getLike().isEmpty()) {
				for(String like: post.getLike()) {
					if(!super.getUtentiRegistrati().contains(like)) {
						throw new BlogException("Un utente non registrato non può mettere like");
					}
					if(this.getUtentiRegistrati().contains(like)) {
						try {
							this.InserisciLike(post.getIdentificatore(), like);
						}catch(BlogException e) {
							System.out.println(e);
						}
					} 
				}
			}
		}
}
