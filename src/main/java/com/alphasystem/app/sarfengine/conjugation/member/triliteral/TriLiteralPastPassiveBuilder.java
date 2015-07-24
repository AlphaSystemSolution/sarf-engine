package com.alphasystem.app.sarfengine.conjugation.member.triliteral;

import com.alphasystem.arabic.model.ArabicLetterType;
import com.alphasystem.arabic.model.NamedTemplate;
import com.alphasystem.sarfengine.xml.model.SarfTermType;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import static com.alphasystem.sarfengine.xml.model.SarfTermType.PAST_PASSIVE_TENSE;

/**
 * @author sali
 */
public class TriLiteralPastPassiveBuilder extends TriLiteralPastTenseBuilder {

    @AssistedInject
    public TriLiteralPastPassiveBuilder(@Assisted NamedTemplate template,
                                        @Assisted boolean skipRuleProcessing,
                                        @Assisted("firstRadical") ArabicLetterType firstRadical,
                                        @Assisted("secondRadical") ArabicLetterType secondRadical,
                                        @Assisted("thirdRadical") ArabicLetterType thirdRadical) {
        super(template, skipRuleProcessing, firstRadical, secondRadical, thirdRadical);
    }

    @Override
    public SarfTermType getTermType() {
        return PAST_PASSIVE_TENSE;
    }
}
