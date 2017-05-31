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
class ElementInterface extends ElementImpl {

    private static final Logger logger = Logger.getLogger(ElementInterface.class.getName());

    private final ArrayList<Methode> methoden = new ArrayList<>();

    public ElementInterface(String name, int id, String typ) {
        super(name, id, typ);
    }

    Methode[] methoden() {
        return methoden.toArray(new Methode[0]);
    }

    void fuegeHinzu(Methode m) {
        logger.fine("Fuege Methode " + m.ToString("") + "hinzu\n");
        methoden.add(m);
    }

    @Override
    public String toString(String tab) {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString(tab));

        tab += "-";

        for (Methode m : methoden) {
            sb.append("\n").append(m.ToString(tab));

        }

        return sb.toString();
    }

}
