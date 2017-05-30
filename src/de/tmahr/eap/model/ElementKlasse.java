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
public class ElementKlasse extends ElementInterface {

    private static final Logger logger = Logger.getLogger(ElementKlasse.class.getName());

    private final ArrayList<Attribut> attribute = new ArrayList<>();

    public ElementKlasse(String name, int id, String typ) {
        super(name, id, typ);
    }

    void fuegeHinzu(Attribut a) {
        logger.fine("Fuege Attribut " + a.ToString("") + "hinzu\n");
        attribute.add(a);
    }

    ArrayList<Attribut> attribute() {
        return attribute;
    }

    @Override
    public String toString(String tab) {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString(tab));

        tab += "-";

        for (Attribut a : attribute) {
            sb.append("\n").append(a.ToString(tab));

        }

        return sb.toString();
    }
}
