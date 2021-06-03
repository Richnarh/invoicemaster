/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.im.admin.listener;

import com.khoders.im.admin.Pages;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.util.Faces;

/**
 *
 * @author pascal
 */
@Named
@RequestScoped
public class SessionListener implements PhaseListener
{

    @Inject
    private AppSession appSession;

    @Override
    public void beforePhase(final PhaseEvent event)
    {
        if (event.getPhaseId() == PhaseId.RENDER_RESPONSE)
        {
            FacesContext facesContext = event.getFacesContext();

            String viewId = facesContext.getViewRoot().getViewId();
            if (appSession != null)
            {
                if (viewId.contains("app"))
                {
                    if (appSession.getCurrentUser() == null)
                    {
                        try
                        {
                            Faces.redirect(Pages.login);
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void afterPhase(PhaseEvent pe)
    {

    }

    @Override
    public PhaseId getPhaseId()
    {
        return PhaseId.ANY_PHASE;
    }

}
