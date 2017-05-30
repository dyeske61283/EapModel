/** 
 * @author Thomas Mahr (www.tmahr.de)
 * @version $Rev: 4081 $
 * @date $Date: 2016-12-05 22:55:01 +0100 (Mo, 05. Dez 2016) $
 */
package de.tmahr.eap.model;

import java.io.Serializable;

public interface Modell extends Serializable 
{
    Paket[] wurzelPakete();
    Paket[] pakete();
    Element[] elemente();
    Element[] elemente(String typ);
    Verbindung[] verbindungen();
    Verbindung[] verbindungenAusgehend(Element anker, String verbindungstyp);
}
