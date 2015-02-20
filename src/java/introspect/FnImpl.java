package introspect;

import clojure.lang.AFn;

public class FnImpl extends AFn {
  @Override
  public Object invoke(Object arg1, Object arg2) {
    return 2;
  }
}