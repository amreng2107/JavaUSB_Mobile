package de.jservice.kidsgard.Components;

import java.util.EventListener;

/**
 *
 * @author AmrReda
 */
public interface TableButtonListener extends EventListener {
  public void tableButtonClicked( int row, int col );
    
}
