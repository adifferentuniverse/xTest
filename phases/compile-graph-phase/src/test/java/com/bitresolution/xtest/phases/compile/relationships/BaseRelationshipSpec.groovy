package com.bitresolution.xtest.phases.compile.relationships

import com.bitresolution.succor.junit.category.Unit
import com.bitresolution.xtest.phases.compile.nodes.GenericNode
import com.bitresolution.xtest.phases.compile.nodes.XNode
import com.bitresolution.xtest.phases.compile.relationships.BaseRelationship
import spock.lang.Specification

import javax.validation.constraints.NotNull

@org.junit.experimental.categories.Category(Unit.class)
class BaseRelationshipSpec extends Specification {

    def "relationships are equal if of same type and equal source and destination"() {
        expect:
        equal == left.equals(right)

        where:
        left | right | equal
        new RelationshipA(new GenericNode<String>("a"), new GenericNode<Integer>(1)) | new RelationshipA(new GenericNode<String>("a"), new GenericNode<Integer>(1)) | true
        new RelationshipA(new GenericNode<String>("a"), new GenericNode<Integer>(1)) | new RelationshipB(new GenericNode<String>("a"), new GenericNode<Integer>(1)) | false
        new RelationshipB(new GenericNode<String>("a"), new GenericNode<Integer>(1)) | new RelationshipA(new GenericNode<String>("a"), new GenericNode<Integer>(1)) | false
        new RelationshipA(new GenericNode<String>("a"), new GenericNode<Integer>(1)) | new RelationshipC(new GenericNode<Integer>(1), new GenericNode<String>("a")) | false
    }

    static class RelationshipA extends BaseRelationship<String, Integer> {
        RelationshipA(@NotNull XNode<String> source, @NotNull XNode<Integer> destination) {
            super(source, destination)
        }
    }
    static class RelationshipB extends BaseRelationship<String, Integer> {
        RelationshipB(@NotNull XNode<String> source, @NotNull XNode<Integer> destination) {
            super(source, destination)
        }
    }
    static class RelationshipC extends BaseRelationship<Integer, String> {
        RelationshipC(@NotNull XNode<Integer> source, @NotNull XNode<String> destination) {
            super(source, destination)
        }
    }
}
