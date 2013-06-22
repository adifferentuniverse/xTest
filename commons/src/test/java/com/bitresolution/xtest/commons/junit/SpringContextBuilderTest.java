package com.bitresolution.xtest.commons.junit;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.*;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DelegatingSmartContextLoader;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class SpringContextBuilderTest {

    private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
    private static final String[] EMPTY_STRING_ARRAY = new String[0];


    private void assertAttributes(ContextConfigurationAttributes attributes, Class<?> expectedDeclaringClass,
                                  String[] expectedLocations, Class<?>[] expectedClasses,
                                  Class<? extends ContextLoader> expectedContextLoaderClass, boolean expectedInheritLocations) {
        assertEquals(expectedDeclaringClass, attributes.getDeclaringClass());
        assertArrayEquals(expectedLocations, attributes.getLocations());
        assertArrayEquals(expectedClasses, attributes.getClasses());
        assertEquals(expectedInheritLocations, attributes.isInheritLocations());
        assertEquals(expectedContextLoaderClass, attributes.getContextLoaderClass());
    }

    private void assertLocationsFooAttributes(ContextConfigurationAttributes attributes) {
        assertAttributes(attributes, LocationsFoo.class, new String[] { "/foo.xml" }, EMPTY_CLASS_ARRAY,
                ContextLoader.class, false);
    }

    private void assertClassesFooAttributes(ContextConfigurationAttributes attributes) {
        assertAttributes(attributes, ClassesFoo.class, EMPTY_STRING_ARRAY, new Class<?>[] { FooConfig.class },
                ContextLoader.class, false);
    }

    private void assertLocationsBarAttributes(ContextConfigurationAttributes attributes) {
        assertAttributes(attributes, LocationsBar.class, new String[] { "/bar.xml" }, EMPTY_CLASS_ARRAY,
                AnnotationConfigContextLoader.class, true);
    }

    private void assertClassesBarAttributes(ContextConfigurationAttributes attributes) {
        assertAttributes(attributes, ClassesBar.class, EMPTY_STRING_ARRAY, new Class<?>[]{BarConfig.class},
                AnnotationConfigContextLoader.class, true);
    }

    private void assertMergedContextConfiguration(MergedContextConfiguration mergedConfig, Class<?> expectedTestClass,
                                                  String[] expectedLocations, Class<?>[] expectedClasses,
                                                  Class<? extends ContextLoader> expectedContextLoaderClass) {
        assertNotNull(mergedConfig);
        assertEquals(expectedTestClass, mergedConfig.getTestClass());
        assertNotNull(mergedConfig.getLocations());
        assertArrayEquals(expectedLocations, mergedConfig.getLocations());
        assertNotNull(mergedConfig.getClasses());
        assertArrayEquals(expectedClasses, mergedConfig.getClasses());
        assertNotNull(mergedConfig.getActiveProfiles());
        assertEquals(expectedContextLoaderClass, mergedConfig.getContextLoader().getClass());
    }

    @Test(expected = IllegalStateException.class)
    public void resolveContextConfigurationAttributesWithConflictingLocations() {
        SpringContextBuilder.resolveContextConfigurationAttributes(ConflictingLocations.class);
    }

    @Test
    public void resolveContextConfigurationAttributesWithBareAnnotations() {
        List<ContextConfigurationAttributes> attributesList = SpringContextBuilder.resolveContextConfigurationAttributes(BareAnnotations.class);
        assertNotNull(attributesList);
        assertEquals(1, attributesList.size());
        assertAttributes(attributesList.get(0), BareAnnotations.class, EMPTY_STRING_ARRAY, EMPTY_CLASS_ARRAY,
                ContextLoader.class, true);
    }

    @Test
    public void resolveContextConfigurationAttributesWithLocalAnnotationAndLocations() {
        List<ContextConfigurationAttributes> attributesList = SpringContextBuilder.resolveContextConfigurationAttributes(LocationsFoo.class);
        assertNotNull(attributesList);
        assertEquals(1, attributesList.size());
        assertLocationsFooAttributes(attributesList.get(0));
    }

    @Test
    public void resolveContextConfigurationAttributesWithLocalAnnotationAndClasses() {
        List<ContextConfigurationAttributes> attributesList = SpringContextBuilder.resolveContextConfigurationAttributes(ClassesFoo.class);
        assertNotNull(attributesList);
        assertEquals(1, attributesList.size());
        assertClassesFooAttributes(attributesList.get(0));
    }

    @Test
    @Ignore
    public void resolveContextConfigurationAttributesWithLocalAndInheritedAnnotationsAndLocations() {
        List<ContextConfigurationAttributes> attributesList = SpringContextBuilder.resolveContextConfigurationAttributes(LocationsBar.class);
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());
        assertLocationsFooAttributes(attributesList.get(0));
        assertLocationsBarAttributes(attributesList.get(1));
    }

    @Test
    @Ignore
    public void resolveContextConfigurationAttributesWithLocalAndInheritedAnnotationsAndClasses() {
        List<ContextConfigurationAttributes> attributesList = SpringContextBuilder.resolveContextConfigurationAttributes(ClassesBar.class);
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());
        assertClassesFooAttributes(attributesList.get(0));
        assertClassesBarAttributes(attributesList.get(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildMergedContextConfigurationWithoutAnnotation() {
        SpringContextBuilder.getContextConfiguration(Enigma.class);
    }

    @Test
//    @Ignore
    public void buildMergedContextConfigurationWithBareAnnotations() {
        Class<BareAnnotations> testClass = BareAnnotations.class;
        MergedContextConfiguration mergedConfig = SpringContextBuilder.getContextConfiguration(testClass);

        assertMergedContextConfiguration(
                mergedConfig,
                testClass,
                new String[] { "classpath:/com/bitresolution/test/xtest/commons/junit/SpringContextBuilderTests$BareAnnotations-context.xml" },
                EMPTY_CLASS_ARRAY, DelegatingSmartContextLoader.class);
    }

    @Test
    public void buildMergedContextConfigurationWithLocalAnnotationAndLocations() {
        Class<?> testClass = LocationsFoo.class;
        MergedContextConfiguration mergedConfig = SpringContextBuilder.getContextConfiguration(testClass);

        assertMergedContextConfiguration(mergedConfig, testClass, new String[] { "classpath:/foo.xml" },
                EMPTY_CLASS_ARRAY, DelegatingSmartContextLoader.class);
    }

    @Test
    public void buildMergedContextConfigurationWithLocalAnnotationAndClasses() {
        Class<?> testClass = ClassesFoo.class;
        MergedContextConfiguration mergedConfig = SpringContextBuilder.getContextConfiguration(testClass);

        assertMergedContextConfiguration(mergedConfig, testClass, EMPTY_STRING_ARRAY,
                new Class<?>[] { FooConfig.class }, DelegatingSmartContextLoader.class);
    }

    @Test
    public void buildMergedContextConfigurationWithLocalAndInheritedAnnotationsAndLocations() {
        Class<?> testClass = LocationsBar.class;
        String[] expectedLocations = new String[] { "/foo.xml", "/bar.xml" };

        MergedContextConfiguration mergedConfig = SpringContextBuilder.getContextConfiguration(testClass);
        assertMergedContextConfiguration(mergedConfig, testClass, expectedLocations, EMPTY_CLASS_ARRAY,
                AnnotationConfigContextLoader.class);
    }

    @Test
    public void buildMergedContextConfigurationWithLocalAndInheritedAnnotationsAndClasses() {
        Class<?> testClass = ClassesBar.class;
        Class<?>[] expectedClasses = new Class<?>[] { FooConfig.class, BarConfig.class };

        MergedContextConfiguration mergedConfig = SpringContextBuilder.getContextConfiguration(testClass);
        assertMergedContextConfiguration(mergedConfig, testClass, EMPTY_STRING_ARRAY, expectedClasses,
                AnnotationConfigContextLoader.class);
    }

    @Test
    public void resolveActiveProfilesWithoutAnnotation() {
        String[] profiles = SpringContextBuilder.resolveActiveProfiles(Enigma.class);
        assertArrayEquals(EMPTY_STRING_ARRAY, profiles);
    }

    @Test
    public void resolveActiveProfilesWithNoProfilesDeclared() {
        String[] profiles = SpringContextBuilder.resolveActiveProfiles(BareAnnotations.class);
        assertArrayEquals(EMPTY_STRING_ARRAY, profiles);
    }

    @Test
    public void resolveActiveProfilesWithEmptyProfiles() {
        String[] profiles = SpringContextBuilder.resolveActiveProfiles(EmptyProfiles.class);
        assertArrayEquals(EMPTY_STRING_ARRAY, profiles);
    }

    @Test
    public void resolveActiveProfilesWithDuplicatedProfiles() {
        String[] profiles = SpringContextBuilder.resolveActiveProfiles(DuplicatedProfiles.class);
        assertNotNull(profiles);
        assertEquals(3, profiles.length);

        List<String> list = Arrays.asList(profiles);
        assertTrue(list.contains("foo"));
        assertTrue(list.contains("bar"));
        assertTrue(list.contains("baz"));
    }

    @Test
    public void resolveActiveProfilesWithLocalAnnotation() {
        String[] profiles = SpringContextBuilder.resolveActiveProfiles(LocationsFoo.class);
        assertNotNull(profiles);
        assertArrayEquals(new String[] { "foo" }, profiles);
    }

    @Test
    public void resolveActiveProfilesWithInheritedAnnotationAndLocations() {
        String[] profiles = SpringContextBuilder.resolveActiveProfiles(InheritedLocationsFoo.class);
        assertNotNull(profiles);
        assertArrayEquals(new String[] { "foo" }, profiles);
    }

    @Test
    public void resolveActiveProfilesWithInheritedAnnotationAndClasses() {
        String[] profiles = SpringContextBuilder.resolveActiveProfiles(InheritedClassesFoo.class);
        assertNotNull(profiles);
        assertArrayEquals(new String[] { "foo" }, profiles);
    }

    @Test
    public void resolveActiveProfilesWithLocalAndInheritedAnnotations() {
        String[] profiles = SpringContextBuilder.resolveActiveProfiles(LocationsBar.class);
        assertNotNull(profiles);
        assertEquals(2, profiles.length);

        List<String> list = Arrays.asList(profiles);
        assertTrue(list.contains("foo"));
        assertTrue(list.contains("bar"));
    }

    @Test
    public void resolveActiveProfilesWithOverriddenAnnotation() {
        String[] profiles = SpringContextBuilder.resolveActiveProfiles(Animals.class);
        assertNotNull(profiles);
        assertEquals(2, profiles.length);

        List<String> list = Arrays.asList(profiles);
        assertTrue(list.contains("dog"));
        assertTrue(list.contains("cat"));
    }


    private static class Enigma {
    }

    @ContextConfiguration(value = "x", locations = "y")
    private static class ConflictingLocations {
    }

    @ContextConfiguration
    @ActiveProfiles
    private static class BareAnnotations {
    }

    @ActiveProfiles({ "    ", "\t" })
    private static class EmptyProfiles {
    }

    @ActiveProfiles({ "foo", "bar", "  foo", "bar  ", "baz" })
    private static class DuplicatedProfiles {
    }

    @Configuration
    private static class FooConfig {
    }

    @ContextConfiguration(locations = "/foo.xml", inheritLocations = false)
    @ActiveProfiles(profiles = "foo")
    private static class LocationsFoo {
    }

    @ContextConfiguration(classes = FooConfig.class, inheritLocations = false)
    @ActiveProfiles(profiles = "foo")
    private static class ClassesFoo {
    }

    private static class InheritedLocationsFoo extends LocationsFoo {
    }

    private static class InheritedClassesFoo extends ClassesFoo {
    }

    @Configuration
    private static class BarConfig {
    }

    @ContextConfiguration(locations = "/bar.xml", inheritLocations = true, loader = AnnotationConfigContextLoader.class)
    @ActiveProfiles("bar")
    private static class LocationsBar extends LocationsFoo {
    }

    @ContextConfiguration(classes = BarConfig.class, inheritLocations = true, loader = AnnotationConfigContextLoader.class)
    @ActiveProfiles("bar")
    private static class ClassesBar extends ClassesFoo {
    }

    @ActiveProfiles(profiles = { "dog", "cat" }, inheritProfiles = false)
    private static class Animals extends LocationsBar {
    }

}
