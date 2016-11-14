/*
 AIDemonProcReadTest.java
  すべての種類のデモン手続きのスーパークラス

  when-read procedure は，スロット値を Iterator として
  返さなけらばならない
*/

import java.util.*;

class AIDemonProcReadTest extends AIDemonProc {

public
Object eval(
 AIFrameSystem inFrameSystem,
 AIFrame inFrame,
 String inSlotName,
 Iterator inSlotValues,
 Object inOpts )
{
 Object height = inFrame.readSlotValue( inFrameSystem, "height", false );
 String s = height.toString();
 if ( s instanceof String ) {
  int h = Integer.parseInt(s);
  int i = (int)( 0.9 * (h - 100));
  return AIFrame.makeEnum( new String(String.valueOf(i)) );
 }
 return null;
}

}