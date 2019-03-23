package com.example.tetrisapp;


import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    //Declaración global del tablero logico como una matriz
    int [][] tablero;
    //Declaración global de la posición central con respecto a las columnas en el tablero
    int posicionCentral;
    //Declaracion global del numero total de columnas que tiene el gridlayout
    int totalColumnas;
    //Declaración global de número de filas que tiene el gridLayout
    int filas;
    //Declaración global de la posicion central de las filas
    int posicionCentralfilas;
    //Declaración arreglo de posiciones de la figura en el tablero
    List<Point> points = new ArrayList<Point>();
    //Declaración de arreglo que guarda las posiciones anteriores
    List<Point> savepoints = new ArrayList<Point>();
    //Declaración del arreglo que contiene objetos tipo figura
    ArrayList<Figuras> Figuras = new ArrayList<>();
    //Declaración del arreglo que contiene los colores de las figuras
    ArrayList<Integer> Colores = new ArrayList<>(Arrays.asList(0,1,2,3,4));
    //Declaración de atributo objetoFigura
    Figuras objetoFigura;
    //Declaración de arreglo estático que contendrá la figura actual
     ArrayList<ArrayList<Integer>> subarreglo = new ArrayList<>();
     //Declaración de string del nombre de la figura actual
    String nombreFiguraActual;
    //Declaración variable que contiene el número de fila actual completa
    int filaCompleta;
    ArrayList<String> coordAnterioresFigura = new ArrayList<>();
    final Handler handler = new Handler();
    final Runnable caidaPieza = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this,900);
            moverPieza();
        }
    };
    ArrayList<Integer> tableroUnos = new ArrayList<>();
    ArrayList<String> tags = new ArrayList<>(Arrays.asList("0,0","0,1","0,2","0,3","0,4","0,5","0,6","0,7","0,8","0,9","0,10",
            "1,0","1,1","1,2","1,3","1,4","1,5","1,6","1,7","1,8","1,9","1,10",
            "2,0","2,1","2,2","2,3","2,4","2,5","2,6","2,7","2,8","2,9","2,10",
            "3,0","3,1","3,2","3,3","3,4","3,5","3,6","3,7","3,8","3,9","3,10",
            "4,0","4,1","4,2","4,3","4,4","4,5","4,6","4,7","4,8","4,9","4,10",
            "5,0","5,1","5,2","5,3","5,4","5,5","5,6","5,7","5,8","5,9","5,10",
            "6,0","6,1","6,2","6,3","6,4","6,5","6,6","6,7","6,8","6,9","6,10",
            "7,0","7,1","7,2","7,3","7,4","7,5","7,6","7,7","7,8","7,9","7,10",
            "8,0","8,1","8,2","8,3","8,4","8,5","8,6","8,7","8,8","8,9","8,10",
            "9,0","9,1","9,2","9,3","9,4","9,5","9,6","9,7","9,8","9,9","9,10",
            "10,0","10,1","10,2","10,3","10,4","10,5","10,6","10,7","10,8","10,9","10,10",
            "11,0","11,1","11,2","11,3","11,4","11,5","11,6","11,7","11,8","11,9","11,10",
            "12,0","12,1","12,2","12,3","12,4","12,5","12,6","12,7","12,8","12,9","12,10",
            "13,0","13,1","13,2","13,3","13,4","13,5","13,6","13,7","13,8","13,9","13,10",
            "14,0","14,1","14,2","14,3","14,4","14,5","14,6","14,7","14,8","14,9","14,10"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        crearTableroLogico();
        crearTableroInterfaz();
        inicializarFiguras();
    }

    @Override
    protected void onStart() {
        super.onStart();
        iniciarJuego();
    }
    public void gameOver(){
        handler.removeCallbacks(caidaPieza);
        Intent i = new Intent(this,GameOver.class);
        startActivity(i);
        finish();
    }
    //---------------------------------------------------------------TABLERO LÓGICO------------------------------------------------------------------------
    public void iniciarJuego(){
        escogerFigura();
        ponerFigura();
        llenarTableroUnos();
        actualizarTableroGrafico();
        runnable();
    }
    //Creación del tablero lógico con el uso de una matriz de 15x10
    public void crearTableroLogico(){
          tablero = new int [][]
                  { {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                    {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1},
                    {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1},
                    {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1},
                    {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1},
                    {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1},
                    {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1},
                    {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1},
                    {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1},
                    {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1},
                    {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1},
                    {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1},
                    {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1},
                    {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1},
                    {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}};
          //Inicialización de los valores de columnas y filas del tablero
          GridLayout grid = findViewById(R.id.boardLayout);
          totalColumnas = grid.getColumnCount();
          posicionCentral = grid.getColumnCount()/2;
          filas = grid.getRowCount();
          posicionCentralfilas = 1;
    }
    //Determina si hay una colision cuando se pone la primer pieza
    public boolean hayColisionInicio(){
        boolean hayColisionInicial = false;
        int largoColumna = totalColumnas-1;
        for(int i=0; i<=largoColumna;i++){
            if(tablero[1][i]==1){
                hayColisionInicial =true;
            }
        }
        return hayColisionInicial;
    }
    //Determina si hay una colisión hacia la derecha
    public boolean hayColisionLados(String pBtnPresionado){
        borrarPosicionAnterior();
        boolean hayColisionLados=false;
        int tamañoSubarreglo = subarreglo.size()-1;
        int pos0Tablero;
        int pos0Figura;
        int pos1Tablero;
        int pos1Figura;
        int pos2Tablero;
        int pos2Figura;
        if((posicionCentral==0 && pBtnPresionado=="btnIzquierdaClicked")||(posicionCentral==10 && pBtnPresionado=="btnDerechaClicked")){
            hayColisionLados=true;
        }else{
            for(int i=0;i<=tamañoSubarreglo;i++){
                pos0Tablero = tablero[i+posicionCentralfilas][posicionCentral-1];
                pos0Figura = subarreglo.get(i).get(0);
                if((pos0Tablero==1 || pos0Tablero==-1) && pos0Figura==1){
                    hayColisionLados = true;
                    break;
                }
                pos1Tablero = tablero[i+posicionCentralfilas][posicionCentral];
                pos1Figura = subarreglo.get(i).get(1);
                if((pos1Tablero==1 || pos1Tablero==-1) && pos1Figura==1){
                    hayColisionLados = true;
                    break;
                }
                pos2Tablero = tablero[i+posicionCentralfilas][posicionCentral+1];
                pos2Figura = subarreglo.get(i).get(2);
                if((pos2Tablero==1 || pos2Tablero==-1) && pos2Figura==1){
                    hayColisionLados = true;
                    break;
                }
            }
        }

        restaurarPosicionAnterior();
        return hayColisionLados;
    }
    //Determina si hay colisión hacia abajo
    public boolean hayColisionAbajo(){
        borrarPosicionAnterior();
        boolean hayColisionAbajo = false;
        int tamañoSubarreglo = subarreglo.size()-1;
        int pos0Tablero;
        int pos0Figura;
        int pos1Tablero;
        int pos1Figura;
        int pos2Tablero;
        int pos2Figura;
        for(int i=0;i<=tamañoSubarreglo;i++){
            pos0Tablero = tablero[i+posicionCentralfilas][posicionCentral-1];
            pos0Figura = subarreglo.get(i).get(0);
            if((pos0Tablero==1 || pos0Tablero==-1) && pos0Figura==1){
                hayColisionAbajo = true;
                break;
            }
            pos1Tablero = tablero[i+posicionCentralfilas][posicionCentral];
            pos1Figura = subarreglo.get(i).get(1);
            if((pos1Tablero==1 || pos1Tablero==-1) && pos1Figura==1){
                hayColisionAbajo = true;
                break;
            }
            pos2Tablero = tablero[i+posicionCentralfilas][posicionCentral+1];
            pos2Figura = subarreglo.get(i).get(2);
            if((pos2Tablero==1 || pos2Tablero==-1) && pos2Figura==1){
                hayColisionAbajo = true;
                break;
            }
        }restaurarPosicionAnterior();
        return hayColisionAbajo;
    }
    //Determina si hay colisión al rotar una pieza
    public boolean hayColisionRotar(){
        boolean hayColisionRotar = false;
        ArrayList<ArrayList<Integer>> subarregloTemp = obtenerRotacionTemp();
        borrarPosicionAnterior();
        int tamañoSubarreglo = subarregloTemp.size()-1;
        int pos0Tablero;
        int pos0Figura;
        int pos1Tablero;
        int pos1Figura;
        int pos2Tablero;
        int pos2Figura;
        for(int i=0;i<=tamañoSubarreglo;i++){
            pos0Tablero = tablero[i+posicionCentralfilas][posicionCentral-1];
            pos0Figura = subarregloTemp.get(i).get(0);
            if((pos0Tablero==1 || pos0Tablero==-1) && pos0Figura==1){
                hayColisionRotar = true;
                break;
            }
            pos1Tablero = tablero[i+posicionCentralfilas][posicionCentral];
            pos1Figura = subarregloTemp.get(i).get(1);
            if((pos1Tablero==1 || pos1Tablero==-1) && pos1Figura==1){
                hayColisionRotar = true;
                break;
            }
            pos2Tablero = tablero[i+posicionCentralfilas][posicionCentral+1];
            pos2Figura = subarregloTemp.get(i).get(2);
            if((pos2Tablero==1 || pos2Tablero==-1) && pos2Figura==1){
                hayColisionRotar = true;
                break;
            }
        }
        restaurarPosicionAnterior();
        return hayColisionRotar;
    }
    //Método que realiza una rotación temporal de la figura actual para determinar si hay colision
    public ArrayList<ArrayList<Integer>> obtenerRotacionTemp(){
        int largoArregloFiguras = Figuras.size()-1;
        ArrayList<ArrayList<Integer>> figuraTActualArray = new ArrayList<>();
        figuraTActualArray = subarreglo;

        for(int i=0;i<=largoArregloFiguras;i++) {
            objetoFigura = Figuras.get(i);
            if (nombreFiguraActual == "S1" || nombreFiguraActual == "S2" || nombreFiguraActual == "P") {
                if (objetoFigura.getNombre() == nombreFiguraActual && figuraTActualArray.equals(objetoFigura.getFiguraBase())) {
                    figuraTActualArray = objetoFigura.getRotacion1();
                    break;
                }
                if (objetoFigura.getNombre() == nombreFiguraActual && figuraTActualArray.equals(objetoFigura.getRotacion1())) {
                    figuraTActualArray = objetoFigura.getFiguraBase();
                    break;
                }
            }
            if (nombreFiguraActual == "T" || nombreFiguraActual == "L1" || nombreFiguraActual == "L2") {
                if (objetoFigura.getNombre() == nombreFiguraActual && figuraTActualArray.equals(objetoFigura.getFiguraBase())) {
                    figuraTActualArray = objetoFigura.getRotacion1();
                    break;
                }
                if(objetoFigura.getNombre()==nombreFiguraActual && figuraTActualArray.equals(objetoFigura.getRotacion1())){
                    figuraTActualArray = objetoFigura.getRotacion2();
                    break;
                }
                if(objetoFigura.getNombre()==nombreFiguraActual && figuraTActualArray.equals(objetoFigura.getRotacion2())){
                    figuraTActualArray = objetoFigura.getRotacion3();
                    break;
                }
                if(objetoFigura.getNombre()==nombreFiguraActual && figuraTActualArray.equals(objetoFigura.getRotacion3())){
                    figuraTActualArray = objetoFigura.getFiguraBase();
                    break;
                }
            }

        }
        return figuraTActualArray;
    }
    //Método que elige de manera aleatoria las figuras
    public void escogerFigura(){
        int largoListaFiguras = Figuras.size()-1;
        int numeroAleatorio = numeroAleatorio(largoListaFiguras);
        objetoFigura = Figuras.get(numeroAleatorio);
        subarreglo = objetoFigura.getFiguraBase();
        nombreFiguraActual = objetoFigura.getNombre();
    }
    //Pone figura al inicio del tablero parte lógica
    public void ponerFigura(){
        int tamañoSubarreglo = subarreglo.size()-1;
        GridLayout grid = findViewById(R.id.boardLayout);
        totalColumnas = grid.getColumnCount();
        posicionCentral = grid.getColumnCount()/2;
        filas = grid.getRowCount();
        posicionCentralfilas = 1;
        savepoints.clear();
        points.clear();
        //Verifica si hay una colisión al inicio
        boolean hayColisionInicial = false;
        hayColisionInicial = hayColisionInicio();
        if(hayColisionInicial==false){
            for(int i=0;i<=tamañoSubarreglo;i++) {
                if(subarreglo.get(i).get(0)==1){
                    tablero[i + 1][posicionCentral - 1] = subarreglo.get(i).get(0);;
                }
                if(subarreglo.get(i).get(1)==1) {
                    tablero[i + 1][posicionCentral] = subarreglo.get(i).get(1);;
                }
                if(subarreglo.get(i).get(2)==1) {
                    tablero[i + 1][posicionCentral + 1] = subarreglo.get(i).get(2);;
                }
                actualizarPosicionActualColumnas(i,posicionCentralfilas+i);
            }
        }else{
            gameOver();
        }
    }
    //Actualizar la posición actual de la figura en las columnas
    public void actualizarPosicionActualColumnas(int i, int pPosicion){
        if (subarreglo.get(i).get(0) == 1) {
            points.add(new Point(pPosicion, posicionCentral - 1));
        }
        if (subarreglo.get(i).get(1) == 1) {
            points.add(new Point(pPosicion, posicionCentral));
        }
        if (subarreglo.get(i).get(2) == 1) {
            points.add(new Point( pPosicion,posicionCentral + 1));
        }
    }
    //Actualizar la posición actual de la figura en las filas
    public void actualizarPosicionActualFilas(int i, int pPosicion){
        if (subarreglo.get(i).get(0) == 1) {
            points.add(new Point(pPosicion, posicionCentral - 1));
        }
        if (subarreglo.get(i).get(1) == 1) {
            points.add(new Point(pPosicion, posicionCentral));
        }
        if (subarreglo.get(i).get(2) == 1) {
            points.add(new Point(pPosicion, posicionCentral + 1));
        }
    }
    //Resetear lista de coordenadas, arreglo de points
    public void resetearPoints(){
        savepoints.clear();
        for(int i=0;i<=points.size()-1;i++){
            savepoints.add(points.get(i));
        }
        points.clear();
    }
    //Restaura la lista de coordenadas, arreglo de points
    public void restaurarPoints(){
        points.clear();
        for(int i=0;i<=savepoints.size()-1;i++){
            points.add(savepoints.get(i));
        }
        savepoints.clear();
    }
    //Método para mover figura hacia la derecha o izquierda
    public void moverFiguraLados(){
        int tamañoSubarreglo = subarreglo.size()-1;
        for(int i=0;i<=tamañoSubarreglo;i++){
            if(subarreglo.get(i).get(0)==1){
                tablero[i+posicionCentralfilas][posicionCentral-1] = subarreglo.get(i).get(0);
            }
            if(subarreglo.get(i).get(1)==1){
                tablero[i+posicionCentralfilas][posicionCentral] = subarreglo.get(i).get(1);
            }
            if(subarreglo.get(i).get(2)==1){
                tablero[i+posicionCentralfilas][posicionCentral+1] = subarreglo.get(i).get(2);
            };
            actualizarPosicionActualColumnas(i,posicionCentralfilas+i);
        }
    }
    //Método para mover la figura hacia abajo
    public void moverFiguraAbajo(){
        posicionarNuevaFigura();
    }
    //Método para rotar la figura
    public void rotarFigura(){
        obtenerRotacion();
        posicionarNuevaFigura();
    }
    public void posicionarNuevaFigura(){
        int tamañoSubarreglo = subarreglo.size()-1;
        for(int i=0;i<=tamañoSubarreglo;i++){
            if(subarreglo.get(i).get(0)==1) {
                tablero[i + posicionCentralfilas][posicionCentral - 1] = subarreglo.get(i).get(0);
            }
            if(subarreglo.get(i).get(1)==1) {
                tablero[i + posicionCentralfilas][posicionCentral] = subarreglo.get(i).get(1);
            }
            if(subarreglo.get(i).get(2)==1) {
                tablero[i + posicionCentralfilas][posicionCentral + 1] = subarreglo.get(i).get(2);
            }
            actualizarPosicionActualFilas(i,i+posicionCentralfilas);
        }
    }
    //Método que obtiene la rotación de una figura
    public void obtenerRotacion(){
        if(nombreFiguraActual != "C"){
            int largoArregloFiguras = Figuras.size()-1;
            ArrayList<ArrayList<Integer>> figuraTActualArray = new ArrayList<>();
            figuraTActualArray = subarreglo;

            for(int i=0;i<=largoArregloFiguras;i++) {
                objetoFigura = Figuras.get(i);
                if (nombreFiguraActual == "S1" || nombreFiguraActual == "S2" || nombreFiguraActual == "P") {
                    if (objetoFigura.getNombre() == nombreFiguraActual && figuraTActualArray.equals(objetoFigura.getFiguraBase())) {
                        figuraTActualArray = objetoFigura.getRotacion1();
                        break;
                    }
                    if (objetoFigura.getNombre() == nombreFiguraActual && figuraTActualArray.equals(objetoFigura.getRotacion1())) {
                        figuraTActualArray = objetoFigura.getFiguraBase();
                        break;
                    }
                }
                if (nombreFiguraActual == "T" || nombreFiguraActual == "L1" || nombreFiguraActual == "L2") {
                    if (objetoFigura.getNombre() == nombreFiguraActual && figuraTActualArray.equals(objetoFigura.getFiguraBase())) {
                        figuraTActualArray = objetoFigura.getRotacion1();
                        break;
                    }
                    if(objetoFigura.getNombre()==nombreFiguraActual && figuraTActualArray.equals(objetoFigura.getRotacion1())){
                        figuraTActualArray = objetoFigura.getRotacion2();
                        break;
                    }
                    if(objetoFigura.getNombre()==nombreFiguraActual && figuraTActualArray.equals(objetoFigura.getRotacion2())){
                        figuraTActualArray = objetoFigura.getRotacion3();
                        break;
                    }
                    if(objetoFigura.getNombre()==nombreFiguraActual && figuraTActualArray.equals(objetoFigura.getRotacion3())){
                        figuraTActualArray = objetoFigura.getFiguraBase();
                        break;
                    }
                }

            }
            subarreglo = figuraTActualArray;

        }
    }

    //Método que borra la posición anterior de la figura
    public void borrarPosicionAnterior(){
        int tamañoPoints = points.size()-1;
        for(int i=0;i<=tamañoPoints;i++){
            //Atributo posFila obtiene la posicion de la fila, los Xs [(x,y),(x,y),(x,y),...]
            //De la lista de posicion actual de la figura
            int posFila = points.get(i).x;
            //Atributo posFila obtiene la posicion de la columna, los Ys [(x,y),(x,y),(x,y),...]
            //De la lista de posicion actual de la figura
            int posColumna = points.get(i).y;
            tablero[posFila][posColumna] = 0;
        }
        resetearPoints();
    }
    //Método que restaura la posición anterior de la figura
    public void restaurarPosicionAnterior(){
        int tamañoPoints = savepoints.size()-1;
        //Ciclo que recorre la lista de posicion actual de la figura en el tablero para resetearlo
        for(int i=0;i<=tamañoPoints;i++){
            //Atributo posFila obtiene la posicion de la fila, los Xs [(x,y),(x,y),(x,y),...]
            //De la lista de posicion actual de la figura
            int posFila = savepoints.get(i).x;
            //Atributo posFila obtiene la posicion de la columna, los Ys [(x,y),(x,y),(x,y),...]
            //De la lista de posicion actual de la figura
            int posColumna = savepoints.get(i).y;
            tablero[posFila][posColumna] = 1;
        }
        restaurarPoints();
    }
    //Método que convierte el subarreglo de la figura actual de tipo int[][] a ArrayList<ArrayList<Integer>>
    //Para poder pasarselo al método de figuras para hacer una rotación
    public ArrayList<ArrayList<Integer>> subarregloToArray(int [][] pSubarreglo){
        int largoSubarreglo = pSubarreglo.length-1;
        ArrayList<ArrayList<Integer>> figuraActualArray = new ArrayList<>();
        for(int i=0;i<=largoSubarreglo;i++){
            int pos0 = pSubarreglo[i][0];
            int pos1 = pSubarreglo[i][1];
            int pos2 = pSubarreglo[i][2];
            ArrayList<Integer> linea1 = new ArrayList<Integer>(Arrays.asList(pos0, pos1, pos2));
            figuraActualArray.add(linea1);
        }
        return  figuraActualArray;
    }
    //Método que convierte tablero de arreglo estático a dinámico
    public ArrayList<ArrayList<Integer>> tableroToArray(int [][] pSubarreglo){
        int largoArreglo = pSubarreglo.length-1;
        ArrayList<ArrayList<Integer>> figuraActualArray = new ArrayList<>();
        for(int i=0;i<=largoArreglo;i++){
            int pos0 = pSubarreglo[i][0];
            int pos1 = pSubarreglo[i][1];
            int pos2 = pSubarreglo[i][2];
            int pos3 = pSubarreglo[i][3];
            int pos4 = pSubarreglo[i][4];
            int pos5 = pSubarreglo[i][5];
            int pos6 = pSubarreglo[i][6];
            int pos7 = pSubarreglo[i][7];
            int pos8 = pSubarreglo[i][8];
            int pos9 = pSubarreglo[i][9];
            int pos10 = pSubarreglo[i][10];
            ArrayList<Integer> linea1 = new ArrayList<Integer>(Arrays.asList(pos0,pos1,pos2,pos3,pos4,pos5,pos6,pos7,pos8,pos9,pos10));
            figuraActualArray.add(linea1);
        }
        return  figuraActualArray;
    }
    //Algoritmo que elige un numero aleatorio
    public int numeroAleatorio(int pLargoLista){
        //generador de numeros aleatorios
        Random generadorAleatorios = new Random();
        //Genera un número entre 0 y el largo del arreglo figuras
        int numero = generadorAleatorios.nextInt(pLargoLista+1);
        //imprimo el numero en consola
        return numero;
    }
    //Método que mueve la figura hacia la derecha
    public void btnDerechaClicked(View view){
        //imprimirTablero();

        posicionCentral = posicionCentral+1;
        String btnDer = "btnDerechaClicked";
        if(hayColisionLados(btnDer)==false){
            borrarFiguraAnterior();
            borrarPosicionAnterior();
            moverFiguraLados();
            llenarTableroUnos();
            actualizarTableroGrafico();
        }else{
            posicionCentral = posicionCentral-1;
            llenarTableroUnos();
            actualizarTableroGrafico();
        }
    }
    //Método que mueve la figura hacia la izquierda
    public void btnIzquierdaClicked(View view){
        posicionCentral = posicionCentral-1;
        String btnIzq = "btnIzquierdaClicked";
        if(hayColisionLados(btnIzq)==false){
            borrarFiguraAnterior();
            borrarPosicionAnterior();
            moverFiguraLados();
            llenarTableroUnos();
            actualizarTableroGrafico();
        }else{
            posicionCentral = posicionCentral+1;
            llenarTableroUnos();
            actualizarTableroGrafico();
        }
    }
    //Método que mueve la figura hacia abajo
    public void btnAbajoClicked(View view){
        moverPieza();
    }
    public void moverPieza(){
        //imprimirTablero();
        posicionCentralfilas = posicionCentralfilas+1;
        if(hayColisionAbajo()==false){
            borrarFiguraAnterior();
            borrarPosicionAnterior();
            moverFiguraAbajo();
            llenarTableroUnos();
            actualizarTableroGrafico();
        }else{
            restaurarPosicionAnterior();
            if(verificarFilasCompletas()==true){
                while(verificarFilasCompletas()==true){
                    actualizarTablero();
                    llenarTableroUnos();
                    actualizarTableroGrafico();
                }
                escogerFigura();
                ponerFigura();
                llenarTableroUnos();
                actualizarTableroGrafico();
            }else{
                posicionCentralfilas = posicionCentralfilas-1;
                escogerFigura();
                ponerFigura();
                llenarTableroUnos();
                actualizarTableroGrafico();
            }
        }
    }
    public void runnable(){
        handler.post(caidaPieza);
    }
    public void borrarFiguraAnterior(){
        int tamañoPoints = points.size()-1;
        ArrayList<String> temporal = new ArrayList<>();
        GridLayout grid = findViewById(R.id.boardLayout);
        int imagen = R.drawable.cuadrotransparente;

        for(int i=0;i<=tamañoPoints;i++){
            String x = String.valueOf(points.get(i).x);
            String y = String.valueOf(points.get(i).y);
            String coord = x+","+y;
            temporal.add(coord);
        }
        coordAnterioresFigura=temporal;
        int largoTotal =(filas*totalColumnas)-1;
        ImageView vista = new ImageView(MainActivity.this);
        for(int i =0;i<=largoTotal;i++){
            for(int p=0;p<=points.size()-1;p++){
                if(grid.getChildAt(i).getTag().equals(temporal.get(p))){
                    vista= (ImageView)grid.getChildAt(i);
                    vista.setImageResource(imagen);
                }
            }
        }
    }
    //Método que rota la figura
    public void btnRotarClicked(View view){
        if(nombreFiguraActual !="C"){
            if(hayColisionRotar()==false){
                borrarCuadrosAnteriores();
                borrarPosicionAnterior();
                rotarFigura();
                llenarTableroUnos();
                actualizarTableroGrafico();
            }else{
                borrarCuadrosAnteriores();
                llenarTableroUnos();
                actualizarTableroGrafico();
            }
        }
    }
    public void borrarCuadrosAnteriores(){
        GridLayout grid = findViewById(R.id.boardLayout);
        int tamaño =(filas*totalColumnas)-1;
        int imagen = R.drawable.cuadrotransparente;

        ImageView v = new ImageView(MainActivity.this);
        for(int i=0;i<=tamaño;i++){
            if(tableroUnos.get(i) == 1){
                v= (ImageView)grid.getChildAt(i);
                v.setImageResource(imagen);

            }
        }
    }
    //Método que inicializa los objetos de tipo figura
    public void inicializarFiguras(){

        //Crea el objeto figura T con sus valores respectivos
        ArrayList<Integer> linea1 = new ArrayList<Integer>(Arrays.asList(1, 1, 1));
        ArrayList<Integer> linea2 = new ArrayList<Integer>(Arrays.asList(0, 1, 0));
        ArrayList<Integer> linea3 = new ArrayList<Integer>(Arrays.asList(0, 0, 0));

        Figuras figuraBaseT = new Figuras();
        figuraBaseT.setNombre("T");
        figuraBaseT.setFiguraBase(linea1,linea2,linea3);

        linea1 = new ArrayList<Integer>(Arrays.asList(0, 1, 0));
        linea2 = new ArrayList<Integer>(Arrays.asList(1, 1, 0));
        linea3 = new ArrayList<Integer>(Arrays.asList(0, 1, 0));
        figuraBaseT.setRotacion1(linea1,linea2,linea3);

        linea1 = new ArrayList<Integer>(Arrays.asList(0, 1, 0));
        linea2 = new ArrayList<Integer>(Arrays.asList(1, 1, 1));
        linea3 = new ArrayList<Integer>(Arrays.asList(0, 0, 0));
        figuraBaseT.setRotacion2(linea1,linea2,linea3);

        linea1 = new ArrayList<Integer>(Arrays.asList(0, 1, 0));
        linea2 = new ArrayList<Integer>(Arrays.asList(0, 1, 1));
        linea3 = new ArrayList<Integer>(Arrays.asList(0, 1, 0));
        figuraBaseT.setRotacion3(linea1,linea2,linea3);

        //Agrega el objeto figuraBaseT al arreglo
        Figuras.add(figuraBaseT);

        //Crea el objeto figura S1 con sus valores respectivos

        Figuras figuraBaseS1 = new Figuras();
        figuraBaseS1.setNombre("S1");
        linea1 = new ArrayList<Integer>(Arrays.asList(0, 1, 1));
        linea2 = new ArrayList<Integer>(Arrays.asList(1, 1, 0));
        linea3 = new ArrayList<Integer>(Arrays.asList(0, 0, 0));
        figuraBaseS1.setFiguraBase(linea1, linea2, linea3);

        linea1 = new ArrayList<Integer>(Arrays.asList(0, 1, 0));
        linea2 = new ArrayList<Integer>(Arrays.asList(0, 1, 1));
        linea3 = new ArrayList<Integer>(Arrays.asList(0, 0, 1));
        figuraBaseS1.setRotacion1(linea1,linea2,linea3);

        //Agrega el objeto figuraBaseS1 al arreglo
        Figuras.add(figuraBaseS1);

        //Crea el objeto figura S2 con sus valores respectivos

        Figuras figuraBaseS2 = new Figuras();
        figuraBaseS2.setNombre("S2");
        linea1 = new ArrayList<Integer>(Arrays.asList(1, 1, 0));
        linea2 = new ArrayList<Integer>(Arrays.asList(0, 1, 1));
        linea3 = new ArrayList<Integer>(Arrays.asList(0, 0, 0));
        figuraBaseS2.setFiguraBase(linea1, linea2, linea3);

        linea1 = new ArrayList<Integer>(Arrays.asList(0, 1, 0));
        linea2 = new ArrayList<Integer>(Arrays.asList(1, 1, 0));
        linea3 = new ArrayList<Integer>(Arrays.asList(1, 0, 0));
        figuraBaseS2.setRotacion1(linea1,linea2,linea3);

        //Agrega el objeto figuraBaseS2 al arreglo
        Figuras.add(figuraBaseS2);

        //Crea el objeto figura L1 con sus valores respectivos

        Figuras figuraBaseL1 = new Figuras();
        figuraBaseL1.setNombre("L1");
        linea1 = new ArrayList<Integer>(Arrays.asList(1, 0, 0));
        linea2 = new ArrayList<Integer>(Arrays.asList(1, 0, 0));
        linea3 = new ArrayList<Integer>(Arrays.asList(1, 1, 0));
        figuraBaseL1.setFiguraBase(linea1, linea2, linea3);

        linea1 = new ArrayList<Integer>(Arrays.asList(1, 1, 1));
        linea2 = new ArrayList<Integer>(Arrays.asList(1, 0, 0));
        linea3 = new ArrayList<Integer>(Arrays.asList(0, 0, 0));
        figuraBaseL1.setRotacion1(linea1,linea2,linea3);

        linea1 = new ArrayList<Integer>(Arrays.asList(1, 1, 0));
        linea2 = new ArrayList<Integer>(Arrays.asList(0, 1, 0));
        linea3 = new ArrayList<Integer>(Arrays.asList(0, 1, 0));
        figuraBaseL1.setRotacion2(linea1,linea2,linea3);

        linea1 = new ArrayList<Integer>(Arrays.asList(0, 0, 1));
        linea2 = new ArrayList<Integer>(Arrays.asList(1, 1, 1));
        linea3 = new ArrayList<Integer>(Arrays.asList(0, 0, 0));
        figuraBaseL1.setRotacion3(linea1,linea2,linea3);

        //Agrega el objeto figuraBaseL1 al arreglo
        Figuras.add(figuraBaseL1);

        //Crea el objeto figura L2 con sus valores respectivos

        Figuras figuraBaseL2 = new Figuras();
        figuraBaseL2.setNombre("L2");
        linea1 = new ArrayList<Integer>(Arrays.asList(0, 1, 0));
        linea2 = new ArrayList<Integer>(Arrays.asList(0, 1, 0));
        linea3 = new ArrayList<Integer>(Arrays.asList(1, 1, 0));
        figuraBaseL2.setFiguraBase(linea1, linea2, linea3);

        linea1 = new ArrayList<Integer>(Arrays.asList(1, 0, 0));
        linea2 = new ArrayList<Integer>(Arrays.asList(1, 1, 1));
        linea3 = new ArrayList<Integer>(Arrays.asList(0, 0, 0));
        figuraBaseL2.setRotacion1(linea1,linea2,linea3);

        linea1 = new ArrayList<Integer>(Arrays.asList(0, 1, 1));
        linea2 = new ArrayList<Integer>(Arrays.asList(0, 1, 0));
        linea3 = new ArrayList<Integer>(Arrays.asList(0, 1, 0));
        figuraBaseL2.setRotacion2(linea1,linea2,linea3);

        linea1 = new ArrayList<Integer>(Arrays.asList(1, 1, 1));
        linea2 = new ArrayList<Integer>(Arrays.asList(0, 0, 1));
        linea3 = new ArrayList<Integer>(Arrays.asList(0, 0, 0));
        figuraBaseL2.setRotacion3(linea1,linea2,linea3);

        //Agrega el objeto figuraBaseL2 al arreglo
        Figuras.add(figuraBaseL2);

        //Crea el objeto figura P con sus valores respectivos

        Figuras figuraBaseP = new Figuras();
        figuraBaseP.setNombre("P");
        linea1 = new ArrayList<Integer>(Arrays.asList(0, 1, 0));
        linea2 = new ArrayList<Integer>(Arrays.asList(0, 1, 0));
        linea3 = new ArrayList<Integer>(Arrays.asList(0, 1, 0));
        figuraBaseP.setFiguraBase(linea1, linea2, linea3);

        linea1 = new ArrayList<Integer>(Arrays.asList(0, 0, 0));
        linea2 = new ArrayList<Integer>(Arrays.asList(1, 1, 1));
        linea3 = new ArrayList<Integer>(Arrays.asList(0, 0, 0));
        figuraBaseP.setRotacion1(linea1,linea2,linea3);

        //Agrega el objeto figuraBaseP al arreglo
        Figuras.add(figuraBaseP);

        //Crea el objeto figura C con sus valores respectivos

        Figuras figuraBaseC = new Figuras();
        figuraBaseC.setNombre("C");
        linea1 = new ArrayList<Integer>(Arrays.asList(1, 1, 0));
        linea2 = new ArrayList<Integer>(Arrays.asList(1, 1, 0));
        linea3 = new ArrayList<Integer>(Arrays.asList(0, 0, 0));
        figuraBaseC.setFiguraBase(linea1, linea2, linea3);

        //Agrega el objeto figuraBaseC al arreglo
        Figuras.add(figuraBaseC);


    }
    //Método que verifica si hay una fila completa
    public Boolean verificarFilasCompletas(){
        Boolean estadoFilas = false;
        int numeroFilas = filas-1;
        ArrayList<Integer> filaUnos= new ArrayList<>(Arrays.asList(-1, 1, 1, 1, 1, 1, 1, 1, 1, 1,-1));
        ArrayList<ArrayList<Integer>> tableroArray = new ArrayList<>();;
        tableroArray = tableroToArray(tablero);
        for(int i=0;i<=numeroFilas;i++){
            if (tableroArray.get(i).equals(filaUnos)){
                filaCompleta = i;
                estadoFilas =true;
                break;
            }
        }
        return estadoFilas;
    }
    //Método que actualiza el tablero cuando hay una fila completa
    public void actualizarTablero(){
        int [] filaCeros = {-1,0,0,0,0,0,0,0,0,0,-1};
        for(int i=1;i<filaCompleta;filaCompleta--){
            tablero[filaCompleta] = tablero[filaCompleta-1];
        }
        tablero[1]= filaCeros;
    }
    //---------------------------------------------------------------TABLERO GRÁFICO------------------------------------------------------------------------
    //Método que se encarga de la creación del tablero físico con el uso de GridLayout
    public void crearTableroInterfaz(){

        GridLayout grid = findViewById(R.id.boardLayout);
        int columnasIni = grid.getColumnCount();
        int filasIni = grid.getRowCount();
        int tamaño = columnasIni*filasIni;
        for(int i =0 ;i<tamaño;i++){
            if(esBorde(i)==true){
                ImageView imagen = new ImageView(this);
                imagen.setImageResource(R.drawable.cuadronegro);
                grid.addView(imagen,i,new ViewGroup.LayoutParams(90,90));
                imagen.setTag(tags.get(i));
            }else{
                ImageView imagen = new ImageView(this);
                imagen.setImageResource(R.drawable.cuadrotransparente);
                grid.addView(imagen,i,new ViewGroup.LayoutParams(90,90));
                imagen.setTag(tags.get(i));
            }
        }
    }
    //Método que determina si es un borde para la creación del tablero físico
    public boolean esBorde(int pNumero){
        int [] bordes = {0,1,2,3,4,5,6,7,8,9,10,11,
                        21,22,32,33,43,44,54,55,65,
                        66,76,77,87,88,98,99,109,110,
                       120,121,131,132,142,143,153,154,
                       155,156,157,158,159,160,161,162,
                       163,164};
        int largoLista = bordes.length;
        boolean estado=false;
        for(int i=0;i<largoLista;i++){
            if(bordes[i]==pNumero) {
                estado = true;
                break;
            }
        }
        return estado;
    }
    //Método que dibuja la figura en el tablero gráfico
    public void actualizarTableroGrafico(){

        GridLayout grid = findViewById(R.id.boardLayout);
        int columnasIni = grid.getColumnCount();
        int filasIni = grid.getRowCount();
        int tamaño = (columnasIni*filasIni)-1;
        int imagen = R.drawable.cuadrotransparente;

        ImageView v = new ImageView(MainActivity.this);
        for(int i=0;i<=tamaño;i++){
            if(tableroUnos.get(i) == 0){
                v= (ImageView)grid.getChildAt(i);
                v.setImageResource(imagen);

            }
        }

        imagen=R.drawable.cuadroceleste;

        //ImageView v = new ImageView(MainActivity.this);
        for(int i=0;i<=tamaño;i++){
            if(tableroUnos.get(i) == 1){
                v= (ImageView)grid.getChildAt(i);
                v.setImageResource(imagen);

            }
        }
    }
    public void llenarTableroUnos(){
        tableroUnos.removeAll(tableroUnos);
        for(int i=0;i<=filas-1;i++){
            for(int p=0;p<=totalColumnas-1;p++){
                if(tablero[i][p]==1){
                    tableroUnos.add(1);
                }
                if(tablero[i][p]==-1){
                    tableroUnos.add(-1);
                }
                if(tablero[i][p]==0){
                    tableroUnos.add(0);
                }
            }
        }
    }

    //--------------------------------------------------------------MÉTODOS AUXILIARES---------------------------------------------
    //Convierte el tablero a toString
    public String tablerotoString() {
        String impresionTablero = "";
        for(int i=0;i<=filas-1;i++){
            impresionTablero = impresionTablero+"{";
            for(int p=0;p<=totalColumnas-1;p++){
                impresionTablero = impresionTablero +tablero[i][p];
            }
            impresionTablero = impresionTablero+"}\n";
        }
        return impresionTablero;
    }
    //Imprimir tablero en consola
    public void imprimirTablero(){
        String tableroStr = tablerotoString();
        Log.i("TABLERO:",tableroStr);
    }
}
