package org.fontysvenlo.cwb.registry;

import java.util.ArrayList;
import java.util.Collections;
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
public class AnnotationRegistry {

    private static class Holder<T> {

        @SuppressWarnings("rawtypes")
        private static final AnnotationRegistry REGISTRY
                = new AnnotationRegistry();
    }

    /**
     * Get the registry instance
     *
     * @return
     */
    public static AnnotationRegistry getInstance() {
        return Holder.REGISTRY;
    }
    private final ConcurrentMap<Class<?>, ConcurrentMap<String, ListPair<?>>> register
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
    public <A extends Annotation> void addAnnotation(A an, String relFilePath,
            int lineNumber) {
        List<?> anList = ensureFileMapping(an.getClass(), relFilePath);
        @SuppressWarnings("unchecked")
        List<A> uList = (List<A>) anList;
        uList.add(an);
        logger.log(Level.INFO, String.format(
                "Added to file relFilePath %s, annotation %s at line %d",
                relFilePath, an, lineNumber));
    }

    /**
     * Remove an annotation for file. If the list of annotation is empty, the
     * file is removed from the mapping too.
     *
     * @param <A> annotation type
     * @param an annotation
     * @param relFilePath for file
     */
    public <A extends Annotation> void removeAnnotation(A an, String relFilePath) {
        List<?> list = register.get(an.getClass()).get(relFilePath).privateList;
        list.remove(an);
        if (list.isEmpty()) {
            register.get(an.getClass()).remove(relFilePath);
        }
    }

    /**
     * Get the annotation of a annotation type on a file.
     *
     * @param aClass
     * @param relFilePath
     * @return
     */
    public <A extends Annotation> List<A> getAnnotations(Class<?> aClass,
            String relFilePath) {
        ListPair<?> result = null;
        ConcurrentMap<?, ListPair<?>> fileMap = register.get(aClass);
        if (fileMap != null) {
            result = fileMap.get(relFilePath);
        }
        if (result == null) {
            result = new ListPair<>(aClass);
        }
        @SuppressWarnings("unchecked")
        List<A> r = (List<A>) result.publicList;
        return r;
    }

    /**
     * Get the number of registered annotations. For tests only
     *
     * @return
     */
    <A extends Annotation> int registerCount() {
        int result = 0;
        for (ConcurrentMap<?, ListPair<?>> m : register.values()) {
            for (ListPair<?> l : m.values()) {
                result += l.privateList.size();
            }
        }
        return result;
    }

    <A extends Annotation> int count(Class<A> aClass, String relFitePath) {
        int result = 0;
        if (!register.containsKey(aClass)) {
            return result;
        }
        if (!register.get(aClass).containsKey(relFitePath)) {
            return result;
        }
        result = register.get(aClass).get(relFitePath).privateList.size();
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
        register.get(aClass).get(relFitePath).privateList.clear();
        register.get(aClass).remove(relFitePath);
    }

    /**
     * Ensure taht a mapping from type to map of string to list exists.
     *
     * @param aClass
     * @return
     */
    private ConcurrentMap<String, ListPair<?>> ensureClassMapping(
            Class<?> aClass) {
        if (!register.containsKey(aClass)) {
            synchronized (register) {
                ConcurrentMap<String, ListPair<?>> fileMap
                        = new ConcurrentHashMap<>();
                register.putIfAbsent(aClass, fileMap);
            }
        }

        ConcurrentMap<String, ListPair<?>> res = register.get(aClass);
        return res;
    }

    private <A extends Annotation> List<?> ensureFileMapping(Class<A> aClass,
            String relFilePath) {
        ConcurrentMap<String, ListPair<?>> fileMap = ensureClassMapping(aClass);
        if (!fileMap.containsKey(relFilePath)) {
            synchronized (fileMap) {
                ListPair<A> aList = new ListPair<>(aClass);
                //Collections.checkedList(aList,aClass);
                fileMap.putIfAbsent(relFilePath, aList);
            }
        }
        return fileMap.get(relFilePath).privateList;
    }

    private static class ListPair<A> {

        final List<?> privateList;
        final List<?> publicList;

        private ListPair(Class<A> clazz) {
            this.privateList = Collections.
                    checkedList(new ArrayList<A>(), clazz);
            this.publicList = Collections.unmodifiableList(privateList);
        }

    }
    private static final Logger logger = Logger.getLogger(
            AnnotationRegistry.class.getName());
}
