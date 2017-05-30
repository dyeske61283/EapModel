/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tmahr.eap.model;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author parallels
 */
class MethodeImpl implements Methode {

    private static final Logger logger = Logger.getLogger(MethodeImpl.class.getName());
    
    private final int id;
    private final String returnType;
    private final String name;
    private final String nameMitId;
    private final String visibility;
    private final String isQuery;
    private final String isLeaf;
    private final String isStatic;
    private final String isPure;
    private final String isConst;
    private final String isAbstract;
    private final Stereotypen stereotypen = new Stereotypen();
    private final ArrayList<ParameterImpl> parameter = new ArrayList<>();

    public MethodeImpl(int id, String returnType, String name, String visibility, boolean isQuery, boolean isLeaf, boolean isStatic, boolean isPure, boolean isConst, boolean isAbstract) {
        this.id = id;
        this.returnType = returnType;
        this.name = name;
        this.nameMitId = name + " {" + String.valueOf(id) + "}";
        this.visibility = visibility;
        this.isQuery = " isQuery: " + String.valueOf(isQuery);
        this.isLeaf = " isLeaf: " + String.valueOf(isLeaf);
        this.isStatic = " isStatic: " + String.valueOf(isStatic);
        this.isPure = " isPure: " + String.valueOf(isPure);
        this.isConst = " isConst: " + String.valueOf(isConst);
        this.isAbstract = " isAbstract: " + String.valueOf(isAbstract);
    }

    @Override
    public String ToString(String tab) {
        StringBuilder sb = new StringBuilder();
        sb.append(tab).append("Methode ").append(visibility).append(" ").append(returnType).append(" ").append(name).append(" ");
        sb.append(isQuery).append(isLeaf).append(isStatic).append(isPure).append(isConst).append(isAbstract);
        
        
        return sb.toString();
    }

    @Override
    public String returnType() {
        return this.returnType;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String visibility() {
        return this.visibility;
    }

    @Override
    public int id() {
        return this.id;
    }

    public ArrayList<ParameterImpl> parameter(){
        return this.parameter;
    }
    
    @Override
    public String isStatic() {
        return this.isStatic;
    }

    @Override
    public String isQuery() {
        return this.isQuery;
    }

    @Override
    public String isPure() {
        return this.isPure;
    }

    @Override
    public String isLeaf() {
        return this.isLeaf;
    }

    @Override
    public String isConst() {
        return this.isConst;
    }

    @Override
    public String isAbstract() {
        return this.isAbstract;
    }

    void fuegeHinzu(ParameterImpl p) {
        logger.fine("Fuege Parameter " + p.ToString("") + "hinzu\n");
        parameter.add(p);
    }

    void hinzufuegenStereotyp(Stereotyp s) {
        stereotypen.hinzufuegenStereotyp(s);
    }

    void hinzufuegenStereotypen(Stereotypen s) {
        stereotypen.hinzufuegenStereotypen(s);
    }

    @Override
    public Stereotyp[] liefereStereotypen() {
        return stereotypen.liefereStereotypen();
    }

    @Override
    public boolean istStereotypisiert() {
        return stereotypen.sindStereotypenEnthalten();
    }

    @Override
    public String stereotypenToString() {
        return stereotypen.toString();
    }

    @Override
    public String nameMitId() {
        return nameMitId;
    }

    @Override
    public String nameExt() {
        StringBuilder sb = new StringBuilder();
        sb.append(nameMitId);
        if (stereotypen.sindStereotypenEnthalten()) {
            sb.append(" ").append(stereotypen.toString());
        }
        return sb.toString();
    }

}
