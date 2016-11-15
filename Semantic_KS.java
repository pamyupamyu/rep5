
import java.util.ArrayList;

/***
 * Semantic Net の使用例
 */
public class Semantic_KS {
    public static void main(String args[]){
	SemanticNet sn = new SemanticNet();

	//島野は名古屋工業大学の学生である
	sn.addLink(new Link("is-a","Shimano","NIT-student",sn));

	//清水は島野の友人である
	sn.addLink(new Link("is-a","Shimizu","Shimano's friend",sn));

	//新海は島野の友人である
	sn.addLink(new Link("is-a","Shinkai","Shimano's friend",sn));

	//椙田は島野の友人である
	sn.addLink(new Link("is-a","Sugita","Shimano's friend",sn));

	//鈴木は島野の友人である
	sn.addLink(new Link("is-a","Suzuki","Shimano's friend",sn));

	//島田は島野の友人である
	sn.addLink(new Link("is-a","Shimada","Shimano's friend",sn));

	//ヒナまつりは漫画である
	sn.addLink(new Link("is-a","Hinamatsuri","Comic",sn));

	//島野はヒナまつりが好きである
	sn.addLink(new Link("like","Shimano","Hinamatsuri",sn));

	//鈴木は島野の友人である
	sn.addLink(new Link("has-a","Shinkai","Hinamatsuri",sn));






	sn.printLinks();
	sn.printNodes();

	ArrayList<Link> query = new ArrayList<Link>();
	query.add(new Link("own","?y","Ferrari"));
	query.add(new Link("is-a","?y","student"));
	query.add(new Link("hobby","?y","baseball"));
	sn.query(query);
    }
}

