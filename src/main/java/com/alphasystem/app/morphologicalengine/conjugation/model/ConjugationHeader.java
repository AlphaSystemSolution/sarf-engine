/**
 *
 */
package com.alphasystem.app.morphologicalengine.conjugation.model;

import com.alphasystem.arabic.model.ArabicLetterType;
import com.alphasystem.arabic.model.ArabicWord;
import com.alphasystem.arabic.model.VerbType;
import com.alphasystem.arabic.model.WeakVerbType;
import com.alphasystem.morphologicalanalysis.morphology.model.RootLetters;
import com.alphasystem.morphologicalanalysis.morphology.model.RootWord;

import static com.alphasystem.arabic.model.ArabicLetterType.*;
import static com.alphasystem.arabic.model.ArabicLetters.LETTER_COMMA;
import static com.alphasystem.arabic.model.ArabicLetters.WORD_SPACE;
import static com.alphasystem.arabic.model.ArabicWord.*;

/**
 * @author sali
 */
public class ConjugationHeader {

    private static final ArabicWord WEIGHT_LABEL = getWord(WAW, ZAIN, NOON);

    private final RootWord pastTenseRoot;
    private final RootWord presentTenseRoot;
    private final RootLetters rootLetters;
    private final String translation;
    private final ArabicWord baseWord;
    private final ChartMode chartMode;
    private ArabicWord typeLabel1;
    private ArabicWord typeLabel2;
    private ArabicWord typeLabel3;

    /**
     * @param translation
     * @param pastTenseRoot
     * @param presentTenseRoot
     * @param baseWord
     * @param chartMode
     * @param rootLetters
     */
    public ConjugationHeader(String translation, RootWord pastTenseRoot, RootWord presentTenseRoot,
                             ArabicWord baseWord, ChartMode chartMode, RootLetters rootLetters) {
        this.translation = translation;
        this.pastTenseRoot = pastTenseRoot;
        this.presentTenseRoot = presentTenseRoot;
        this.baseWord = baseWord;
        this.chartMode = chartMode;
        this.rootLetters = rootLetters;
        initLabels();
    }

    public RootWord getPastTenseRoot() {
        return pastTenseRoot;
    }

    public RootWord getPresentTenseRoot() {
        return presentTenseRoot;
    }

    public ArabicWord getBaseWord() {
        return baseWord;
    }

    public ChartMode getChartMode() {
        return chartMode;
    }

    public RootLetters getRootLetters() {
        return rootLetters;
    }

    public String getTranslation() {
        return translation;
    }

    public ArabicWord getTypeLabel1() {
        return typeLabel1;
    }

    public ArabicWord getTypeLabel2() {
        return typeLabel2;
    }

    public ArabicWord getTypeLabel3() {
        return typeLabel3;
    }

    public ArabicWord getTitle() {
        ArabicWord title = WORD_SPACE;
        if (pastTenseRoot != null) {
            title = concatenateWithSpace(title, pastTenseRoot.getLabel());
        }
        if (presentTenseRoot != null) {
            title = concatenateWithSpace(title, presentTenseRoot.getLabel());
        }
        if (rootLetters != null) {
            ArabicLetterType fourthRadical = rootLetters.getFourthRadical();
            ArabicWord rl = concatenate(getWord(LEFT_PARENTHESIS), WORD_SPACE, getWord(rootLetters.getFirstRadical()),
                    WORD_SPACE, getWord(rootLetters.getSecondRadical()), WORD_SPACE, getWord(rootLetters.getThirdRadical()));
            if (fourthRadical != null) {
                rl = concatenate(WORD_SPACE, getWord(fourthRadical));
            }
            rl = concatenate(rl, getWord(RIGHT_PARENTHESIS), WORD_SPACE);
            title = concatenateWithSpace(title, rl);
        }
        return title;
    }

    private void initLabels() {
        if (chartMode != null) {
            typeLabel1 = chartMode.getTemplate().getType();
            typeLabel2 = concatenateWithSpace(WEIGHT_LABEL, baseWord);
            VerbType verbType = chartMode.getVerbType();
            WeakVerbType weakVerbType = chartMode.getWeakVerbType();
            ArabicWord label = verbType.getLabel();
            typeLabel3 = weakVerbType == null ? label : concatenateWithSpace(
                    concatenate(label, new ArabicWord(LETTER_COMMA)),
                    weakVerbType.getLabel());
        }
    }
}