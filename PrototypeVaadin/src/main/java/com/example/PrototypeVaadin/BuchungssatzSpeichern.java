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
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TextArea;

@CDIView("Buchungssatz speichern")
public class BuchungssatzSpeichern extends CustomComponent implements View, ClickListener {

	@Inject
	BuchungssatzDAO buchungssatzdao;
	@Inject
	Buchungssatz buchungssatz;

	private static final long serialVersionUID = 1L;
	String Konto = "";
	String Gegenkonto = "";
	String Buchungstext = "";
	String Betrag = "";
	private Navigator navigator;
	final AbsoluteLayout layout = new AbsoluteLayout();
	MenuItem laden;
	MenuItem bearbeiten;
	MenuItem löschen;
	MenuItem startseite;
	Double euro;
	FormLayout content = new FormLayout();
	Panel panel = new Panel();
	TabSheet ts = new TabSheet();
	VerticalLayout tab1 = new VerticalLayout();
	VerticalLayout tab2 = new VerticalLayout();
	VerticalLayout tab3 = new VerticalLayout();
	VerticalLayout tab4 = new VerticalLayout();
	VerticalLayout tab5 = new VerticalLayout();
	Long id;

	public void enter(ViewChangeEvent event) {

		tab1.setCaption("Buchungssatz(einzeln)");
		tab2.setCaption("Buchungssatz ändern");
		tab3.setCaption("Buchungssatz speichern");
		tab4.setCaption("Buchungssatz löschen");
		tab5.setCaption("Buchungssatz(Liste)");
		ts.addTab(tab1);
		ts.addTab(tab2);
		ts.addTab(tab3);
		ts.addTab(tab4);
		ts.addTab(tab5);

		ts.setSelectedTab(tab3);

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

		navigator = getUI().getNavigator();

		final TextField kontoText = new TextField();
		kontoText.setCaption("Konto:");

		final TextField gegenkontoText = new TextField();
		gegenkontoText.setCaption("Gegenkonto:");

		final TextArea buchungsText = new TextArea();
		buchungsText.setCaption("Buchungstext:");

		final TextField betrag = new TextField();
		betrag.setCaption("Betrag:");

		kontoText.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				Konto = (String) event.getProperty().getValue();

			}
		});

		gegenkontoText.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				Gegenkonto = (String) event.getProperty().getValue();
			}
		});

		buchungsText.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				Buchungstext = (String) event.getProperty().getValue();
			}
		});

		betrag.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				Betrag = (String) event.getProperty().getValue();
				euro = Double.parseDouble(Betrag);
			}
		});

		Button save = new Button("Speichern");
		save.addStyleName("button_style");
		save.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {

				id = buchungssatzdao.getId().get(0);

				if (Konto.equals(Gegenkonto) || (euro <= 0) || Konto.isEmpty() || Gegenkonto.isEmpty()
						|| (String.valueOf(euro) == null) || Buchungstext.isEmpty()) {

					Notification notification = new Notification("Eingabe ungültig!");
					notification.show(Page.getCurrent());

				} else {
					id = id++;
					buchungssatz = new Buchungssatz(id, Konto, Gegenkonto, Buchungstext, EStatus.A, euro, new Date(),
							123);
					buchungssatzdao.save(buchungssatz);

					Notification notification = new Notification("Buchungssatz angelegt!");
					notification.show(Page.getCurrent());
					kontoText.setValue("");
					gegenkontoText.setValue("");
					buchungsText.setValue("");
					betrag.setValue("0");
				}
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

		Button reset = new Button("Felder leeren");
		reset.addStyleName("button_style");
		reset.setIcon(FontAwesome.WARNING);
		reset.addClickListener(e -> {
			kontoText.clear();
			gegenkontoText.clear();
			buchungsText.clear();
			betrag.setValue("0");
		});

		Button refresh = new Button("Seite neu laden");
		refresh.setClickShortcut(KeyCode.F5);
		refresh.setIcon(FontAwesome.REFRESH);
		refresh.addClickListener(e -> {

			content.addComponents(refresh, ts, kontoText, gegenkontoText, buchungsText, betrag, save, reset);

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

		content.addComponents(refresh, ts, kontoText, gegenkontoText, buchungsText, betrag, save, reset);

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
