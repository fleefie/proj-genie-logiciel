package fr.cytech.projetgenielogiciel;

import java.io.*;

public final class Serialiseur {
    public static <T extends Serializable> void serialiser(T objet, String fichier) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fichier))) {
            out.writeObject(objet);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deserialiser(String fichier, Class<T> classe)
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fichier))) {
            return classe.cast(in.readObject());
        }
    }
}
