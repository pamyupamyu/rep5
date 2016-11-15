<?
//ああああああああああ//
import java.util.*;

/***
 * Semantic Net の使用例
 */
public class Example {
    public static void main(String args[]){
	SemanticNet sn = new SemanticNet();

	// サイクリングはスポーツである．
	sn.addLink(new Link("is-a","cycling","sports",sn));

	// 涼太は名古屋工業大学の学生である．
	sn.addLink(new Link("is-a","Ryota","NIT-student",sn));

	// 涼太の専門は人工知能である．
	sn.addLink(new Link("speciality","Ryota","AI",sn));
	
	// エスケープは自転車である．
	sn.addLink(new Link("is-a","Escape","bicycle",sn));

	// 自転車はペダルを持つ．
	sn.addLink(new Link("has-a","bicycle","pedal",sn));
	
	// 涼太の趣味はお絵かきである．
	sn.addLink(new Link("hobby","Ryota","Drawing",sn));
	
	// 涼太の趣味はサイクリングである。
	sn.addLink(new Link("hobby","Ryota","cycling",sn));
	
	// 涼太はエスケープを所有する．
	sn.addLink(new Link("own","Ryota","Escape",sn));

	// 名古屋工業大学の学生は，学生である．
	sn.addLink(new Link("is-a","NIT-student","student",sn));

	// 学生は勉強しない．
	sn.addLink(new Link("donot","student","study",sn));
	
	// 大輔は涼太の友人である
	sn.addLink(new Link("is-a","Daisuke","Ryota's-friend",sn));

	// 広大は涼太の友人である
		sn.addLink(new Link("is-a","koudai","Ryota's-friend",sn));
	
	// 知道は涼太の友人である
		sn.addLink(new Link("is-a","tomomichi","Ryota's-friend",sn));
	
	// 健太は涼太の友人である
		sn.addLink(new Link("is-a","kenta","Ryota's-friend",sn));	
		
	sn.printLinks();
	sn.printNodes();

	ArrayList<Link> query = new ArrayList<Link>();
	query.add(new Link("own","?y","Escape"));
	query.add(new Link("is-a","?y","student"));
	query.add(new Link("hobby","?y","Drawing"));
	sn.query(query);
    }    
}

