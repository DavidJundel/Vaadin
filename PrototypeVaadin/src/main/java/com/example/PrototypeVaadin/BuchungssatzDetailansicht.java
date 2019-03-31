package com.example.PrototypeVaadin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import javax.inject.Inject;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

@CDIView("detail")
public class BuchungssatzDetailansicht extends CustomComponent implements View, ClickListener {

	@Inject
	BuchungssatzDAO buchungssatzdao;

	@Inject
	BuchungssatzAll buchungssatzall;

	private Navigator navigator;
	TextArea buchungstext = new TextArea("Buchungstext:");
	Label Id = new Label();
	TextField Betrag = new TextField("Betrag:");
	TextField Konto = new TextField("Konto:");
	TextField Gegenkonto = new TextField("Gegenkonto:");
	TextField soll = new TextField("Soll:");
	TextField haben = new TextField("Haben:");
	DateField date = new DateField("Datum:");
	String text;
	String ID;
	String konto;
	String gegenkonto;
	Date buchungsdatum;
	FormLayout content = new FormLayout();
	Panel panel = new Panel();
	final AbsoluteLayout layout = new AbsoluteLayout();
	Button back = new Button();
	Button speichernback = new Button("Speichern und zurück");
	Button speichern = new Button("Speichern");
	Button loeschen = new Button("Löschen");
	Long id;
	double betrag;

	final Embedded image = new Embedded("Upload Image");

	public void enter(ViewChangeEvent event) {

		image.setVisible(false);

		Id.setCaption("Id:");

		buchungstext.setValue(buchungssatzall.buchungstext);
		Id.setValue(String.valueOf(buchungssatzall.id));
		Betrag.setValue(String.valueOf(buchungssatzall.betrag));
		Konto.setValue(buchungssatzall.konto);
		Gegenkonto.setValue(buchungssatzall.gegenkonto);
		date.setValue(buchungssatzall.datum);

		text = buchungssatzall.buchungstext;
		id = buchungssatzall.id;

		if (buchungssatzall.betrag == null) {
			betrag = 100;
		} else {
			betrag = buchungssatzall.betrag;
		}
		konto = buchungssatzall.konto;
		gegenkonto = buchungssatzall.gegenkonto;
		buchungsdatum = buchungssatzall.datum;

		navigator = getUI().getNavigator();

		Button abmelden = new Button("Abmelden");
		abmelden.setIcon(FontAwesome.SIGN_OUT);
		abmelden.addClickListener(e -> {
			navigator.navigateTo("login");
		});

		Button menu = new Button("Menü");
		menu.addClickListener(e -> {
			navigator.navigateTo("start2");
		});

		layout.setWidth("1400px");
		layout.setHeight("500px");

		Konto.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				konto = (String) event.getProperty().getValue();

			}
		});

		Gegenkonto.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				gegenkonto = (String) event.getProperty().getValue();

			}
		});

		buchungstext.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				text = (String) event.getProperty().getValue();

			}
		});

		date.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				buchungsdatum = (Date) event.getProperty().getValue();

			}
		});

		Betrag.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				String wert = (String) event.getProperty().getValue();
				if (!wert.isEmpty()) {
					try {

						betrag = Double.valueOf(wert);
					} catch (NullPointerException np) {

					}
				}
			}
		});

		HorizontalLayout save = new HorizontalLayout();
		save.addComponents(back, speichern, speichernback);

		back.setIcon(FontAwesome.ARROW_LEFT);
		back.addClickListener(e -> {
			navigator.navigateTo("Buchungssätze laden");
		});

		speichernback.setIcon(FontAwesome.SAVE);
		speichernback.addClickListener(e -> {

			if (konto.equals(gegenkonto) || (betrag <= 0) || konto.isEmpty() || gegenkonto.isEmpty()
					|| (String.valueOf(betrag) == null) || text.isEmpty()) {

				Notification notification = new Notification("Eingabe ungültig!");
				notification.show(Page.getCurrent());

			} else {

				buchungssatzdao.edit(id, konto, gegenkonto, text, buchungsdatum, betrag);

				navigator.navigateTo("Buchungssätze laden");
			}
		});

		speichern.setIcon(FontAwesome.SAVE);
		speichern.addClickListener(e -> {

			if (konto.equals(gegenkonto) || (betrag <= 0) || konto.isEmpty() || gegenkonto.isEmpty()
					|| (String.valueOf(betrag) == null) || text.isEmpty()) {

				Notification notification = new Notification("Eingabe ungültig!");
				notification.show(Page.getCurrent());

			} else {

				buchungssatzdao.edit(id, konto, gegenkonto, text, buchungsdatum, betrag);

			}
		});

		loeschen.addClickListener(e -> {

			buchungssatzdao.delete(id);

			layout.addComponent(new Label("Buchungssatz gelöscht!"));
			Konto.clear();
			Konto.setValue("");
			Gegenkonto.setValue("");
			buchungstext.setValue("");
			Betrag.setValue("");

		});

		Button refresh = new Button("Seite neu laden");
		refresh.setClickShortcut(KeyCode.F5);
		refresh.setIcon(FontAwesome.REFRESH);
		refresh.addClickListener(e -> {

			ImageUploader receiver = new ImageUploader();

			Upload upload = new Upload("Datei auswählen", receiver);
			upload.setButtonCaption("Datei hochladen");
			upload.addSucceededListener(receiver);

			Panel panel2 = new Panel("Datenupload");
			panel2.setWidth("300px");
			Layout panelContent = new VerticalLayout();
			panelContent.addComponents(upload, image);
			panel2.setContent(panelContent);

			buchungstext.setValue(buchungssatzall.buchungstext);

			content.addComponents(refresh, save, Id, Konto, Gegenkonto, Betrag, buchungstext, date);
			AbsoluteLayout absolute = new AbsoluteLayout();
			Panel panel = new Panel();
			panel.setWidth("280px");
			panel.setHeight("800px");
			panel.addStyleName("my_bg_style");
			FormLayout form = new FormLayout();

			form.addComponents(menu, abmelden, Startseite.menu);
			panel.setContent(form);
			absolute.addComponent(panel, "left:0px;top:0px;bottom:0px;");
			absolute.addComponent(content, "left:300px;");
			absolute.addComponent(panel2, "left:300px;top:600px;");

			absolute.setWidth("1600px");
			absolute.setHeight("800px");

			setCompositionRoot(absolute);

		});

		ImageUploader receiver = new ImageUploader();

		Upload upload = new Upload("Datei auswählen", receiver);
		upload.setButtonCaption("Datei hochladen");
		upload.addSucceededListener(receiver);

		Panel panel2 = new Panel("Datenupload");
		panel2.setWidth("300px");
		Layout panelContent = new VerticalLayout();
		panelContent.addComponents(upload, image);
		panel2.setContent(panelContent);

		buchungstext.setValue(buchungssatzall.buchungstext);

		content.addComponents(refresh, save, Id, Konto, Gegenkonto, Betrag, buchungstext, date);
		AbsoluteLayout absolute = new AbsoluteLayout();
		Panel panel = new Panel();
		panel.setWidth("280px");
		panel.setHeight("1000px");
		panel.addStyleName("my_bg_style");
		FormLayout form = new FormLayout();

		form.addComponents(menu, abmelden, Startseite.menu);
		panel.setContent(form);
		absolute.addComponent(panel, "left:0px;top:0px;bottom:0px;");
		absolute.addComponent(content, "left:300px;");
		// absolute.addComponent(panel2, "left:300px;top:600px;");

		absolute.setWidth("1600px");
		absolute.setHeight("800px");

		setCompositionRoot(absolute);

	}

	class ImageUploader implements Receiver, SucceededListener {

		public File file;

		public OutputStream receiveUpload(String filename, String mimeType) {
			FileOutputStream fos = null;
			try {
				file = new File("D:\\copies" + filename);
				fos = new FileOutputStream(file);
			} catch (final java.io.FileNotFoundException e) {
				new Notification("Could not open file<br/>", e.getMessage(), Notification.Type.ERROR_MESSAGE)
						.show(Page.getCurrent());
				return null;
			}
			return fos;

		}

		public void uploadSucceeded(SucceededEvent event) {
			image.setVisible(true);
			image.setSource(new FileResource(file));
		}
	};

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}

}