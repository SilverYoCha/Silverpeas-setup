/**
 * Copyright (C) 2000 - 2009 Silverpeas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * As a special exception to the terms and conditions of version 3.0 of
 * the GPL, you may redistribute this Program in connection with Free/Libre
 * Open Source Software ("FLOSS") applications as described in Silverpeas's
 * FLOSS exception.  You should have recieved a copy of the text describing
 * the FLOSS exception, and it is also available here:
 * "http://repository.silverpeas.com/legal/licensing"
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.silverpeas.dbbuilder;

import java.util.HashMap;

/**
 * Titre : dbBuilder Description : Builder des BDs Silverpeas Copyright :
 * Copyright (c) 2001 Soci�t� : Strat�lia Silverpeas
 * 
 * @author ATH
 * @version 1.0
 */

public class Orderer {

  public Orderer() {
  }

  /*
   * ordonne les �l�ments d'un HashMap structur�e comme suit : pour chaque item,
   * la cl� est le nom de l'item, et la valeur un vecteur de string d'items
   * prioritaires
   */
  public static HashMap order(HashMap hOri) {

    // trace sur la HashMap d'entr�e
    /*
     * Iterator result = hOri.keySet().iterator(); while ( result.hasNext() ) {
     * String key = (String) result.next(); System.out.print("\n" + key + ">>");
     * Object[] o = (Object[]) hOri.get(key); if (o != null) for (int
     * i=0;i<o.length;i++) System.out.print(o[i] + " ; "); System.out.println();
     * }
     */

    return null;
  }
}