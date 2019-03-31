package com.example.PrototypeVaadin;

import java.util.Date;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@CDIView("Bestellung")
public class BestellungListe extends CustomComponent implements View, ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	Date created;
	boolean deleted;
	Date updated;
	boolean abgeschlossen;

	String type;
	Date bought;
	boolean bezahl;
	boolean abgeschl;
	String extern;
	String intern;
	String sta;

	Table table = new Table();
	TextField typ = new TextField("Typ");
	TextField bestellt = new TextField("Bestellt am:");
	TextField bestellnr = new TextField("Bestell-Nr.");
	TextField extbestellnr = new TextField("Ext-B.Nr");
	TextField stat = new TextField("Status");
	TextField bezahlt = new TextField("Bezahlt");
	TextField done = new TextField("Abgeschlossen");

	@Override
	public void enter(ViewChangeEvent event) {

		table.setImmediate(true);

		table.setEditable(false);
		table.addContainerProperty("Aktionen", VerticalLayout.class, null);
		table.addContainerProperty("Lieferant", VerticalLayout.class, null);
		table.addContainerProperty("Positionen", VerticalLayout.class, null);
		table.addContainerProperty("Status", VerticalLayout.class, null);

		Button button1 = new Button("Test1");
		Button button2 = new Button("Test2");
		VerticalLayout aktionen = new VerticalLayout();
		aktionen.setSizeFull();
		aktionen.addComponents(button1, button2);
		aktionen.setComponentAlignment(button1, Alignment.TOP_LEFT);

		Panel lief = new Panel("Lieferant");
		lief.setWidth("300px");
		lief.setHeight("200px");
		VerticalLayout lieferant = new VerticalLayout();
		lieferant.addComponent(lief);
		lieferant.setComponentAlignment(lief, Alignment.TOP_CENTER);

		Panel pos = new Panel("Positionen");
		pos.setWidth("500px");
		pos.setHeight("400px");
		VerticalLayout positionen = new VerticalLayout();
		positionen.addComponent(pos);

		FormLayout bestell = new FormLayout();

		bestell.addComponents(typ, bestellt, bestellnr, extbestellnr, stat, bezahlt, done);

		Panel bestellung = new Panel("Bestellung");
		bestellung.setWidth("320px");
		bestellung.setHeight("450px");
		bestellung.setContent(bestell);
		Panel transportauftrag = new Panel("Transportauftrag");
		transportauftrag.setWidth("320px");
		transportauftrag.setHeight("450px");

		VerticalLayout status = new VerticalLayout();
		status.addComponents(bestellung, transportauftrag);

		table.addItem(new Object[] { aktionen, lieferant, positionen, status }, 1);

		AbsoluteLayout absolute = new AbsoluteLayout();

		absolute.setWidth("2000px");
		absolute.setHeight("1500px");

		absolute.addComponent(table);

		setCompositionRoot(absolute);

		typ.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				type = (String) event.getProperty().getValue();

			}
		});

		bestellt.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				bought = (Date) event.getProperty().getValue();

			}
		});

		bestellnr.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				intern = (String) event.getProperty().getValue();
			}

		});

		extbestellnr.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				extern = (String) event.getProperty().getValue();
			}

		});

		stat.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {

				sta = (String) event.getProperty().getValue();
			}

		});

		bezahlt.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				bezahl = (boolean) event.getProperty().getValue();

			}

		});

		done.addValueChangeListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				abgeschl = (boolean) event.getProperty().getValue();
			}

		});

	}

	public void load() {

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}

}
