package com.example.PrototypeVaadin;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.cdi.CDIView;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;

@CDIView("start")
@PreserveOnRefresh
public class Startseite extends CustomComponent implements View {

	private Navigator navigator;

	static Tree menu = new Tree();;

	static Link linkAendern = new Link("Buchungssatz ändern",
			new ExternalResource("http://localhost:8080/ValoPrototypeUI/#!Buchungssatz%20aendern"));
	static Link linkSpeichern = new Link("Buchungssatz speichern",
			new ExternalResource("http://localhost:8080/ValoPrototypeUI/#!Buchungssatz%20speichern"));
	static Link linkLoeschen = new Link("Buchungssatz löschen",
			new ExternalResource("http://localhost:8080/ValoPrototypeUI/#!Buchungssatz%20loeschen"));
	static Link linkLaden = new Link("Buchungssätze laden(Liste)",
			new ExternalResource("http://localhost:8080/ValoPrototypeUI/#!Buchungssatz%20laden"));
	static Link linkAll = new Link("Buchungssatz laden(einzeln)",
			new ExternalResource("http://localhost:8080/ValoPrototypeUI/#!All"));

	@Override
	public void enter(ViewChangeEvent event) {

		navigator = getUI().getNavigator();

		menu.addItem("Buchungssatz");
		menu.setItemIcon("Buchungssatz", FontAwesome.BOOK);

		menu.addItem("Wirtschaftsjahr");
		menu.setItemIcon("Wirtschaftsjahr", FontAwesome.CALCULATOR);

		menu.addItem("Bestellung");
		menu.setItemIcon("Bestellung", FontAwesome.REORDER);

		menu.addItem("Buchungssatz bearbeiten");
		menu.setItemIcon("Buchungssatz bearbeiten", FontAwesome.EDIT);

		menu.addItem("Buchungssatz anlegen");
		menu.setItemIcon("Buchungssatz anlegen", FontAwesome.SAVE);

		menu.addItem("Buchungssatz löschen");
		menu.setItemIcon("Buchungssatz löschen", FontAwesome.ERASER);

		menu.addItem("Buchungssätze laden");
		menu.setItemIcon("Buchungssätze laden", FontAwesome.UPLOAD);

		menu.addItem("Buchungssatz suchen");
		menu.setItemIcon("Buchungssatz suchen", FontAwesome.SEARCH);

		menu.addItem("Wirtschaftsjahr abschließen");
		menu.setItemIcon("Wirtschaftsjahr abschließen", FontAwesome.CHECK);

		menu.setParent("Buchungssatz bearbeiten", "Buchungssatz");
		menu.setParent("Buchungssatz anlegen", "Buchungssatz");
		menu.setParent("Buchungssatz löschen", "Buchungssatz");
		menu.setParent("Buchungssätze laden", "Buchungssatz");
		menu.setParent("Buchungssatz suchen", "Buchungssatz");

		menu.setParent("Wirtschaftsjahr abschließen", "Wirtschaftsjahr");

		menu.setParent("Bestellung", "Bestellung");

		menu.addValueChangeListener(event2 -> {

			Object o = event2.getProperty().getValue();
			String s = String.valueOf(o);

			if (s.equals("Buchungssatz bearbeiten")) {
				navigator.navigateTo("Buchungssatz aendern");

			} else if (s.equals("Buchungssatz anlegen")) {
				navigator.navigateTo("Buchungssatz speichern");
			} else if (s.equals("Buchungssatz löschen")) {
				navigator.navigateTo("Buchungssatz loeschen");
			} else if (s.equals("Buchungssätze laden")) {
				navigator.navigateTo("Buchungssätze laden");
			} else if (s.equals("Buchungssatz suchen")) {
				navigator.navigateTo("Buchungssatz suchen");
			} else if (s.equals("Bestellung")) {
				navigator.navigateTo("Bestellung");
			}

		});

		Button refresh = new Button("Seite neu laden");
		refresh.setClickShortcut(KeyCode.F5);
		refresh.setIcon(FontAwesome.REFRESH);
		refresh.addClickListener(e -> {

			AbsoluteLayout absolute = new AbsoluteLayout();
			FormLayout form = new FormLayout();
			form.addComponents(refresh, menu);

			Panel panel = new Panel();
			panel.setContent(form);
			panel.setWidth("280px");
			panel.setHeight("800px");
			panel.addStyleName("my_bg_style");

			absolute.addComponent(panel);

			absolute.addComponent(panel, "left:0px;top:0px;bottom:0px;");

			absolute.setWidth("1600px");
			absolute.setHeight("800px");

			setCompositionRoot(absolute);

		});

		AbsoluteLayout absolute = new AbsoluteLayout();
		FormLayout form = new FormLayout();
		form.addComponents(refresh, menu);

		Panel panel = new Panel();
		panel.setContent(form);
		panel.setWidth("280px");
		panel.setHeight("800px");
		panel.addStyleName("my_bg_style");

		absolute.addComponent(panel);

		absolute.addComponent(panel, "left:0px;top:0px;bottom:0px;");

		absolute.setWidth("1600px");
		absolute.setHeight("800px");

		setCompositionRoot(absolute);
	}

}
