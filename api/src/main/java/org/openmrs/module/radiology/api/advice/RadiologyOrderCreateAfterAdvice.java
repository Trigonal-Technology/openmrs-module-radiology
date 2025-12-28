package org.openmrs.module.radiology.api.advice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Order;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.radiology.api.model.RadiologyOrder;
import org.openmrs.module.radiology.api.service.PacsIntegrationService;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class RadiologyOrderCreateAfterAdvice implements AfterReturningAdvice {

    private static final Log LOG = LogFactory.getLog(RadiologyOrderCreateAfterAdvice.class);

    PacsIntegrationService pacsService = Context.getService(PacsIntegrationService.class);


    @Override
    public void afterReturning(Object o, Method method, Object[] args, Object target) throws Throwable {
        LOG.info("Inside RadiologyOrderCreateAfterAdvice > afterReturning method");
        try {
            if (method.getName().equals("saveOrder") && args.length > 0 && args[0] instanceof Order) {
                Order order = (Order) args[0];


                if (order instanceof RadiologyOrder) {
                    RadiologyOrder radiologyOrder = (RadiologyOrder) order;

                    //Check if PACS integration is enabled
                    AdministrationService adminService = Context.getAdministrationService();
                    String enablePacsIntegration = adminService.getGlobalProperty("radiology.enablePacsIntegration", "true");

                    if (Boolean.parseBoolean(enablePacsIntegration)) {
                        //Call pacs Service to send
                        pacsService.processOrder(radiologyOrder);
                        LOG.info("PACS integration enabled - sent order to modality");
                    } else {
                        // Skip PACS integration - middleware will handle it
                        LOG.info("PACS integration disabled - middleware will handle modality requests");
                    }
                }

            }
        } catch (Exception e) {
            LOG.error("Error intercepting after order creation: " + e.getMessage(), e);
        }
    }
}