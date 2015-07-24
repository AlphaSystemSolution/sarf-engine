package com.alphasystem.app.sarfengine.guice;

import com.alphasystem.app.sarfengine.conjugation.member.MemberBuilderFactory;
import com.alphasystem.app.sarfengine.conjugation.member.TenseMemberBuilder;
import com.alphasystem.app.sarfengine.conjugation.member.triliteral.TriLiteralPastPassiveBuilder;
import com.alphasystem.app.sarfengine.conjugation.member.triliteral.TriLiteralPastTenseBuilder;
import com.alphasystem.app.sarfengine.conjugation.member.triliteral.TriLiteralPresentPassiveBuilder;
import com.alphasystem.app.sarfengine.conjugation.member.triliteral.TriLiteralPresentTenseBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import static com.google.inject.name.Names.named;

/**
 * @author sali
 */
public class MemberBuilderModule extends AbstractModule {

    public static final String TRI_LITERAL_PAST_TENSE_BUILDER = "TriLiteralPastTenseBuilder";
    public static final String TRI_LITERAL_PRESENT_TENSE_BUILDER = "TriLiteralPresentTenseBuilder";
    public static final String TRI_LITERAL_PAST_PASSIVE_BUILDER = "TriLiteralPastPassiveBuilder";
    public static final String TRI_LITERAL_PRESENT_PASSIVE_BUILDER = "TriLiteralPresentPassiveBuilder";

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder()
                .implement(TenseMemberBuilder.class, named(TRI_LITERAL_PAST_TENSE_BUILDER),
                        TriLiteralPastTenseBuilder.class)
                .implement(TenseMemberBuilder.class, named(TRI_LITERAL_PRESENT_TENSE_BUILDER),
                        TriLiteralPresentTenseBuilder.class)
                .implement(TenseMemberBuilder.class, named(TRI_LITERAL_PAST_PASSIVE_BUILDER),
                        TriLiteralPastPassiveBuilder.class)
                .implement(TenseMemberBuilder.class, named(TRI_LITERAL_PRESENT_PASSIVE_BUILDER),
                        TriLiteralPresentPassiveBuilder.class)
                .build(MemberBuilderFactory.class));
    }
}
