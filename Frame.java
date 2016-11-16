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



	public static void main(String args[]) {

		System.out.println( "Frame" );



		// �t���[���V�X�e���̏�����

		//AIFrameSystem fs = new AIFrameSystem();

		fs = new AIFrameSystem();



		// �N���X�t���[�� human �̐���

		fs.createClassFrame( "human" );

		// height �X���b�g��ݒ�

		fs.writeSlotValue( "human", "height", new String( "160" ) );

		// height ���� weight ���v�Z���邽�߂̎� weight = 0.9*(height-100) ��

		// when-requested demon �Ƃ��� weight �X���b�g�Ɋ��蓖�Ă�  

		fs.setWhenRequestedProc( "human", "weight", new AIDemonProcReadTest() );



		//member�Ƃ����N���X�t���[���쐬

		fs.createClassFrame( "human" , "member" );



		//�e�C���X�^���X�t���[����member�t���[������쐬

		fs.writeSlotValue( "member", "task", new String( "no job" ) );

		fs.createInstanceFrame( "member", "Daisuke" );

		fs.createInstanceFrame( "member", "Kenta" );

		fs.createInstanceFrame( "member", "Ryota" );

		fs.createInstanceFrame( "member", "Kodai" );

		fs.createInstanceFrame( "member", "Tomomichi" );



		//Daisuke�̃X���b�g�Ƀf�[�^������

		fs.writeSlotValue( "Daisuke", "height", new String( "172" ) );

		fs.writeSlotValue( "Kenta", "height", new String( "168" ) );

		fs.writeSlotValue( "Ryota", "height", new String( "172" ) );

		fs.writeSlotValue( "Kodai", "height", new String( "165" ) );

		fs.writeSlotValue( "Tomomichi", "height", new String( "182" ) );



		fs.writeSlotValue( "Daisuke", "task", new String( "5-2" ) );

		fs.writeSlotValue( "Kenta", "task", new String( "5-4" ) );

		fs.writeSlotValue( "Ryota", "task", new String( "5-3" ) );

		fs.writeSlotValue( "Kodai", "task", new String( "5-3" ) );

		fs.writeSlotValue( "Tomomichi", "task", new String( "5-1" ) );



		//Daisuke�̃X���b�g�̃f�[�^��ǂ�

		System.out.println("Daisuke" +  "'s height is " + fs.readSlotValue( "Daisuke", "height", false ) );

		System.out.println("Daisuke's weight is " + fs.readSlotValue( "Daisuke", "weight", false ) );

		System.out.println("Daisuke's task is " + fs.readSlotValue( "Daisuke", "task", false ) );





		//query

		System.out.println("�R�}���h����͂��Ă�������");

		try{

			while(true){

				System.out.println("Frame����Slot������(�󔒋�؂�),�I��:exit");

				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

				String s = br.readLine();

				String[] data = s.split(" ",0);

				//exit���͂ŏI��

				if(data[0].equals("exit")) System.exit(0);

				new Frame().query(data);

			}

		}catch(IOException e){

			System.out.println("Exception :" + e);

		}



	}



	/**

	 * query���s�����ʂ�\������

	 * @param string

	 */

	void query(String[] string){

		HashMap<String,AIFrame>temp = new HashMap<String,AIFrame>();

		temp =  fs.solution();

		ArrayList<String> keylist = new ArrayList<String>(temp.keySet());

		//���͂����\���̂���X���b�g����slotlist�ɕۑ�

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

		//�������򂵂�query

		if(slotlist.contains(string[1])){

			if( var(string[0]) &&  var(string[2])) varquery3(string);

			else if( var(string[0]) && !var(string[2])) varquery1(string);

			else if(!var(string[0]) &&  var(string[2])) varquery2(string);

			else{

				System.out.println(matching(string));

				return;

			}

		}else{

			System.out.println("error");

			return;

		}

	}



	/**

	 * �t���[������ϐ��œn���ꂽ�ꍇ��matching����t���[������\������

	 * 

	 */

	void varquery1(String[] string){

		HashMap<String,AIFrame>temp = new HashMap<String,AIFrame>();

		temp =  fs.solution();

		ArrayList<String> keylist = new ArrayList<String>(temp.keySet());

		for(int i=0;i<keylist.size();i++){

			if(!keylist.get(i).equals("top_level_frame") && fs.readSlotValue(keylist.get(i), string[1],false) != null){

				if(fs.readSlotValue(keylist.get(i), string[1],false).equals(string[2])){

					System.out.println(keylist.get(i)+" "+string[1]+" "+string[2]);

				}

			}

		}

	}

	

	/**

	 * �X���b�g�l��ϐ��Ƃ��ēn���ꂽ�ꍇ��matching����t���[���l��\������

	 * @param string

	 */

	void varquery2(String[] string){

		HashMap<String,AIFrame>temp = new HashMap<String,AIFrame>();

		temp =  fs.solution();

		AIFrame frame = (AIFrame)temp.get(string[0]);

		System.out.println(string[0]+" "+string[1]+" "+fs.readSlotValue(string[0], string[1],false));

	}

	

	/**

	 * �t���[�����ƃX���b�g�l��ϐ��Ƃ��ēn���ꂽ�ꍇ��matching����t���[�����ƃX���b�g�l��\������

	 * @param string

	 */

	void varquery3(String[] string){

		HashMap<String,AIFrame>temp = new HashMap<String,AIFrame>();

		temp =  fs.solution();

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



	/**

	 * �ϐ����܂܂�Ă��Ȃ�����n���ꂽ�ꍇ�ɐ�����������Ԃ�

	 * @param string

	 * @return

	 */

	boolean matching(String[] string){

		HashMap<String,AIFrame>temp = new HashMap<String,AIFrame>();

		temp =  fs.solution();

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

		// �擪�� ? �Ȃ�ϐ�

		return str1.startsWith("?");

	}





}