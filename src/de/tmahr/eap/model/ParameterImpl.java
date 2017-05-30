/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tmahr.eap.model;

/**
 *
 * @author parallels
 */
class ParameterImpl implements Parameter {

    private final String name;
    private final String type;
    private final String kind;
    private final boolean isConst;
    private final Stereotypen stereotypen = new Stereotypen();

    ParameterImpl(String name, String type, String kind, boolean isConst) {
        this.isConst = isConst;
        this.kind = kind;
        this.name = name;
        this.type = type;
    }

    @Override
    public String ToString(String tab) {
        StringBuilder sb = new StringBuilder();
        sb.append(tab).append("Parameter ").append(kind).append(" ").append(type).append(" ").append(name);
        if(stereotypen.sindStereotypenEnthalten())
        {
            sb.append(" <<").append(stereotypen.toString()).append(">>");
        }
        
        return sb.toString();
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String type() {

        return type;
    }

    @Override
    public String kind() {
        return kind;
    }

    @Override
    public boolean isConst() {
        return isConst;
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
    public String nameExt() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        if (stereotypen.sindStereotypenEnthalten()) {
            sb.append(" ").append(stereotypen.toString());
        }
        return sb.toString();
    }

}
