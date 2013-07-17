package com.bitresolution.xtest.core.phases.compile;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.bitresolution.succor.reflection.FullyQualifiedMethodName;
import com.bitresolution.xtest.Node;
import com.bitresolution.xtest.core.phases.compile.nodes.ClassNode;
import com.bitresolution.xtest.core.phases.compile.nodes.MethodNode;
import com.bitresolution.xtest.core.phases.compile.nodes.XNode;
import com.bitresolution.xtest.core.phases.sources.Sources;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Set;

import static com.bitresolution.xtest.core.phases.compile.relationships.RelationshipBuilder.where;
import static org.reflections.ReflectionUtils.withAnnotation;

@Component
public class DefaultGraphBuilder implements GraphBuilder {

    private static final Logger log = LoggerFactory.getLogger(DefaultGraphBuilder.class);

    private final JungTestGraph graph = new JungTestGraph();
    private final XNode rootNode = graph.getRootNode();

    @Override
    public GraphBuilder add(Sources input) throws CompileGraphException {
        for(FullyQualifiedClassName fqcn : input.getClasses()) {
            try {
                Class<?> klass = fqcn.loadClass();

                @SuppressWarnings("unchecked")
                Set<Method> collectedMethods = ReflectionUtils.getAllMethods(klass, withAnnotation(Node.class));

                if(klass.isAnnotationPresent(Node.class) || !collectedMethods.isEmpty()) {
                    buildClassNode(fqcn, collectedMethods);
                }
                else {
                    log.error("Class {} is not an xTest node", fqcn.getFullyQualifiedName());
                }
            }
            catch (ClassNotFoundException e) {
                throw new CompileGraphException("Error loading class: " + fqcn, e);
            }
        }
        return this;
    }

    private void buildClassNode(FullyQualifiedClassName fqcn, Set<Method> annotatedMethods) throws CompileGraphException {
        ClassNode classNode = new ClassNode(fqcn);
        graph.addNode(classNode);
        graph.addRelationship(rootNode, classNode, where(rootNode).contains(classNode));
        log.debug("Adding class node for '{}' to graph", fqcn.getFullyQualifiedName());

        for(Method m : annotatedMethods) {
            FullyQualifiedMethodName method = new FullyQualifiedMethodName(m);
            MethodNode methodNode = new MethodNode(method);
            graph.addNode(methodNode);
            graph.addRelationship(classNode, methodNode, where(classNode).contains(methodNode));
            log.debug("Adding method node for '{}' to graph", method.getFullyQualifiedName());
        }
    }

    @Override
    public TestGraph build() {
        return graph;
    }
}
