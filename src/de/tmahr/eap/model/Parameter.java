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
public interface Parameter extends Serializable, Stereotypisierbar {
    String ToString(String tab);
    String name();
    String nameExt();
    String type();
    String kind();
    boolean isConst();
}
