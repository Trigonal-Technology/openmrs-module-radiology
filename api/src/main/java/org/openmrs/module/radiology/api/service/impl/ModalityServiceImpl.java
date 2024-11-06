package org.openmrs.module.radiology.api.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.radiology.api.dao.ModalityDao;
import org.openmrs.module.radiology.api.model.Modality;
import org.openmrs.module.radiology.api.service.ModalityService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class ModalityServiceImpl implements ModalityService {
    private static final Log LOG = LogFactory.getLog(ModalityServiceImpl.class);

    private ModalityDao modalityDao;

    public void setModalityDao(ModalityDao modalityDao) {
        this.modalityDao = modalityDao;
    }

    @Override
    public Optional<Modality> get(int id) {
        LOG.info("Inside get Modality");
        return modalityDao.get(id);
    }

    @Override
    public Modality saveOrUpdate(Modality modality) {
        LOG.info("Inside saveOrUpdate Modality");
        return modalityDao.saveOrUpdate(modality);
    }
} 