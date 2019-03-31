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
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;

@CDIView("Buchungssatz laden")
public class BuchungssatzEinzeln extends CustomComponent implements View, ClickListener {

	@Inject
	BuchungssatzDAO buchungssatzdao;

	String value;
	String konto;
	String gegenkonto;
	String buchungstext;
	long id;
	double betrag;
	Date datum;
	Long wert;
	private Navigator navigator;
	final AbsoluteLayout layout = new AbsoluteLayout();
	MenuItem speichern;
	MenuItem bearbeiten;
	MenuItem löschen;
	MenuItem startseite;
	Table table = new Table();
	Button edit;
	Button save;
	Button laden;
	Button show;
	Button delete;
	String wahl;
	String test;
	TextField Konto;
	TextField Gegenkonto;
	TextField Buchungstext;
	TextField Betrag;
	FormLayout content = new FormLayout();
	Panel panel = new Panel();
	Label sumField = new Label();
	TabSheet ts = new TabSheet();
	VerticalLayout tab1 = new VerticalLayout();
	VerticalLayout tab2 = new VerticalLayout();
	VerticalLayout tab3 = new VerticalLayout();
	VerticalLayout tab4 = new VerticalLayout();
	VerticalLayout tab5 = new VerticalLayout();

	@SuppressWarnings("serial")
	@Override
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

		ts.setSelectedTab(tab1);

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

		TextField Konto = new TextField();
		TextField Gegenkonto = new TextField();
		TextField Buchungstext = new TextField();
		TextField Betrag = new TextField();

		Konto.setReadOnly(false);
		Gegenkonto.setReadOnly(false);
		Buchungstext.setReadOnly(false);
		Betrag.setReadOnly(false);

		NativeSelect select = new NativeSelect("Suchen nach:");
		select.addItems("Datum", "Id", "Alles");
		select.setValue("Alles");
		select.addStyleName("button_style");

		table.addContainerProperty("ID", Long.class, null);
		table.addContainerProperty("Konto", TextField.class, null);
		table.addContainerProperty("Gegenkonto", TextField.class, null);
		table.addContainerProperty("Buchungstext", TextField.class, null);
		table.addContainerProperty("Betrag", TextField.class, null);
		table.addContainerProperty("Datum", Date.class, null);
		table.addContainerProperty("Option", Button.class, null);
		table.addContainerProperty("Option2", Button.class, null);
		table.addContainerProperty("Option3", Button.class, null);
		table.addContainerProperty("HTML", Label.class, null);
		table.setPageLength(table.size());

		navigator = getUI().getNavigator();

		final TextField nameText = new TextField();
		nameText.setCaption("Buchungssatz Id:");

		final TextField kontoText = new TextField();
		kontoText.setCaption("Konto");

		final TextField gegenkontoText = new TextField();
		gegenkontoText.setCaption("Gegenkonto:");

		final TextField buchungstextText = new TextField();
		buchungstextText.setCaption("Buchungstext");

		TextField von = new TextField();
		von.setCaption("von");
		von.setEnabled(false);

		TextField bis = new TextField();
		bis.setCaption("bis");
		bis.setEnabled(false);

		TextField soll = new TextField();
		soll.setCaption("Soll");
		soll.setEnabled(false);

		TextField haben = new TextField();
		haben.setCaption("Haben");
		haben.setEnabled(false);

		soll.setEnabled(false);
		haben.setEnabled(false);
		von.setEnabled(false);
		bis.setEnabled(false);

		Konto.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				konto = (String) event.getProperty().getValue();
			}
		});

		Gegenkonto.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				gegenkonto = (String) event.getProperty().getValue();
			}
		});

		Buchungstext.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				buchungstext = (String) event.getProperty().getValue();
			}
		});

		Betrag.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				String test = (String) event.getProperty().getValue();
				betrag = Double.parseDouble(test);
			}
		});

		select.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				wahl = (String) event.getProperty().getValue();
				if (wahl.equals("Datum")) {
					von.setEnabled(true);
					bis.setEnabled(true);
					soll.setEnabled(false);
					haben.setEnabled(false);
				}
				if (wahl.equals("Id")) {
					soll.setEnabled(true);
					haben.setEnabled(true);
					von.setEnabled(false);
					bis.setEnabled(false);
				}
			}
		});

		nameText.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				value = (String) event.getProperty().getValue();
				wert = Long.valueOf(value);

				Konto.setReadOnly(false);
				Gegenkonto.setReadOnly(false);
				Buchungstext.setReadOnly(false);
				Betrag.setReadOnly(false);
			}
		});

		save = new Button("Speichern");
		save.setIcon(FontAwesome.SAVE);
		save.addClickListener(e -> {
			table.setSelectable(false);
			table.setEditable(false);
			save.setEnabled(false);
			edit.setEnabled(true);
			delete.setEnabled(true);
			buchungssatzdao.edit(id, konto, gegenkonto, buchungstext, datum, betrag);
			Konto.setReadOnly(true);
			Gegenkonto.setReadOnly(true);
			Buchungstext.setReadOnly(true);
			Betrag.setReadOnly(true);
		});

		edit = new Button("Bearbeiten");
		edit.addClickListener(e -> {
			save.setEnabled(true);
			edit.setEnabled(false);
			delete.setEnabled(false);

			Konto.setReadOnly(false);
			Gegenkonto.setReadOnly(false);
			Buchungstext.setReadOnly(false);
			Betrag.setReadOnly(false);
		});

		delete = new Button("Buchungssatz löschen");
		delete.addClickListener(e -> {

			Window subWindow = new Window("Sub-window");
			VerticalLayout subContent = new VerticalLayout();
			subContent.setMargin(true);
			subWindow.setContent(subContent);

			subContent.addComponent(new Label("Meatball sub"));
			subContent.addComponent(new Button("Awlright"));

			subWindow.center();

			buchungssatzdao.delete(id);

			Notification notification = new Notification("Buchungssatz gelöscht.");
			notification.show(Page.getCurrent());

		});

		sumField = new Label(String.format("Preis: <b>$%04.2f</b>", new Object[] { new Double(Math.random() * 1000) }),
				ContentMode.HTML);

		laden = new Button("Laden");
		laden.addStyleName("button_style");
		laden.addClickListener(e -> {

			if (nameText.getValue() == null) {
				Notification.show("Bitte gueltigen Wert eingeben!", Notification.Type.WARNING_MESSAGE);
			} else {

				if ((buchungssatzdao.load(wert)) == null) {
					Notification.show("Buchungssatz ungültig.", Notification.Type.WARNING_MESSAGE);
				} else {

					id = (buchungssatzdao.daten().getId());
					konto = (buchungssatzdao.daten().getKonto());
					gegenkonto = (buchungssatzdao.daten().getGegenkonto());
					buchungstext = (buchungssatzdao.daten().getBuchungstext());
					betrag = (buchungssatzdao.daten().getBetrag());
					datum = (buchungssatzdao.daten().getBuchungsdatum());

					Konto.setValue(konto);
					Gegenkonto.setValue(gegenkonto);
					Buchungstext.setValue(buchungstext);
					Betrag.setValue(Double.toString(betrag));

					table.addItem(new Object[] { id, Konto, Gegenkonto, Buchungstext, Betrag, datum, edit, delete, save,
							sumField }, 1);
					kontoText.setValue(konto);
					gegenkontoText.setValue(gegenkonto);
					buchungstextText.setValue(buchungstext);

					Konto.setReadOnly(true);
					Gegenkonto.setReadOnly(true);
					Buchungstext.setReadOnly(true);
					Betrag.setReadOnly(true);
				}
			}
		});

		Button menu = new Button("Menü");
		menu.addClickListener(e -> {
			navigator.navigateTo("start2");
		});

		Button abmelden = new Button("Abmelden");
		abmelden.setIcon(FontAwesome.SIGN_OUT);
		abmelden.addClickListener(e -> {
			navigator.navigateTo("login");
		});

		Button refresh = new Button("Seite neu laden");
		refresh.setClickShortcut(KeyCode.F5);
		refresh.setIcon(FontAwesome.REFRESH);
		refresh.addClickListener(e -> {

			setCompositionRoot(layout);

			content.addComponents(refresh, ts, select, von, bis, soll, haben, nameText, laden, table);
			panel.setContent(content);
			panel.setSizeFull();

			layout.addComponent(menu, "left:0px;top:0px");
			layout.addComponent(Startseite.linkAendern, "left:0px;top:30px;");
			layout.addComponent(Startseite.linkSpeichern, "left:0px;top:50px;");
			layout.addComponent(Startseite.linkLoeschen, "left:0px;top:70px;");
			layout.addComponent(Startseite.linkLaden, "left:0px;top:90px;");
			layout.addComponent(Startseite.linkAll, "left:0px;top:110px;");
			layout.addComponent(panel, "left: 150px; right: 50px; " + "top: 30px;bottom: 50px;");
			layout.addComponent(abmelden, "right: 50px; top: 0px;");
			layout.addComponents(panel, abmelden);

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

			absolute.setWidth("1800px");
			absolute.setHeight("800px");
			setCompositionRoot(absolute);

			save.setEnabled(false);

		});

		setCompositionRoot(layout);

		content.addComponents(refresh, ts, select, von, bis, soll, haben, nameText, laden, table);
		panel.setContent(content);
		panel.setSizeFull();

		layout.addComponent(menu, "left:0px;top:0px");
		layout.addComponent(Startseite.linkAendern, "left:0px;top:30px;");
		layout.addComponent(Startseite.linkSpeichern, "left:0px;top:50px;");
		layout.addComponent(Startseite.linkLoeschen, "left:0px;top:70px;");
		layout.addComponent(Startseite.linkLaden, "left:0px;top:90px;");
		layout.addComponent(Startseite.linkAll, "left:0px;top:110px;");
		layout.addComponent(panel, "left: 150px; right: 50px; " + "top: 30px;bottom: 50px;");
		layout.addComponent(abmelden, "right: 50px; top: 0px;");
		layout.addComponents(panel, abmelden);

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

		absolute.setWidth("1800px");
		absolute.setHeight("800px");
		setCompositionRoot(absolute);

		save.setEnabled(false);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}

}
