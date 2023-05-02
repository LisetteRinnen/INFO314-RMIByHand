import java.io.*;
import java.net.*;


public class Server {


  public static void main (String... args) {
    try (
      ServerSocket server = new ServerSocket(10314);
    ) {
      Socket socket = null;
      while ((socket = server.accept()) != null) {

        try (
          ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
          ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ) {
          Remote request = (Remote) ois.readObject();

          if (request.getMethodName().equals("echo")) {
            String message = (String) request.getArgs()[0];
            String result = echo(message);
            oos.writeObject(result);
            oos.flush();

          } else if (request.getMethodName().equals("add")) {
            int lhs = (int) request.getArgs()[0];
            int rhs = (int) request.getArgs()[1];
            int result = add(lhs, rhs);
            oos.writeObject(result);
            oos.flush();

          } else if (request.getMethodName().equals("divide")) {
            int num = (int) request.getArgs()[0];
            int denom = (int) request.getArgs()[1];

            try {
              int result = divide(num, denom);
              oos.writeObject("success");
              oos.writeObject(result);
              oos.flush();

            } catch (ArithmeticException e) {
              ArithmeticException error = e;
              oos.writeObject("fail");
              oos.writeObject(error);
              oos.flush();
            }

          }

        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Do not modify any code below tihs line
  // --------------------------------------
  public static String echo(String message) {
    return "You said " + message + "!";
  }
  public static int add(int lhs, int rhs) {
    return lhs + rhs;
  }
  public static int divide(int num, int denom) {
    if (denom == 0)
      throw new ArithmeticException();

    return num / denom;
  }
}
