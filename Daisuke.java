import java.util.*;

/***
 * Semantic Net の使用例
 */
public class Daisuke {
    public static void main(String args[]){
	SemanticNet sn = new SemanticNet();

	// 大輔は男性である．
	sn.addLink(new Link("is-a","Daisuke","man",sn));
	
	// 男性は人間である．
		sn.addLink(new Link("is-a","man","human",sn));

	// 大輔は名古屋工業大学の学生である．
	sn.addLink(new Link("is-a","Daisuke","NIT-student",sn));

	// 大輔の専門は人工知能である．
	sn.addLink(new Link("speciality","Daisuke","AI",sn));
	
	// エロゲはゲームである．
	sn.addLink(new Link("is-a","Hentai-Game","Game",sn));

	// 人間は知性を持つ．
	sn.addLink(new Link("has-a","human","intelligence",sn));
	
	// 大輔の趣味はエロゲである．
	sn.addLink(new Link("hobby","Daisuke","Hentai-Game",sn));
	
	// 大輔はエロゲを所有する．
	sn.addLink(new Link("own","Daisuke","Hentai-Game",sn));

	// 名古屋工業大学の学生は，学生である．
	sn.addLink(new Link("is-a","NIT-student","student",sn));

	// 学生は勉強しない．
	sn.addLink(new Link("donot","student","study",sn));
	
	//大輔は広大の友達である。
	sn.addLink(new Link("friend","Daisuke","Koudai",sn));
	
	//大輔は涼太の友達である。
	sn.addLink(new Link("friend","Daisuke","Ryota",sn));
	
	//大輔は知道の友達である。
	sn.addLink(new Link("friend","Daisuke","Tomomiti",sn));
	
	//大輔は健太の友達である。
	sn.addLink(new Link("friend","Daisuke","Kenta",sn));
	    
		
	sn.printLinks();
	sn.printNodes();

	ArrayList<Link> query = new ArrayList<Link>();
	query.add(new Link("own","?y","Hentai-Game"));
	query.add(new Link("is-a","?y","student"));
	query.add(new Link("hobby","?y","Hentai-Game"));
	sn.query(query);
    }    
}

