/** 
 * @author Thomas Mahr (www.tmahr.de)
 * @version $Rev: 4077 $
 * @date $Date: 2016-12-04 14:09:05 +0100 (So, 04. Dez 2016) $
 */
package de.tmahr.eap.model;

import java.io.Serializable;

public interface Element extends Serializable, Stereotypisierbar 
{
    int id();
    String name();
    String nameExt();
    String nameMitId();
    String toString(String tab);
    String typ();
}
