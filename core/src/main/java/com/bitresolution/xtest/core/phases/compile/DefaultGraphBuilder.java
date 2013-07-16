package com.bitresolution.xtest.core.phases.compile;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.bitresolution.xtest.Node;
import com.bitresolution.xtest.core.phases.compile.nodes.ClassNode;
import com.bitresolution.xtest.core.phases.compile.nodes.MethodNode;
import com.bitresolution.xtest.core.phases.compile.nodes.XNode;
import com.bitresolution.xtest.core.phases.sources.Sources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static com.bitresolution.xtest.core.phases.compile.relationships.RelationshipBuilder.where;

@Component
public class DefaultGraphBuilder implements GraphBuilder {

    private static final Logger log = LoggerFactory.getLogger(DefaultGraphBuilder.class);

    private JungTestGraph graph = new JungTestGraph();

    public void from(FullyQualifiedClassName name) throws CompileGraphException {
        try {
            from(name.loadClass());
        }
        catch (ClassNotFoundException e) {
            throw new CompileGraphException("Error loading class: " + name, e);
        }
    }

    public void from(Class<?> klass) throws CompileGraphException {
        if(klass.isAnnotationPresent(Node.class)){
            processClass(klass, graph);
        }
        log.error("Class " + klass.getName() + " is not an xTest node");
    }

    @Override
    public GraphBuilder add(Sources input) throws CompileGraphException {
        for(FullyQualifiedClassName name : input.getClasses()) {
            from(name);
        }
        return this;
    }

    private void processClass(Class<?> klass, JungTestGraph graph) throws CompileGraphException {
        XNode node = buildClassNode(klass, graph);
        XNode rootNode = graph.getRootNode();
        graph.addRelationship(rootNode, node, where(rootNode).contains(node));
    }

    private ClassNode buildClassNode(Class<?> klass, JungTestGraph graph) throws CompileGraphException {
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

    private MethodNode buildMethodNode(Method method, JungTestGraph graph) throws CompileGraphException {
        MethodNode methodNode = new MethodNode(method);
        graph.addNode(methodNode);
        return methodNode;
    }

    @Override
    public TestGraph build() {
        return graph;
    }
}
