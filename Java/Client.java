import java.io.*;
import java.net.*;


public class Client {

  /**
   * This method name and parameters must remain as-is
   */
  public static int add(int lhs, int rhs) {
    int result = 0;

    try (
      Socket socket = new Socket("localhost", 10314);
      ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
    ) {

      // Create the serializable object
      Remote add = new Remote("add", new Object[]{lhs, rhs});

      // Send the object request to the server
      oos.writeObject(add);
      oos.flush();

      // Recieve and deserialize response
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      result = (int) ois.readObject();

    } catch (Exception e) {
      System.out.println("Server not running. Error: " + e.toString());
    }

    return result;
  }


  /**
   * This method name and parameters must remain as-is
   */
  public static int divide(int num, int denom) {
    int result = 0;

    try (
      Socket socket = new Socket("localhost", 10314);
      ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
    ) {

      // Create the serializable object
      Remote divide = new Remote("divide", new Object[]{num, denom});

      // Send the object request to the server
      oos.writeObject(divide);
      oos.flush();

      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

      String status = (String) ois.readObject();
      if (status.equals("success")) {
        result = (int) ois.readObject();

      } else {
        ArithmeticException error = (ArithmeticException) ois.readObject();
        throw new ArithmeticException();
      }

    } catch (ArithmeticException ae) {
      throw new ArithmeticException();

    } catch (Exception e) {
      System.out.println("Server not running. Error: " + e.toString());
    }
    return result;
  }


  /**
   * This method name and parameters must remain as-is
   */
  public static String echo(String message) {
    String result = "";

    try (
      Socket socket = new Socket("localhost", 10314);
      ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
    ) {

        // Create the serializable object
        Remote echo = new Remote("echo", new Object[]{message});

        // Send the object request to the server
        oos.writeObject(echo);
        oos.flush();

      // Recieve and deserialize response
      try (
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      ) {
        result = (String) ois.readObject();

      } catch (Exception e) {
        e.printStackTrace();
      }

    } catch (Exception e) {
      System.out.println("Server not running. Error: " + e.toString());
    }

    return result;
  }

  // Do not modify any code below this line
  // --------------------------------------
  String server = "localhost";
  public static final int PORT = 10314;

  public static void main(String... args) {
    // All of the code below this line must be uncommented
    // to be successfully graded.
    System.out.print("Testing... ");

    if (add(2, 4) == 6)
      System.out.print(".");
    else
      System.out.print("X");

    try {
      divide(1, 0);
      System.out.print("X");
    }
    catch (ArithmeticException x) {
      System.out.print(".");
    }

    if (echo("Hello").equals("You said Hello!"))
      System.out.print(".");
    else
      System.out.print("X");

    System.out.println(" Finished");
  }
}
