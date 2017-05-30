/**
 * @author Thomas Mahr (www.tmahr.de)
 * @version $Rev: 4080 $
 * @date $Date: 2016-12-05 18:33:32 +0100 (Mo, 05. Dez 2016) $
 */
package de.tmahr.eap.model;

import java.util.ArrayList;

class ElementImpl implements Element {

    private final int id;
    private final String name;
    private final String nameMitId;
    private final String typ;
    private final ArrayList<VerbindungImpl> verbindung = new ArrayList<>();
    private final ArrayList<ElementImpl> subelemente = new ArrayList<>();
    private final Stereotypen stereotypen = new Stereotypen();

    ElementImpl(String name, int id, String typ) {
        this.id = id;
        this.name = name;
        this.typ = typ;
        nameMitId = name + " {" + String.valueOf(id) + "}";
    }

    @Override
    public String name() {
        return name;
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
    public int id() {
        return id;
    }

    @Override
    public String nameMitId() {
        return nameMitId;
    }

    @Override
    public String typ() {
        return typ;
    }

    void fuegeHinzu(VerbindungImpl v) {
        verbindung.add(v);
    }

    void fuegeHinzu(ElementImpl e) {
        subelemente.add(e);
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
    public String toString() {
        return toString("");
    }

    @Override
    public String toString(String tab) {
        StringBuilder sb = new StringBuilder();
        sb.append(tab).append("Element ").append(typ).append(" ").append(nameMitId);
        if (stereotypen.sindStereotypenEnthalten()) {
            sb.append(" <<").append(stereotypen.toString()).append(">>");
        }

        tab += "-";

        for (VerbindungImpl v : verbindung) {
            Element target = v.ziel();
            if (target != null) {
                sb.append("\n").append(tab).append(v.typ());
                if (v.istStereotypisiert()) {
                    sb.append(" ").append(stereotypen.toString());
                }
                sb.append(": ").append(nameMitId).append(" -> ").append(target.nameMitId());
            }
        }

        for (ElementImpl e : subelemente) {
            sb.append("\n").append(e.toString(tab));
        }

        return sb.toString();
    }
}
