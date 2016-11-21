import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
/*
Example.java
 */
public class Frame {
	static AIFrameSystem fs;
	ArrayList<String>slotlist;
	public static boolean n_or_q = true;
	public static void main(String args[]){
		System.out.println( "Frame" );
		// フレームシステムの初期化
		//AIFrameSystem fs = new AIFrameSystem();
		fs = new AIFrameSystem();
		// クラスフレーム human の生成
		fs.createClassFrame( "human" );
		// height スロットを設定
		fs.writeSlotValue( "human", "height", new String( "160" ) );
		// height から weight を計算するための式 weight = 0.9*(height-100) を
		// when-requested demon として weight スロットに割り当てる
		fs.setWhenRequestedProc( "human", "weight", new AIDemonProcReadTest() );
		//memberというクラスフレーム作成
		fs.createClassFrame( "human" , "member" );
		//各インスタンスフレームをmemberフレームから作成
		fs.writeSlotValue( "member", "task", new String( "no job" ) );
		fs.createInstanceFrame( "member", "Daisuke" );
		fs.createInstanceFrame( "member", "Kenta" );
		fs.createInstanceFrame( "member", "Ryota" );
		fs.createInstanceFrame( "member", "Kodai" );
		fs.createInstanceFrame( "member", "Tomomichi" );
		//Daisukeのスロットにデータを書く
		fs.writeSlotValue( "Daisuke", "height", new String( "172" ) );
		fs.writeSlotValue( "Kenta", "height", new String( "168" ) );
		fs.writeSlotValue( "Ryota", "height", new String( "172" ) );
		fs.writeSlotValue( "Kodai", "height", new String( "165" ) );
		fs.writeSlotValue( "Tomomichi", "height", new String( "182" ) );
		fs.writeSlotValue( "Daisuke", "task", new String( "5-2" ) );
		fs.writeSlotValue( "Kenta", "task", new String( "5-3" ) );
		fs.writeSlotValue( "Ryota", "task", new String( "5-4" ) );
		fs.writeSlotValue( "Kodai", "task", new String( "5-3" ) );
		fs.writeSlotValue( "Tomomichi", "task", new String( "5-1" ) );


		//query
		System.out.println("コマンドを入力してください");
		try{
			while(true){
				if(n_or_q){		//質疑応答モードの切り替えフラグtrue;クエリーfalse:自然言語
					System.out.println("Frame名とSlot名入力(空白区切り),終了:exit,自然言語:natural");
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					String s = br.readLine();
					String[] data = s.split(" ",0);
					if(data[0].equals("exit")) System.exit(0);
					if(data[0].equals("natural")){
						n_or_q = false;
					}else{	//モードの切り替え
					new Frame().query(data);
					}

				//自然言語の質疑応答
				}else{
					System.out.println("質門文を入力,終了:exit,クエリー:query");
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					String s = br.readLine();
					if(s.indexOf("exit") != -1) System.exit(0);
					if(s.indexOf("query") != -1){
						n_or_q = true;	//モードの切り替え
					}else{
					String[] data = {"","",""};
					//フレーム名の確保
					HashMap<String,AIFrame>temp = new HashMap<String,AIFrame>();
					temp = fs.solution();
					ArrayList<String> keylist = new ArrayList<String>(temp.keySet());
					//フレーム名を格納
					for(int i = 0;i < keylist.size();i++){
						if(s.indexOf(keylist.get(i)) != -1) data[0] = keylist.get(i);
					}
					if(s.indexOf("健太") != -1) data[0] ="Kenta";
					if(s.indexOf("知道") != -1) data[0] ="Tomomichi";
					if(s.indexOf("大輔") != -1) data[0] ="Daisuke";
					if(s.indexOf("涼太") != -1) data[0] ="Ryota";
					if(s.indexOf("広大") != -1) data[0] ="Kodai";
					if(s.indexOf("人間") != -1 || s.indexOf("人類") != -1) data[0] ="human";
					if(data[0] =="")data[0] = "?x";


					//スロットの値を格納
					//体重に関しての質問
					if(s.indexOf("体重") != -1){
						data[1] = "weight";
						int index = s.indexOf("体重") + "体重".length();
						if((s.length() - s.substring(0,index).length()) > 1){//「体重」の後に2文字続いているか
						data[2] = s.substring(index, index +2);
						if(!isNumber(data[2]))data[2] = "?体重";//体重の後ろの文字が2ケタの数字でないなら
						}else data[2] = "?体重";
					}
					//身長に関しての質問
					if(s.indexOf("身長") != -1){
						data[1] = "height";
						int index = s.indexOf("身長") + "身長".length();
						if((s.length() - s.substring(0,index).length()) > 2){//「身長」の後に3文字続いているか
							data[2] = s.substring(index, index +3);
							if(!isNumber(data[2]))data[2] = "?身長";//身長の後ろの文字が2ケタの数字でないなら
						}else data[2] = "?身長";
					}
					//担当課題に関しての質問
					if(s.indexOf("課題") != -1 || s.indexOf("担当") != -1){
						data[1] = "task";
						int index = s.indexOf("課題") + "課題".length();
						if((s.length() - s.substring(0,index).length()) > 2){//「課題」の後に3文字続いているか
							data[2] = s.substring(index, index +3);
							if(!isNumber(data[2].substring(0,1)) || !isNumber(data[2].substring(3-1)))data[2] = "?課題";////課題の後ろの文字が○-○でないなら
						}else data[2] = "?課題";
					}
					new Frame().query(data);
					}
				}
			}
		}catch(IOException e){

			System.out.println("Exception :" + e);
		}
	}


	void query(String[] string){
		HashMap<String,AIFrame>temp = new HashMap<String,AIFrame>();
		temp = fs.solution();
		ArrayList<String> keylist = new ArrayList<String>(temp.keySet());
		slotlist = new ArrayList<String>();
		for(int i=0;i<keylist.size();i++){
			AIFrame frame = (AIFrame)temp.get(keylist.get(i));
			ArrayList<String>slot = new ArrayList<String>();
			Iterator supers = frame.getSupers();
			while(supers != null && supers.hasNext() == true){
				frame = (AIFrame) supers.next();
				slot = frame.solution();
				for(int j=0;j<slot.size();j++){
					if(!slotlist.contains(slot.get(j))) slotlist.add(slot.get(j));
				}
				supers = frame.getSupers();
			}
		}
		if(string.length == 3 && slotlist.contains(string[1])){
			if( var(string[0]) && var(string[2])) varquery3(string);
			else if( var(string[0]) && !var(string[2])) varquery1(string);
			else if(!var(string[0]) && var(string[2])) varquery2(string);
			else{
				System.out.println(matching(string));
				return;
			}
		}else{
			System.out.println("error");
			return;
		}
	}

	void varquery1(String[] string){
		HashMap<String,AIFrame>temp = new HashMap<String,AIFrame>();
		temp = fs.solution();
		ArrayList<String> keylist = new ArrayList<String>(temp.keySet());
		for(int i=0;i<keylist.size();i++){
			if(!keylist.get(i).equals("top_level_frame") && fs.readSlotValue(keylist.get(i), string[1],false) != null){
				if(fs.readSlotValue(keylist.get(i), string[1],false).equals(string[2])){
					System.out.println(keylist.get(i)+" "+string[1]+" "+string[2]);
				}
			}
		}
	}


	void varquery2(String[] string){
		HashMap<String,AIFrame>temp = new HashMap<String,AIFrame>();
		temp = fs.solution();
		AIFrame frame = (AIFrame)temp.get(string[0]);
		System.out.println(string[0]+" "+string[1]+" "+fs.readSlotValue(string[0], string[1],false));
	}


	void varquery3(String[] string){
		HashMap<String,AIFrame>temp = new HashMap<String,AIFrame>();
		temp = fs.solution();
		ArrayList<String> keylist = new ArrayList<String>(temp.keySet());
		for(int i=0;i<keylist.size();i++){
			if(!keylist.get(i).equals("top_level_frame") && fs.readSlotValue(keylist.get(i), string[1],false) != null){
				AIFrame frame = (AIFrame)temp.get(keylist.get(i));
				ArrayList<String>slot = new ArrayList<String>();
				slot = frame.solution();
				for(int j=0;j<slot.size();j++){
					if(slot.get(j).equals(string[1])){
						System.out.println(keylist.get(i)+" "+string[1]+" "+fs.readSlotValue(keylist.get(i), string[1],false));
					}
				}
			}
		}
	}



	//変数を含まない文のマッチング
	boolean matching(String[] string){
		HashMap<String,AIFrame>temp = new HashMap<String,AIFrame>();
		temp = fs.solution();
		ArrayList<String> keylist = new ArrayList<String>(temp.keySet());
		for(int i=0;i<keylist.size();i++){
			if(!keylist.get(i).equals("top_level_frame") && fs.readSlotValue(keylist.get(i), string[1],false) != null){
				if(keylist.get(i).equals(string[0]) && fs.readSlotValue(keylist.get(i),string[1],false).equals(string[2])){
					return true;
				}
			}
		}
		return false;
	}

	boolean var(String str1){
		// 先頭が ? なら変数
		return str1.startsWith("?");
	}

	static boolean isNumber(String val) {
		try {
			Integer.parseInt(val);
			return true;
		} catch (NumberFormatException nfex) {
			return false;
		}
	}

}
