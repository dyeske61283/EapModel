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
class AttributImpl implements Attribut {

    private final int id;
    private final String name;
    private final String nameMitId;
    private final String typ;
    private final String visibility;
    private final boolean isStatic;
    private final boolean isConst;
    private final String Default;
    private final Stereotypen stereotypen = new Stereotypen();

    public AttributImpl(String Default, String name, String visibility, int id, String typ, boolean isStatic, boolean isConst) {
        this.id = id;
        this.name = name;
        this.nameMitId = name + " {" + String.valueOf(id) + "}";
        this.typ = typ;
        this.visibility = visibility;
        this.isStatic = isStatic;
        this.isConst = isConst;
        this.Default = Default;
    }

    @Override
    public String ToString(String tab) {
        StringBuilder sb = new StringBuilder();
        sb.append("tab").append("Attribut ").append(typ).append(" ").append(name);
        if (stereotypen.sindStereotypenEnthalten()) {
            sb.append(" <<").append(stereotypen.toString()).append(">>");
        }
        return sb.toString();
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String typ() {
        return typ;
    }

    @Override
    public boolean isStatic() {
        return isStatic;
    }

    @Override
    public boolean isConst() {
        return isConst;
    }

    @Override
    public int id() {
        return id;
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

    @Override
    public String visibility() {
        return this.visibility;
    }

    @Override
    public String Default() {
        return Default;
    }

}
