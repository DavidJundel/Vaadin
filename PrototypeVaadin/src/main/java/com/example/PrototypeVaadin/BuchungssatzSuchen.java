package com.example.PrototypeVaadin;

import java.util.Date;

import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;

@CDIView("Buchungssatz suchen")
public class BuchungssatzSuchen extends CustomComponent implements View, ClickListener {

	TabSheet ts = new TabSheet();
	VerticalLayout tab1 = new VerticalLayout();
	VerticalLayout tab2 = new VerticalLayout();
	VerticalLayout tab3 = new VerticalLayout();
	VerticalLayout tab4 = new VerticalLayout();
	VerticalLayout tab5 = new VerticalLayout();
	VerticalLayout tab6 = new VerticalLayout();

	Navigator navigator;
	AbsoluteLayout layout = new AbsoluteLayout();
	String value;
	String konto;
	String gegenkonto;
	String buchungstext;
	double betrag;
	Date buchungsdatum = new Date();
	FormLayout content = new FormLayout();

	Long id;

	@Inject
	BuchungssatzDAO buchungssatzdao;

	@Override
	public void enter(ViewChangeEvent event) {

		navigator = getUI().getNavigator();

		tab1.setCaption("Buchungssatz(einzeln)");
		tab2.setCaption("Buchungssatz ändern");
		tab3.setCaption("Buchungssatz speichern");
		tab4.setCaption("Buchungssatz löschen");
		tab5.setCaption("Buchungssatz(Liste)");
		tab6.setCaption("Buchungssatz suchen");
		ts.addTab(tab1);
		ts.addTab(tab2);
		ts.addTab(tab3);
		ts.addTab(tab4);
		ts.addTab(tab5);
		ts.addTab(tab6);

		ts.setSelectedTab(tab6);

		ts.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {
			public void selectedTabChange(SelectedTabChangeEvent event) {

				TabSheet tabsheet = event.getTabSheet();
				Layout tab = (Layout) tabsheet.getSelectedTab();
				String caption = tabsheet.getTab(tab).getCaption();

				if (caption.equals("Buchungssatz(einzeln)")) {
					navigator.navigateTo("Buchungssatz suchen");
				} else if (caption.equals("Buchungssatz ändern")) {
					navigator.navigateTo("Buchungssatz aendern");
				} else if (caption.equals("Buchungssatz speichern")) {
					navigator.navigateTo("Buchungssatz speichern");
				} else if (caption.equals("Buchungssatz löschen")) {
					navigator.navigateTo("Buchungssatz loeschen");
				} else if (caption.equals("Buchungssatz(Liste)")) {
					navigator.navigateTo("Buchungssätze laden");
				}
			}
		});

		layout.setWidth("1400px");
		layout.setHeight("500px");

		TextField idText = new TextField();
		idText.setCaption("ID:");

		TextField kontoText = new TextField();
		kontoText.setCaption("Konto");

		TextField gegenkontoText = new TextField();
		gegenkontoText.setCaption("Gegenkonto");

		TextField buchungsText = new TextField();
		buchungsText.setCaption("Buchungstext");

		TextField betragText = new TextField();
		betragText.setCaption("Betrag");

		TextField datumText = new TextField();
		datumText.setCaption("Datum");

		idText.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialversionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				value = (String) event.getProperty().getValue();
				id = Long.valueOf(value);
			}
		});

		Button load = new Button("Buchungssatz suchen");
		load.setClickShortcut(KeyCode.ENTER);
		load.addStyleName("button_style");
		load.addClickListener(e -> {

			if (id == null) {
				id = 0L;
			}
			try {
				buchungssatzdao.load(id);
				konto = (buchungssatzdao.daten().getKonto());
				gegenkonto = (buchungssatzdao.daten().getGegenkonto());
				buchungstext = (buchungssatzdao.daten().getBuchungstext());

				kontoText.setValue(konto);
				gegenkontoText.setValue(gegenkonto);
				buchungsText.setValue(buchungstext);

			} catch (NullPointerException np) {
				Notification notification = new Notification("Buchungssatz existiert nicht!");
				notification.show(Page.getCurrent());

			}

		});

		Button abmelden = new Button("Abmelden");
		abmelden.setIcon(FontAwesome.SIGN_OUT);
		abmelden.addClickListener(e -> {
			navigator.navigateTo("login");
		});

		Button menu = new Button("Menü");
		menu.addClickListener(e -> {
			navigator.navigateTo("start2");
		});

		Button refresh = new Button("Seite neu laden");
		refresh.setClickShortcut(KeyCode.F5);
		refresh.setIcon(FontAwesome.REFRESH);
		refresh.addClickListener(e -> {

			content.addComponents(refresh, ts, idText, kontoText, gegenkontoText, buchungsText, betragText, datumText,
					load);

			AbsoluteLayout absolute = new AbsoluteLayout();
			Panel panel = new Panel();
			panel.setHeight("800px");
			panel.setWidth("280px");
			panel.addStyleName("my_bg_style");
			FormLayout form = new FormLayout();

			form.addComponents(menu, abmelden, Startseite.menu);
			panel.setContent(form);
			absolute.addComponent(panel, "left:0px;top:0px;bottom:0px;");
			absolute.addComponent(content, "left:300px;");

			absolute.setWidth("1600px");
			absolute.setHeight("800px");

			setCompositionRoot(absolute);

		});

		content.addComponents(refresh, ts, idText, kontoText, gegenkontoText, buchungsText, betragText, datumText,
				load);

		AbsoluteLayout absolute = new AbsoluteLayout();
		Panel panel = new Panel();
		panel.setHeight("1000px");
		panel.setWidth("280px");
		panel.addStyleName("my_bg_style");
		FormLayout form = new FormLayout();

		form.addComponents(menu, abmelden, Startseite.menu);
		panel.setContent(form);
		absolute.addComponent(panel, "left:0px;top:0px;bottom:0px;");
		absolute.addComponent(content, "left:300px;");

		absolute.setWidth("1600px");
		absolute.setHeight("800px");

		setCompositionRoot(absolute);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}

}