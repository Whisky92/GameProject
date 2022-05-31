package jsondatas;

import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public abstract class Repository<T> {

    protected Class<T> elementType;

    protected List<T> elements;

    /**
     * A constructor for Repository class
     * @param elementType the type of used object
     */
    protected Repository(Class<T> elementType) {
        Logger.info("Entering the constructor of Repository class");
        this.elementType = elementType;
        elements = new ArrayList<>();
    }

    /**
     * A function that adds the element given as a parameter to the List of elements
     * @param element the element being added
     */
    public void add(T element) {
        Logger.info("Entering the add method of Repository class");
        elements.add(element);
    }

    /**
     * {@return the unmodifiable List of elements}
     */
    public List<T> findAll() {
        Logger.info("Entering the findAll method of Repository class");
        return Collections.unmodifiableList(elements);
    }

}