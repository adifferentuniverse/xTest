package com.bitresolution.xtest.phases.sources;

import com.bitresolution.succor.junit.category.Unit;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

@org.junit.experimental.categories.Category(Unit.class)public class ClassSourceSpec {

    @Test(expected = NullPointerException.class)
    public void shouldNotAllowNullClassNames() {
        new ClassSource(null);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(ClassSource.class)
                .usingGetClass()
                .verify();

    }
}
