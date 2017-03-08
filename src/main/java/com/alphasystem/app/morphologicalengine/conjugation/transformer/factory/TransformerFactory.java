package com.alphasystem.app.morphologicalengine.conjugation.transformer.factory;

import com.alphasystem.app.morphologicalengine.conjugation.model.ConjugationGroup;
import com.alphasystem.app.morphologicalengine.conjugation.model.RootBase;
import com.alphasystem.app.morphologicalengine.conjugation.rule.RuleProcessor;
import com.alphasystem.morphologicalanalysis.morphology.model.RootLetters;
import com.alphasystem.morphologicalanalysis.morphology.model.support.SarfTermType;

/**
 * @author sali
 */
public interface TransformerFactory<G extends ConjugationGroup, B extends RootBase> {

    /**
     * Perform the conjugation.
     *
     * @param ruleProcessor {@link RuleProcessor} to process null, maybe null if client wants to skip rule processing
     * @param sarfTermType {@link SarfTermType}
     * @param rootBase      base word for conjugation
     * @param rootLetters   root letters
     * @return instance of specific {@link ConjugationGroup}
     * @see com.alphasystem.app.morphologicalengine.conjugation.model.NounRootBase
     * @see com.alphasystem.app.morphologicalengine.conjugation.model.VerbRootBase
     * @see com.alphasystem.app.morphologicalengine.conjugation.model.NounConjugationGroup
     * @see com.alphasystem.app.morphologicalengine.conjugation.model.VerbConjugationGroup
     */
    G doConjugation(RuleProcessor ruleProcessor, SarfTermType sarfTermType, B rootBase, RootLetters rootLetters);
}
