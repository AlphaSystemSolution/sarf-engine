package com.alphasystem.app.sarfengine.conjugation.member;

import com.alphasystem.app.morphologicalengine.conjugation.rule.RuleProcessor;
import com.alphasystem.morphologicalanalysis.morphology.model.RootWord;
import com.alphasystem.morphologicalanalysis.morphology.model.support.SarfTermType;

import static com.alphasystem.app.sarfengine.guice.GuiceSupport.getInstance;
import static com.alphasystem.arabic.model.ArabicLetterType.TA_MARBUTA;
import static com.alphasystem.arabic.model.DiacriticType.FATHA;
import static com.alphasystem.morphologicalanalysis.morphology.model.support.SarfTermType.NOUN_OF_PLACE_AND_TIME;
import static com.alphasystem.morphologicalanalysis.morphology.model.support.SarfTermType.VERBAL_NOUN;

/**
 * @author sali
 */
public class AbstractTriLiteralVerbalNounAndAdverbBuilder extends AbstractParticipleMemberBuilder {

    protected final ParticipleMemberBuilder masculineBuilder;
    protected final ParticipleMemberBuilder feminineBuilder;
    private boolean feminineBased;
    private boolean verbalNoun;

    protected AbstractTriLiteralVerbalNounAndAdverbBuilder(RuleProcessor ruleProcessor, boolean skipRuleProcessing,
                                                           RootWord baseRootWord, int variableLetterIndex,
                                                           boolean verbalNoun) {
        super(ruleProcessor, skipRuleProcessing, baseRootWord, variableLetterIndex);
        this.verbalNoun = verbalNoun;
        feminineBased = getRootWord().getRootWord().getLastLetter().getLetter().equals(TA_MARBUTA);
        MemberBuilderFactory memberBuilderFactory = getInstance().getOldMemberBuilderFactory();
        masculineBuilder = memberBuilderFactory.getTriLiteralActiveParticipleMasculineBuilder(ruleProcessor,
                skipRuleProcessing, baseRootWord);
        RootWord feminineRoot = new RootWord(getRootWord());
        feminineRoot.setRootWord(feminineRoot.getRootWord().replaceDiacritic(getVariableLetterIndex(), FATHA)
                .append(TA_MARBUTA_WITH_DAMMATAN));
        feminineRoot = feminineBased ? getRootWord() : feminineRoot;
        feminineBuilder = memberBuilderFactory.getTriLiteralActiveParticipleFeminineBuilder(ruleProcessor,
                skipRuleProcessing, feminineRoot);
    }

    @Override
    protected RootWord doNominativeSingular(RootWord rootWord) {
        RootWord src = feminineBased ? feminineBuilder.nominativeSingular() : masculineBuilder.nominativeSingular();
        return new RootWord(src).withSarfTermType(getTermType());
    }

    @Override
    protected RootWord doNominativeDual(RootWord rootWord) {
        RootWord src = feminineBased ? feminineBuilder.nominativeDual() : masculineBuilder.nominativeDual();
        return new RootWord(src).withSarfTermType(getTermType());
    }

    @Override
    protected RootWord doNominativePlural(RootWord rootWord) {
        return new RootWord(feminineBuilder.nominativePlural()).withSarfTermType(getTermType());
    }

    @Override
    protected RootWord doAccusativeSingular(RootWord rootWord) {
        RootWord src = feminineBased ? feminineBuilder.accusativeSingular() : masculineBuilder.accusativeSingular();
        return new RootWord(src).withSarfTermType(getTermType());
    }

    @Override
    protected RootWord doAccusativeDual(RootWord rootWord) {
        RootWord src = feminineBased ? feminineBuilder.accusativeDual() : masculineBuilder.accusativeDual();
        return new RootWord(src).withSarfTermType(getTermType());
    }

    @Override
    protected RootWord doAccusativePlural(RootWord rootWord) {
        return new RootWord(feminineBuilder.accusativePlural()).withSarfTermType(getTermType());
    }

    @Override
    protected RootWord doGenitiveSingular(RootWord rootWord) {
        RootWord src = feminineBased ? feminineBuilder.genitiveSingular() : masculineBuilder.genitiveSingular();
        return new RootWord(src).withSarfTermType(getTermType());
    }

    @Override
    public RootWord getDefaultConjugation() {
        return verbalNoun ? accusativeSingular() : nominativeSingular();
    }

    @Override
    public SarfTermType getTermType() {
        return verbalNoun ? VERBAL_NOUN : NOUN_OF_PLACE_AND_TIME;
    }

}
