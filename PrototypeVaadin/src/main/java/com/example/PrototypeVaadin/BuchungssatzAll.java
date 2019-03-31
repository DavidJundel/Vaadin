package com.example.PrototypeVaadin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;

@CDIView("Buchungssätze laden")
public class BuchungssatzAll extends CustomComponent implements View, ClickListener {

	@Inject
	BuchungssatzDAO buchungssatzdao;

	String value;
	String konto;
	String gegenkonto;
	String buchungstext;
	Double betrag;
	Date datum;
	Long wert;
	Long id;
	private Navigator navigator;
	AbsoluteLayout layout = new AbsoluteLayout();
	Table table = new Table();
	FormLayout content = new FormLayout();
	Panel panel = new Panel();
	Button bearbeiten = new Button();
	Button speichern = new Button();
	Button löschen = new Button();
	Button pdf = new Button();
	Button neu = new Button();
	Label bsKonto = new Label();
	Label bsGegenKonto = new Label();
	TextArea bsBuchungstext = new TextArea();
	Label bsBetrag = new Label();
	Label bsId = new Label();
	Label menge = new Label("Anzahl:");
	String k;
	static DateField date = new DateField();
	CheckBox cb10 = new CheckBox("10");
	CheckBox cb20 = new CheckBox("20");
	CheckBox cb50 = new CheckBox("50");
	CheckBox cb100 = new CheckBox("100");
	TabSheet ts = new TabSheet();
	VerticalLayout tab1 = new VerticalLayout();
	VerticalLayout tab2 = new VerticalLayout();
	VerticalLayout tab3 = new VerticalLayout();
	VerticalLayout tab4 = new VerticalLayout();
	VerticalLayout tab5 = new VerticalLayout();
	int anzahl = 50;
	boolean cb1, cb2, cb3, cb4;
	TextField tf = new TextField("Konto:");
	String s = "%";
	String s2;
	String wahl = "Konto";
	int filter = 1;
	DateField from;
	DateField to;
	SimpleDateFormat parser;
	String formattedDate;
	String formattedDate2;
	String formattedDateDB;
	List<Buchungssatz> listBuchungssaetze;
	List<Buchungssatz> listBuchungssaetze2;
	List<Buchungssatz> listBuchungssaetze3;
	Date von = new Date(0L);
	Date bis = new Date();
	Date db;
	TextField sum = new TextField("Betrag bis €:");
	String summe = Double.toString(Double.MAX_VALUE);
	Double amount = Double.MAX_VALUE;
	TextArea text = new TextArea("Buchungstext:");
	TextField ggkonto = new TextField("Gegenkonto:");

	VerticalLayout optionen = new VerticalLayout();
	HorizontalLayout auswahl = new HorizontalLayout();
	HorizontalLayout buttons = new HorizontalLayout();

	HorizontalLayout seite = new HorizontalLayout();

	TextField page = new TextField("Seite:");
	Label allpage = new Label("von  ");

	HorizontalLayout created = new HorizontalLayout();
	HorizontalLayout logout = new HorizontalLayout();

	int number = 1;
	int a;
	int x = 0;
	int y = 10;

	int gesammt;

	Label v = new Label("von:");

	Button seitevor = new Button("Seite vor");
	Button seitezurueck = new Button("Seite zurück");

	@Override
	public void enter(ViewChangeEvent event) {

		page.setValue("1");
		page.setHeight("30px");
		page.setWidth("60px");

		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:sss");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:sss");

		bearbeiten.setHeight("200px");
		bearbeiten.setWidth("200px");

		from = new DateField("von");
		from.setValue(new Date());
		from.setVisible(false);
		to = new DateField("bis");
		to.setValue(new Date());
		to.setVisible(false);

		text.setVisible(false);

		ggkonto.setVisible(false);

		cb50.setValue(true);

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

		seitezurueck = new Button("Seite zurück");
		seitezurueck.addClickListener(e -> {
			number--;
			page.setValue(String.valueOf(number));
			seitevor.setEnabled(true);
			if (number == 1) {
				seitezurueck.setEnabled(false);
			} else {
				seitezurueck.setEnabled(true);
			}
			x = x - 10;
			y = y - 10;
			loadPage();

		});

		seitevor = new Button("Seite vor");
		seitevor.addClickListener(e -> {

			number++;
			page.setValue(String.valueOf(number));
			seitezurueck.setEnabled(true);
			if (number == gesammt) {
				seitevor.setEnabled(false);
			} else {
				seitevor.setEnabled(true);
			}
			x = x + 10;
			y = y + 10;
			loadPage();

		});

		page.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {

				String n = (String) event.getProperty().getValue();
				number = Integer.valueOf(n);

			}
		});

		ts.setSelectedTab(tab5);

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
					navigator.navigateTo("Buchungssatz laden");
				}
			}
		});

		navigator = getUI().getNavigator();

		loadPage();
		// load();

		bsId.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				String i = (String) event.getProperty().getValue();
				id = Long.parseLong(i);

			}
		});

		bsKonto.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				konto = (String) event.getProperty().getValue();
			}
		});

		bsGegenKonto.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				gegenkonto = (String) event.getProperty().getValue();
			}
		});

		bsBuchungstext.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				buchungstext = (String) event.getProperty().getValue();
			}
		});

		bsBetrag.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				String b = (String) event.getProperty().getValue();
				betrag = Double.parseDouble(b);
			}
		});

		date.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				datum = (Date) event.getProperty().getValue();

			}
		});

		cb10.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				cb1 = (Boolean) event.getProperty().getValue();

				if (cb1 == true) {
					cb20.setValue(false);
					cb50.setValue(false);
					cb100.setValue(false);
					anzahl = 10;
				}

			}
		});

		cb20.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				cb2 = (Boolean) event.getProperty().getValue();

				if (cb2 == true) {
					cb10.setValue(false);
					cb50.setValue(false);
					cb100.setValue(false);
					anzahl = 20;
				}

			}
		});

		cb50.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				cb3 = (Boolean) event.getProperty().getValue();

				if (cb3 == true) {
					cb10.setValue(false);
					cb20.setValue(false);
					cb100.setValue(false);
					anzahl = 50;
				}
			}
		});

		cb100.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				cb4 = (Boolean) event.getProperty().getValue();

				if (cb4 == true) {
					cb10.setValue(false);
					cb20.setValue(false);
					cb50.setValue(false);
					anzahl = 100;
				}
			}
		});

		tf.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				s = (String) event.getProperty().getValue();

			}
		});

		ggkonto.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				s2 = (String) event.getProperty().getValue();
			}
		});

		ComboBox combobox = new ComboBox("Suche nach:");
		combobox.setInvalidAllowed(false);
		combobox.setNullSelectionAllowed(false);
		combobox.setTextInputAllowed(false);

		combobox.addItems("Konto", "Gegenkonto", "Datum", "Betrag");
		combobox.setValue("Konto");

		combobox.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				wahl = (String) combobox.getValue();

				if (wahl.equals("Datum")) {
					from.setVisible(true);
					to.setVisible(true);
					tf.setVisible(false);
					sum.setVisible(false);
				} else if (wahl.equals("Betrag")) {
					from.setVisible(false);
					to.setVisible(false);
					tf.setVisible(false);
					sum.setVisible(true);
				} else {
					from.setVisible(false);
					to.setVisible(false);
					tf.setVisible(true);
					sum.setVisible(false);
				}
			}
		});

		from.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				Date date = (Date) event.getProperty().getValue();
				String formattedDate = formatter.format(date);

				try {
					von = parser.parse(formattedDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

		to.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				Date date = (Date) event.getProperty().getValue();
				String formattedDate2 = formatter.format(date);
				try {
					bis = parser.parse(formattedDate2);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

		sum.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				summe = (String) event.getProperty().getValue();
				amount = Double.parseDouble(summe);

			}
		});

		ComboBox optional = new ComboBox("erweiterte Suche (optional)");
		optional.setInvalidAllowed(false);
		optional.setNullSelectionAllowed(false);
		optional.setTextInputAllowed(false);
		optional.addItems("keine", "Gegenkonto", "Buchungstext", "Datum");
		optional.setValue("keine");

		optional.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				wahl = (String) optional.getValue();
				if (wahl.equals("Gegenkonto")) {
					ggkonto.setVisible(true);
					from.setVisible(false);
					to.setVisible(false);
				} else if (wahl.equals("Buchungstext")) {
					text.setVisible(true);
					from.setVisible(false);
					to.setVisible(false);
					ggkonto.setVisible(false);
				} else if (wahl.equals("Datum")) {
					from.setVisible(true);
					to.setVisible(true);
					text.setVisible(false);
					ggkonto.setVisible(false);
				} else {
					from.setVisible(false);
					to.setVisible(false);
					text.setVisible(false);
					ggkonto.setVisible(false);
				}
			}
		});

		Button neu = new Button("Neu anlegen");
		neu.setIcon(FontAwesome.PLUS);
		neu.addClickListener(e -> {
			navigator.navigateTo("Buchungssatz speichern");

		});

		Button menu = new Button("Menü");
		menu.addClickListener(e -> {
			navigator.navigateTo("start2");
		});

		Button abmelden = new Button("Abmelden");
		abmelden.setWidth("120px");
		abmelden.setHeight("30px");
		abmelden.setIcon(FontAwesome.SIGN_OUT);
		abmelden.addClickListener(e -> {
			navigator.navigateTo("login");
		});

		Button refresh = new Button("Aktualisieren");
		refresh.setClickShortcut(KeyCode.F5);
		refresh.setIcon(FontAwesome.REFRESH);
		refresh.addClickListener(e -> {

			seitevor.setEnabled(true);
			seitezurueck.setEnabled(true);

			loadPage();
			// load();

		});

		NativeButton suchen = new NativeButton("Suchen");
		suchen.setClickShortcut(KeyCode.ENTER);
		suchen.setIcon(FontAwesome.SEARCH);
		suchen.addClickListener(e -> {

			loadSuche2();

		});

		seitezurueck.setIcon(FontAwesome.BACKWARD);

		seitevor.setIcon(FontAwesome.FORWARD);

		HorizontalLayout p = new HorizontalLayout();
		p.addComponent(page);

		HorizontalLayout pp = new HorizontalLayout();
		pp.addComponent(v);
		pp.addComponent(allpage);

		seite.addComponents(seitezurueck, p, pp, seitevor);
		seite.setWidth("1200px");
		seite.setComponentAlignment(seitezurueck, Alignment.BOTTOM_LEFT);
		seite.setComponentAlignment(seitevor, Alignment.BOTTOM_RIGHT);
		seite.setComponentAlignment(p, Alignment.BOTTOM_CENTER);
		seite.setComponentAlignment(pp, Alignment.BOTTOM_CENTER);

		auswahl.addComponents(cb10, cb20, cb50, cb100);

		buttons.setWidth("1200px");
		buttons.setHeight("50px");
		buttons.addComponents(refresh, abmelden);
		buttons.setComponentAlignment(refresh, Alignment.TOP_LEFT);
		buttons.setComponentAlignment(abmelden, Alignment.TOP_RIGHT);

		table.setWidth("1200px");
		table.setHeight("400px");
		neu.addStyleName("button_style");
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponents(tf, sum, from, to, text, ggkonto);
		HorizontalLayout hl2 = new HorizontalLayout();
		hl2.addComponent(optional);

		HorizontalLayout searchcreate = new HorizontalLayout();
		searchcreate.setHeight("50px");
		searchcreate.setWidth("1200px");
		searchcreate.addComponents(suchen, neu);
		searchcreate.setComponentAlignment(suchen, Alignment.TOP_LEFT);
		searchcreate.setComponentAlignment(neu, Alignment.TOP_RIGHT);

		content.addComponents(ts, buttons, menge, auswahl, hl2, hl, searchcreate, table, seite);

		AbsoluteLayout absolute = new AbsoluteLayout();
		Panel panel = new Panel();
		panel.setHeight("1000px");
		panel.setWidth("280px");
		panel.addStyleName("background_style");
		FormLayout form = new FormLayout();

		form.addComponents(menu, Startseite.menu);
		panel.setContent(form);
		absolute.addComponent(panel, "left:0px;top:0px;bottom:0px;");
		absolute.addComponent(content, "left:280px;");

		absolute.setWidth("2000px");
		absolute.setHeight("1000px");

		setCompositionRoot(absolute);
	}

	public void load() {

		table.getContainerDataSource().removeAllItems();

		bsId.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				String i = (String) event.getProperty().getValue();
				id = Long.parseLong(i);

			}
		});

		bsKonto.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				konto = (String) event.getProperty().getValue();
			}
		});

		bsGegenKonto.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				gegenkonto = (String) event.getProperty().getValue();
			}
		});

		bsBuchungstext.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				buchungstext = (String) event.getProperty().getValue();
			}
		});

		bsBetrag.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				String b = (String) event.getProperty().getValue();
				betrag = Double.parseDouble(b);
			}
		});

		date.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				datum = (Date) event.getProperty().getValue();
			}
		});

		table.setImmediate(true);

		date.setValue(new Date());
		// date.setResolution(Resolution.MINUTE);
		bearbeiten.setIcon(FontAwesome.EDIT);
		löschen.setIcon(FontAwesome.CLOSE);

		table.setEditable(false);
		table.addContainerProperty("Optionen", VerticalLayout.class, null);
		table.addContainerProperty("ID", Label.class, null);
		table.addContainerProperty("Konto", Label.class, null);
		table.addContainerProperty("Gegenkonto", Label.class, null);
		table.addContainerProperty("Buchungstext", TextArea.class, null);
		table.addContainerProperty("Betrag", Label.class, null);
		table.addContainerProperty("Datum", HorizontalLayout.class, null);

		List<Buchungssatz> listBuchungssatze = buchungssatzdao.findAlleBuchungssaetze(anzahl);

		int i = 0;
		for (Buchungssatz b : listBuchungssatze) {

			Label bsKonto = new Label();
			Label bsGegenKonto = new Label();
			TextArea bsBuchungstext = new TextArea();
			Label bsBetrag = new Label();
			Label bsId = new Label();

			DateField date = new DateField();
			date.setValue(b.getBuchungsdatum());
			konto = b.getKonto();
			gegenkonto = b.getGegenkonto();
			buchungstext = b.getBuchungstext();
			betrag = b.getBetrag();
			id = b.getId();
			datum = b.getBuchungsdatum();

			bearbeiten.setHeight("30px");
			bearbeiten.setWidth("160px");

			löschen.setHeight("30px");
			löschen.setWidth("160px");

			pdf.setHeight("30px");
			pdf.setWidth("160px");

			bearbeiten = new Button("Bearbeiten", new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {

					konto = b.getKonto();
					gegenkonto = b.getGegenkonto();
					buchungstext = b.getBuchungstext();
					betrag = b.getBetrag();
					id = b.getId();
					datum = b.getBuchungsdatum();

					navigator.navigateTo("detail");

				}
			});

			speichern = new Button("Speichern", new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {

					String test = bsId.getValue();
					id = Long.parseLong(test);
					konto = bsKonto.getValue();
					gegenkonto = bsGegenKonto.getValue();
					buchungstext = bsBuchungstext.getValue();
					datum = date.getValue();

					String test2 = bsBetrag.getValue();

					if (test2.equals("null")) {
						betrag = 100.0;
					} else {
						betrag = Double.parseDouble(test2);
					}
					if (konto.equals(gegenkonto)) {
						Notification notification = new Notification("Eingabe ungültig!");
						notification.show(Page.getCurrent());
					} else {
						buchungssatzdao.edit(id, konto, gegenkonto, buchungstext, datum, betrag);

						bsId.setReadOnly(true);
						bsKonto.setReadOnly(true);
						bsGegenKonto.setReadOnly(true);
						bsBuchungstext.setReadOnly(true);
						bsBetrag.setReadOnly(true);

						Notification notification = new Notification("Änderungen gespeichert!");
						notification.show(Page.getCurrent());
					}
				}
			});

			löschen = new Button("Löschen", new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {

					String test = bsId.getValue();
					id = Long.parseLong(test);
					buchungssatzdao.delete(id);

					Notification notification = new Notification("Buchungssatz mit ID " + test + " gelöscht.");
					notification.show(Page.getCurrent());

					load();

				}
			});

			pdf = new Button("PDF erstellen");
			pdf.setIcon(FontAwesome.FILE_PDF_O);
			pdf.addClickListener(e -> {

				konto = b.getKonto();
				gegenkonto = b.getGegenkonto();
				buchungstext = b.getBuchungstext();
				betrag = b.getBetrag();
				id = b.getId();
				datum = b.getBuchungsdatum();

				if (betrag == null)
					betrag = 100.0;

			});
			VerticalLayout optionen = new VerticalLayout();
			optionen.addComponents(bearbeiten, löschen, pdf);
			optionen.setWidth("200px");
			optionen.setHeight("150px");

			Label datum = new Label("Erstellt am:");
			HorizontalLayout created = new HorizontalLayout();
			created.setHeight("100px");
			created.setWidth("300px");

			created.addComponents(datum, date);

			table.addItem(new Object[] { optionen, bsId, bsKonto, bsGegenKonto, bsBuchungstext, bsBetrag, created }, i);

			bsKonto.setValue(konto);
			bsGegenKonto.setValue(gegenkonto);
			bsBuchungstext.setValue(buchungstext);
			bsBuchungstext.setWidth("200px");
			bsBuchungstext.setHeight("200px");
			bsBetrag.setValue(String.valueOf(betrag));
			bsId.setValue(Long.toString(id));
			bearbeiten.setIcon(FontAwesome.EDIT);
			speichern.setIcon(FontAwesome.SAVE);
			löschen.setIcon(FontAwesome.CLOSE);
			i++;

		}
	}

	public void loadSuche() {

		if (wahl.equals("Konto")) {
			filter = 1;
		} else {
			filter = 2;
		}
		table.getContainerDataSource().removeAllItems();

		bsId.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				String i = (String) event.getProperty().getValue();
				id = Long.parseLong(i);

			}
		});

		bsKonto.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				konto = (String) event.getProperty().getValue();
			}
		});

		bsGegenKonto.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				gegenkonto = (String) event.getProperty().getValue();
			}
		});

		bsBuchungstext.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				buchungstext = (String) event.getProperty().getValue();
			}
		});

		bsBetrag.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				String b = (String) event.getProperty().getValue();
				betrag = Double.parseDouble(b);
			}
		});

		date.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				datum = (Date) event.getProperty().getValue();
			}
		});

		table.setImmediate(true);

		date.setValue(new Date());
		bearbeiten.setIcon(FontAwesome.EDIT);
		löschen.setIcon(FontAwesome.CLOSE);

		table.setEditable(false);
		table.addContainerProperty("Optionen", VerticalLayout.class, null);
		table.addContainerProperty("ID", Label.class, null);
		table.addContainerProperty("Konto", Label.class, null);
		table.addContainerProperty("Gegenkonto", Label.class, null);
		table.addContainerProperty("Buchungstext", TextArea.class, null);
		table.addContainerProperty("Betrag", Label.class, null);
		table.addContainerProperty("Datum", HorizontalLayout.class, null);

		if (wahl.equals("Konto") || wahl.equals("Gegenkonto")) {
			listBuchungssaetze = buchungssatzdao.findBuchungssaetze(filter, s);
		} else if (wahl.equals("Betrag")) {
			listBuchungssaetze = buchungssatzdao.findBuchungssaetzeBetrag(amount);
		} else {
			listBuchungssaetze = buchungssatzdao.findBuchungssaetzeDatum(von, bis);
		}

		if (listBuchungssaetze.isEmpty()) {
			Notification notification = new Notification("Kein Konto gefunden!");
			notification.show(Page.getCurrent());
		} else {

			int i = 0;
			for (Buchungssatz b : listBuchungssaetze) {

				Label bsKonto = new Label();
				Label bsGegenKonto = new Label();
				TextArea bsBuchungstext = new TextArea();
				Label bsBetrag = new Label();
				Label bsId = new Label();

				DateField date = new DateField();
				date.setValue(b.getBuchungsdatum());

				konto = b.getKonto();
				gegenkonto = b.getGegenkonto();
				buchungstext = b.getBuchungstext();
				betrag = b.getBetrag();
				id = b.getId();
				datum = b.getBuchungsdatum();

				bearbeiten = new Button("Bearbeiten", new Button.ClickListener() {
					public void buttonClick(ClickEvent event) {

						konto = b.getKonto();
						gegenkonto = b.getGegenkonto();
						buchungstext = b.getBuchungstext();
						betrag = b.getBetrag();
						id = b.getId();
						datum = b.getBuchungsdatum();

						navigator.navigateTo("detail");

					}
				});

				speichern = new Button("Speichern", new Button.ClickListener() {
					public void buttonClick(ClickEvent event) {

						String test = bsId.getValue();
						id = Long.parseLong(test);
						konto = bsKonto.getValue();
						gegenkonto = bsGegenKonto.getValue();
						buchungstext = bsBuchungstext.getValue();
						datum = date.getValue();
						String test2 = bsBetrag.getValue();

						if (test2.equals("null")) {
							betrag = 100.0;
						} else {
							betrag = Double.parseDouble(test2);
						}
						if (konto.equals(gegenkonto)) {
							Notification notification = new Notification("Eingabe ungültig!");
							notification.show(Page.getCurrent());
						} else {
							buchungssatzdao.edit(id, konto, gegenkonto, buchungstext, datum, betrag);

							bsId.setReadOnly(true);
							bsKonto.setReadOnly(true);
							bsGegenKonto.setReadOnly(true);
							bsBuchungstext.setReadOnly(true);
							bsBetrag.setReadOnly(true);

							Notification notification = new Notification("Änderungen gespeichert!");
							notification.show(Page.getCurrent());
						}
					}
				});

				löschen = new Button("Löschen", new Button.ClickListener() {
					public void buttonClick(ClickEvent event) {

						String test = bsId.getValue();
						id = Long.parseLong(test);
						buchungssatzdao.delete(id);

						Notification notification = new Notification("Buchungssatz mit ID " + test + " gelöscht.");
						notification.show(Page.getCurrent());

						load();

					}
				});

				Button pdf = new Button("PDF erstellen");
				pdf.setIcon(FontAwesome.FILE_PDF_O);
				pdf.addClickListener(e -> {

					konto = b.getKonto();
					gegenkonto = b.getGegenkonto();
					buchungstext = b.getBuchungstext();
					betrag = b.getBetrag();
					id = b.getId();
					datum = b.getBuchungsdatum();

					if (betrag == null)
						betrag = 100.0;

				});
				VerticalLayout optionen = new VerticalLayout();
				optionen.addComponents(bearbeiten, löschen, pdf);
				optionen.setWidth("200px");
				optionen.setHeight("150px");

				Label datum = new Label("Erstellt am:");
				HorizontalLayout created = new HorizontalLayout();
				created.setHeight("100px");
				created.setWidth("300px");

				created.addComponents(datum, date);

				table.addItem(new Object[] { optionen, bsId, bsKonto, bsGegenKonto, bsBuchungstext, bsBetrag, created },
						i);

				bsKonto.setValue(konto);
				bsGegenKonto.setValue(gegenkonto);
				bsBuchungstext.setValue(buchungstext);
				bsBuchungstext.setWidth("200px");
				bsBuchungstext.setHeight("200px");
				bsBetrag.setValue(String.valueOf(betrag));
				bsId.setValue(Long.toString(id));
				bearbeiten.setIcon(FontAwesome.EDIT);
				speichern.setIcon(FontAwesome.SAVE);
				löschen.setIcon(FontAwesome.CLOSE);
				i++;

			}
		}
	}

	public void loadSuche2() {

		seitevor.setEnabled(false);
		seitezurueck.setEnabled(false);

		table.getContainerDataSource().removeAllItems();

		bsId.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				String i = (String) event.getProperty().getValue();
				id = Long.parseLong(i);

			}
		});

		bsKonto.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				konto = (String) event.getProperty().getValue();
			}
		});

		bsGegenKonto.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				gegenkonto = (String) event.getProperty().getValue();
			}
		});

		bsBuchungstext.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				buchungstext = (String) event.getProperty().getValue();
			}
		});

		bsBetrag.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				String b = (String) event.getProperty().getValue();
				betrag = Double.parseDouble(b);
			}
		});

		date.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				datum = (Date) event.getProperty().getValue();
			}
		});

		table.setImmediate(true);

		date.setValue(new Date());
		bearbeiten.setIcon(FontAwesome.EDIT);
		löschen.setIcon(FontAwesome.CLOSE);

		table.setEditable(false);
		table.addContainerProperty("Optionen", VerticalLayout.class, null);
		table.addContainerProperty("ID", Label.class, null);
		table.addContainerProperty("Konto", Label.class, null);
		table.addContainerProperty("Gegenkonto", Label.class, null);
		table.addContainerProperty("Buchungstext", TextArea.class, null);
		table.addContainerProperty("Betrag", Label.class, null);
		table.addContainerProperty("Datum", HorizontalLayout.class, null);

		if (wahl.equals("Gegenkonto")) {
			listBuchungssaetze = buchungssatzdao.findBuchungssaetze(1, s);
			listBuchungssaetze2 = buchungssatzdao.findBuchungssaetzeBetrag(amount);
			listBuchungssaetze3 = buchungssatzdao.findBuchungssaetze(2, s2);
		} else if (wahl.equals("Buchungstext")) {
			listBuchungssaetze = buchungssatzdao.findBuchungssaetze(1, s);
			listBuchungssaetze2 = buchungssatzdao.findBuchungssaetzeBetrag(amount);
			listBuchungssaetze3 = null;
		} else if (wahl.equals("Datum")) {
			listBuchungssaetze = buchungssatzdao.findBuchungssaetzeKontoBetragDatum(s, amount, von, bis);
		} else {
			listBuchungssaetze = buchungssatzdao.findBuchungssaetzeKontoBetrag(s, amount);

		}

		if (listBuchungssaetze.isEmpty()) {
			Notification notification = new Notification("Kein Konto gefunden!");
			notification.show(Page.getCurrent());
		} else {

			int i = 0;
			for (Buchungssatz b : listBuchungssaetze) {

				// gesammt = listBuchungssaetze.size() / 10;
				// allpage.setValue(String.valueOf(gesammt));
				// int i = 0;
				// for (a = x; a <= y; a++) {
				//
				// gesammt = listBuchungssaetze.size() / 10;
				// allpage.setValue(String.valueOf(gesammt));
				//
				//
				// Label bsKonto = new Label();
				// Label bsGegenKonto = new Label();
				// TextArea bsBuchungstext = new TextArea();
				// Label bsBetrag = new Label();
				// Label bsId = new Label();
				//
				// DateField date = new DateField();
				// date.setValue(listBuchungssaetze.get(a).getBuchungsdatum());
				// konto = listBuchungssaetze.get(a).getKonto();
				// gegenkonto = listBuchungssaetze.get(a).getGegenkonto();
				// buchungstext = listBuchungssaetze.get(a).getBuchungstext();
				// betrag = listBuchungssaetze.get(a).getBetrag();
				// id = listBuchungssaetze.get(a).getId();
				// datum = listBuchungssaetze.get(a).getBuchungsdatum();
				//
				// bearbeiten.setHeight("30px");
				// bearbeiten.setWidth("160px");
				//
				// löschen.setHeight("30px");
				// löschen.setWidth("160px");
				//
				// pdf.setHeight("30px");
				// pdf.setWidth("160px");
				//
				// bearbeiten = new Button("Bearbeiten", new
				// Button.ClickListener() {
				// public void buttonClick(ClickEvent event) {
				//
				// konto = listBuchungssaetze.get(a).getKonto();
				// gegenkonto = listBuchungssaetze.get(a).getGegenkonto();
				// buchungstext = listBuchungssaetze.get(a).getBuchungstext();
				// betrag = listBuchungssaetze.get(a).getBetrag();
				// id = listBuchungssaetze.get(a).getId();
				// datum = listBuchungssaetze.get(a).getBuchungsdatum();
				//
				// navigator.navigateTo("detail");
				//
				// }
				// });
				//
				// speichern = new Button("Speichern", new
				// Button.ClickListener() {
				// public void buttonClick(ClickEvent event) {
				//
				// String test = bsId.getValue();
				// id = Long.parseLong(test);
				// konto = bsKonto.getValue();
				// gegenkonto = bsGegenKonto.getValue();
				// buchungstext = bsBuchungstext.getValue();
				// datum = date.getValue();
				//
				// String test2 = bsBetrag.getValue();
				//
				// if (test2.equals("null")) {
				// betrag = 100.0;
				// } else {
				// betrag = Double.parseDouble(test2);
				// }
				// if (konto.equals(gegenkonto)) {
				// Notification notification = new Notification("Eingabe
				// ungültig!");
				// notification.show(Page.getCurrent());
				// } else {
				// buchungssatzdao.edit(id, konto, gegenkonto, buchungstext,
				// datum, betrag);
				//
				// bsId.setReadOnly(true);
				// bsKonto.setReadOnly(true);
				// bsGegenKonto.setReadOnly(true);
				// bsBuchungstext.setReadOnly(true);
				// bsBetrag.setReadOnly(true);
				//
				// Notification notification = new Notification("Änderungen
				// gespeichert!");
				// notification.show(Page.getCurrent());
				// }
				// }
				// });
				//
				// löschen = new Button("Löschen", new Button.ClickListener() {
				// public void buttonClick(ClickEvent event) {
				//
				// String test = bsId.getValue();
				// id = Long.parseLong(test);
				// buchungssatzdao.delete(id);
				//
				// Notification notification = new Notification("Buchungssatz
				// mit ID " + test + " gelöscht.");
				// notification.show(Page.getCurrent());
				//
				// load();
				//
				// }
				// });
				//
				// pdf = new Button("PDF erstellen");
				// pdf.setIcon(FontAwesome.FILE_PDF_O);
				// pdf.addClickListener(e -> {
				//
				// konto = listBuchungssaetze.get(a).getKonto();
				// gegenkonto = listBuchungssaetze.get(a).getGegenkonto();
				// buchungstext = listBuchungssaetze.get(a).getBuchungstext();
				// betrag = listBuchungssaetze.get(a).getBetrag();
				// id = listBuchungssaetze.get(a).getId();
				// datum = listBuchungssaetze.get(a).getBuchungsdatum();
				//
				// if (betrag == null)
				// betrag = 100.0;
				//
				// });
				// VerticalLayout optionen = new VerticalLayout();
				// optionen.addComponents(bearbeiten, löschen, pdf);
				// optionen.setWidth("200px");
				// optionen.setHeight("150px");
				//
				// Label datum = new Label("Erstellt am:");
				// HorizontalLayout created = new HorizontalLayout();
				// created.setHeight("100px");
				// created.setWidth("300px");
				//
				// created.addComponents(datum, date);
				//
				// table.addItem(new Object[] { optionen, bsId, bsKonto,
				// bsGegenKonto, bsBuchungstext, bsBetrag, created },
				// i);
				//
				// bsKonto.setValue(konto);
				// bsGegenKonto.setValue(gegenkonto);
				// bsBuchungstext.setValue(buchungstext);
				// bsBuchungstext.setWidth("200px");
				// bsBuchungstext.setHeight("200px");
				// bsBetrag.setValue(String.valueOf(betrag));
				// bsId.setValue(Long.toString(id));
				// bearbeiten.setIcon(FontAwesome.EDIT);
				// speichern.setIcon(FontAwesome.SAVE);
				// löschen.setIcon(FontAwesome.CLOSE);
				// i++;
				//
				// }
				// }

				// gesammt = listBuchungssaetze.size() / 10;
				// allpage.setValue(String.valueOf(gesammt));

				Label bsKonto = new Label();
				Label bsGegenKonto = new Label();
				TextArea bsBuchungstext = new TextArea();
				Label bsBetrag = new Label();
				Label bsId = new Label();

				DateField date = new DateField();
				date.setValue(b.getBuchungsdatum());

				konto = b.getKonto();
				gegenkonto = b.getGegenkonto();
				buchungstext = b.getBuchungstext();
				betrag = b.getBetrag();
				id = b.getId();
				datum = b.getBuchungsdatum();

				gesammt = listBuchungssaetze.size() / 10;
				allpage.setValue(String.valueOf(gesammt));

				bearbeiten = new Button("Bearbeiten", new Button.ClickListener() {
					public void buttonClick(ClickEvent event) {

						konto = b.getKonto();
						gegenkonto = b.getGegenkonto();
						buchungstext = b.getBuchungstext();
						betrag = b.getBetrag();
						id = b.getId();
						datum = b.getBuchungsdatum();

						navigator.navigateTo("detail");

					}
				});

				speichern = new Button("Speichern", new Button.ClickListener() {
					public void buttonClick(ClickEvent event) {

						String test = bsId.getValue();
						id = Long.parseLong(test);
						konto = bsKonto.getValue();
						gegenkonto = bsGegenKonto.getValue();
						buchungstext = bsBuchungstext.getValue();
						datum = date.getValue();
						String test2 = bsBetrag.getValue();
						System.out.println(test2);
						if (test2.equals("null")) {
							betrag = 100.0;
						} else {
							betrag = Double.parseDouble(test2);
						}
						if (konto.equals(gegenkonto)) {
							Notification notification = new Notification("Eingabe ungültig!");
							notification.show(Page.getCurrent());
						} else {
							buchungssatzdao.edit(id, konto, gegenkonto, buchungstext, datum, betrag);

							bsId.setReadOnly(true);
							bsKonto.setReadOnly(true);
							bsGegenKonto.setReadOnly(true);
							bsBuchungstext.setReadOnly(true);
							bsBetrag.setReadOnly(true);

							Notification notification = new Notification("Änderungen gespeichert!");
							notification.show(Page.getCurrent());
						}
					}
				});

				löschen = new Button("Löschen", new Button.ClickListener() {
					public void buttonClick(ClickEvent event) {

						String test = bsId.getValue();
						id = Long.parseLong(test);
						buchungssatzdao.delete(id);

						Notification notification = new Notification("Buchungssatz mit ID " + test + " gelöscht.");
						notification.show(Page.getCurrent());

						load();

					}
				});

				Button pdf = new Button("PDF erstellen");
				pdf.setIcon(FontAwesome.FILE_PDF_O);
				pdf.addClickListener(e -> {

					konto = b.getKonto();
					gegenkonto = b.getGegenkonto();
					buchungstext = b.getBuchungstext();
					betrag = b.getBetrag();
					id = b.getId();
					datum = b.getBuchungsdatum();

					if (betrag == null)
						betrag = 100.0;

				});

				VerticalLayout optionen = new VerticalLayout();
				optionen.addComponents(bearbeiten, löschen, pdf);
				optionen.setWidth("200px");
				optionen.setHeight("150px");

				Label datum = new Label("Erstellt am:");
				HorizontalLayout created = new HorizontalLayout();
				created.setHeight("100px");
				created.setWidth("300px");

				created.addComponents(datum, date);

				table.addItem(new Object[] { optionen, bsId, bsKonto, bsGegenKonto, bsBuchungstext, bsBetrag, created },
						i);

				// pdf

				bsKonto.setValue(konto);
				bsGegenKonto.setValue(gegenkonto);
				bsBuchungstext.setValue(buchungstext);
				bsBuchungstext.setWidth("200px");
				bsBuchungstext.setHeight("200px");
				bsBetrag.setValue(String.valueOf(betrag));
				bsId.setValue(Long.toString(id));
				bearbeiten.setIcon(FontAwesome.EDIT);
				speichern.setIcon(FontAwesome.SAVE);
				löschen.setIcon(FontAwesome.CLOSE);
				i++;

			}
		}
	}

	public void loadPage() {
		table.getContainerDataSource().removeAllItems();

		bsId.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				String i = (String) event.getProperty().getValue();
				id = Long.parseLong(i);

			}
		});

		bsKonto.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				konto = (String) event.getProperty().getValue();
			}
		});

		bsGegenKonto.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				gegenkonto = (String) event.getProperty().getValue();
			}
		});

		bsBuchungstext.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				buchungstext = (String) event.getProperty().getValue();
			}
		});

		bsBetrag.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				String b = (String) event.getProperty().getValue();
				betrag = Double.parseDouble(b);
			}
		});

		date.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				datum = (Date) event.getProperty().getValue();
			}
		});

		table.setImmediate(true);

		date.setValue(new Date());
		bearbeiten.setIcon(FontAwesome.EDIT);
		löschen.setIcon(FontAwesome.CLOSE);

		table.setEditable(false);
		table.addContainerProperty("Optionen", VerticalLayout.class, null);
		table.addContainerProperty("ID", Label.class, null);
		table.addContainerProperty("Konto", Label.class, null);
		table.addContainerProperty("Gegenkonto", Label.class, null);
		table.addContainerProperty("Buchungstext", TextArea.class, null);
		table.addContainerProperty("Betrag", Label.class, null);
		table.addContainerProperty("Datum", HorizontalLayout.class, null);

		List<Buchungssatz> listBuchungssatze = buchungssatzdao.findAlleBuchungssaetze(anzahl);
		gesammt = listBuchungssatze.size() / 10;
		allpage.setValue(String.valueOf(gesammt));

		int i = 0;
		for (a = x; a <= y; a++) {

			gesammt = listBuchungssatze.size() / 10;
			allpage.setValue(String.valueOf(gesammt));

			Label bsKonto = new Label();
			Label bsGegenKonto = new Label();
			TextArea bsBuchungstext = new TextArea();
			Label bsBetrag = new Label();
			Label bsId = new Label();

			DateField date = new DateField();
			date.setValue(listBuchungssatze.get(a).getBuchungsdatum());
			konto = listBuchungssatze.get(a).getKonto();
			gegenkonto = listBuchungssatze.get(a).getGegenkonto();
			buchungstext = listBuchungssatze.get(a).getBuchungstext();
			betrag = listBuchungssatze.get(a).getBetrag();
			id = listBuchungssatze.get(a).getId();
			datum = listBuchungssatze.get(a).getBuchungsdatum();

			bearbeiten.setHeight("30px");
			bearbeiten.setWidth("160px");

			löschen.setHeight("30px");
			löschen.setWidth("160px");

			pdf.setHeight("30px");
			pdf.setWidth("160px");

			bearbeiten = new Button("Bearbeiten", new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {

					konto = listBuchungssatze.get(a).getKonto();
					gegenkonto = listBuchungssatze.get(a).getGegenkonto();
					buchungstext = listBuchungssatze.get(a).getBuchungstext();
					betrag = listBuchungssatze.get(a).getBetrag();
					id = listBuchungssatze.get(a).getId();
					datum = listBuchungssatze.get(a).getBuchungsdatum();

					navigator.navigateTo("detail");

				}
			});

			speichern = new Button("Speichern", new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {

					String test = bsId.getValue();
					id = Long.parseLong(test);
					konto = bsKonto.getValue();
					gegenkonto = bsGegenKonto.getValue();
					buchungstext = bsBuchungstext.getValue();
					datum = date.getValue();

					String test2 = bsBetrag.getValue();

					if (test2.equals("null")) {
						betrag = 100.0;
					} else {
						betrag = Double.parseDouble(test2);
					}
					if (konto.equals(gegenkonto)) {
						Notification notification = new Notification("Eingabe ungültig!");
						notification.show(Page.getCurrent());
					} else {
						buchungssatzdao.edit(id, konto, gegenkonto, buchungstext, datum, betrag);

						bsId.setReadOnly(true);
						bsKonto.setReadOnly(true);
						bsGegenKonto.setReadOnly(true);
						bsBuchungstext.setReadOnly(true);
						bsBetrag.setReadOnly(true);

						Notification notification = new Notification("Änderungen gespeichert!");
						notification.show(Page.getCurrent());
					}
				}
			});

			löschen = new Button("Löschen", new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {

					String test = bsId.getValue();
					id = Long.parseLong(test);
					buchungssatzdao.delete(id);

					Notification notification = new Notification("Buchungssatz mit ID " + test + " gelöscht.");
					notification.show(Page.getCurrent());

					load();

				}
			});

			pdf = new Button("PDF erstellen");
			pdf.setIcon(FontAwesome.FILE_PDF_O);
			pdf.addClickListener(e -> {

				konto = listBuchungssatze.get(a).getKonto();
				gegenkonto = listBuchungssatze.get(a).getGegenkonto();
				buchungstext = listBuchungssatze.get(a).getBuchungstext();
				betrag = listBuchungssatze.get(a).getBetrag();
				id = listBuchungssatze.get(a).getId();
				datum = listBuchungssatze.get(a).getBuchungsdatum();

				if (betrag == null)
					betrag = 100.0;

			});
			VerticalLayout optionen = new VerticalLayout();
			optionen.addComponents(bearbeiten, löschen, pdf);
			optionen.setWidth("200px");
			optionen.setHeight("150px");

			Label datum = new Label("Erstellt am:");
			HorizontalLayout created = new HorizontalLayout();
			created.setHeight("100px");
			created.setWidth("300px");

			created.addComponents(datum, date);

			table.addItem(new Object[] { optionen, bsId, bsKonto, bsGegenKonto, bsBuchungstext, bsBetrag, created }, i);

			bsKonto.setValue(konto);
			bsGegenKonto.setValue(gegenkonto);
			bsBuchungstext.setValue(buchungstext);
			bsBuchungstext.setWidth("200px");
			bsBuchungstext.setHeight("200px");
			bsBetrag.setValue(String.valueOf(betrag));
			bsId.setValue(Long.toString(id));
			bearbeiten.setIcon(FontAwesome.EDIT);
			speichern.setIcon(FontAwesome.SAVE);
			löschen.setIcon(FontAwesome.CLOSE);
			i++;

		}
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}

}
