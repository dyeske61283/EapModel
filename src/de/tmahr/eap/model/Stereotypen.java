/** 
 * @author Thomas Mahr (www.tmahr.de)
 * @version $Rev: 4080 $
 * @date $Date: 2016-12-05 18:33:32 +0100 (Mo, 05. Dez 2016) $
 */
package de.tmahr.eap.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Stereotypen implements Serializable
{
    private final ArrayList<Stereotyp> stereotypes = new ArrayList<>();
    
    public void hinzufuegenStereotyp(Stereotyp s)
    {
        if(!stereotypes.contains(s) && s.name().length()>0)
        {
            stereotypes.add(s);
        }
    }
    
    void hinzufuegenStereotypen(Stereotypen st)
    {
        for(Stereotyp s : st.stereotypes)
        {
            if(!stereotypes.contains(s) && s.name().length()>0)
            {
                stereotypes.add(s);
            }
        }
    }
    
    public Stereotyp[] liefereStereotypen()
    {
        return stereotypes.toArray(new Stereotyp[0]);
    }    
    
    public boolean sindStereotypenEnthalten()
    {
        return !stereotypes.isEmpty();
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        if(!stereotypes.isEmpty())
        {
            sb.append(stereotypes.get(0));
            for(int i=1; i<stereotypes.size(); i++)
            {
                sb.append(",").append(stereotypes.get(1));
            }
        }
        return sb.toString();
    }
}
