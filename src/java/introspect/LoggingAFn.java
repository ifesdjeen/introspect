package introspect;

import clojure.lang.AFn;
import clojure.lang.IFn;
import clojure.lang.ISeq;
import clojure.lang.Util;
import clojure.lang.RT;
import clojure.lang.ArityException;
import clojure.lang.Compiler;

public abstract class LoggingAFn implements IFn {

  public Object call() {
    return invoke();
  }

  public void run(){
    invoke();
  }

  public Object invoke() {
    System.out.println(1);
    return throwArity(0);
  }

  public Object invoke(Object arg1) {
    System.out.println(arg1);
    return throwArity(1);
  }

  public Object invoke(Object arg1, Object arg2) {
    System.out.println(arg1);
    System.out.println(arg2);
    return throwArity(2);
  }

  public Object invoke(Object arg1, Object arg2, Object arg3) {
    System.out.println(arg1);
    System.out.println(arg2);
    System.out.println(arg3);
    return throwArity(3);
  }

  public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4) {
    System.out.println(arg1);
    System.out.println(arg2);
    System.out.println(arg3);
    return throwArity(4);
  }

  public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
		    System.out.println(arg1);
    System.out.println(arg2);
    System.out.println(arg3);
    return throwArity(5);
  }

  public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) {
		    System.out.println(arg1);
    System.out.println(arg2);
    System.out.println(arg3);
    return throwArity(6);
  }

  public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7)
  {
		    System.out.println(arg1);
    System.out.println(arg2);
    System.out.println(arg3);
    return throwArity(7);
  }

  public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                       Object arg8) {
		    System.out.println(arg1);
    System.out.println(arg2);
    System.out.println(arg3);
    return throwArity(8);
  }

  public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                       Object arg8, Object arg9) {
		    System.out.println(arg1);
    System.out.println(arg2);
    System.out.println(arg3);
    return throwArity(9);
  }

  public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                       Object arg8, Object arg9, Object arg10) {
		    System.out.println(arg1);
    System.out.println(arg2);
    System.out.println(arg3);
    return throwArity(10);
  }

  public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                       Object arg8, Object arg9, Object arg10, Object arg11) {
		    System.out.println(arg1);
    System.out.println(arg2);
    System.out.println(arg3);
    return throwArity(11);
  }

  public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                       Object arg8, Object arg9, Object arg10, Object arg11, Object arg12) {
		    System.out.println(arg1);
    System.out.println(arg2);
    System.out.println(arg3);
    return throwArity(12);
  }

  public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                       Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13)
  {
    return throwArity(13);
  }

  public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                       Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14)
  {
    return throwArity(14);
  }

  public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                       Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                       Object arg15) {
    return throwArity(15);
  }

  public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                       Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                       Object arg15, Object arg16) {
    return throwArity(16);
  }

  public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                       Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                       Object arg15, Object arg16, Object arg17) {
    return throwArity(17);
  }

  public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                       Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                       Object arg15, Object arg16, Object arg17, Object arg18) {
    return throwArity(18);
  }

  public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                       Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                       Object arg15, Object arg16, Object arg17, Object arg18, Object arg19) {
    return throwArity(19);
  }

  public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                       Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                       Object arg15, Object arg16, Object arg17, Object arg18, Object arg19, Object arg20)
  {
    return throwArity(20);
  }


  public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
                       Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14,
                       Object arg15, Object arg16, Object arg17, Object arg18, Object arg19, Object arg20,
                       Object... args)
  {
    return throwArity(21);
  }

  public Object applyTo(ISeq arglist) {
    return applyToHelper(this, Util.ret1(arglist,arglist = null));
  }

  static public Object applyToHelper(IFn ifn, ISeq arglist) {
		return ApplyHelper.applyToHelper(ifn, arglist);
  }

  public Object throwArity(int n){
    String name = getClass().getSimpleName();
    throw new ArityException(n, Compiler.demunge(name));
  }
}
