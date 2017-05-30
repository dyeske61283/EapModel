/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tmahr.eap.model;

import java.io.Serializable;

/**
 *
 * @author parallels
 */
public interface Attribut extends Serializable, Stereotypisierbar{
    
    String ToString(String tab);
    String name();
    String nameMitId();
    String nameExt();
    String typ();
    String visibility();
    boolean isStatic();
    boolean isConst();
    String Default();
    int id();
    
}
