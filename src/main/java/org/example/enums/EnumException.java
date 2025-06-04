package org.example.enums;

public enum EnumException {

    WEB_SERVLET_MISSING_ANNOTATION("Não foi encontrada anotação WebServlet para a o servlet"),
    WEB_SERVLET_MISSING_PATH("Nao foi possivel encontrar o path do servlet");

    private final String message;

    EnumException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
