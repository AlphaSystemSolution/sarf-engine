package com.alphasystem.app.morphologicalengine.conjugation.transformer.verb;

import com.alphasystem.app.morphologicalengine.conjugation.rule.RuleProcessor;
import com.alphasystem.arabic.model.ArabicWord;
import com.alphasystem.morphologicalanalysis.morphology.model.RootWord;

import static com.alphasystem.arabic.model.ArabicLetterType.TA;
import static com.alphasystem.arabic.model.DiacriticType.KASRA;
import static com.alphasystem.arabic.model.DiacriticType.SUKUN;
import static com.alphasystem.arabic.model.HiddenPronounStatus.*;

/**
 * @author sali
 */
class PresentTenseSecondPersonFeminineTransformer extends PresentTenseThirdPersonFeminineTransformer {

    PresentTenseSecondPersonFeminineTransformer() {
        super();
    }

    @Override
    protected RootWord doSingular(RuleProcessor ruleProcessor, RootWord rootWord) {
        final RootWord target = copyRootWord(rootWord, SECOND_PERSON_FEMININE_SINGULAR);
        final ArabicWord arabicWord = target.getRootWord().replaceLetter(0, TA)
                .replaceDiacritic(target.getThirdRadicalIndex(), KASRA).append(YA_WITH_SUKUN, NOON_WITH_FATHA);
        return processRules(ruleProcessor, target.withRootWord(arabicWord));
    }

    @Override
    protected RootWord doDual(RuleProcessor ruleProcessor, RootWord rootWord) {
        return new RootWord(super.doDual(ruleProcessor, rootWord)).withMemberType(SECOND_PERSON_FEMININE_DUAL);
    }

    @Override
    protected RootWord doPlural(RuleProcessor ruleProcessor, RootWord rootWord) {
        final RootWord target = copyRootWord(rootWord, SECOND_PERSON_FEMININE_PLURAL);
        final ArabicWord arabicWord = target.getRootWord().replaceLetter(0, TA).replaceDiacritic(
                target.getThirdRadicalIndex(), SUKUN).append(NOON_WITH_FATHA);
        return processRules(ruleProcessor, target.withRootWord(arabicWord));
    }
}
