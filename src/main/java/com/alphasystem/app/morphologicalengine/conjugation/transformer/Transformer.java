package com.alphasystem.app.morphologicalengine.conjugation.transformer;

import com.alphasystem.app.morphologicalengine.conjugation.model.NounConjugation;
import com.alphasystem.app.morphologicalengine.conjugation.rule.RuleProcessor;
import com.alphasystem.arabic.model.ArabicLetterType;
import com.alphasystem.morphologicalanalysis.morphology.model.RootWord;

/**
 * @author sali
 */
public interface Transformer<G> {

    /**
     * Transform given <code>rootWord</code> into its singular, dual, and plural forms.
     *
     * @param ruleProcessor Rule Processor
     * @param rootWord      base word
     * @param firstRadical  first radical of the target word
     * @param secondRadical second radical of the target word
     * @param thirdRadical  third radical of the target word
     * @param fourthRadical fourth radical of the target word, may be null
     * @return conjugation of given <code>rootWord</code> depends on whether the <code>rootWord</code> is noun or verb.
     * @see NounConjugation
     */
    G doTransform(RuleProcessor ruleProcessor, RootWord rootWord, ArabicLetterType firstRadical,
                  ArabicLetterType secondRadical, ArabicLetterType thirdRadical, ArabicLetterType fourthRadical);
}
