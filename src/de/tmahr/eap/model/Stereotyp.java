/** 
 * @author Thomas Mahr (www.tmahr.de)
 * @version $Rev: 4077 $
 * @date $Date: 2016-12-04 14:09:05 +0100 (So, 04. Dez 2016) $
 */
package de.tmahr.eap.model;

import java.io.Serializable;

public final class Stereotyp implements Comparable<Stereotyp>, Serializable
{
    private final String name;
    
    public Stereotyp(String name)
    {
        this.name = name;
    }
    
    public String name()
    {
        return name;
    }
    
    @Override
    public String toString()
    {
        return name;
    }    

    @Override
    public int compareTo(Stereotyp s)
    {
        return name.compareTo(s.name);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Stereotyp))
        {
            return false;
        }
        return compareTo((Stereotyp) obj)==0;
    }

    @Override
    public int hashCode()
    {
       int result = 17;
       result = 37 * result + name.hashCode();
       return result;
    }  
    
}
