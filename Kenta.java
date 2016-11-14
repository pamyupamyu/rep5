import java.util.*;

/***
 * Semantic Net の使用例
 */
public class Kenta {
    public static void main(String args[]){
	SemanticNet sn = new SemanticNet();

	// 球技はスポーツである．
	sn.addLink(new Link("is-a","Ball-game","sports",sn));
	
	// テニスは球技である
	sn.addLink(new Link("is-a","tennis","Ball-game",sn));

	// 健太は名古屋工業大学の学生である．
	sn.addLink(new Link("is-a","Kenta","NIT-student",sn));

	// 健太の専門は人工知能である．
	sn.addLink(new Link("speciality","Kenta","AI",sn));
	
	// 健太の趣味はテニスである．
	sn.addLink(new Link("hobby","Kenta","tennis",sn));
	
	// 健太の趣味はボルダリングである
	sn.addLink(new Link("hobby","Kenta","bouldering",sn));

	// 名古屋工業大学の学生は，学生である．
	sn.addLink(new Link("is-a","NIT-student","student",sn));

	//　健太はりょうたの友達である
	sn.addLink(new Link("is-a","Kenta","Ryota",sn));
	
	// 健太は広大の友達である
	sn.addLink(new Link("is-a","Kenta","Koudai",sn));
	
	 // 健太は知道の友達である
	sn.addLink(new Link("is-a","Kenta","Tomomichi",sn));

	// 健太は大輔の友達である
	sn.addLink(new Link("is-a","Kenta","Daisuke",sn));
	

	sn.printLinks();
	sn.printNodes();

	ArrayList<Link> query = new ArrayList<Link>();
	query.add(new Link("is-a","?y","student"));
	query.add(new Link("hobby","?y","tennis"));
	sn.query(query);
    }    
}
