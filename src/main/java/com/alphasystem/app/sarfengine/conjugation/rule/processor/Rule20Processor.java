/**
 *
 */
package com.alphasystem.app.sarfengine.conjugation.rule.processor;

import com.alphasystem.app.sarfengine.conjugation.model.WordStatus;
import com.alphasystem.app.sarfengine.conjugation.rule.AbstractRuleProcessor;
import com.alphasystem.arabic.model.ArabicLetter;
import com.alphasystem.arabic.model.ArabicWord;
import com.alphasystem.arabic.model.DiacriticType;
import com.alphasystem.arabic.model.NamedTemplate;
import com.alphasystem.sarfengine.xml.model.RootWord;
import com.alphasystem.sarfengine.xml.model.SarfTermType;

import static com.alphasystem.arabic.model.ArabicLetterType.YA;
import static com.alphasystem.sarfengine.xml.model.SarfTermType.*;

/**
 * @author sali
 */
public class Rule20Processor extends AbstractRuleProcessor {

    /**
     * @param template
     */
    public Rule20Processor(NamedTemplate template) {
        super(template);
    }

    @Override
    public RootWord applyRules(RootWord baseRootWord) {
        try {
            checkArgument(baseRootWord, null, new SarfTermType[]{VERBAL_NOUN,
                    NOUN_OF_PLACE_AND_TIME, IMPERATIVE, FORBIDDING});
        } catch (IllegalArgumentException e) {
            return baseRootWord;
        }
        WordStatus wordStatus = new WordStatus(baseRootWord);
        if (!wordStatus.isFirstRadicalWaw() && !wordStatus.isSecondRadicalWaw()
                && !wordStatus.isThirdRadicalWaw()) {
            return baseRootWord;
        }
        ArabicWord result = new ArabicWord(baseRootWord.getRootWord());
        int indexOfWaw = -1;
        for (int i = 0; i < result.getLength(); i++) {
            if (isWaw(result.getLetter(i).getLetter())) {
                indexOfWaw = i;
                break;
            }
        }
        if (indexOfWaw < 3) {
            return baseRootWord;
        }
        int previousIndex = indexOfWaw - 1;
        ArabicLetter previousLetter = result.getLetter(previousIndex);
        DiacriticType previousDiacritic = getDiacritic(previousLetter);
        if (isDamma(previousDiacritic) || isSakin(previousDiacritic)) {
            return baseRootWord;
        }
        result.replaceLetter(indexOfWaw, YA);
        baseRootWord.setRootWord(result);
        return baseRootWord;
    }

}
