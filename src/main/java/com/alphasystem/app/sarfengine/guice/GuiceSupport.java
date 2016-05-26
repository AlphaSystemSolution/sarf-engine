package com.alphasystem.app.sarfengine.guice;

import com.alphasystem.app.sarfengine.conjugation.builder.ConjugationBuilderFactory;
import com.alphasystem.app.sarfengine.conjugation.member.MemberBuilderFactory;
import com.alphasystem.app.sarfengine.conjugation.rule.RuleProcessorFactory;
import com.alphasystem.app.sarfengine.conjugation.transformer.noun.TransformerFactory;
import com.alphasystem.app.sarfengine.conjugation.transformer.noun.TransformerModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mycila.guice.ext.closeable.CloseableModule;
import com.mycila.guice.ext.jsr250.Jsr250Module;

/**
 * @author sali
 */
public final class GuiceSupport {

    private static GuiceSupport instance = new GuiceSupport();
    private final Injector injector;

    /**
     * Do not let anyone instantiate this class
     */
    private GuiceSupport() {
        injector = Guice.createInjector(new CloseableModule(), new Jsr250Module(), new TransformerModule(),
                new RuleProcessorModule(), new MemberBuilderModule(), new ConjugationBuilderModule());
    }

    public static GuiceSupport getInstance() {
        return instance;
    }

    public Injector getInjector() {
        return injector;
    }

    public TransformerFactory getTransformerFactory() {
        return injector.getInstance(TransformerFactory.class);
    }

    public RuleProcessorFactory getRuleProcessorFactory() {
        return injector.getInstance(RuleProcessorFactory.class);
    }

    public MemberBuilderFactory getMemberBuilderFactory() {
        return injector.getInstance(MemberBuilderFactory.class);
    }

    public ConjugationBuilderFactory getConjugationBuilderFactory() {
        return injector.getInstance(ConjugationBuilderFactory.class);
    }

}
