package introspect;

import clojure.lang.AFn;
import clojure.lang.IFn;
import clojure.lang.ISeq;
import clojure.lang.Util;
import clojure.lang.RT;
import clojure.lang.ArityException;
import clojure.lang.Compiler;

public class ApplyHelper {

  static public Object applyToHelper(IFn ifn, ISeq arglist) {
    switch(RT.boundedLength(arglist, 20))
      {
      case 0:
        arglist = null;
        return ifn.invoke();
      case 1:
        return ifn.invoke(Util.ret1(arglist.first(),arglist = null));
      case 2:
        return ifn.invoke(arglist.first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      case 3:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      case 4:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      case 5:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      case 6:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      case 7:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      case 8:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      case 9:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      case 10:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      case 11:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      case 12:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      case 13:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      case 14:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      case 15:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      case 16:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      case 17:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      case 18:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      case 19:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      case 20:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , Util.ret1((arglist = arglist.next()).first(),arglist = null)
                          );
      default:
        return ifn.invoke(arglist.first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , (arglist = arglist.next()).first()
                          , RT.seqToArray(Util.ret1(arglist.next(),arglist = null)));

      }
	}
}
