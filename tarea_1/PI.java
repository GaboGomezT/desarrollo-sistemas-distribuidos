import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.nio.ByteBuffer;

class PI
{
  static Object lock = new Object();
  static double pi = 0;
  static class Worker extends Thread
  {
    Socket conexion;
    Worker(Socket conexion)
    {
      this.conexion = conexion;
    }
    public void run()
    {
      try {
        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());

        double x = entrada.readDouble();
        System.out.println(x);

        synchronized(lock){
            pi += x;
        }

        salida.close();
        entrada.close();
        conexion.close();
      }
      catch (Exception e)
      {
        System.err.println(e.getMessage());
      }
    }
  }
  public static void main(String[] args) throws Exception
  {
    if (args.length != 1)
    {
      System.err.println("Uso:");
      System.err.println("java PI <nodo>");
      System.exit(0);
    }
    int nodo = Integer.valueOf(args[0]);
    System.out.println("NODO: " + nodo);
    if (nodo == 0)
    {
        ServerSocket servidor = new ServerSocket(50000);
        Worker[] workers = new Worker[4];
        for(int i = 0; i < 4; i++){
            Socket conexion = servidor.accept();
            workers[i] = new Worker(conexion);
            workers[i].start();
        }
        for(int i = 0; i < 4; i++){
            workers[i].join();
        }

        System.out.println("PI: " + pi);
    }
    else
    {
        Socket conexion = null;
    
        for(;;)
          try
          {
              conexion = new Socket("localhost",50000);
              break;
          }
          catch (Exception e)
          {
            Thread.sleep(100);
          }
    
        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());
        
        double suma  = 0;

        for(int i = 0; i < 10000000; i++){
            suma  += 4.0/(8*i+2*(nodo-2)+3); 
        }

        suma = (nodo % 2) == 0 ? -suma : suma;
        salida.writeDouble(suma);

        salida.close();
        entrada.close();
        conexion.close(); 
    }
  }
}