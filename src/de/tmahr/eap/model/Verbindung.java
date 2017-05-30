/** 
 * @author Thomas Mahr (www.tmahr.de)
 * @version $Rev: 4083 $
 * @date $Date: 2016-12-06 21:38:34 +0100 (Di, 06. Dez 2016) $
 */
package de.tmahr.eap.model;

import java.io.Serializable;

public interface Verbindung extends Serializable, Stereotypisierbar
{
    String typ();
    Element ziel();
    Element anker();
    String nameExt();
    boolean hatTransitionAusloeser();
    boolean hatTransitionAktion();
    boolean hatTransitionBedingung();
    Element transitionAusloeser();
    String transitionAktion();
    String transitionBedingung();
}
