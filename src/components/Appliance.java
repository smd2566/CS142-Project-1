//Shane Doherty
package components;

/**
 * This class creates methods for the appliance datatype
 */
public class Appliance extends Component implements Switchable {
    int rating;
    boolean isOn;

    /**
     * This constructor initializes the rating and sets the isOn boolean to be false
     */
    public Appliance(String name, Component source, int rating) {
        super(name, source);
        this.rating = rating;
        this.isOn = false;
    }

    /**
     * This method returns the rating
     */
    public int getRating(){
        return rating;
    }
    /**
     *This method returns the status of the isOn switch
     */
    @Override
    public boolean isSwitchOn() {
        return isOn;
    }
    /**
     *This method sets the isOn switch to true, and if the component is engaged, it changes the rating
     */
    @Override
    public void turnOn() {
        isOn = true;
        Reporter.report(this, Reporter.Msg.SWITCHING_ON);
        if (this.engaged()){
            this.getSource().changeDraw(rating);
        }
    }

    /**
     *This method sets the isOn switch to false, and if the component is engaged, it changes the rating
     */
    @Override
    public void turnOff() {
        isOn = false;
        Reporter.report(this, Reporter.Msg.SWITCHING_OFF);
        if (this.engaged()) {
            this.getSource().changeDraw(-rating);
        }
    }
    /**
     * This method engages the component if the component is on, and changes the draw accordingly
     */
    @Override
    public void engage(){
        Reporter.report(this, Reporter.Msg.ENGAGING);
        if (isOn){
            this.changeDraw(rating); //I changed this line 5:42PM
        }
        isEngaged = true;


    }
    /**
     * This method disengages previously engaged methods and changes the draw accordingly and sets the flag
     */
    @Override
    public void disengage() {
        if (isOn && isEngaged){
            Reporter.report(this, Reporter.Msg.DISENGAGING);

            changeDraw(-rating);
        }
        isEngaged = false;
    }

}
