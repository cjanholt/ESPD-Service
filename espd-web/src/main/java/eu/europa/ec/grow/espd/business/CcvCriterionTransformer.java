package eu.europa.ec.grow.espd.business;

import com.google.common.base.Function;
import eu.europa.ec.grow.espd.constants.enums.Agency;
import eu.europa.ec.grow.espd.criteria.CcvCriterion;
import eu.europa.ec.grow.espd.criteria.enums.CriterionJurisdictionLevel;
import isa.names.specification.ubl.schema.xsd.ccv_commonaggregatecomponents_1.CriterionType;
import isa.names.specification.ubl.schema.xsd.ccv_commonaggregatecomponents_1.LegislationType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.*;
import org.springframework.stereotype.Component;

/**
 * Created by vigi on 11/16/15:3:38 PM.
 */
@Component
class CcvCriterionTransformer implements Function<CcvCriterion, CriterionType> {

    @Override
    public CriterionType apply(CcvCriterion input) {
        CriterionType criterionType = new CriterionType();

        addCriterionID(input, criterionType);
        addTypeCode(input, criterionType);
        addName(input, criterionType);
        addDescription(input, criterionType);
        addLegislationReference(input, criterionType);

        return criterionType;
    }

    private void addCriterionID(CcvCriterion input, CriterionType criterionType) {
        IDType idType = new IDType();
        idType.setValue(input.getId());
        idType.setSchemeAgencyID(Agency.EU_COM_GROW.getIdentifier());
        idType.setSchemeVersionID("1.0");
        idType.setSchemeID("CriteriaID");
        criterionType.setCriterionID(idType);
    }

    private void addTypeCode(CcvCriterion input, CriterionType criterionType) {
        TypeCodeType typeCodeType = new TypeCodeType();
        typeCodeType.setValue(input.getTypeCode());
        typeCodeType.setListAgencyID(Agency.EU_COM_GROW.getIdentifier());
        typeCodeType.setListID("CriteriaTypeCode");
        typeCodeType.setListVersionID("1.0");
        criterionType.setCriterionTypeCode(typeCodeType);
    }

    private void addName(CcvCriterion input, CriterionType criterionType) {
        NameType nameType = new NameType();
        nameType.setValue(input.getName());
        criterionType.setCriterionName(nameType);
    }

    private void addDescription(CcvCriterion input, CriterionType criterionType) {
        DescriptionType descriptionType = new DescriptionType();
        descriptionType.setValue(input.getDescription());
        criterionType.setCriterionDescription(descriptionType);
    }

    private void addLegislationReference(CcvCriterion input, CriterionType criterionType) {
        if (input.getLegislation() == null) {
            return;
        }

        LegislationType legislationType = new LegislationType();

        TextType title = new TextType();
        title.setValue(input.getLegislation().getTitle());
        legislationType.setLegislationTitle(title);

        DescriptionType description = new DescriptionType();
        description.setValue(input.getLegislation().getDescription());
        legislationType.setLegislationDescription(description);

        TypeCodeType jurisdictionLevelCode = new TypeCodeType();
        jurisdictionLevelCode.setValue(input.getLegislation().getJurisdictionLevelCode());
        jurisdictionLevelCode.setListAgencyID(Agency.EU_COM_GROW.getIdentifier());
        jurisdictionLevelCode.setListID(CriterionJurisdictionLevel.LIST_ID);
        jurisdictionLevelCode.setListVersionID("1.0");
        legislationType.setJurisdictionLevelCode(jurisdictionLevelCode);

        TextType article = new TextType();
        article.setValue(input.getLegislation().getArticle());
        legislationType.setLegislationArticle(article);

        IDType uriid = new IDType();
        uriid.setValue(input.getLegislation().getUrl());
        legislationType.setLegislationURIID(uriid);

        criterionType.getCriterionLegislationReference().add(legislationType);
    }
}