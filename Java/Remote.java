import java.io.Serializable;


public class Remote implements Serializable {
  private String methodName;
  private Object[] args;

  public Remote(String methodName, Object[] args) {
    this.methodName = methodName;
    this.args = args;
  }

  public String getMethodName() {
    return methodName;
  }

  public Object[] getArgs() {
    return args;
  }
}
