package fr.cytech.projetgenielogiciel;

import java.io.*;

/**
 * This class provides methods to serialize and deserialize objects.
 * That's kind of it really.
 */
public final class Serializer {
    /**
     * Serializes an object to a file.
     *
     * @param object   the object to Serialize
     * @param filename the file to write to
     * @param <T>      the type of the object
     */
    public static <T extends Serializable> void serialize(T object, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(object);
        }
    }

    /**
     * Deserializes an object from a file.
     *
     * @param filename  the file to read from
     * @param classname the class of the object
     * @param <T>       the type of the object
     * @return the deserialized object
     */
    public static <T extends Serializable> T deserialize(String filename, Class<T> classname)
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return classname.cast(in.readObject());
        }
    }
}
