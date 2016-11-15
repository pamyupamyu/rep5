/*
 AIFrame.java

 */

import java.util.*;

abstract
class AIFrame {

	private boolean mIsInstance;
	private String mName;
	private HashMap<String,AISlot> mSlots = new HashMap<String,AISlot>();
	private AIWhenConstructedProc mWhenConstructedProc = null;

	/*
	 * AIFrame
	 *  コンストラクタ
	 */
	AIFrame( AIFrameSystem inFrameSystem,
			AIClassFrame inSuperFrame,
			String inName,
			boolean inIsInstance )
			{
		mName = inName;
		mIsInstance = inIsInstance;
		if ( inSuperFrame != null )
			setSlotValue( getSuperSlotName(), inSuperFrame );
		evalWhenConstructedProc( inFrameSystem, this );
			}


	/*
	 * AIFrame
	 *  コンストラクタ
	 */
	AIFrame( AIFrameSystem inFrameSystem,
			Iterator inSuperFrames,
			String inName,
			boolean inIsInstance )
			{
		mName = inName;
		mIsInstance = inIsInstance;
		while ( inSuperFrames.hasNext() == true ) {
			AIFrame frame = (AIFrame) inSuperFrames.next();
			addSlotValue( getSuperSlotName(), frame );
		}
		evalWhenConstructedProc( inFrameSystem, this );
			}


	/*
	 * setWhenConstructedProc
	 *  when-constructed proc を登録
	 */
	public
	void setWhenConstructedProc( AIWhenConstructedProc inProc ) {
		mWhenConstructedProc = inProc;
	}


	/*
	 * getWhenConstructedProc
	 *  when-constructed proc を返す
	 */
	public
	AIWhenConstructedProc getWhenConstructedProc() {
		return mWhenConstructedProc;
	}


	/*
	 * evalWhenConstructedProc
	 *  when-constructed proc を評価
	 */
	void evalWhenConstructedProc(
			AIFrameSystem inFrameSystem,
			AIFrame inFrame )
	{
		Iterator supers = getSupers();
		if ( supers != null ) {
			while ( supers.hasNext() == true ) {
				AIClassFrame frame = (AIClassFrame) supers.next();
				frame.evalWhenConstructedProc( inFrameSystem, inFrame );
			}
		}
		if ( mWhenConstructedProc != null )
			mWhenConstructedProc.eval( inFrameSystem, inFrame );
	}


	/*
	 * isInstance
	 *  このフレームがインスタンスフレームなら true を返す
	 */
	public boolean isInstance() { return mIsInstance; }


	/*
	 * getSupers
	 *  このフレームのスーパーフレームを返す
	 */
	public
	Iterator getSupers() {
		return getSlotValues( getSuperSlotName() );
	}


	/**
	 * readSlotValue
	 *  スロット inSlotName に格納されているスロット値を返す．
	 *  複数のスロット値が格納されているときは，最初のオブジェクトを返す．
	 *
	 *  スロット値の優先度
	 *   1. 自分の when-requested procedure
	 *   2. スーパークラスの when-requested procedure
	 *   3. 自分の when-read procedure
	 *   4. スーパークラスの when-read procedure
	 *   5. 自分のスロット値
	 *   6. スーパークラスのスロット値
	 */
	public
	Object
	readSlotValue(
			AIFrameSystem inFrameSystem,
			String inSlotName,
			boolean inDefault )
	{
		return getFirst(
				readSlotValues( inFrameSystem, inSlotName, inDefault ) );
	}


	/**
	 * readSlotValues
	 *  スロット inSlotName に格納されているスロット値を返す．
	 */
	public Iterator readSlotValues(
			AIFrameSystem inFrameSystem,
			String inSlotName,
			boolean inDefault )
	{
		Iterator obj = null;

		if ( inDefault == false ) { //falseだったら自分のフレームのスロットを使う。
			AISlot slot = getSlot( inSlotName );
			if ( slot != null )
				obj = slot.getSlotValues();
		}

		if ( obj == null ) //自分のフレームに目当てのスロットがないならスーパーを見ていく
			obj = readSlotValuesWithWhenRequestedProc(
					inFrameSystem, inSlotName );

		if ( obj == null ) {
			Iterator supers = getSupers();
			//Iterator home = supers; //一番最初のスーパークラスの保存
			while (supers != null && supers.hasNext() == true) {
				AIClassFrame frame = (AIClassFrame) supers.next();
				obj = frame.getSlotValues( inSlotName );
				if ( obj != null )
					break;
				//追加内容
				supers = frame.getSupers(); //superの更新
				//ここまで
			}
			//supers = home; //一番最初のスーパークラスに戻す
		}
		//System.out.println("obj:" + obj); //nullだとダメ
		return readSlotValuesWithWhenReadProc(
				inFrameSystem, inSlotName, obj );
	}


	/**
	 * readSlotValuesWithWhenRequestedProc
	 *  スロット inSlotName に格納されているスロット値を返す．
	 */
	Iterator readSlotValuesWithWhenRequestedProc(
			AIFrameSystem inFrameSystem,
			String inSlotName )
	{
		return readSlotValuesWithWhenRequestedProc(
				inFrameSystem, this, inSlotName );
	}

	protected
	Iterator readSlotValuesWithWhenRequestedProc(
			AIFrameSystem inFrameSystem,
			AIFrame inFrame,
			String inSlotName )
	{
		Iterator obj = null;
		AISlot slot = getSlot( inSlotName );

		obj = evalWhenRequestedProc(
				inFrameSystem, inFrame, slot, inSlotName );
		if ( obj != null )
			return obj;

		Iterator supers = getSupers();
		//if ( supers != null ) {
			while (supers != null && supers.hasNext() == true ) {
				AIClassFrame frame = (AIClassFrame) supers.next();
				//System.out.println(frame);
				slot = frame.getSlot( inSlotName ); //frameの中のslotにweightが見つからないからダメ
				obj = frame.evalWhenRequestedProc(
						inFrameSystem, inFrame, slot, inSlotName );
				if ( obj != null )
					return obj;
				//追加内容
				supers = frame.getSupers(); //superの更新
					//ここまで
				}
			//}

		return null;
	}

	protected
	Iterator evalWhenRequestedProc(
			AIFrameSystem inFrameSystem,
			AIFrame inFrame,
			AISlot inSlot,
			String inSlotName )
	{
		//ここがずっとnullだからダメ
		/*
	if(inSlot == null)
		System.out.println("inSlot is null");
	else if(inSlot.getWhenRequestedProc() == null)
		System.out.println("inSlot.getWhenRequestedProc() is null");
		 */
		if ( inSlot != null && inSlot.getWhenRequestedProc() != null ) {
			AIDemonProc demon = inSlot.getWhenRequestedProc();
			//System.out.println("In"); //追加
			if ( demon != null ){
				//System.out.println("In"); //追加
				return (Iterator) demon.eval(
						inFrameSystem, inFrame, inSlotName, null );
			}
		}
		return null;
	}


	/**
	 * readSlotValuesWithWhenReadProc
	 *  スロット inSlotName に格納されているスロット値を返す．
	 */
	Iterator
	readSlotValuesWithWhenReadProc(
			AIFrameSystem inFrameSystem,
			String inSlotName,
			Iterator inSlotValue )
	{
		return readSlotValuesWithWhenReadProc(
				inFrameSystem, this, inSlotName, inSlotValue );
	}

	protected
	Iterator readSlotValuesWithWhenReadProc(
			AIFrameSystem inFrameSystem,
			AIFrame inFrame,
			String inSlotName,
			Iterator inSlotValue )
	{
		AISlot slot;

		Iterator supers = getSupers();
		if ( supers != null ) {
			while ( supers.hasNext() == true ) {
				AIClassFrame frame = (AIClassFrame) supers.next();
				slot = frame.getSlot( inSlotName );
				inSlotValue =
						frame.evalWhenReadProc(
								inFrameSystem, inFrame, slot, inSlotName, inSlotValue );
			}
		}

		slot = getSlot( inSlotName );
		return evalWhenReadProc( inFrameSystem, inFrame,
				slot, inSlotName, inSlotValue );
	}

	protected
	Iterator evalWhenReadProc(
			AIFrameSystem inFrameSystem,
			AIFrame inFrame,
			AISlot inSlot,
			String inSlotName,
			Iterator inSlotValue )
	{
		if ( inSlot != null && inSlot.getWhenReadProc() != null ) {
			AIDemonProc demon = inSlot.getWhenReadProc();
			if ( demon != null )
				inSlotValue =
				(Iterator) demon.eval( inFrameSystem, inFrame,
						inSlotName, inSlotValue );
		}

		return inSlotValue; 
	}

	/**
	 * writeSlotValue
	 *  スロット inSlotName にスロット値 inSlotValue を設定する．
	 */
	public
	void
	writeSlotValue(
			AIFrameSystem inFrameSystem,
			String inSlotName,
			Object inSlotValue )
	{
		AISlot slot = getSlot( inSlotName );
		if ( slot == null ) {
			slot = new AISlot();
			mSlots.put( inSlotName, slot );
		}

		slot.setSlotValue( inSlotValue );

		writeSlotValueWithWhenWrittenProc(
				inFrameSystem, inSlotName, inSlotValue );
	}


	void writeSlotValueWithWhenWrittenProc(
			AIFrameSystem inFrameSystem,
			String inSlotName,
			Object inSlotValue )
	{
		Iterator supers = getSupers();
		if ( supers != null ) {
			while ( supers.hasNext() == true ) {
				AIClassFrame frame = (AIClassFrame) supers.next();
				frame.writeSlotValueWithWhenWrittenProc(
						inFrameSystem, inSlotName, inSlotValue );
			}
		}

		AISlot slot = getSlot( inSlotName );
		if ( slot != null ) {
			AIDemonProc demon = slot.getWhenWrittenProc();
			if ( demon != null )
				demon.eval( inFrameSystem, this,
						inSlotName, makeEnum( inSlotValue ) );  
		}
	}


	// ----------------------------------------------------------------------
	public
	Object getSlotValue( String inSlotName ) {
		Iterator iter = getSlotValues( inSlotName );
		if ( iter != null && iter.hasNext() == true )
			return iter.next();
		return null;
	}

	public
	Iterator getSlotValues( String inSlotName ) {
		AISlot slot = getSlot( inSlotName );
		if ( slot == null )
			return null;
		return slot.getSlotValues();
	}

	public
	void setSlotValue( String inSlotName, Object inSlotValue ) {
		AISlot slot = getSlot( inSlotName );
		if ( slot == null ) {
			slot = new AISlot();
			mSlots.put( inSlotName, slot );
		}
		slot.setSlotValue( inSlotValue );
	}

	public
	void addSlotValue( String inSlotName, Object inSlotValue ) {
		AISlot slot = getSlot( inSlotName );
		if ( slot == null ) {
			slot = new AISlot();
			mSlots.put( inSlotName, slot );
		}
		slot.addSlotValue( inSlotValue );
	}

	public
	void removeSlotValue( String inSlotName, Object inSlotValue ) {
		AISlot slot = getSlot( inSlotName );
		if ( slot != null )
			slot.removeSlotValue( inSlotValue );
	}

	public
	void setDemonProc(
			int inType,
			String inSlotName,
			AIDemonProc inDemonProc )
	{
		AISlot slot = getSlot( inSlotName );
		if ( slot == null ) {
			slot = new AISlot();
			mSlots.put( inSlotName, slot ); 
		}
		slot.setDemonProc( inType, inDemonProc );
	}


	// ------------------------------------------------------------------
	// utils
	// ------------------------------------------------------------------

	/*
	 * getSuperSlotName
	 *  スーパーフレームを格納しているスロットの名前を返す．
	 */
	String getSuperSlotName() {
		if ( isInstance() == true )
			return "is-a";
		return "ako";
	}

	/*
	 * getSlot
	 *  スロット名が inSlotName であるスロットを返す．
	 */
	AISlot getSlot( String inSlotName ) {
		return (AISlot) mSlots.get( inSlotName );
	}


	/*
	 * getFirst
	 *  inEnum 中の最初のオブジェクトを返す
	 */
	public static
	Object getFirst( Iterator inEnum ) {
		if ( inEnum != null && inEnum.hasNext() == true )
			return inEnum.next();
		return null;
	}


	/*
	 * makeEnum
	 *
	 */
	public static
	Iterator makeEnum( Object inObj ) {
		ArrayList list = new ArrayList();
		list.add( inObj );
		return list.iterator();
	}
	
	ArrayList<String> solution(){
		HashMap temp = new HashMap<String,AISlot>();
		temp.putAll(mSlots);
		ArrayList<String> ans = new ArrayList<String>(temp.keySet());
		
		return ans;
	}
	

} // end of class definition