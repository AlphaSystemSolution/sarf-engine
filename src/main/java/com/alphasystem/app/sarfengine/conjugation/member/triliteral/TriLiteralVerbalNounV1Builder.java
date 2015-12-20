package com.alphasystem.app.sarfengine.conjugation.member.triliteral;

import com.alphasystem.app.sarfengine.conjugation.member.AbstractTriLiteralVerbalNounAndAdverbBuilder;
import com.alphasystem.app.sarfengine.conjugation.rule.RuleProcessor;
import com.alphasystem.morphologicalanalysis.morphology.model.RootWord;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

;

/**
 * @author sali
 */
public class TriLiteralVerbalNounV1Builder extends AbstractTriLiteralVerbalNounAndAdverbBuilder {

    @AssistedInject
    public TriLiteralVerbalNounV1Builder(@Assisted RuleProcessor ruleProcessor,
                                         @Assisted boolean skipRuleProcessing,
                                         @Assisted RootWord baseRootWord) {
        super(ruleProcessor, skipRuleProcessing, baseRootWord, baseRootWord.getRootWord().getLength() - 1, true);
    }

}
