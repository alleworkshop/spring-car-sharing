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
    /**
     * Events:
     * Car is rented
     * Car is returned
     * Car is parked
     * Distance x travelled on car y
     * Billing sent
     * Car located
     *
     *
     */
    public static void main(String[] args) throws Exception {
        // a Structurizr workspace is the wrapper for a software architecture model, views and documentation
        Workspace workspace = new Workspace("Getting Started", "This is a model of my software system.");
        Model model = workspace.getModel();

        // add some elements to your software architecture model
        Person driver = model.addPerson("Driver", "A user that rents and drives a car");
        SoftwareSystem users = model.addSoftwareSystem(
                "users", "Responsible for authentication, authorization and user profiles");
        SoftwareSystem locator = model.addSoftwareSystem("locator", "Resposnible for locating cars");
        SoftwareSystem cars = model.addSoftwareSystem("cars", "Resposnible for car catalog");
        SoftwareSystem rental = model.addSoftwareSystem("rental", "Responsible for renting of cars");
        SoftwareSystem billing = model.addSoftwareSystem("billing", "Responsible for calculating cost");

        driver.uses(locator, "User searches for a car nearby");
        driver.uses(cars, "User browses car details");
        driver.uses(rental, "User rents a car");
        driver.uses(rental, "User returns a car");
        driver.uses(rental, "User parks a car");
        driver.uses(billing, "User asks for current cost");

        rental.uses(locator, "Gets travelled distance"); //or listens to events?
        rental.uses(locator, "Sends events on rent/return");
        rental.uses(billing, "Sends events on rent/return");
        rental.uses(cars, "Gets details of a car");
        billing.uses(users, "Get billing address of a user");

        // define some views (the diagrams you would like to see)
        ViewSet views = workspace.getViews();
        EnterpriseContextView contextView = views.createEnterpriseContextView("SystemContext", "An example of a System Context diagram.");
        contextView.setPaperSize(PaperSize.A5_Landscape);
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