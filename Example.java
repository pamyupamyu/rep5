package Semantic_pac;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Example {

public static void write_text(String args[]){
		 try{
		      File file = new File("test2.txt");

		      if (checkBeforeWritefile(file)){
		        FileWriter filewriter = new FileWriter(file, true);

		        filewriter.write("\r\n");
		        filewriter.write(args[1]);
		        filewriter.write("\r\n");
		        filewriter.write(args[2]);
		        filewriter.write("\r\n");
		        filewriter.write(args[3]);

		        filewriter.close();
		      }else{
		        System.out.println("ファイルに書き込めません");
		      }
		    }catch(IOException e){
		      System.out.println(e);
		    }
		  }

 private static boolean checkBeforeWritefile(File file){
		    if (file.exists()){
		      if (file.isFile() && file.canWrite()){
		        return true;
		      }
		    }

		    return false;
		  }

public static String[] readt(){
			String[] str2 = new String[1000]; //現状拡張する幅がわからない
			try{
				  File file = new File("test2.txt");
				  BufferedReader br = new BufferedReader(new FileReader(file));
				  int k =0;
				  String str;

				  while((str = br.readLine()) != null){
					  str2[k]=str;
					  k++;
				    //System.out.println(str);
				  }
				  br.close();
				}catch(FileNotFoundException e){
				  System.out.println(e);
				}catch(IOException e){
				  System.out.println(e);
				}
			return(str2);
		}

public static void delete_text(){
	 try{
	      File file = new File("test2.txt");

	      if (checkBeforeWritefile(file)){
	        FileWriter filewriter = new FileWriter(file);

	        filewriter.close();
	      }else{
	        System.out.println("ファイルに書き込めません");
	      }
	    }catch(IOException e){
	      System.out.println(e);
	    }
}

public static String[] template(String args[]){
	//Who is a students

}

	   public static void main(String args[]){
			SemanticNet sn = new SemanticNet();

			String data[] = readt();
			int n=0;
			boolean n_check=true;
			for(int i=0;i<1000;i++){
				if(data[i]==null&&n_check){
					n=i;
					n_check=false;
				}
			}

			if(args[0].equals("w")){
				 write_text(args);
				 //example [w friends Shinkai Suzuki]
			 }else if(args[0].equals("delete_all")){
				 delete_text();
				 //全部消えるので注意
			 }else if(args[0].equals("do")){


			for(int i=0;i<n/3;i++){
				sn.addLink(new Link(data[3*i],data[3*i+1],data[3*i+2],sn));
			}

			sn.printLinks(); //linkの表示
			sn.printNodes(); //nodeの表示

			}else if(args[0].equals("q")){
			//example[q 1 friends Shinkai ?y]

				for(int i=0;i<n/3;i++){
					sn.addLink(new Link(data[3*i],data[3*i+1],data[3*i+2],sn));
				}
				ArrayList<Link> query = new ArrayList<Link>();
/*
				query.add(new Link("like","?x","Rockman"));
				query.add(new Link("is-a","?x","NIT-student"));
				query.add(new Link("friends","?x","?y"));
				sn.query(query);
*/
				int m = Integer.parseInt(args[1]);
				for(int i=0;i<m;i++){
				query.add(new Link(args[i*3+2],args[i*3+3],args[i*3+4]));
				}
				sn.query(query);
			}else if(args[0].equals("?")){







			}
		    }//main
}//class
