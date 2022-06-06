package de.studentenverwaltung.exceptions;

import de.studentenverwaltung.gui.ErrorCallback;

public class UserInputException extends Exception{
    public UserInputException(String message, ErrorCallback errorCallback) {
        super(message);
        errorCallback.showErrorMessage(message);
    }
}