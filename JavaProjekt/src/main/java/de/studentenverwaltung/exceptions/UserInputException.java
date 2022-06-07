package de.studentenverwaltung.exceptions;

import de.studentenverwaltung.gui.ErrorCallback;

public class UserInputException extends Exception{
    public UserInputException(String message, ErrorCallback errorCallback) {
        super(message);
        System.out.println(message);
        errorCallback.showErrorMessage(message);
    }
}
