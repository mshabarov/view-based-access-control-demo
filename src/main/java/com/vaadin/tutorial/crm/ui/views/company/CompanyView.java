package com.vaadin.tutorial.crm.ui.views.company;

import javax.annotation.security.PermitAll;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RoutePrefix;
import com.vaadin.flow.router.RouterLink;

@RoutePrefix("company")
@Route("")
@PermitAll
public class CompanyView extends VerticalLayout {

    public CompanyView() {
        this.setSizeFull();
        H3 header = new H3("Company information page");
        RouterLink companiesInfo = new RouterLink("Companies common information",
                CompanyDescriptionView.class);
        RouterLink companyFeedbacks = new RouterLink("Company feedbacks", CompanyFeedbackView.class);
        RouterLink companyEdit = new RouterLink("Edit company information", CompanyEditView.class);
        add(header, companiesInfo, companyFeedbacks, companyEdit);
    }
}
