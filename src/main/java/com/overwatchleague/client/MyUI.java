package com.overwatchleague.client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.swing.JOptionPane;

import com.overwatchleague.client.database.CharacterData;
import com.overwatchleague.client.database.OWCharacter;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
//@Theme("mytheme")
public class MyUI extends UI {
	
	private static DatabaseConnection databaseConnection = new DatabaseConnection();

    @SuppressWarnings("unchecked")
	@Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
                
        CharacterData data = new CharacterData(databaseConnection.getDbService().getConnection());
        
        Grid<OWCharacter> grid = new Grid<>(OWCharacter.class);
        grid.setCaption("Characters");
        grid.setItems(data.getCharacters(false));
        grid.setSizeFull();
        grid.setColumnOrder(grid.getColumn("name"));
        layout.addComponent(grid);
        layout.setExpandRatio(grid, 1); // Expand to fill
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
