/**
 *
 */
package com.alphasystem.app.sarfengine.conjugation.rule.processor;

import com.alphasystem.app.sarfengine.conjugation.model.WordStatus;
import com.alphasystem.app.sarfengine.conjugation.rule.AbstractRuleProcessor;
import com.alphasystem.arabic.model.*;
import com.alphasystem.sarfengine.xml.model.RootWord;
import com.alphasystem.sarfengine.xml.model.SarfTermType;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import javax.annotation.Nullable;

import static com.alphasystem.app.sarfengine.conjugation.rule.RuleProcessorHelper.*;
import static com.alphasystem.arabic.model.ArabicLetters.LETTER_TATWEEL;
import static com.alphasystem.arabic.model.ArabicLetters.YA_WITH_SUKUN;
import static com.alphasystem.arabic.model.DiacriticType.DAMMA;
import static com.alphasystem.arabic.model.DiacriticType.KASRA;
import static com.alphasystem.sarfengine.xml.model.SarfTermType.*;
import static org.apache.commons.lang3.ArrayUtils.contains;

/**
 * @author sali
 */
public class Rule9Processor extends AbstractRuleProcessor {

    @AssistedInject
    public Rule9Processor(@Assisted NamedTemplate template,
                          @Nullable @Assisted DiacriticType diacriticForWeakSecondRadicalWaw,
                          @Assisted boolean pastTenseHasTransformed) {
        super(template, diacriticForWeakSecondRadicalWaw, pastTenseHasTransformed);
    }

    @Override
    public RootWord applyRules(RootWord baseRootWord) {
        try {
            checkArgument(baseRootWord,
                    new SarfTermType[]{PAST_PASSIVE_TENSE},
                    new SarfTermType[]{IMPERATIVE, FORBIDDING});
        } catch (IllegalArgumentException e) {
            return baseRootWord;
        }
        WordStatus wordStatus = new WordStatus(baseRootWord);
        if (!wordStatus.isHollow()) {
            return baseRootWord;
        }
        ArabicWord result = new ArabicWord(baseRootWord.getRootWord());

        SarfMemberType memberType = baseRootWord.getMemberType();
        ArabicLetter firstRadical = baseRootWord.getFirstRadical();
        DiacriticType firstRadicalDiacritic = getDiacritic(firstRadical);
        ArabicLetter secondRadical = baseRootWord.getSecondRadical();
        int secondRadicalIndex = baseRootWord.getSecondRadicalIndex();
        boolean weakWaw = wordStatus.isSecondRadicalWaw();
        boolean thirdPersonFemininePluralAndSeconAndFirstPersonsType = contains(
                THIRD_PERSON_FEMENINE_PLURAL_AND_SECOND_AND_FIRST_PERSONS_MEMBERS,
                memberType);
        result.replaceDiacritic(baseRootWord.getFirstRadicalIndex(),
                secondRadical.getDiacritics()).replaceLetter(
                secondRadicalIndex, YA_WITH_SUKUN);
        baseRootWord.setSecondRadical(YA_WITH_SUKUN);
        if (thirdPersonFemininePluralAndSeconAndFirstPersonsType) {
            if (isFatha(diacriticForWeakSecondRadicalWaw)
                    && weakWaw) {
                firstRadicalDiacritic = DAMMA;
            } else if ((isKasra(diacriticForWeakSecondRadicalWaw) && weakWaw)
                    || !weakWaw) {
                firstRadicalDiacritic = KASRA;
            }
            ArabicLetter replacementLetter = LETTER_TATWEEL;
            result.replaceDiacritic(baseRootWord.getFirstRadicalIndex(),
                    firstRadicalDiacritic).replaceLetter(secondRadicalIndex,
                    LETTER_TATWEEL);
            baseRootWord.setSecondRadical(replacementLetter);
        }

        baseRootWord.setRootWord(result);
        return baseRootWord;
    }

}