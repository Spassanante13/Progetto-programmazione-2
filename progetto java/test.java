import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.*;
public class test {
 
	public static void main(String[] args) {
		MicroBlog insta=new MicroBlog();
		try {//test per la registrazione di utenti nella rete sociale
			insta.InserisciUtente("Stefano");
			insta.InserisciUtente("Alfredo");
			insta.InserisciUtente("Maria");
			insta.InserisciUtente("Michelle");
			insta.InserisciUtente("Irene");
			insta.InserisciUtente("Samuele");
			insta.InserisciUtente("Giuseppe");
			insta.InserisciUtente("Rosario");
			insta.InserisciUtente("Luca");
			System.out.println("Gli utenti presenti nella rete sociale sono: ");
			insta.StampaRegistrati();
			
		}catch(BlogException e) {
			System.out.println(e);
		}
		try {//proviamo ad inserire utenti non validi
			insta.InserisciUtente("");//lancia eccezione BlogException
	
		}catch(BlogException e) {
			
			System.out.println(e);
		}
		try {//Adesso proviamo ad inserire i primi post nella rete sociale e mettere i like
			insta.CreaPost("Andrea", "Ciao a tutti quanti");/*Andrea non è stato inserito precedentemente nella rete socia
															 ma non deve lanciare eccezzione perchè abbiamo assunto che
															 ogni utente che crea un nuovo post viene automaticamente inserito nella rete sociale
															*/
			insta.CreaPost("Stefano", "Forza Italia");
			insta.CreaPost("Luca", "Oggi c'è una bella giornata");
			insta.CreaPost("Luca", "Domani pioverà");
			insta.CreaPost("Matteo", "Domani vado a scuola");
			insta.CreaPost("Claudia","Qual è il vostro film preferito?");
			insta.InserisciLike(0,"Alfredo");
			insta.InserisciLike(0,"Luca");
			insta.InserisciLike(1,"Giuseppe");//questi like sono stati messi da utenti registrati nella rete sociale quindi non devono lanciare eccezione
			insta.InserisciLike(1,"Alfredo");
			insta.InserisciLike(1,"Andrea");
			insta.InserisciLike(2,"Samuele");//L'utente Andrea è stato inserito nelle rete sociale nel momento in cui ha creato un post, quindi nessuna eccezione viene lanciata
			insta.InserisciLike(0,"Stefano");
			insta.InserisciLike(2,"Stefano");
			insta.InserisciLike(3,"Stefano");
			insta.InserisciLike(4,"Stefano");
			insta.InserisciLike(4,"Claudia");
			insta.InserisciLike(4, "Rosario");
			insta.InserisciLike(1, "Rosario");
			insta.InserisciLike(1, "Irene");
			insta.InserisciLike(2,"Rosario");
			insta.InserisciLike(5,"Matteo");
			//proviamo a stampare la Map Follows della rete sociale formatasi
			System.out.println("L'utente Alfredo segue: ");
			insta.StampaFollows("Alfredo");//L'utente Alfredo ha inserito like ad Andrea e Stefano 
			System.out.println("L'utente Giuseppe segue: ");
			insta.StampaFollows("Giuseppe");//L'utente Giuseppe ha messo like solamente a Stefano
			System.out.println("L'utente Andrea segue: ");
			insta.StampaFollows("Andrea");
			//Proviamo a stampare la Map Followers della rete sociale formatasi
			System.out.println("L'utente Stefano è seguto da: ");
			insta.StampaFollowers("Stefano");
			System.out.println("L'utente Luca è seguito da: ");
			insta.StampaFollowers("Luca");
			System.out.println("L'utente Andrea è seguto da:");
			insta.StampaFollowers("Andrea");
			//Adesso proviamo a rimuovere i like 
			insta.TogliLike(0,"Alfredo");//Alfredo toglie il like ad andrea
			insta.TogliLike(1,"Andrea");//Andrea toglie il like a Stefano
			System.out.println("L'utente Stefano adesso  è seguto da: ");
			insta.StampaFollowers("Stefano");//Dato che Andrea ha tolto il like a Stefano ed era l'unico like di Andrea ad un post di 
											//Stefano, Andrea non deve comparire nella Map Followers[Stefano]
			System.out.println("L'utente Andrea adesso è seguto da:");
			insta.StampaFollowers("Andrea");//La stessa cosa per Alfredo con Andrea
			//TESTIAMO IL METODO RICHIESTO DAL PROGETTO: CONTAINING
			ArrayList<String> words=new ArrayList<String>();
			words.add("Forza");//parola contenuta nel post di stefano
			words.add("bella");//parola contenuta nel post di luca
			words.add("CIao");//questa parola, così per come è scritta non è contenuta in nessun post
			System.out.println("I post che contengono le parole di words sono:");
			System.out.println(insta.containing(words));
			//TESTIAMO IL METODO RICHIESTO NEL PROGETTO: WrittenBy(String Username)
			System.out.println("La lista di post scritti da Luca è: ");
			System.out.println(insta.writtenBy("Luca"));//Luca ha scritto due post "Oggi c'è una bella giornata" e "Domani pioverà"
			//TESTIAMO IL METODO RICHIESTO NEL PROGETTO: getMentionedUsers()
			System.out.println("Gli utenti menzionati nella nostra rete sociale sono:");
			System.out.println(insta.getMentionedUsers());
			//TESTIAMO IL METODO RICHIESTO NEL PROGETTO: influencers(Map<String, Set<String> followers)
			System.out.println("Gli utenti più influenti della nostra rete sociale sono: ");
			System.out.println(insta.influencers(insta.getFollowers()));
		}catch(BlogException e) {
		System.out.println(e);
		}
		try {//Proviamo ad usare il metodo writtenBy con un utente che non è registrato nella rete sociale
			insta.writtenBy("Carlo");//Non è possibile trovare i post scritti da un utente che non è iscritto alla rete sociale
		}
		catch(BlogException e) {
			System.out.println(e);
		}
		try {//Impossibile mettere like a se stessi
			insta.InserisciLike(1, "Stefano");
			
		}catch(BlogException e) {
			System.out.println(e);
		}
		try {//Un utente non registrato  non  può mettere like
			insta.InserisciLike(1,"Emanuele");;
		}
		catch(BlogException e) {
			System.out.println(e);
		}
		try {//Ora testiamo il MicroBlog inserendo post creati all'esterno
			Post post6=new Post("Michele","Oggi sono felice");
			Post post7=new Post("Tommaso","Vado in vacanza in montagna");
			Post post8=new Post("Lorenzo","Che ore sono?");
			Post post9= new Post("Michele","Sono le 15:00");
			insta.InserisciUtente("Lucia");
			post6.setLike("Lucia");
			post6.setLike("Stefano");
			insta.InserisciPost(post6);
			insta.InserisciPost(post7);
			insta.InserisciPost(post8);
			insta.InserisciPost(post9);
			insta.InserisciLike(6,"Samuele");
			insta.InserisciLike(9, "Samuele");
			System.out.println("La nuova lista di utenti registrati è: ");
			insta.StampaRegistrati();//Uguale alla precedente lista, con in aggiunta Michele,Tommaso,Lorenzo e Lucia
			System.out.println("L'utente Michele è seguito da: ");
			insta.StampaFollowers("Michele");
			insta.TogliLike(6,"Samuele");//Abbiamo tolto il like di samuele al post1 di  michele
			//stampiamo la lista di Followers di michele dopo aver tolto un like di samuele
			System.out.println("La lista di utenti che seguono Michele è: ");
			insta.StampaFollowers("Michele");//deve essere uguale a quella precente percheè
											//anche se Samuele ha tolo un like ad un post di Michele
											//quello non era l'unico like di Samuele ad un post di Michele	
			//Stampiamo anche la lista seguiti di Samuele
			System.out.println("La lista degli utenti seguiti da Samuele è: ");
			insta.StampaFollows("Samuele");//deve comparire Michele e Luca(like a luca messo in precedenza)
		}
		catch(NewException | BlogException e) {
			System.out.println(e);
		}
		try {//Proviamo a creare un post dall'esterno vuoto
			Post post10=new Post("Stefano","");
		}
		catch(NewException e) {
			System.out.println(e);//Il campo testo non può essere vuoto
		}
		try {//Proviamo a mettere like ad un post creato dall'esterno da un utente non iscritto alla rete sociale
			Post post11=new Post("Stefano","Domani giochiamo a calcio.");
			post11.setLike("Antonina");
			insta.InserisciPost(post11);
		}
		catch(NewException | BlogException  e) {
			System.out.println(e);
		}
		try {//TESTIAMO IL METODO RICHIESTO NEL PROGETTO: guessFollowers(Lis<Post> ps)
			Post npost1=new Post("Riccardo","Ti piace la pizza?");
			npost1.setLike("Stefano");
			npost1.setLike("Irene");
			Post npost2=new Post("Mattia","Si");
			npost2.setLike("Claudia");
			npost2.setLike("Michele");
			Post npost3=new Post("Vito","A me piace il sushi");
			npost3.setLike("Giuseppe");
			List<Post> Postlist=new ArrayList<Post>();
			Postlist.add(npost1);
			Postlist.add(npost2);
			Postlist.add(npost3);
			Map<String, Set<String>>guess=insta.guessFollowers(Postlist);
			System.out.println("La rete sociale creatasi dai post: Postlist è:\n"+guess);
			//TESTIAMO IL  METODO RICHIESTO NEL PROGETTO: getMentionedUsers(List<Post> ps)
			System.out.println("Gli utenti menzionati in base alla lista di post(Postlist) sono: ");
			System.out.println(insta.getMentionedUsers(Postlist));
			//TESTIAMO IL METODO RICHIESTO NEL PROGETTO: writtenBy(List<Post>, String username)
			System.out.println("I post di Postlist scritti da Riccardo sono:");
			System.out.println(insta.writtenBy(Postlist, "Riccardo"));
		}
		catch(NewException | BlogException e) {
			System.out.println(e);
		}
		try {//Testiamo il nostro MicroBlogFiltrato
			List<String> parolefiltrate=new ArrayList<String>();
			parolefiltrate.add("brutto");
			parolefiltrate.add("cattivo");
			parolefiltrate.add("idiota");
			MicroBlogFiltrato insta2=new MicroBlogFiltrato(parolefiltrate);
			insta2.CreaPost("Stefano", "Sei brutto");
			insta2.CreaPost("Federico", "Marco è cattivo con te?");
			insta2.CreaPost("Stefano","Oggi c'è un cattivo tempo");
			insta2.InserisciUtente("Tommaso");
			insta2.InserisciUtente("Melchiorre");
			insta2.InserisciUtente("Filippo");
			insta2.InserisciLike(14,"Filippo");
			insta2.InserisciLike(15, "Stefano");
			insta2.InserisciLike(15,"Melchiorre");
			insta2.InserisciLike(14,"Melchiorre");
			insta2.InserisciLike(16,"Melchiorre");
			System.out.println("I post filtrati scritti da Stefano sono:");
			System.out.println(insta2.writtenBy("Stefano"));
			System.out.println("La rete sociale di questo Micro Blog Filtrato è: ");
			System.out.println(insta2.getFollows());
			//Proviamo il nostro MicroBlogFiltrato con post creati all'esterno
			Post post=new Post("Luca","Sei un idiota");
			post.setLike("Stefano");
			post.setLike("Federico");
			insta2.InserisciPost(post);
			System.out.println("I post filtrati scritti da Luca sono: ");
			System.out.println(insta2.writtenBy("Luca"));
			
			
		}catch(NewException | BlogException e) {
			System.out.println(e);
		}
	}	
	
}
