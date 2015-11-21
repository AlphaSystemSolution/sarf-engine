/**
 *
 */
package com.alphasystem.app.sarfengine.conjugation.rule.processor;

import com.alphasystem.app.sarfengine.conjugation.model.WordStatus;
import com.alphasystem.app.sarfengine.conjugation.rule.AbstractRuleProcessor;
import com.alphasystem.arabic.model.ArabicWord;
import com.alphasystem.arabic.model.DiacriticType;
import com.alphasystem.arabic.model.NamedTemplate;
import com.alphasystem.sarfengine.xml.model.RootWord;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import javax.annotation.Nullable;

import static com.alphasystem.app.sarfengine.conjugation.rule.RuleProcessorHelper.checkArgument;
import static com.alphasystem.arabic.model.ArabicLetterType.HAMZA;
import static com.alphasystem.sarfengine.xml.model.SarfTermType.ACTIVE_PARTICIPLE_FEMININE;
import static com.alphasystem.sarfengine.xml.model.SarfTermType.ACTIVE_PARTICIPLE_MASCULINE;

/**
 * @author sali
 */
public class Rule17Processor extends AbstractRuleProcessor {

    @AssistedInject
    public Rule17Processor(@Assisted NamedTemplate template,
                           @Nullable @Assisted DiacriticType diacriticForWeakSecondRadicalWaw,
                           @Assisted boolean pastTenseHasTransformed) {
        super(template, diacriticForWeakSecondRadicalWaw, pastTenseHasTransformed);
    }

    @Override
    public RootWord applyRules(RootWord baseRootWord) {
        try {
            checkArgument(baseRootWord, ACTIVE_PARTICIPLE_MASCULINE,
                    ACTIVE_PARTICIPLE_FEMININE);
        } catch (IllegalArgumentException e) {
            return baseRootWord;
        }
        WordStatus wordStatus = new WordStatus(baseRootWord);
        if (!wordStatus.isHollow() || !isPastTenseHasTransformed()) {
            return baseRootWord;
        }
        ArabicWord result = new ArabicWord(baseRootWord.getRootWord());
        int secondRadicalIndex = baseRootWord.getSecondRadicalIndex();
        result.replaceLetter(secondRadicalIndex, HAMZA);
        baseRootWord.setSecondRadical(result.getLetter(secondRadicalIndex));
        baseRootWord.setRootWord(result);
        return baseRootWord;
    }

}