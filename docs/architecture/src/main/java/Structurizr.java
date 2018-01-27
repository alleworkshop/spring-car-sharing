import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.model.Tags;
import com.structurizr.view.EnterpriseContextView;
import com.structurizr.view.PaperSize;
import com.structurizr.view.Shape;
import com.structurizr.view.Styles;
import com.structurizr.view.ViewSet;

import java.io.InputStream;
import java.util.Properties;

import static java.lang.Long.parseLong;

/**
 * This is a simple example of how to get started with Structurizr for Java.
 */
public class Structurizr {

    public static void main(String[] args) throws Exception {
        // a Structurizr workspace is the wrapper for a software architecture model, views and documentation
        Workspace workspace = new Workspace("Getting Started", "This is a model of my software system.");
        Model model = workspace.getModel();

        // add some elements to your software architecture model
        Person driver = model.addPerson("Driver", "A user that rents and drives a car");
        SoftwareSystem users = model.addSoftwareSystem(
                "users", "Responsible for authentication, authorization and user profiles");
        SoftwareSystem locator = model.addSoftwareSystem("locator", "Responsible for locating cars and calculating distance (no storage)");
        SoftwareSystem cars = model.addSoftwareSystem("cars", "Responsible for car catalog (DB)");
        SoftwareSystem rental = model.addSoftwareSystem("rental", "Responsible for renting of cars (DB)");
        SoftwareSystem billing = model.addSoftwareSystem("billing", "Responsible for calculating cost (DB)");
        SoftwareSystem search = model.addSoftwareSystem("search", "Responsible for searching nearby free cars");
        SoftwareSystem availability = model.addSoftwareSystem("availability", "Responsible for information whether car is available or not (DB)");
        SoftwareSystem mailing = model.addSoftwareSystem("mailing", "Sending mails to users");


        Person gps = model.addPerson("Gps", "Gps in a physical car");
        gps.uses(locator, "send position and id");

        driver.uses(search, "User searches for a car nearby");

        search.uses(cars, "Get details of a car by gpsId");
        search.uses(availability, "which of those are available");

        driver.uses(rental, "User rents a car");
        driver.uses(rental, "User returns a car");
        driver.uses(rental, "User parks a car");
        driver.uses(billing, "User asks for current cost");


        locator.uses(rental, "New position with distance from last (Event)");
        locator.uses(billing, "New position with distance from last (Event)");
        locator.uses(search, "New position with distance from last (Event)");

        rental.uses(billing, "Rent (Event)");
        rental.uses(billing, "Return (Event)");
        rental.uses(availability, "Rent (Event)");
        rental.uses(availability, "Return (Event)");

        rental.uses(cars, "Gets details of a car by carId");
        billing.uses(users, "Get billing address of a user");
        billing.uses(mailing, "Billing email (Event)");

        // define some views (the diagrams you would like to see)
        ViewSet views = workspace.getViews();
        EnterpriseContextView contextView = views.createEnterpriseContextView("SystemContext", "An example of a System Context diagram.");
        contextView.setPaperSize(PaperSize.A4_Landscape);
        contextView.addAllSoftwareSystems();
        contextView.addAllPeople();

        // add some styling
        Styles styles = views.getConfiguration().getStyles();
        styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff");
        styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);

        uploadWorkspaceToStructurizr(workspace);
    }

    private static void uploadWorkspaceToStructurizr(Workspace workspace) throws Exception {
        try(InputStream inputStream =
                    Structurizr.class.getClassLoader().getResourceAsStream("secret.properties")) {
            Properties prop = new Properties();
            prop.load(inputStream);
            long workspaceId = parseLong(prop.getProperty("workspace.id"));
            String apiKey = prop.getProperty("api.key");
            String apiSecret = prop.getProperty("api.secret");
            StructurizrClient structurizrClient = new StructurizrClient(apiKey, apiSecret);
            structurizrClient.putWorkspace(workspaceId, workspace);
        }
    }

}