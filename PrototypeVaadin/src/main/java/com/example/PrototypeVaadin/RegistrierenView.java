package com.example.PrototypeVaadin;

import javax.inject.Inject;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@CDIView("registrieren")
public class RegistrierenView extends CustomComponent implements View {

	@Inject
	LoginDAO logindao;

	Login login;
	FormLayout content = new FormLayout();
	TextField user;
	PasswordField password;
	PasswordField password2;
	Label text;
	String username;
	String passwort;
	String passwort2;
	Navigator navigator;

	public RegistrierenView() {

		text = new Label("Bitte geben Sie hier Ihre gewünschten Login Daten ein");
		user = new TextField("Benutzername");
		user.setRequired(true);
		password = new PasswordField("Passwort");
		password.addValidator(
				new StringLengthValidator("Passwort muss zwischen 8 und 30 Zeichen lang sein!", 8, 30, true));
		password.setRequired(true);
		password2 = new PasswordField("Passwort bestätigen");
		password2.setRequired(true);

		Button account = new Button("Account anlegen");
		account.setClickShortcut(KeyCode.ENTER);

		user.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				username = (String) event.getProperty().getValue();
			}
		});

		password.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				passwort = (String) event.getProperty().getValue();

			}
		});

		password2.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				passwort2 = (String) event.getProperty().getValue();

			}
		});

		account.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (username == null || passwort == null || passwort2 == null) {
					content.addComponent(new Label("Bitte alle Felder ausfüllen!"));
				} else if (!(passwort.equals(passwort2))) {
					content.addComponent(new Label("Passwörter nicht identisch. Bitte Eingabe überprüfen."));
				} else if (passwort.length() < 8) {
					content.addComponent(new Label("Passwort muss mindesten 8 Zeichen lang sein!"));
				} else {
					content.addComponent(new Label("Account erstellt!"));
					login = new Login(username, passwort);
					logindao.register(login);
					navigator.navigateTo("login");

				}
			}
		});

		Button abbrechen = new Button("Abbrechen");
		abbrechen.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("login");
			}
		});

		content.addComponents(text, user, password, password2, account, abbrechen);

		Panel panel = new Panel();
		panel.setContent(content);
		setCompositionRoot(panel);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

		navigator = getUI().getNavigator();

	}

}
