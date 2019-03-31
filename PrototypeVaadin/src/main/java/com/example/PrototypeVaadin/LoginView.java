package com.example.PrototypeVaadin;

import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@CDIView("login")
public class LoginView extends VerticalLayout implements View {

	@Inject
	LoginDAO login;

	Navigator navigator;
	String name;
	String passwort;
	Label text = new Label("Login");
	CheckBox cb=new CheckBox("Angemeldet bleiben");

	public LoginView() {

		text.addStyleName("my_bg_style");
		Panel panel = new Panel("Login");
		panel.setSizeUndefined();
		panel.setContent(text);
		panel.addStyleName("my_panel");
		addComponent(panel);

		FormLayout content = new FormLayout();
		TextField username = new TextField("Benutzername");
		content.addComponent(username);
		PasswordField password = new PasswordField("Passwort");
		content.addComponent(password);

		Button anmelden = new Button("Anmelden");
		anmelden.setClickShortcut(KeyCode.ENTER);

		username.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				name = (String) event.getProperty().getValue();
			}
		});

		password.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				passwort = (String) event.getProperty().getValue();

			}
		});

		anmelden.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (name == null || passwort == null) {
					content.addComponent(new Label("Falsches Login Daten!"));
				} else if (login.exists(name, passwort) == true) {
					navigator.navigateTo("start");
				} else {
					content.addComponent(new Label("Falsches Login Daten!"));
				}
			}

		});

		Button registrieren = new Button("Registrieren");

		registrieren.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				navigator.navigateTo("registrieren");

			}
		});

		content.addComponents(cb,anmelden, registrieren);
		content.setSizeUndefined();
		content.setMargin(true);
		panel.setContent(content);
		setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

	}

	@Override
	public void enter(ViewChangeEvent event) {

		navigator = getUI().getNavigator();

	}
}