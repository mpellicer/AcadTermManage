package net.stellers.sakai.academicsessionadmin.tool.pages;

import java.util.Locale;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.head.StringHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import net.stellers.sakai.academicsessionadmin.logic.AcademicSessionLogic;
import net.stellers.sakai.academicsessionadmin.logic.AcademicSessionSakaiProxy;


public class BasePage extends WebPage implements IHeaderContributor {


	private static final long serialVersionUID = 1L;

	
	@SpringBean(name="net.stellers.sakai.academicsessionadmin.logic.AcademicSessionSakaiProxy")
	private AcademicSessionSakaiProxy sakaiProxy;
	
	@SpringBean(name="net.stellers.sakai.academicsessionadmin.logic.AcademicSessionLogic")
	protected AcademicSessionLogic semesterLogic;
	
	
	private FeedbackPanel feedbackPanel;
	
	public BasePage() {
		super();
		if (!semesterLogic.isAcademicSessionManager()) {
			throw new SecurityException("This site can only be accessed if you have been granted the necessary permission(s)");
		}			
		// Add a FeedbackPanel for displaying our messages
        feedbackPanel = createFeedbackPanel("feedback");
		add(feedbackPanel); 		
		initLocale();		
    }
	
	
	
	protected FeedbackPanel createFeedbackPanel(final String componentId){

				FeedbackPanel myfeedbackPanel = new FeedbackPanel(componentId) {
					private static final long serialVersionUID = 1L;

					@Override
					protected Component newMessageDisplayComponent(final String id,
							final FeedbackMessage message) {															
						final Component newMessageDisplayComponent = super.newMessageDisplayComponent(
								id,message);
						 int msgLevel = message.getLevel();
						 if (msgLevel== FeedbackMessage.ERROR
                            || msgLevel == FeedbackMessage.DEBUG
                            || msgLevel == FeedbackMessage.FATAL
                            || msgLevel == FeedbackMessage.WARNING) {
							 add(new AttributeModifier("class", "term-admin-feedbackpanel alertMessage"));
						 }
	                     else if (msgLevel == FeedbackMessage.INFO 
	                    		 || msgLevel == FeedbackMessage.SUCCESS) {
                            add(new AttributeModifier("class", "term-admin-feedbackpanel success"));
	                     }
						 return newMessageDisplayComponent;
						
					}
				};

				myfeedbackPanel.setOutputMarkupId(true);
				return myfeedbackPanel;
	}
	
	
	protected void clearFeedback(){
		clearFeedback(feedbackPanel);
	}
	
	/**
	 * Helper to clear the feedbackpanel display.
	 * @param f	FeedBackPanel
	 */
	private void clearFeedback(FeedbackPanel f) {
		if(!f.hasFeedbackMessage()) {
			f.add(new AttributeModifier("class", ""));
		}
	}
	
	
	
	@Override
	public Locale getLocale(){
		Locale sakaiLocale = sakaiProxy.getCurrentUserLocale();
		if (sakaiLocale != null) {
			return sakaiLocale;
		}
		return super.getLocale();
	}
	

	protected void initLocale(){
		Locale l = getLocale();
		if (l != null) {
			Session s = getSession();
			s.setLocale(l);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * This block adds the required wrapper markup to style it like a Sakai tool. 
	 * Add to this any additional CSS or JS references that you need.
	 * 
	 */
	public void renderHead(IHeaderResponse response) {
		
		
		//get Sakai skin
		String skinRepo = sakaiProxy.getSkinRepoProperty();
		String toolCSS = sakaiProxy.getToolSkinCSS(skinRepo);
		String toolBaseCSS = skinRepo + "/tool_base.css";
		
		//Sakai additions				
		response.render(JavaScriptReferenceHeaderItem.forUrl("/library/js/headscripts.js"));
		response.render(CssReferenceHeaderItem.forUrl(toolBaseCSS));
		response.render(CssReferenceHeaderItem.forUrl(toolCSS));
		response.render (OnLoadHeaderItem.forScript("setMainFrameHeight( window.name )"));
		response.render(StringHeaderItem.forString("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"));					
		
	}
	
	
	/** 
	 * Helper to disable a link. Add the Sakai class 'current'.
	 */
	protected void disableLink(Link<Void> l) {
		l.add(new AttributeAppender("class", new Model<String>("current"), " "));
		l.setRenderBodyOnly(true);
		l.setEnabled(false);
	}
	
	
	
}
