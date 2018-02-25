package net.stellers.sakai.academicsessionadmin.logic.impl;

public interface SakaiConstants {
	
	// defined by Sakai:
	public static final String PROP_NAME_TERM_EID="term_eid";
	public static final String PROP_NAME_TERM_TITLE="term";
	
	
	// defined by myself, for posting events to the sakai eventservice:
	
	/** Name of event being posted to the sakai eventservice if an academic session has been updated. */
	public static final String EVENTSERVICE_EVENT_ACADEMICSESSION_UPDATE = "termadmin.as.upd";

	/** Name of event being posted to the sakai eventservice if an academic session has been added. */
	public static final String EVENTSERVICE_EVENT_ACADEMICSESSION_ADD = "termadmin.as.add";

	
	/** Prefix used to build resource references for academic session events.
	 *  The reference will be built by appending the updated academic session's EID to this prefix.
	 */
	// TODO look at the course management API if it already defines a prefix for its AcademicSessions
	public static final String EVENTSERVICE_EVENT_RESOURCE_PREFIX= "/academicsession/";
}
