import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.nio.ByteBuffer;

class Servidor
{
  // lee del DataInputStream todos los bytes requeridos

  static void read(DataInputStream f,byte[] b,int posicion,int longitud) throws Exception
  {
    while (longitud > 0)
    {
      int n = f.read(b,posicion,longitud);
      posicion += n;
      longitud -= n;
    }
  }

  public static void main(String[] args) throws Exception
  {
    ServerSocket servidor = new ServerSocket(50000);

    Socket conexion = servidor.accept();

    DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
    DataInputStream entrada = new DataInputStream(conexion.getInputStream());

    // recibe un entero de 32 bits
    int n = entrada.readInt();
    System.out.println(n);

    // recibe un numero punto flotante
    double x = entrada.readDouble();
    System.out.println(x);

    // recibe mil números punto flotante
    long startTime = System.currentTimeMillis();
    double[] flotantes = new double[10000];
    for (int i = 0; i < 10000; i++) {
      flotantes[i] = entrada.readDouble();
    }
    long endTime = System.currentTimeMillis();
    System.out.println("Tiempo ejecución(recibo punto flotantes con readDouble): " + (endTime - startTime));

    // recibe una cadena
    byte[] buffer = new byte[4];
    read(entrada,buffer,0,4);
    System.out.println(new String(buffer,"UTF-8"));

    // envia una cadena
    salida.write("HOLA".getBytes());

    // recibe 5 numeros punto flotante
    byte[] a = new byte[5*8];
    read(entrada,a,0,5*8);
    ByteBuffer b = ByteBuffer.wrap(a);
    for (int i = 0; i < 5; i++)
      System.out.println(b.getDouble());    


    // recibe 10mil números punto flotante
    startTime = System.currentTimeMillis();
    byte[] flotanteEntrada = new byte[10000*8];
    read(entrada, flotanteEntrada, 0, 10000*8);
    ByteBuffer arregloFlotantes = ByteBuffer.wrap(flotanteEntrada);
    for (int i = 0; i <= 10; i++) {
      System.out.println(arregloFlotantes.getDouble());
    }
    endTime = System.currentTimeMillis();
    System.out.println("Tiempo ejecución(recibe punto flotantes con BytBuffer): " + (endTime - startTime));

    salida.close();
    entrada.close();
    conexion.close();
  }
}