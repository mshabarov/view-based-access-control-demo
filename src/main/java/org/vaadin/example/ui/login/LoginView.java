package org.vaadin.example.ui.login;

import javax.inject.Inject;

import org.vaadin.example.backend.service.SecurityUtils;
import org.vaadin.example.ui.home.HomeView;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver,
        ComponentEventListener<AbstractLogin.LoginEvent> {

    private final LoginForm login = new LoginForm();

    private SecurityUtils securityUtils;

    @Inject
    public LoginView(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;

        addClassName("login-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        login.addLoginListener(this);

        add(new H1("Test Application"), login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }

    @Override
    public void onComponentEvent(AbstractLogin.LoginEvent loginEvent) {
        boolean authenticate = securityUtils.authenticate(
                loginEvent.getUsername(), loginEvent.getPassword());
        if (authenticate) {
            UI.getCurrent().navigate(HomeView.class);
        } else {
            login.setError(true);
        }
    }
}
