package dmit2015.faces;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

import java.io.Serializable;

@Named("currentHelloDmit2015StudentView")
@ViewScoped // create this object for one HTTP request and keep in memory if the next is for the same page
// class must implement Serializable
public class HelloDmit2015StudentView implements Serializable {

    // Declare read/write properties (field + getter + setter) for each form field
    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @PostConstruct // This method is executed after DI is completed (fields with @Inject now have values)
    public void init() { // Use this method to initialized fields instead of a constructor
        // Code to access fields annotated with @Inject

    }

    public void onSubmit() {
        try {
            String message = "Hello " + firstName + " " + lastName + "!";
            Messages.addGlobalInfo(message);

        } catch (Exception ex) {
            handleException(ex);
        }
    }


    /**
     * This method is used to handle exceptions and display root cause to user.
     *
     * @param ex The Exception to handle.
     */
    protected void handleException(Exception ex) {
        var details = new StringBuilder();
        Throwable causes = ex;
        while (causes.getCause() != null) {
            details.append(ex.getMessage());
            details.append("    Caused by:");
            details.append(causes.getCause().getMessage());
            causes = causes.getCause();
        }
        Messages.create(ex.getMessage()).detail(details.toString()).error().add("errors");
    }

}