/** 
 * @author Thomas Mahr (www.tmahr.de)
 * @version $Rev: 4083 $
 * @date $Date: 2016-12-06 21:38:34 +0100 (Di, 06. Dez 2016) $
 */
package de.tmahr.eap.model;

class VerbindungImpl implements Verbindung
{
    private final String typ;
    private final ElementImpl anker;
    private final ElementImpl ziel;
    private final Stereotypen stereotypen = new Stereotypen();
    private final ElementImpl transitionAusloeser;
    private final String transitionBedingung;
    private final String transitionAktion;

    public VerbindungImpl(String typ, ElementImpl anker, ElementImpl ziel, ElementImpl transitionAusloeser, String transitionAktion, String transitionBedingung)
    {
        this.typ = typ;
        this.anker = anker;
        this.ziel = ziel;
        this.transitionAusloeser = transitionAusloeser;
        this.transitionAktion = transitionAktion;
        this.transitionBedingung = transitionBedingung;
    }
    
    @Override
    public String nameExt() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append(typ);
        if(stereotypen.sindStereotypenEnthalten())
        {
            sb.append(" ").append(stereotypen.toString());
        }
        return sb.toString();
    }

    @Override
    public String typ() 
    {
        return typ;
    }

    @Override
    public Element anker() 
    {
        return anker;
    }

    @Override
    public Element ziel() 
    {
        return ziel;
    }
    
    @Override
    public boolean hatTransitionAusloeser()
    {
        return transitionAusloeser!=null;
    }
    
    @Override
    public boolean hatTransitionAktion()
    {
        return (transitionAktion!=null) && (transitionAktion.length()>0);
    }
    
    @Override
    public boolean hatTransitionBedingung()
    {
        return (transitionBedingung!=null) && (transitionBedingung.length()>0);
    }
    
    @Override
    public String transitionAktion()
    {
        return transitionAktion;
    }
    
    @Override
    public String transitionBedingung()
    {
        return transitionBedingung;
    }

    @Override
    public Element transitionAusloeser()
    {
        return transitionAusloeser;
    }

    void hinzufuegenStereotyp(Stereotyp s)
    {
        stereotypen.hinzufuegenStereotyp(s);
    }
    
    void hinzufuegenStereotypen(Stereotypen s)
    {
        stereotypen.hinzufuegenStereotypen(s);
    }    

    @Override
    public Stereotyp[] liefereStereotypen()
    {
        return stereotypen.liefereStereotypen();
    }    
    
    @Override
    public boolean istStereotypisiert()
    {
        return stereotypen.sindStereotypenEnthalten();
    }
    
    @Override
    public String stereotypenToString()
    {
        return stereotypen.toString();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(typ);
        if(stereotypen.sindStereotypenEnthalten())
        {
            sb.append(" <<").append(stereotypen.toString()).append(">>");
        }
        sb.append(", Anker: ").append(anker.nameMitId()).append(", Ziel: ").append(ziel.nameMitId());
        if(transitionAusloeser!=null)
        {
            sb.append(", transitionEvent: ").append(transitionAusloeser.nameExt());
        }
        if(transitionAktion!=null && transitionAktion.length()>0)
        {
            sb.append(", transitionAction: ").append(transitionAktion);
        }
        if(transitionBedingung!=null && transitionBedingung.length()>0)
        {
            sb.append(", transitionGuard: ").append(transitionBedingung);
        }
        return sb.toString();
    }    
 }
