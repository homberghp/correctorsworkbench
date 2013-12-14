package org.fontysvenlo.cwb.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.text.Annotation;

/**
 * Registry for active SolutionMarker Annotations. Annotation are registered
 * based on type and filename. Multiple annotation with the same information are
 * allowed.
 *
 * @author Pieter van den Hombergh
 */
public class AnnotationRegistry<A extends Annotation> {

    private static class Holder<T> {

        @SuppressWarnings("rawtypes")
        private static final AnnotationRegistry<?> REGISTRY
                = new AnnotationRegistry();
    }

    /**
     * Get the registry instance
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static AnnotationRegistry getInstance() {
        return Holder.REGISTRY;
    }
    private final ConcurrentMap<Class<?>, ConcurrentMap<String, List<A>>> register
            = new ConcurrentHashMap<>();

    /**
     * Add an annotation to register. The file path is used in the register, the
     * line number is only used for debugging, as a linenumber in a source file
     * is rather fragile (it changes easily) and cannot reliably be used in
     * lookup.
     *
     * @param an the annotation
     * @param relFilePath the file in which the annotation is placed
     * @param lineNumber the line number for information in logging only
     */
    @SuppressWarnings({"rawtypes","unchecked"})
    public <A extends Annotation> void addAnnotation(A an, String relFilePath,
            int lineNumber) {
        List anList = ensureFileMapping(an.getClass(), relFilePath);
        anList.add(an);
        logger.log(Level.INFO, String.format(
                "Added to file relFilePath %s, annotation %s at line %d",
                relFilePath, an, lineNumber));
    }

    /**
     * Get the annotation of a annotation type on a file.
     *
     * @param aClass
     * @param relFilePath
     * @return
     */
    public List<A> getAnnotations(Class<?> aClass, String relFilePath) {
        List<A> result = null;
        ConcurrentMap<String, List<A>> fileMap = register.get(aClass);
        if (fileMap != null) {
            result = fileMap.get(relFilePath);
        }
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    /**
     * Get the number of registered annotations. For tests only
     *
     * @return
     */
    int registerCount() {
        int result = 0;
        for (ConcurrentMap<String, List<A>> m : register.values()) {
            for (List<A> l : m.values()) {
                result += l.size();
            }
        }
        return result;
    }

    int count(Class<A> aClass, String relFitePath) {
        int result = 0;
        if (!register.containsKey(aClass)) {
            return result;
        }
        if (!register.get(aClass).containsKey(relFitePath)) {
            return result;
        }
        result = register.get(aClass).get(relFitePath).size();
        return result;
    }

    /**
     * Erase all stored information or annotation type and filename.
     *
     * @param <A> type of annotation
     * @param aClass annotation for which the instances must be erased for
     * @param relFitePath the file to erase annotations for
     */
    public <A extends Annotation> void clear(Class<A> aClass, String relFitePath) {
        if (!register.containsKey(aClass)) {
            return;
        }
        if (!register.get(aClass).containsKey(relFitePath)) {
            return;
        }
        register.get(aClass).get(relFitePath).clear();
        register.get(aClass).remove(relFitePath);
    }

    private ConcurrentMap<String, List<A>> ensureClassMapping(Class<?> aClass) {
        if (!register.containsKey(aClass)) {
            synchronized (register) {
                ConcurrentMap<String, List<A>> fileMap
                        = new ConcurrentHashMap<>();
                register.putIfAbsent(aClass, fileMap);
            }
        }
        return register.get(aClass);

    }

    private List<A> ensureFileMapping(Class<?> aClass, String relFilePath) {
        ConcurrentMap<String, List<A>> fileMap = ensureClassMapping(aClass);
        if (!fileMap.containsKey(relFilePath)) {
            synchronized (fileMap) {
                List<A> list = new ArrayList<>();
                fileMap.putIfAbsent(relFilePath, list);
            }
        }
        return fileMap.get(relFilePath);
    }

    private static final Logger logger = Logger.getLogger(
            AnnotationRegistry.class.getName());
}
