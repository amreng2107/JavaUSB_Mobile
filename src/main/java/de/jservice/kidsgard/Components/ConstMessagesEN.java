package de.jservice.kidsgard.Components;

/**
 *
 * @author AmrReda
 */
public interface ConstMessagesEN {

    interface DialogTitles {
        
        String ADD_DEVICE = "Adding Device";
      
    }

    interface Messages {
        String WINDOWS_STYLE_LOADING_ERROR_MESSAGE = "There was an error while loading windows look an feel: ";
        String ALERT_TILE = "Alert";
        String NON_ROW_SELECTED = "Non row has been selected";
        String INFORMATION_TITLE = "Information";
        String DELETE_ROW_ERROR = "Could not delete a row. Check if there are any connections between tables.";
    }

    interface Labels {
        String DEVICES ="Devices";
       String ID ="id";
        String ADD_BTN = "Add";
        String CANCEL_BTN = "Cancel";
        String REMOVE_BTN = "Remove";
        String NAME = "Name";
        String EMAIL = "E-mail";
        String DEVICENAME = "Device Name";
        String ACCOUNT = "Account";
        String STATUS = "Status";
        
    }

    interface ValidationMessages {
        String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Not all required fields have been filled or filled data is incorrect";
        String PESEL_LENGTH_INCORRECT = "PESEL should contain 11 characters.";
        String DATE_FROM_MUST_BE_EARLIER_THAN_TO_DATE = "Date from must be earlier than date to";
    }
}
