package com.bitresolution.xtest.core.graph;

import com.bitresolution.xtest.Node;
import com.bitresolution.xtest.core.graph.nodes.ClassNode;
import com.bitresolution.xtest.core.graph.nodes.MethodNode;
import com.bitresolution.xtest.core.graph.relationships.Relationships;
import com.bitresolution.xtest.reflection.FullyQualifiedClassName;
import org.reflections.Reflections;
import com.bitresolution.xtest.reflection.Package;

import java.lang.reflect.Method;
import java.util.Set;

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
        com.bitresolution.xtest.core.graph.nodes.Node node = buildClassNode(klass, graph);
        graph.getRootNode().addNode(node, Relationships.contains());
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
        ClassNode classNode = new ClassNode(new FullyQualifiedClassName(klass), graph);
        for(Method method : klass.getMethods()) {
            if(method.isAnnotationPresent(Node.class)) {
                com.bitresolution.xtest.core.graph.nodes.Node methodNode = buildMethodNode(method, graph);
                classNode.addNode(methodNode, Relationships.contains());
            }
        }
        return classNode;
    }

    private MethodNode buildMethodNode(Method method, JungTestGraph graph) {
        return new MethodNode(method, graph);
    }
}
