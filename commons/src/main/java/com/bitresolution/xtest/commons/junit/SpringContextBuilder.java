package com.bitresolution.xtest.commons.junit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.context.*;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.*;

import static org.springframework.beans.BeanUtils.instantiateClass;
import static org.springframework.core.annotation.AnnotationUtils.findAnnotationDeclaringClass;

public class SpringContextBuilder {

    private static final Logger log = LoggerFactory.getLogger(SpringContextBuilder.class);

    private static final String DEFAULT_CONTEXT_LOADER_CLASS_NAME = "org.springframework.test.context.support.DelegatingSmartContextLoader";
    private static final String DEFAULT_WEB_CONTEXT_LOADER_CLASS_NAME = "org.springframework.test.context.web.WebDelegatingSmartContextLoader";

    private static final String WEB_APP_CONFIGURATION_CLASS_NAME = "org.springframework.test.context.web.WebAppConfiguration";
    private static final String WEB_MERGED_CONTEXT_CONFIGURATION_CLASS_NAME = "org.springframework.test.context.web.WebMergedContextConfiguration";


    public static ApplicationContext forTestClass(Class<?> testClass) {
        MergedContextConfiguration contextConfiguration = getContextConfiguration(testClass);
        try {
            return loadApplicationContext(contextConfiguration);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Load an <code>ApplicationContext</code> for this test context using the
     * configured {@code ContextLoader} and merged context configuration. Supports
     * both the {@link SmartContextLoader} and {@link ContextLoader} SPIs.
     *
     * @throws Exception if an error occurs while loading the application context
     */
    private static ApplicationContext loadApplicationContext(MergedContextConfiguration mergedContextConfiguration) throws Exception {
        ContextLoader contextLoader = mergedContextConfiguration.getContextLoader();
        Assert.notNull(contextLoader, "Cannot load an ApplicationContext with a NULL 'contextLoader'. "
                + "Consider annotating your test class with @ContextConfiguration.");

        ApplicationContext applicationContext;

        if(contextLoader instanceof SmartContextLoader) {
            SmartContextLoader smartContextLoader = (SmartContextLoader) contextLoader;
            applicationContext = smartContextLoader.loadContext(mergedContextConfiguration);
        }
        else {
            String[] locations = mergedContextConfiguration.getLocations();
            Assert.notNull(locations, "Cannot load an ApplicationContext with a NULL 'locations' array. "
                    + "Consider annotating your test class with @ContextConfiguration.");
            applicationContext = contextLoader.loadContext(locations);
        }

        return applicationContext;
    }

    static MergedContextConfiguration getContextConfiguration(Class<?> testClass) {
        final List<ContextConfigurationAttributes> configAttributesList = resolveContextConfigurationAttributes(testClass);
        final ContextLoader contextLoader = resolveContextLoader(testClass, configAttributesList, null);
        final List<String> locationsList = new ArrayList<String>();
        final List<Class<?>> classesList = new ArrayList<Class<?>>();

        for(ContextConfigurationAttributes configAttributes : configAttributesList) {
            log.trace("Processing locations and classes for context configuration attributes {}", configAttributes);

            if(contextLoader instanceof SmartContextLoader) {
                SmartContextLoader smartContextLoader = (SmartContextLoader) contextLoader;
                smartContextLoader.processContextConfiguration(configAttributes);
                locationsList.addAll(0, Arrays.asList(configAttributes.getLocations()));
                classesList.addAll(0, Arrays.asList(configAttributes.getClasses()));
            }
            else {
                String[] processedLocations = contextLoader.processLocations(configAttributes.getDeclaringClass(),
                        configAttributes.getLocations());
                locationsList.addAll(0, Arrays.asList(processedLocations));
                // Legacy ContextLoaders don't know how to process classes
            }

            if(!configAttributes.isInheritLocations()) {
                break;
            }
        }

        String[] locations = StringUtils.toStringArray(locationsList);
        Class<?>[] classes = ClassUtils.toClassArray(classesList);
        Set<Class<? extends ApplicationContextInitializer<? extends ConfigurableApplicationContext>>> initializerClasses = resolveInitializerClasses(configAttributesList);
        String[] activeProfiles = resolveActiveProfiles(testClass);

        MergedContextConfiguration mergedConfig = buildWebMergedContextConfiguration(testClass, locations, classes,
                initializerClasses, activeProfiles, contextLoader);

        if(mergedConfig == null) {
            mergedConfig = new MergedContextConfiguration(testClass, locations, classes, initializerClasses,
                    activeProfiles, contextLoader);
        }

        return mergedConfig;
    }

    /**
     * Resolve the list of {@link ContextConfigurationAttributes configuration
     * attributes} for the supplied {@link Class class} and its superclasses.
     * <p/>
     * <p>Note that the {@link org.springframework.test.context.ContextConfiguration#inheritLocations
     * inheritLocations} and {@link org.springframework.test.context.ContextConfiguration#inheritInitializers()
     * inheritInitializers} flags of {@link org.springframework.test.context.ContextConfiguration
     * &#064;ContextConfiguration} will <strong>not</strong> be taken into
     * consideration. If these flags need to be honored, that must be handled
     * manually when traversing the list returned by this method.
     *
     * @param testClass the class for which to resolve the configuration attributes (must
     *                  not be {@code null})
     * @return the list of configuration attributes for the specified class, ordered <em>bottom-up</em>
     *         (i.e., as if we were traversing up the class hierarchy); never {@code null}
     * @throws IllegalArgumentException if the supplied class is {@code null} or
     *                                  if {@code @ContextConfiguration} is not <em>present</em> on the supplied class
     */
    static List<ContextConfigurationAttributes> resolveContextConfigurationAttributes(Class<?> testClass) {
        Assert.notNull(testClass, "Class must not be null");

        final List<ContextConfigurationAttributes> attributesList = new ArrayList<ContextConfigurationAttributes>();

        Class<ContextConfiguration> annotationType = ContextConfiguration.class;
        Class<?> declaringClass = findAnnotationDeclaringClass(annotationType, testClass);
        Assert.notNull(declaringClass, String.format(
                "Could not find an 'annotation declaring class' for annotation type [%s] and class [%s]",
                annotationType.getName(), testClass.getName()));

        while(declaringClass != null) {
            ContextConfiguration contextConfiguration = declaringClass.getAnnotation(annotationType);
            log.trace("Retrieved @ContextConfiguration [{}] for declaring class [{}].", contextConfiguration, declaringClass.getName());

            ContextConfigurationAttributes attributes = new ContextConfigurationAttributes(declaringClass, contextConfiguration);
            log.trace("Resolved context configuration attributes: {}", attributes);
            attributesList.add(attributes);

            declaringClass = findAnnotationDeclaringClass(annotationType, declaringClass.getSuperclass());
        }

//        Collections.reverse(attributesList);
        return attributesList;
    }

    /**
     * Resolve the {@link ContextLoader} {@linkplain Class class} to use for the
     * supplied list of {@link ContextConfigurationAttributes} and then
     * instantiate and return that {@code ContextLoader}.
     * <p/>
     * <p>If the supplied <code>defaultContextLoaderClassName</code> is
     * {@code null} or <em>empty</em>, depending on the absence or presence
     * of {@link org.springframework.test.context.web.WebAppConfiguration @WebAppConfiguration}
     * either {@value #DEFAULT_CONTEXT_LOADER_CLASS_NAME}
     * or {@value #DEFAULT_WEB_CONTEXT_LOADER_CLASS_NAME} will be used as the
     * default context loader class name. For details on the class resolution
     * process, see {@link #resolveContextLoaderClass()}.
     *
     * @param testClass                     the test class for which the {@code ContextLoader}
     *                                      should be resolved; must not be {@code null}
     * @param configAttributesList          the list of configuration attributes to process;
     *                                      must not be {@code null} or <em>empty</em>; must be ordered <em>bottom-up</em>
     *                                      (i.e., as if we were traversing up the class hierarchy)
     * @param defaultContextLoaderClassName the name of the default
     *                                      {@code ContextLoader} class to use; may be {@code null} or <em>empty</em>
     * @return the resolved {@code ContextLoader} for the supplied
     *         <code>testClass</code> (never {@code null})
     * @see #resolveContextLoaderClass()
     */
    static ContextLoader resolveContextLoader(Class<?> testClass,
                                              List<ContextConfigurationAttributes> configAttributesList, String defaultContextLoaderClassName) {
        Assert.notNull(testClass, "Class must not be null");
        Assert.notEmpty(configAttributesList, "ContextConfigurationAttributes list must not be empty");

        if(!StringUtils.hasText(defaultContextLoaderClassName)) {
            Class<? extends Annotation> webAppConfigClass = loadWebAppConfigurationClass();
            defaultContextLoaderClassName = webAppConfigClass != null && testClass.isAnnotationPresent(webAppConfigClass)
                                            ? DEFAULT_WEB_CONTEXT_LOADER_CLASS_NAME
                                            : DEFAULT_CONTEXT_LOADER_CLASS_NAME;
        }

        Class<? extends ContextLoader> contextLoaderClass = resolveContextLoaderClass(testClass, configAttributesList,
                defaultContextLoaderClassName);

        return instantiateClass(contextLoaderClass, ContextLoader.class);
    }

    /**
     * Resolve <em>active bean definition profiles</em> for the supplied {@link Class}.
     * <p/>
     * <p>Note that the {@link ActiveProfiles#inheritProfiles inheritProfiles}
     * flag of {@link ActiveProfiles &#064;ActiveProfiles} will be taken into
     * consideration. Specifically, if the <code>inheritProfiles</code> flag is
     * set to <code>true</code>, profiles defined in the test class will be
     * merged with those defined in superclasses.
     *
     * @param testClass the class for which to resolve the active profiles (must
     *                  not be {@code null})
     * @return the set of active profiles for the specified class, including
     *         active profiles from superclasses if appropriate (never {@code null})
     * @see org.springframework.test.context.ActiveProfiles
     * @see org.springframework.context.annotation.Profile
     */
    static String[] resolveActiveProfiles(Class<?> testClass) {
        Assert.notNull(testClass, "Class must not be null");

        Class<ActiveProfiles> annotationType = ActiveProfiles.class;
        Class<?> declaringClass = findAnnotationDeclaringClass(annotationType, testClass);

        if(declaringClass == null && log.isDebugEnabled()) {
            log.debug("Could not find an 'annotation declaring class' for annotation type [{}] and class [{}]",
                    annotationType.getName(), testClass.getName());
        }

        final Set<String> activeProfiles = new HashSet<String>();

        while(declaringClass != null) {
            ActiveProfiles annotation = declaringClass.getAnnotation(annotationType);

            log.trace("Retrieved @ActiveProfiles [{}] for declaring class [{}].", annotation, declaringClass.getName());

            String[] profiles = annotation.profiles();
            String[] valueProfiles = annotation.value();

            if(!ObjectUtils.isEmpty(valueProfiles) && !ObjectUtils.isEmpty(profiles)) {
                String msg = String.format("Test class [%s] has been configured with @ActiveProfiles' 'value' [%s] "
                        + "and 'profiles' [%s] attributes. Only one declaration of active bean "
                        + "definition profiles is permitted per @ActiveProfiles annotation.", declaringClass.getName(),
                        ObjectUtils.nullSafeToString(valueProfiles), ObjectUtils.nullSafeToString(profiles));
                log.error(msg);
                throw new IllegalStateException(msg);
            }
            else if(!ObjectUtils.isEmpty(valueProfiles)) {
                profiles = valueProfiles;
            }

            for(String profile : profiles) {
                if(StringUtils.hasText(profile)) {
                    activeProfiles.add(profile.trim());
                }
            }

            declaringClass = annotation.inheritProfiles()
                             ? findAnnotationDeclaringClass(annotationType, declaringClass.getSuperclass())
                             : null;
        }

        return StringUtils.toStringArray(activeProfiles);
    }

    /**
     * Resolve the list of merged {@code ApplicationContextInitializer} classes
     * for the supplied list of {@code ContextConfigurationAttributes}.
     * <p/>
     * <p>Note that the {@link ContextConfiguration#inheritInitializers inheritInitializers}
     * flag of {@link ContextConfiguration @ContextConfiguration} will be taken into
     * consideration. Specifically, if the <code>inheritInitializers</code> flag is
     * set to <code>true</code> for a given level in the class hierarchy represented by
     * the provided configuration attributes, context initializer classes defined
     * at the given level will be merged with those defined in higher levels
     * of the class hierarchy.
     *
     * @param configAttributesList the list of configuration attributes to process;
     *                             must not be {@code null} or <em>empty</em>; must be ordered <em>bottom-up</em>
     *                             (i.e., as if we were traversing up the class hierarchy)
     * @return the set of merged context initializer classes, including those
     *         from superclasses if appropriate (never {@code null})
     * @since 3.2
     */
    static Set<Class<? extends ApplicationContextInitializer<? extends ConfigurableApplicationContext>>> resolveInitializerClasses(
            List<ContextConfigurationAttributes> configAttributesList) {
        Assert.notEmpty(configAttributesList, "ContextConfigurationAttributes list must not be empty");

        final Set<Class<? extends ApplicationContextInitializer<? extends ConfigurableApplicationContext>>> initializerClasses = //
                new HashSet<Class<? extends ApplicationContextInitializer<? extends ConfigurableApplicationContext>>>();

        for(ContextConfigurationAttributes configAttributes : configAttributesList) {
            log.trace("Processing context initializers for context configuration attributes {}", configAttributes);
            initializerClasses.addAll(Arrays.asList(configAttributes.getInitializers()));

            if(!configAttributes.isInheritInitializers()) {
                break;
            }
        }

        return initializerClasses;
    }

    /**
     * Attempt to build a {@link org.springframework.test.context.web.WebMergedContextConfiguration
     * WebMergedContextConfiguration} from the supplied arguments, using reflection
     * in order to avoid package cycles.
     *
     * @return the {@code WebMergedContextConfiguration} or <code>null</code> if
     *         it could not be built
     * @since 3.2
     */
    @SuppressWarnings("unchecked")
    private static MergedContextConfiguration buildWebMergedContextConfiguration(
            Class<?> testClass,
            String[] locations,
            Class<?>[] classes,
            Set<Class<? extends ApplicationContextInitializer<? extends ConfigurableApplicationContext>>> initializerClasses,
            String[] activeProfiles, ContextLoader contextLoader) {

        Class<? extends Annotation> webAppConfigClass = loadWebAppConfigurationClass();

        if(webAppConfigClass != null && testClass.isAnnotationPresent(webAppConfigClass)) {
            Annotation annotation = testClass.getAnnotation(webAppConfigClass);
            String resourceBasePath = (String) AnnotationUtils.getValue(annotation);

            try {
                Class<? extends MergedContextConfiguration> webMergedConfigClass = (Class<? extends MergedContextConfiguration>) ClassUtils.forName(
                        WEB_MERGED_CONTEXT_CONFIGURATION_CLASS_NAME, SpringContextBuilder.class.getClassLoader());

                Constructor<? extends MergedContextConfiguration> constructor = ClassUtils.getConstructorIfAvailable(
                        webMergedConfigClass, Class.class, String[].class, Class[].class, Set.class, String[].class,
                        String.class, ContextLoader.class);

                if(constructor != null) {
                    return instantiateClass(constructor, testClass, locations, classes, initializerClasses,
                            activeProfiles, resourceBasePath, contextLoader);
                }
            }
            catch (Throwable t) {
                log.debug("Could not instantiate [" + WEB_MERGED_CONTEXT_CONFIGURATION_CLASS_NAME + "].", t);
            }
        }

        return null;
    }

    /**
     * Load the {@link org.springframework.test.context.web.WebAppConfiguration @WebAppConfiguration}
     * class, using reflection in order to avoid package cycles.
     *
     * @return the {@code @WebAppConfiguration} class or <code>null</code> if it
     *         cannot be loaded
     * @since 3.2
     */
    @SuppressWarnings("unchecked")
    private static Class<? extends Annotation> loadWebAppConfigurationClass() {
        Class<? extends Annotation> webAppConfigClass = null;
        try {
            webAppConfigClass = (Class<? extends Annotation>) ClassUtils.forName(WEB_APP_CONFIGURATION_CLASS_NAME,
                    SpringContextBuilder.class.getClassLoader());
        }
        catch (Throwable t) {
            log.debug("Could not load @WebAppConfiguration class [" + WEB_APP_CONFIGURATION_CLASS_NAME + "].", t);
        }
        return webAppConfigClass;
    }

    /**
     * Resolve the {@link ContextLoader} {@linkplain Class class} to use for the
     * supplied list of {@link ContextConfigurationAttributes}.
     * <p/>
     * <p>Beginning with the first level in the context configuration attributes
     * hierarchy:
     * <p/>
     * <ol>
     * <li>If the {@link ContextConfigurationAttributes#getContextLoaderClass()
     * contextLoaderClass} property of {@link ContextConfigurationAttributes} is
     * configured with an explicit class, that class will be returned.</li>
     * <li>If an explicit {@code ContextLoader} class is not specified at the
     * current level in the hierarchy, traverse to the next level in the hierarchy
     * and return to step #1.</li>
     * <li>If no explicit {@code ContextLoader} class is found after traversing
     * the hierarchy, an attempt will be made to load and return the class
     * with the supplied <code>defaultContextLoaderClassName</code>.</li>
     * </ol>
     *
     * @param testClass                     the class for which to resolve the {@code ContextLoader}
     *                                      class; must not be {@code null}; only used for logging purposes
     * @param configAttributesList          the list of configuration attributes to process;
     *                                      must not be {@code null} or <em>empty</em>; must be ordered <em>bottom-up</em>
     *                                      (i.e., as if we were traversing up the class hierarchy)
     * @param defaultContextLoaderClassName the name of the default
     *                                      {@code ContextLoader} class to use; must not be {@code null} or empty
     * @return the {@code ContextLoader} class to use for the supplied test class
     * @throws IllegalArgumentException if {@code @ContextConfiguration} is not
     *                                  <em>present</em> on the supplied test class
     * @throws IllegalStateException    if the default {@code ContextLoader} class
     *                                  could not be loaded
     */
    @SuppressWarnings("unchecked")
    static Class<? extends ContextLoader> resolveContextLoaderClass(Class<?> testClass,
                                                                    List<ContextConfigurationAttributes> configAttributesList, String defaultContextLoaderClassName) {
        Assert.notNull(testClass, "Class must not be null");
        Assert.notEmpty(configAttributesList, "ContextConfigurationAttributes list must not be empty");
        Assert.hasText(defaultContextLoaderClassName, "Default ContextLoader class name must not be null or empty");

        for(ContextConfigurationAttributes configAttributes : configAttributesList) {
            log.trace("Processing ContextLoader for context configuration attributes {}", configAttributes);
            Class<? extends ContextLoader> contextLoaderClass = configAttributes.getContextLoaderClass();
            if(!ContextLoader.class.equals(contextLoaderClass)) {
                log.debug("Found explicit ContextLoader class [{}] for context configuration attributes {}", contextLoaderClass.getName(), configAttributes);
                return contextLoaderClass;
            }
        }

        try {
            log.trace("Using default ContextLoader class [{}] for test class [{}]", defaultContextLoaderClassName, testClass.getName());
            return (Class<? extends ContextLoader>) ClassUtils.forName(defaultContextLoaderClassName, SpringContextBuilder.class.getClassLoader());
        }
        catch (Throwable t) {
            throw new IllegalStateException("Could not load default ContextLoader class ["
                    + defaultContextLoaderClassName + "]. Specify @ContextConfiguration's 'loader' "
                    + "attribute or make the default loader class available.", t);
        }
    }
}
