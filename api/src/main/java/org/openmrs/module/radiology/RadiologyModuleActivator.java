package org.openmrs.module.radiology;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.BaseModuleActivator;

/**
 * This class contains the logic that is run every time this module is either started or stopped.
 */
public class RadiologyModuleActivator extends BaseModuleActivator {
	
	private static final Log LOG = LogFactory.getLog(RadiologyModuleActivator.class);
	
	/**
	 * @see BaseModuleActivator#contextRefreshed()
	 */
	@Override
	public void contextRefreshed() {
		LOG.info("OpenMRS Radiology Module Module refreshed");
	}
	
	/**
	 * @see BaseModuleActivator#started()
	 */
	@Override
	public void started() {
		LOG.info("OpenMRS Radiology Module Module started");
	}
	
	/**
	 * @see BaseModuleActivator#stopped()
	 */
	@Override
	public void stopped() {
		LOG.info("OpenMRS Radiology Module Module stopped");
	}
}
