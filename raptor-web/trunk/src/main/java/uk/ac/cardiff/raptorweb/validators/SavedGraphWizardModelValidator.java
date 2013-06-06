
package uk.ac.cardiff.raptorweb.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;

import uk.ac.cardiff.raptorweb.model.wizard.GraphSet;
import uk.ac.cardiff.raptorweb.model.wizard.SavedGraphWizardModel;

/**
 *
 */
public class SavedGraphWizardModelValidator {

    /** class logger */
    private final Logger log = LoggerFactory.getLogger(SavedGraphWizardModelValidator.class);

    public void validateEventTypeSelector(SavedGraphWizardModel model, ValidationContext context) {
        log.info("validating the eventTypeSelector");
        MessageContext messages = context.getMessageContext();
        if (model.getGraphWizardModel().getGraphSets() == null
                || model.getGraphWizardModel().getGraphSets().size() == 0) {
            log.warn("No event types selected");
            messages.addMessage(new MessageBuilder().error().defaultText("You Must Select At Least One Event Type")
                    .build());
        }

    }

    public void validateDateSelector(SavedGraphWizardModel model, ValidationContext context) {
        log.info("validating the date selector");
    }

    public void validateGraphOptionsSelector(SavedGraphWizardModel model, ValidationContext context) {
        log.info("validating the graph options selector");
        MessageContext messages = context.getMessageContext();
        if (model.getGraphWizardModel().getGraphTitle() == null
                || model.getGraphWizardModel().getGraphTitle().equals("")) {
            log.warn("Graph title required");
            messages.addMessage(new MessageBuilder().error()
                    .defaultText("Graph Title is required. This is used as part of the filename when saved").build());
        }
    }

    public void validateGraphSeriesSelector(SavedGraphWizardModel model, ValidationContext context) {
        log.info("validating the series selector");
        MessageContext messages = context.getMessageContext();
        int count = 1;
        for (GraphSet graphSet : model.getGraphWizardModel().getGraphSets()) {
            if (graphSet.getStatisticalUnitInformation().getStatisticalUnitInformation().getStatisticParameters()
                    .getSeries() == null
                    || graphSet.getStatisticalUnitInformation().getStatisticalUnitInformation()
                            .getStatisticParameters().getSeries().size() == 0) {
                log.warn("No series  selected");
                messages.addMessage(new MessageBuilder().error()
                        .defaultText("You Must Select At Least One Series (Event " + count + " Is Missing)").build());
            }
            count++;
        }
    }

    public void validateGraphTypeSelector(SavedGraphWizardModel model, ValidationContext context) {
        log.info("validating the graph type selector");
        MessageContext messages = context.getMessageContext();
        int count = 1;
        for (GraphSet graphSet : model.getGraphWizardModel().getGraphSets()) {
            if (graphSet.getStatisticalUnitInformation() == null
                    || graphSet.getStatisticalUnitInformation().getStatisticalUnitInformation() == null
                    || graphSet.getStatisticalUnitInformation().getStatisticalUnitInformation()
                            .getStatisticParameters() == null
                    || graphSet.getStatisticalUnitInformation().getStatisticalUnitInformation()
                            .getStatisticParameters().getMethodParams() == null
                    || graphSet.getStatisticalUnitInformation().getStatisticalUnitInformation()
                            .getStatisticParameters().getMethodParams().size() == 0) {
                log.warn("No graph function detected");
                messages.addMessage(new MessageBuilder()
                        .error()
                        .defaultText("You Must Select A Graph Function Per Event Type (Event " + count + " Is Missing)")
                        .build());
            }
            count++;
        }
    }
}
