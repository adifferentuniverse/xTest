package com.bitresolution.xtest.core.phases.sources;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class ClassSourceSpec {

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
