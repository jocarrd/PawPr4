package paw.bd;

import java.util.Arrays;

/**
 * Los objetos de esta clase proporcionan los datos necesarios para confeccionar
 * un menú de paginación de un listado resultado de una búsqueda. Se inicializa
 * a partir del número de registros encontrados en la búsqueda y el tamaño de
 * cada página.
 * <p>
 * Se obtiene un objeto de este tipo como resultado de llamar al método
 * paw.bd.GestorBD.getPaginadorArticulos.
 * <p>
 * El objeto dispone de métodos para saber
 * <ul>
 * <li>El número de resultados obtenidos, el número de páginas...</li>
 * <li>El siguiente y anterior número de página de una dada. Estos métodos
 * devuelven 0 cuando la página no tiene sentido (ej la siguiente a la
 * última)</li>
 * <li>Un array de números de páginas adyacentes a una dada (devuelve null si no
 * hay páginas adyacentes).</li>
 * </ul>
 * <p>
 * Copyright: Copyright (c) 2012</p>
 * <p>
 * Company: Universidad de La Rioja. Departamento de Matemáticas y
 * Computación</p>
 *
 * @author Francisco J. Garcia Izquierdo
 * @version 1.0
 */
public class Paginador {

  private int numRegistros;
  private int tamanioPagina;
  private int numPaginas;

  /**
   * Construye un paginador a partir del número de registros encontrados en la
   * búsqueda y el tamaño de cada página.
   *
   * @param numRegistros
   * @param tamanioPagina
   */
  public Paginador(int numRegistros, int tamanioPagina) {
    this.numRegistros = numRegistros;
    this.tamanioPagina = tamanioPagina;
//    this.numPaginas = (numRegistros-1) / tamanioPagina + 1;  // Falla con numRegs = 0
    this.numPaginas = numRegistros / tamanioPagina + (numRegistros % tamanioPagina > 0 ? 1 : 0);
  }

  /**
   * Devuelve el número de registros encontrados en la búsqueda
   */
  public int getNumRegistros() {
    return numRegistros;
  }

  /**
   * Devuelve el número de páginas necesarias para mostrar todos los resultados
   * encontrados en la búsqueda
   *
   */
  public int getNumPaginas() {
    return numPaginas;
  }

  /**
   * Devuelve el tamaño de cada página
   */
  public int getTamanioPagina() {
    return tamanioPagina;
  }

  /**
   * Devuelve la página siguiente a una dada, o 0 si la página dada no tiene
   * siguiente (por ser la última o estar fuera de rango
   *
   * @param pagina número de página de la que se calcula la siguiente
   */
  public int siguiente(int pagina) {
    return (pagina >= 1 && pagina < this.numPaginas) ? pagina + 1 : 0;
  }

  /**
   * Devuelve la página anterior a una dada, o 0 si la página dada no tiene
   * anterior (por ser la primera o estar fuera de rango
   *
   * @param pagina número de página de la que se calcula la anterior
   */
  public int anterior(int pagina) {
    return (pagina > 1 && pagina <= this.numPaginas) ? pagina - 1 : 0;
  }

//  /**
//   * Devuelve los números de página de 5 páginas adyacentes a una dada.
//   *
//   * @param pagina número de página de la que se calculan las adyacentes
//   * @see #adyacentes(int, int)
//   */
//  public int[] adyacentes(int pagina) {
//    return this.adyacentes(pagina, 5);
//  }
//
//  /**
//   * Devuelve los números de página de las páginas adyacentes a una dada.
//   * Devuelve tramos de n páginas, siendo n el valor del parámetro
//   * <code>cuantas</code>. Por ejemplo, si <code>cuantas</code> valiese 5, para
//   * las páginas 1 a 5, devolverá 1 2 3 4 5; para las páginas 6 a 10, mostrará 6
//   * 7 8 9 10; así sucesivamente. Si no hubiese n páginas disponibles se
//   * devolverán las que se puedan hasta el final.
//   *
//   * @param pagina número de página de la que se calculan las adyacentes
//   * @param cuantas cantidad de páginas adyacentes devueltas
//   */
//  public int[] adyacentes(int pagina, int cuantas) {
//    if (pagina < 1 || pagina > this.numPaginas) {
//      return null;
//    }
//    int primer = 1;
//    int pagsPosibles = cuantas;
//    if (this.numPaginas < cuantas) {
//      pagsPosibles = this.numPaginas;
//    } else {
//      primer = (pagina - 1) / cuantas * cuantas + 1;
//      pagsPosibles = this.numPaginas - primer + 1;
//      if (pagsPosibles > cuantas) {
//        pagsPosibles = cuantas;
//      }
//    }
//    int[] sigs = new int[pagsPosibles];
//    for (int i = 0; i < pagsPosibles; i++) {
//      sigs[i] = primer + i;
//    }
//    return sigs;
//  }

  /**
   * Devuelve los números de página de las páginas adyacentes a una dada.
   * Devuelve tramos de n páginas, siendo n el valor del parámetro
   * <code>cuantas</code>. Si no hubiese n páginas disponibles se
   * devolverán las que se puedan.
   *
   * @param pagina número de página de la que se calculan las adyacentes
   * @param cuantas cantidad de páginas adyacentes devueltas
   */
  public int[] adyacentes(int pagina, int cuantas) {
    if (cuantas < 1) throw new IllegalArgumentException("Valor de \"cuantas\" ilegal: el número de páginas adyacentes debe ser mayor de cero");
    if (pagina < 1 || pagina > this.numPaginas) return null;
    
    int[] pos = next(pagina, (cuantas - 1) / 2 + (cuantas - 1) % 2);
    int[] ant = next(pagina, -(cuantas - 1) / 2);

    int[] sigs = new int[ant.length + 1 + pos.length];    
    for (int i = 0; i < ant.length; i++) sigs[i] = ant[ant.length - i - 1];
    sigs[ant.length] = pagina;
    System.arraycopy(pos, 0, sigs, ant.length + 1, pos.length);

    return sigs;
  }

  /**
   * Devuelve 5 páginas adyacentes a una dada. Si no hubiese 5 páginas 
   * disponibles se devolverán las que se puedan.
   *
   * @param pagina número de página de la que se calculan las adyacentes
   * @param cuantas cantidad de páginas adyacentes devueltas
   */
  public int[] adyacentes(int pagina) {
    return Paginador.this.adyacentes(pagina,5);
  }

  private int[] next(int pagina, int cuantas) {
    int signo = cuantas >= 0 ? 1 : -1;
    int[] adj = new int[cuantas * signo];
    for (int i = 0; i < adj.length; i++) {
      int p = pagina + (i + 1) * signo;
      if (p >= 1 && p <= this.numPaginas) {
        adj[i] = p;
      } else {
        return Arrays.copyOf(adj, i);
      }
    }
    return adj;
  }

//  private static void print(int[] x) {
//    if (x ==null) return;
//    for (int i = 0; i < x.length; i++) {
//      System.out.print(x[i] + " ");
//    }
//    System.out.println();
//  }
//
//  public static void main(String[] args) {
//    Paginador p = new Paginador(447, 12);
//    print(p.next(1, 3));
//    print(p.next(37, 3));
//    print(p.next(10, -3));
//    print(p.next(1, 0));
//    print(p.next(1, -3));
//    print(p.next(38, 3));
//    
//    print(p.adyacentes(1,5));
//    print(p.adyacentes(2,5));
//    print(p.adyacentes(3,5));
//    print(p.adyacentes(4,5));
//    
//    
//    print(p.adyacentes(1,4));
//    print(p.adyacentes(2,4));
//    print(p.adyacentes(3,4));
//    print(p.adyacentes(4,4));
//    
//    print(p.adyacentes(p.numPaginas-3,5));
//    print(p.adyacentes(p.numPaginas-2,5));
//    print(p.adyacentes(p.numPaginas-1,5));
//    print(p.adyacentes(p.numPaginas,5));
//    
//    
//    p = new Paginador(25, 12);
//    print(p.adyacentes(1,1));
//    print(p.adyacentes(-1,5));
//    print(p.adyacentes(p.numPaginas+1,5));
//    print(p.adyacentes(1,0));
//    print(p.adyacentes(4,-5));
//  }
}
