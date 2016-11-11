import java.util.*;

/***
 * Semantic Net 縺ｮ菴ｿ逕ｨ萓�
 */
public class Daisuke {
    public static void main(String args[]){
	SemanticNet sn = new SemanticNet();

	//僕のPCで開いたら文字化けしてた
	// 螟ｧ霈斐�ｯ逕ｷ諤ｧ縺ｧ縺ゅｋ�ｼ�
	sn.addLink(new Link("is-a","Daisuke","man",sn));
	
	// 逕ｷ諤ｧ縺ｯ莠ｺ髢薙〒縺ゅｋ�ｼ�
		sn.addLink(new Link("is-a","man","human",sn));

	// 螟ｧ霈斐�ｯ蜷榊商螻句ｷ･讌ｭ螟ｧ蟄ｦ縺ｮ蟄ｦ逕溘〒縺ゅｋ�ｼ�
	sn.addLink(new Link("is-a","Daisuke","NIT-student",sn));

	// 螟ｧ霈斐�ｮ蟆る摩縺ｯ莠ｺ蟾･遏･閭ｽ縺ｧ縺ゅｋ�ｼ�
	sn.addLink(new Link("speciality","Daisuke","AI",sn));
	
	// 繧ｨ繝ｭ繧ｲ縺ｯ繧ｲ繝ｼ繝�縺ｧ縺ゅｋ�ｼ�
	sn.addLink(new Link("is-a","Hentai-Game","Game",sn));

	// 莠ｺ髢薙�ｯ遏･諤ｧ繧呈戟縺､�ｼ�
	sn.addLink(new Link("has-a","human","intelligence",sn));
	
	// 螟ｧ霈斐�ｮ雜｣蜻ｳ縺ｯ繧ｨ繝ｭ繧ｲ縺ｧ縺ゅｋ�ｼ�
	sn.addLink(new Link("hobby","Daisuke","Hentai-Game",sn));
	
	// 螟ｧ霈斐�ｯ繧ｨ繝ｭ繧ｲ繧呈園譛峨☆繧具ｼ�
	sn.addLink(new Link("own","Daisuke","Hentai-Game",sn));

	// 蜷榊商螻句ｷ･讌ｭ螟ｧ蟄ｦ縺ｮ蟄ｦ逕溘�ｯ�ｼ悟ｭｦ逕溘〒縺ゅｋ�ｼ�
	sn.addLink(new Link("is-a","NIT-student","student",sn));

	// 蟄ｦ逕溘�ｯ蜍牙ｼｷ縺励↑縺�ｼ�
	sn.addLink(new Link("donot","student","study",sn));
	
	//螟ｧ霈斐�ｯ蠎�螟ｧ縺ｮ蜿矩＃縺ｧ縺ゅｋ縲�
	sn.addLink(new Link("friend","Daisuke","Koudai",sn));
	
	//螟ｧ霈斐�ｯ豸ｼ螟ｪ縺ｮ蜿矩＃縺ｧ縺ゅｋ縲�
	sn.addLink(new Link("friend","Daisuke","Ryota",sn));
	
	//螟ｧ霈斐�ｯ遏･驕薙�ｮ蜿矩＃縺ｧ縺ゅｋ縲�
	sn.addLink(new Link("friend","Daisuke","Tomomiti",sn));
	
	//螟ｧ霈斐�ｯ蛛･螟ｪ縺ｮ蜿矩＃縺ｧ縺ゅｋ縲�
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

