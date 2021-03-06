/**
 *
 */
package com.alphasystem.app.morphologicalengine.conjugation.rule.processor;

import com.alphasystem.app.morphologicalengine.conjugation.model.WordStatus;
import com.alphasystem.app.morphologicalengine.conjugation.rule.AbstractRuleProcessor;
import com.alphasystem.app.morphologicalengine.conjugation.rule.RuleInfo;
import com.alphasystem.arabic.model.*;
import com.alphasystem.morphologicalanalysis.morphology.model.RootWord;
import com.alphasystem.morphologicalanalysis.morphology.model.support.SarfTermType;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import static com.alphasystem.app.morphologicalengine.conjugation.rule.RuleProcessorHelper.*;
import static com.alphasystem.arabic.model.ArabicLetterType.WAW;
import static com.alphasystem.arabic.model.ArabicLetterType.YA;
import static com.alphasystem.arabic.model.DiacriticType.KASRA;
import static com.alphasystem.arabic.model.DiacriticType.KASRATAN;
import static com.alphasystem.arabic.model.HiddenNounStatus.GENITIVE_SINGULAR;
import static com.alphasystem.arabic.model.HiddenNounStatus.NOMINATIVE_SINGULAR;
import static com.alphasystem.morphologicalanalysis.morphology.model.support.SarfTermType.ACTIVE_PARTICIPLE_MASCULINE;

/**
 * @author sali
 */
public class Rule14Processor extends AbstractRuleProcessor {

    @AssistedInject
    public Rule14Processor(@Assisted RuleInfo ruleInfo) {
        super(ruleInfo);
    }

    @Override
    public RootWord applyRules(RootWord baseRootWord) {
        try {
            checkArgument(baseRootWord);
        } catch (IllegalArgumentException e) {
            return baseRootWord;
        }
        final WordStatus wordStatus = ruleInfo.getWordStatus();
        if (!wordStatus.isWeak()) {
            return baseRootWord;
        }
        ArabicWord result = new ArabicWord(baseRootWord.getRootWord());

        SarfTermType sarfTermType = baseRootWord.getSarfTermType();
        SarfMemberType memberType = baseRootWord.getMemberType();
        if (sarfTermType.equals(ACTIVE_PARTICIPLE_MASCULINE)
                && wordStatus.isDefective()) {
            if (memberType.equals(NOMINATIVE_SINGULAR)
                    || memberType.equals(GENITIVE_SINGULAR)) {
                result.replaceDiacritic(baseRootWord.getSecondRadicalIndex(),
                        KASRATAN).replaceLetter(
                        baseRootWord.getThirdRadicalIndex(), REMOVE_MARKER);
                baseRootWord.setThirdRadicalIndex(-1);
                baseRootWord.setRootWord(result);
            }
        }

        ArabicWord baseWord = baseRootWord.getBaseWord();
        // We will scan through base word to find out consecutive WAW and YA
        // pattern
        ArabicLetterType firstLetter = null;
        ArabicLetterType secondLetter = null;
        boolean wawYaCombination = false;
        boolean yaWawCombination = false;
        int index = 1;
        while (true) {
            if (index >= baseWord.getLength()) {
                break;
            }
            ArabicLetter letter = baseWord.getLetter(index);
            DiacriticType diacritic = getDiacritic(letter);
            firstLetter = letter.getLetter();
            if ((firstLetter.equals(WAW) || firstLetter.equals(YA))
                    && isSakin(diacritic)) {
                letter = baseWord.getLetter(index + 1);
                diacritic = getDiacritic(letter);
                secondLetter = letter.getLetter();
                wawYaCombination = firstLetter.equals(WAW)
                        && secondLetter.equals(YA);
                yaWawCombination = firstLetter.equals(YA)
                        && secondLetter.equals(WAW);
                if (isMutaharik(diacritic)
                        && (wawYaCombination || yaWawCombination)) {
                    break;
                }
            }
            index++;
        }
        if (index >= baseWord.getLength()) {
            return baseRootWord;
        }

        // now make sure we have WAW and YA in our letter
        ArabicLetter letter = result.getLetter(index);
        firstLetter = letter.getLetter();
        DiacriticType diacritic = getDiacritic(letter);
        if ((firstLetter.equals(WAW) || firstLetter.equals(YA))
                && isSakin(diacritic)) {
            letter = baseWord.getLetter(index + 1);
            diacritic = getDiacritic(letter);
            secondLetter = letter.getLetter();
            wawYaCombination = firstLetter.equals(WAW)
                    && secondLetter.equals(YA);
            yaWawCombination = firstLetter.equals(YA)
                    && secondLetter.equals(WAW);
            if (isMutaharik(diacritic)) {
                int indexToChange = -1;
                if (wawYaCombination) {
                    indexToChange = index;
                } else if (yaWawCombination) {
                    indexToChange = index + 1;
                }
                if (indexToChange > -1) {
                    result.replaceLetter(indexToChange, YA).replaceDiacritic(
                            index - 1, KASRA);
                    if (indexToChange == baseRootWord.getFirstRadicalIndex()) {
                        baseRootWord.setFirstRadicalIndex(-1);
                    } else if (indexToChange == baseRootWord
                            .getSecondRadicalIndex()) {
                        baseRootWord.setSecondRadicalIndex(-1);
                    } else if (indexToChange == baseRootWord
                            .getThirdRadicalIndex()) {
                        baseRootWord.setThirdRadicalIndex(-1);
                    }
                }
            }
        }

        baseRootWord.setRootWord(result);
        return baseRootWord;
    }
}
