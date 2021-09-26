package com.vaadin.tutorial.crm.ui.views.company;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.crm.backend.service.CompanyService;

@Route("edit")
@RolesAllowed("ADMIN")
public class CompanyEditView extends CompanyView {

    public CompanyEditView(@Autowired CompanyService companyService) {
        add(new H4("Company edit page"));
        companyService.findAll().forEach(company -> {
            HorizontalLayout companyLine = new HorizontalLayout(new Span(company.getName()), new Button(
                    "Edit", VaadinIcon.EDIT.create()));
            add(companyLine);
        });

    }
}
