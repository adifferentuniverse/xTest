package com.bitresolution.xtest.core.phases.sources;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class XTestAnnotatedSourceSpec {


    @Test(expected = NullPointerException.class)
    public void shouldNotAllowNullPackageNames() {
        new XTestAnnotatedSource(null);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(XTestAnnotatedSource.class)
                .usingGetClass()
                .verify();

    }
}
