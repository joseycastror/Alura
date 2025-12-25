package com.alura.literalura.model;

public enum Idioma {
    ES("es"), // Español
    EN("en"), // Inglés
    FR("fr"), // Francés
    PT("pt"), // Portugués
    DE("de"); // Alemán

    String idioma;

    private Idioma(String idioma) {
        this.idioma = idioma;
    }

    public static Idioma fromString(String texto) {
        for(Idioma idioma : Idioma.values()) {
            if (idioma.idioma.equalsIgnoreCase(texto)) {
                return idioma;
            }
        }        
        throw new UnsupportedOperationException("Unimplemented method 'fromString'");
    }
}
