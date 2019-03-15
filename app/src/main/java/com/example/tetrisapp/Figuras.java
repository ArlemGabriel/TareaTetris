package com.example.tetrisapp;

import java.util.ArrayList;

public class Figuras {
    private String nombre;
    private ArrayList<ArrayList<Integer>> figuraBase = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> rotacion1 = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> rotacion2 = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> rotacion3 = new ArrayList<>();


    public ArrayList<ArrayList<Integer>> getFiguraBase() {
        return figuraBase;
    }
    public ArrayList<ArrayList<Integer>> getRotacion1(){
        return rotacion1;
    }

    public ArrayList<ArrayList<Integer>> getRotacion2() {
        return rotacion2;
    }

    public ArrayList<ArrayList<Integer>> getRotacion3() {
        return rotacion3;
    }

    public void setNombre(String pNombre) {
        nombre = pNombre;
    }
    public String getNombre() {
        return nombre;
    }

    public void setFiguraBase(ArrayList<Integer> pLinea1,ArrayList<Integer> pLinea2,ArrayList<Integer> pLinea3) {
        figuraBase.add(pLinea1);
        figuraBase.add(pLinea2);
        figuraBase.add(pLinea3);
    }
    public void setRotacion1(ArrayList<Integer> pLinea1,ArrayList<Integer> pLinea2,ArrayList<Integer> pLinea3) {
        rotacion1.add(pLinea1);
        rotacion1.add(pLinea2);
        rotacion1.add(pLinea3);
    }
    public void setRotacion2(ArrayList<Integer> pLinea1,ArrayList<Integer> pLinea2,ArrayList<Integer> pLinea3) {
        rotacion2.add(pLinea1);
        rotacion2.add(pLinea2);
        rotacion2.add(pLinea3);
    }
    public void setRotacion3(ArrayList<Integer> pLinea1,ArrayList<Integer> pLinea2,ArrayList<Integer> pLinea3) {
        rotacion3.add(pLinea1);
        rotacion3.add(pLinea2);
        rotacion3.add(pLinea3);
    }

}
