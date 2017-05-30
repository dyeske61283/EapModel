/** 
 * @author Thomas Mahr (www.tmahr.de)
 * @version $Rev: 4080 $
 * @date $Date: 2016-12-05 18:33:32 +0100 (Mo, 05. Dez 2016) $
 */
package de.tmahr.eap.model;

import java.util.ArrayList;

class PaketImpl implements Paket
{
    private final String name;
    private final int id;
    private final String nameMitId;    
    private final Paket elternPaket;
    private final ArrayList<PaketImpl> kindPakete = new ArrayList<>();
    private final ArrayList<ElementImpl> elemente = new ArrayList<>();
    private final Stereotypen stereotypen = new Stereotypen();

    PaketImpl(String name, int id, Paket eltern)
    {
        this.name = name;
        this.id = id;
        nameMitId = name + " {" + String.valueOf(id) + "}";
        this.elternPaket = eltern;
    }
    
    @Override
    public String name() 
    {
        return name;
    }
    
    @Override
    public String nameExt() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append(nameMitId);
        if(stereotypen.sindStereotypenEnthalten())
        {
            sb.append(" ").append(stereotypen.toString());
        }
        return sb.toString();
    }
    
    @Override
    public String nameMitId() 
    {
        return nameMitId;
    }    

    @Override
    public int id()
    {
        return id;
    }

    @Override
    public Paket elternPaket() 
    {
        return elternPaket;
    }
    
    void fuegeHinzu(PaketImpl p)
    {
        kindPakete.add(p);
    }
    
    @Override
    public Paket[] pakete()
    {
        return kindPakete.toArray(new PaketImpl[0]);
    }
    
    void fuegeHinzu(ElementImpl element)
    {
        elemente.add(element);
    }
    
    @Override
    public ElementImpl[] elemente()
    {
        return elemente.toArray(new ElementImpl[0]);
    }

    @Override
    public ElementImpl element(int elementId)
    {
        for(ElementImpl element : elemente)
        {
            if(element.id() == elementId)
            {
                return element;
            }
        }        
        return null;
    }
    
    @Override
    public boolean istWurzelPaket()
    {
        return elternPaket==null;
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
        return toString("");
    }
    
    @Override
    public String toString(String tab)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(tab).append("Paket ").append(nameMitId);
        if(stereotypen.sindStereotypenEnthalten())
        {
            sb.append(" <<").append(stereotypen.toString()).append(">>");
        }
        if(elternPaket!=null)
        {
            sb.append(" (aus ").append(elternPaket.nameMitId());
        }
        
        tab += "-";

        if(!kindPakete.isEmpty())
        {
            sb.append("\n").append(tab).append("enthaltende Pakete:");
            for(PaketImpl p : kindPakete)
            {
                sb.append("\n").append(p.toString(tab+"-"));
            }
        }

        if (!elemente.isEmpty())
        {
            sb.append("\n").append(tab).append("enthaltende Elemente:");
            for(ElementImpl e : elemente)
            {
                sb.append("\n").append(e.toString(tab+"-"));
            }
        }
        
        return sb.toString();
    }
}
