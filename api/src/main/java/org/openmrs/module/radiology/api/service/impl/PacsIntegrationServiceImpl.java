package org.openmrs.module.radiology.api.service.impl;

import ca.uhn.hl7v2.AcknowledgmentCode;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.llp.LLPException;
import ca.uhn.hl7v2.model.AbstractMessage;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v25.message.ACK;
import ca.uhn.hl7v2.model.v25.message.ORR_O02;
import ca.uhn.hl7v2.parser.PipeParser;
import org.openmrs.module.radiology.api.dao.OrderLogsDao;
import org.openmrs.module.radiology.api.dao.RadiologyDao;
import org.openmrs.module.radiology.api.exception.ModalityException;
import org.openmrs.module.radiology.api.model.Modality;
import org.openmrs.module.radiology.api.model.OrderLogs;
import org.openmrs.module.radiology.api.model.RadiologyOrder;
import org.openmrs.module.radiology.api.service.ModalityService;
import org.openmrs.module.radiology.api.service.OrderLogsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

public class PacsIntegrationServiceImpl {

    private ModalityService modalityService;
    private HL7ServiceImpl hl7Service;
    private OrderLogsService orderLogsService;

    public void setRadiologyDao(ModalityService modalityService, HL7ServiceImpl hl7Service, OrderLogsService orderLogsService) {
        this.modalityService = modalityService;
        this.hl7Service = hl7Service;
        this.orderLogsService = orderLogsService;
    }

    public String sendMessage(AbstractMessage message, Integer orderTypeId) throws HL7Exception, LLPException, IOException {
        Optional<Modality> modalityRecord = modalityService.getByOrderTypeId(orderTypeId);

        if (!modalityRecord.isPresent()) {
            throw new ModalityException("No modality record found.", null);
        }
        Modality modality = modalityRecord.get();
        Message response = post(modality, message);
        String responseMessage = parseResponse(response);
        if (response instanceof ORR_O02) {
            ORR_O02 acknowledgement = (ORR_O02) response;
            String acknowledgmentCode = acknowledgement.getMSA().getAcknowledgmentCode().getValue();
            processAcknowledgement(modality, responseMessage, acknowledgmentCode);
        }
        else if (response instanceof ACK) {
            ACK acknowledgement = (ACK) response;
            String acknowledgmentCode = acknowledgement.getMSA().getAcknowledgmentCode().getValue();
            processAcknowledgement(modality, responseMessage, acknowledgmentCode);
        }
        else {
            throw new ModalityException(responseMessage, modality);
        }
        return responseMessage;
    }

    Message post(Modality modality, Message requestMessage) throws LLPException, IOException, HL7Exception {
        Connection newClientConnection = null;
        try {
            HapiContext hapiContext = new DefaultHapiContext();
            newClientConnection = hapiContext.newClient(modality.getIp(), modality.getPort(), false);
            Initiator initiator = newClientConnection.getInitiator();
            return initiator.sendAndReceive(requestMessage);
        } finally {
            if (newClientConnection != null) {
                newClientConnection.close();
            }
        }
    }

    String parseResponse(Message response) throws HL7Exception {
        return new PipeParser().encode(response);
    }

    private void processAcknowledgement(Modality modality, String responseMessage, String acknowledgmentCode) {
        if (!AcknowledgmentCode.AA.toString().equals(acknowledgmentCode)) {
            throw new ModalityException(responseMessage, modality);
        }
    }

    public void processOrder(RadiologyOrder radiologyOrder) throws HL7Exception, LLPException, IOException {
        AbstractMessage request = hl7Service.createMessage(radiologyOrder);
        String response = sendMessage(request, radiologyOrder.getOrderType().getId());

        orderLogsService.save(radiologyOrder, request.encode(), response, null);
    }
}
