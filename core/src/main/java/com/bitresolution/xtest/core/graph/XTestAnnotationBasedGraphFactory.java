package com.bitresolution.xtest.core.graph;

import com.bitresolution.xtest.Node;
import com.bitresolution.xtest.core.graph.nodes.ClassNode;
import com.bitresolution.xtest.core.graph.nodes.MethodNode;
import com.bitresolution.xtest.core.graph.nodes.XNode;
import com.bitresolution.xtest.reflection.FullyQualifiedClassName;
import com.bitresolution.xtest.reflection.Package;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;

import static com.bitresolution.xtest.core.graph.relationships.RelationshipBuilder.where;

public class XTestAnnotationBasedGraphFactory implements GraphFactory {

    @Override
    public TestGraph from(FullyQualifiedClassName name) throws TestGraphException {
        try {
            return from(name.loadClass());
        }
        catch (ClassNotFoundException e) {
            throw new TestGraphException("Error loading class: " + name, e);
        }
    }

    @Override
    public TestGraph from(Class<?> klass) throws TestGraphException {
        if(klass.isAnnotationPresent(Node.class)){
            JungTestGraph graph = new JungTestGraph();
            processClass(klass, graph);
            return graph;
        }
        throw new TestGraphException("Class " + klass.getName() + " is not an xTest node");
    }

    private void processClass(Class<?> klass, JungTestGraph graph) throws TestGraphException {
        XNode node = buildClassNode(klass, graph);
        XNode rootNode = graph.getRootNode();
        graph.addRelationship(rootNode, node, where(rootNode).contains(node));
    }

    @Override
    public TestGraph from(Package p) throws TestGraphException {
        Reflections reflections = new Reflections(p.getName());
        Set<Class<?>> klasses = reflections.getTypesAnnotatedWith(Node.class);
        JungTestGraph graph = new JungTestGraph();
        for(Class<?> klass : klasses) {
            processClass(klass, graph);
        }
        return graph;
    }

    private ClassNode buildClassNode(Class<?> klass, JungTestGraph graph) throws TestGraphException {
        ClassNode classNode = new ClassNode(new FullyQualifiedClassName(klass));
        graph.addNode(classNode);
        for(Method method : klass.getMethods()) {
            if(method.isAnnotationPresent(Node.class)) {
                XNode methodNode = buildMethodNode(method, graph);
                graph.addRelationship(classNode, methodNode, where(classNode).contains(methodNode));
            }
        }
        return classNode;
    }

    private MethodNode buildMethodNode(Method method, JungTestGraph graph) throws TestGraphException {
        MethodNode methodNode = new MethodNode(method);
        graph.addNode(methodNode);
        return methodNode;
    }
}
