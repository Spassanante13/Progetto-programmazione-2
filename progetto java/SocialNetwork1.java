import java.util.List;
import java.util.Map;
import java.util.Set;


public interface SocialNetwork1 {
	/*REQUIRES=ps != null e per ogni post p appartenente a ps, p!=null
	 *TRHOWS: NullPointerException se ps==null e NullPoineterException se p==null
	 *EFFECTS: Restituisce una map:
	 * 				per ogni chiave a di map[a]->lista degli utenti seguiti da a.
	 * 				(un utente a, segue b quando esiste  nel set like di almeno un post di b, uno o piu' like di a) 
	 */
	public Map<String, Set<String>> guessFollowers(List<Post> ps)throws NullPointerException;
						
	/*REQUIRES:	Followers!=null,∀ ((K,v) ∈ followers) -> v!=null, ∀ (s ∈ v)-> s!=null AND s.lenght>0 
	 *THROWS:	NullPointerException se followers==null OR (∃ (K,v) ∈ followers)-> v==null
	 *        	IllegalArgument se  ∃ (s ∈ v)->s.lenght==0 OR s=null
	 *EFFECTS:  Restituisce una lista che contiene i 3 utenti piu' influenti della map inserita come parametro
	 */
	public List<String> influencers(Map<String, Set<String>> follows);
	/*EFFECTS: Restituisce un set di stringhe che rappresentano gli utenti menzionati(inclusi) nella rete sociale, per utenti menzionati
	 * 		   si intende, tutti  gli utenti che hanno scritto almeno un post  
	 */
	public Set<String> getMentionedUsers();		
	/*REQUIRES: ps!=null,∀(p ∈ ps)->p!=null
	 *TRHOWS:   NullPointerException se ps==null OR ∃ (p ∈ ps)->p==null
	 *EFFECTS:  Restituisce un set di stringhe che rappresentano gli utenti menzionati in riferimento alla map passata come parametro,
	 *			per utenti menzionati si intende, tutti gli utenti che hanno scritto almeno un post
	 */
	public Set<String> getMentionedUsers(List<Post> ps)throws NullPointerException;
					
	/*REQUIRES: username!=null AND username.lenght>0
	* TRHOWS: NullPointerException se username=null
	* 		   IllegalArgumentException se username.lenght=0
	* EFFECTS: Restituisci la lista di tutti i post scritti da username(passato come parametro) nella rete sociale 
	*/
	public List<Post> writtenBy(String username)throws NullPointerException,BlogException;
	/*REQUIRES: ps!=null AND (∀(p ∈ ps)->p !=null) AND username != null AND username.lenght>0
	 * TRHOWS:  NullPointerException se ps==null OR ∃(p ∈ ps)->p==null OR username==null
	 * 			IllegalArgumentException se username.lenght==0
	 * EFFECTS: Restituisce la liste dei  post effettuati dall'utente,il cui nome
	 * 			é dato dal parametro username della map  passata come parametro 
     */
	public List<Post> writtenBy(List<Post> ps, String username)throws NullPointerException,BlogException;		
	/* REQUIRES: words!=null AND ∀(w ∈ words)->(w.lenght>0 AND w!=null) 
	 * TRHOWS:   NullPointerException se words==null OR w==null
     * 		  IllegalArgument se w.lenght==0
	 * EFFECTS: Restituisce la lista dei post presenti nella rete sociale che includono almeno una delle parole
	 * 		 presenti nella lista words passata come parametro
	 */
    public List<Post> containing(List<String> words)throws NullPointerException;
}
