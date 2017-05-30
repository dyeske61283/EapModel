/**
 * @author Thomas Mahr (www.tmahr.de)
 * @version $Rev: 4080 $
 * @date $Date: 2016-12-05 18:33:32 +0100 (Mo, 05. Dez 2016) $
 */
package de.tmahr.eap.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class ModellErbauer implements Serializable {

    private static final Logger logger = Logger.getLogger(ModellErbauer.class.getName());
    private final ArrayList<PaketImpl> wurzelPakete = new ArrayList<>();
    private final ArrayList<PaketImpl> pakete = new ArrayList<>();
    private final ArrayList<ElementImpl> elemente = new ArrayList<>();
    private final ArrayList<VerbindungImpl> verbindungen = new ArrayList<>();
    private final ArrayList<VerbindungId> unaufgeloesteVerbindungen = new ArrayList<>();
    private final ArrayList<VerbindungId> aufgeloesteVerbindungen = new ArrayList<>();
    private final HashMap<Integer, ElementImpl> elementMap = new HashMap<>();

    private final ArrayList<ElementImpl> zustaende = new ArrayList<>();
    private final ArrayList<ElementImpl> ausloeser = new ArrayList<>();
    private final ArrayList<ElementImpl> elementNichtEingeordnet = new ArrayList<>();

    private final ArrayList<ElementInterface> interfaces = new ArrayList<>();
    private final ArrayList<ElementKlasse> klassen = new ArrayList<>();

    public Modell erstellen() {
        if (unaufgeloesteVerbindungen.size() > 0) {
            throw new IllegalStateException("Es existieren unaufeglöste Verbindungen");
        }
        ModellImpl modell = new ModellImpl(wurzelPakete, pakete, elemente,
                verbindungen, zustaende, ausloeser, interfaces, klassen);

        return modell;
    }

    public Paket erstellePaket(String name, int id, Paket eltern, Stereotypen stereotypen) {
        PaketImpl elternImpl = (PaketImpl) eltern;
        PaketImpl paket = new PaketImpl(name, id, eltern);
        paket.hinzufuegenStereotypen(stereotypen);

        fuegeHinzu(paket);
        if (eltern != null) {
            elternImpl.fuegeHinzu(paket);
        }

        return paket;
    }

    public Element erstelleElement(String name, int id, String typ, Paket paket, Stereotypen stereotypen) {
        ElementImpl element = null;
        switch (typ) {
            case "Interface":
                element = new ElementInterface(name, id, typ);
                break;
            case "Class":
                element = new ElementKlasse(name, id, typ);
                break;
            default:
                element = new ElementImpl(name, id, typ);
                break;
        }

        element.hinzufuegenStereotypen(stereotypen);

        PaketImpl paketImpl = (PaketImpl) paket;
        paketImpl.fuegeHinzu(element);
        elemente.add(element);
        elementMap.put(id, element);
        elementEinordnen(element);
        versucheVerbindungenAufzuloesen();
        if (element.typ().equals("Class")) {
            ElementKlasse ek = (ElementKlasse) element;
            klassen.add(ek);
        }
        if (element.typ().equals("Interface")) {
            ElementInterface ei = (ElementInterface) element;
            interfaces.add(ei);
        }
        return element;
    }

    public Element erstelleElement(String name, int id, String typ, Element elternElement, Stereotypen stereotypen) {
        ElementImpl element = null;
        switch (typ) {
            case "Interface":
                element = new ElementInterface(name, id, typ);
                break;
            case "Class":
                element = new ElementKlasse(name, id, typ);
                break;
            default:
                element = new ElementImpl(name, id, typ);
                break;
        }
        element.hinzufuegenStereotypen(stereotypen);

        ElementImpl elternElementImpl = (ElementImpl) elternElement;
        elternElementImpl.fuegeHinzu(element);
        elemente.add(element);
        elementMap.put(id, element);
        elementEinordnen(element);
        versucheVerbindungenAufzuloesen();
        if (element.typ().equals("Class")) {
            ElementKlasse ek = (ElementKlasse) element;
            klassen.add(ek);
        }
        if (element.typ().equals("Interface")) {
            ElementInterface ei = (ElementInterface) element;
            interfaces.add(ei);
        }
        return element;
    }

    public Methode erstelleMethode(int id, String returnType, String name, String visibility, boolean isQuery, boolean isLeaf, boolean isStatic, boolean isPure, boolean isConst, boolean isAbstract, Element elternElement, Stereotypen stereotypen) {
        MethodeImpl methode = new MethodeImpl(id, returnType, name, visibility, isQuery, isLeaf, isStatic, isPure, isConst, isAbstract);
        methode.hinzufuegenStereotypen(stereotypen);

        ElementInterface elternElementImpls = (ElementInterface) elternElement;
        elternElementImpls.fuegeHinzu(methode);

        return methode;
    }

    public Attribut erstelleAttribut(String name, int id, String typ, String visibility, boolean isStatic, boolean isConst, String Default, Element elternElement, Stereotypen stereotypen) {
        AttributImpl attribut = new AttributImpl(Default, name, visibility, id, typ, isStatic, isConst);
        attribut.hinzufuegenStereotypen(stereotypen);

        ElementKlasse elternElementImpl = (ElementKlasse) elternElement;
        elternElementImpl.fuegeHinzu(attribut);

        return attribut;
    }

    public Parameter erstelleParameter(String name, String type, String kind, boolean isConst, Methode methode, Stereotypen stereotypen) {
        ParameterImpl parameter = new ParameterImpl(name, type, kind, isConst);
        parameter.hinzufuegenStereotypen(stereotypen);

        MethodeImpl methodeImpl = (MethodeImpl) methode;
        methodeImpl.fuegeHinzu(parameter);

        return parameter;
    }

    public void erstelleVerbindung(String typ, int ankerElementId, int zielElementId, Stereotypen stereotypes, String transitionAction, String transitionEvent, String transitionGuard) {
        VerbindungId w = new VerbindungId(typ, ankerElementId, zielElementId, transitionAction, transitionEvent, transitionGuard);
        for (Stereotyp s : stereotypes.liefereStereotypen()) {
            w.hinzufuegenStereotyp(s);
        }

        if (!aufgeloesteVerbindungen.contains(w) && !unaufgeloesteVerbindungen.contains(w)) {
            VerbindungImpl v = erstelleVerbindung(w);
            if (v == null) {
                unaufgeloesteVerbindungen.add(w);
            }
        }

        versucheVerbindungenAufzuloesen();
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
            sb.append("Verbindung ").append(v.toString()).append("\n");
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
        sb.append("Anzahl unaufgelöster Verbindungen: ").append(unaufgeloesteVerbindungen.size());
        return sb.toString();
    }

    private VerbindungImpl erstelleVerbindung(String typ, ElementImpl anker, ElementImpl ziel, ElementImpl transitionEvent, String transitionAction, String transitionGuard) {
        VerbindungImpl verbindung = new VerbindungImpl(typ, anker, ziel, transitionEvent, transitionAction, transitionGuard);
        verbindungen.add(verbindung);
        anker.fuegeHinzu(verbindung);
        return verbindung;
    }

    private VerbindungImpl erstelleVerbindung(VerbindungId uv) {
        VerbindungImpl verbindung = null;
        ElementImpl ziel = element(uv.zielElementId);
        if (ziel != null) {
            ElementImpl anker = element(uv.ankerElementId);
            if (anker != null) {
                ElementImpl event = null;
                if (uv.transitionAusloeser != null && uv.transitionAusloeser.length() > 0) {
                    event = sucheAusloeser(uv.transitionAusloeser);
                    if (event == null) {
                        return verbindung;
                    }
                }

                verbindung = erstelleVerbindung(uv.typ, anker, ziel, event, uv.transitionAktion, uv.transitionBedingung);
                if (unaufgeloesteVerbindungen.contains(uv)) {
                    unaufgeloesteVerbindungen.remove(uv);
                }
                aufgeloesteVerbindungen.add(uv);
                for (Stereotyp s : uv.liefereStereotypen()) {
                    verbindung.hinzufuegenStereotyp(s);
                }
            }
        }
        return verbindung;
    }

    private void versucheVerbindungenAufzuloesen() {
        ArrayList<VerbindungId> tmp = new ArrayList<>(unaufgeloesteVerbindungen);
        for (VerbindungId uv : tmp) {
            VerbindungImpl v = erstelleVerbindung(uv);
        }
    }

    private ElementImpl element(int id) {
        return elementMap.get(id);
    }

    private void fuegeHinzu(PaketImpl p) {
        pakete.add(p);
        if (p.istWurzelPaket()) {
            wurzelPakete.add(p);
        }
    }

    private void elementEinordnen(ElementImpl element) {
        if (null == element.typ()) {
            elementNichtEingeordnet.add(element);
        } else {
            switch (element.typ()) {
                case "State":
                    zustaende.add(element);
                    break;
                case "Trigger":
                    ElementImpl e = sucheAusloeser(element.name());
                    if (e != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Namen der Trigger müssen eindeutig sein:")
                                .append("\n\t").append(e.nameExt())
                                .append("\n\t").append(element.nameExt());
                        throw new IllegalArgumentException(sb.toString());
                    }
                    ausloeser.add(element);
                    break;
                default:
                    elementNichtEingeordnet.add(element);
                    break;
            }
        }
    }

    private ElementImpl sucheAusloeser(String name) {
        for (ElementImpl e : ausloeser) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }

    private class VerbindungId implements Comparable<VerbindungId>, Serializable, Stereotypisierbar {

        private final String typ;
        private final int ankerElementId;
        private final int zielElementId;
        private final Stereotypen stereotypen = new Stereotypen();
        private final String transitionAktion;
        private final String transitionAusloeser;
        private final String transitionBedingung;

        private VerbindungId(String typ, int ankerElementId, int zielElementId, String transitionAktion, String transitionAusloeser, String transitionBedingung) {
            this.typ = typ;
            this.ankerElementId = ankerElementId;
            this.zielElementId = zielElementId;
            this.transitionAusloeser = transitionAusloeser;
            this.transitionBedingung = transitionBedingung;
            this.transitionAktion = transitionAktion;
        }

        private void hinzufuegenStereotyp(Stereotyp s) {
            stereotypen.hinzufuegenStereotyp(s);
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
            StringBuilder sb = new StringBuilder();
            sb.append(typ);
            if (stereotypen.sindStereotypenEnthalten()) {
                sb.append(" ").append(stereotypen.toString());
            }
            sb.append(", Anker: ").append(ankerElementId).append(", Ziel: ").append(zielElementId);
            return sb.toString();
        }

        @Override
        public int compareTo(VerbindungId t) {
            int ret = typ.compareTo(t.typ);
            if (ret != 0) {
                return ret;
            } else {
                if (ankerElementId < t.ankerElementId) {
                    return -1;
                } else if (ankerElementId > t.ankerElementId) {
                    return 1;
                } else if (zielElementId < t.zielElementId) {
                    return -1;
                } else if (zielElementId > t.zielElementId) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof VerbindungId)) {
                return false;
            }
            return compareTo((VerbindungId) obj) == 0;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 37 * result + typ.hashCode();
            result = 37 * result + ankerElementId;
            result = 37 * result + zielElementId;
            return result;
        }
    }
}
