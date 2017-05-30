/**
 * @author Thomas Mahr (www.tmahr.de)
 * @version $Rev: 4081 $
 * @date $Date: 2016-12-05 22:55:01 +0100 (Mo, 05. Dez 2016) $
 */
package de.tmahr.eap.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

class ModellImpl implements Modell {

    private static final Logger logger = Logger.getLogger(ModellImpl.class.getName());
    private final ArrayList<PaketImpl> wurzelPakete;
    private final ArrayList<PaketImpl> pakete;
    private final ArrayList<ElementImpl> elemente;
    private final ArrayList<VerbindungImpl> verbindungen;
    private final ArrayList<ElementImpl> zustaende;
    private final ArrayList<ElementImpl> ausloeser;
    private final ArrayList<ElementInterface> interfaces;
    private final ArrayList<ElementKlasse> klassen;

    ModellImpl(ArrayList<PaketImpl> wurzelPakete, ArrayList<PaketImpl> pakete,
            ArrayList<ElementImpl> elemente, ArrayList<VerbindungImpl> verbindungen,
            ArrayList<ElementImpl> zustaende, ArrayList<ElementImpl> ausloeser,
            ArrayList<ElementInterface> interfaces, ArrayList<ElementKlasse> klassen) {
        this.wurzelPakete = wurzelPakete;
        this.pakete = pakete;
        this.elemente = elemente;
        this.verbindungen = verbindungen;
        this.zustaende = zustaende;
        this.ausloeser = ausloeser;
        this.interfaces = interfaces;
        this.klassen = klassen;
    }

    @Override
    public Paket[] pakete() {
        return pakete.toArray(new Paket[0]);
    }

    @Override
    public Element[] elemente() {
        return elemente.toArray(new Element[0]);
    }

    @Override
    public Element[] elemente(String typ) {
        List<Element> liste = new ArrayList<>();
        for (Element e : elemente) {
            if (typ.equals(e.typ())) {
                liste.add(e);
            }
        }

        return liste.toArray(new Element[0]);
    }

    @Override
    public Verbindung[] verbindungen() {
        return verbindungen.toArray(new Verbindung[0]);
    }

    @Override
    public Verbindung[] verbindungenAusgehend(Element anker, String verbindungstyp) {
        List<Verbindung> liste = new ArrayList<>();
        for (Verbindung v : verbindungen) {
            if (verbindungstyp.equals(v.typ())) {
                if (v.anker() == anker) {
                    liste.add(v);
                }
            }
        }

        return liste.toArray(new Verbindung[0]);
    }

    @Override
    public PaketImpl[] wurzelPakete() {
        return wurzelPakete.toArray(new PaketImpl[0]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (PaketImpl p : pakete) {
            sb.append("Paket ").append(p.nameExt()).append("\n");
        }
        for (ElementImpl e : elemente) {
            sb.append("Element ").append(e.nameExt()).append("\n");
        }
        for (VerbindungImpl v : verbindungen) {
            sb.append("Verbindung ").append(v.nameExt()).append("\n");
        }
        sb.append("---\n");
        for (ElementImpl e : zustaende) {
            sb.append("Zustand ").append(e.nameExt()).append("\n");
        }
        for (ElementImpl e : ausloeser) {
            sb.append("Ausloeser ").append(e.nameExt()).append("\n");
        }
        sb.append("---\n");
        for (PaketImpl p : wurzelPakete) {
            sb.append(p.toString()).append("\n");
        }
        sb.append("--- Interfaces: \n");
        for (ElementInterface i : interfaces) {
            sb.append(i.toString(" ")).append("\n");
        }
        sb.append("--- Klassen: \n");
        for (ElementKlasse k : klassen) {
            sb.append(k.toString(" ")).append("\n");
        }
        return sb.toString();
    }

    @Override
    public ElementInterface[] interfaces() {
        return interfaces.toArray(new ElementInterface[0]);
    }

    @Override
    public ElementKlasse[] klassen() {
        return klassen.toArray(new ElementKlasse[0]);
    }
}
