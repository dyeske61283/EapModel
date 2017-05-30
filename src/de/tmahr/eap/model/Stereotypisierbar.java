/** 
 * @author Thomas Mahr (www.tmahr.de)
 * @version $Rev: 4080 $
 * @date $Date: 2016-12-05 18:33:32 +0100 (Mo, 05. Dez 2016) $
 */
package de.tmahr.eap.model;

public interface Stereotypisierbar
{
    Stereotyp[] liefereStereotypen();
    boolean istStereotypisiert();
    String stereotypenToString();
}
