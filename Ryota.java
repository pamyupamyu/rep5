<?
//��������������������//
import java.util.*;

/***
 * Semantic Net �̎g�p��
 */
public class Example {
    public static void main(String args[]){
	SemanticNet sn = new SemanticNet();

	// �T�C�N�����O�̓X�|�[�c�ł���D
	sn.addLink(new Link("is-a","cycling","sports",sn));

	// �����͖��É��H�Ƒ�w�̊w���ł���D
	sn.addLink(new Link("is-a","Ryota","NIT-student",sn));

	// �����̐��͐l�H�m�\�ł���D
	sn.addLink(new Link("speciality","Ryota","AI",sn));
	
	// �G�X�P�[�v�͎��]�Ԃł���D
	sn.addLink(new Link("is-a","Escape","bicycle",sn));

	// ���]�Ԃ̓y�_�������D
	sn.addLink(new Link("has-a","bicycle","pedal",sn));
	
	// �����̎�͂��G�����ł���D
	sn.addLink(new Link("hobby","Ryota","Drawing",sn));
	
	// �����̎�̓T�C�N�����O�ł���B
	sn.addLink(new Link("hobby","Ryota","cycling",sn));
	
	// �����̓G�X�P�[�v�����L����D
	sn.addLink(new Link("own","Ryota","Escape",sn));

	// ���É��H�Ƒ�w�̊w���́C�w���ł���D
	sn.addLink(new Link("is-a","NIT-student","student",sn));

	// �w���͕׋����Ȃ��D
	sn.addLink(new Link("donot","student","study",sn));
	
	// ���͗����̗F�l�ł���
	sn.addLink(new Link("is-a","Daisuke","Ryota's-friend",sn));

	// �L��͗����̗F�l�ł���
		sn.addLink(new Link("is-a","koudai","Ryota's-friend",sn));
	
	// �m���͗����̗F�l�ł���
		sn.addLink(new Link("is-a","tomomichi","Ryota's-friend",sn));
	
	// �����͗����̗F�l�ł���
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

