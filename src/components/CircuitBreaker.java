//Shane Doherty
package components;

/**
 * This class establishes methods for the CircuitBreaker datatype
 */
public class CircuitBreaker extends Component implements Switchable{
    boolean isOn;
    int limit;
    /**
     * This constructor sets the isOn switch to false, and initializes the limit
     */
    public CircuitBreaker(String name, Component source, int limit) {
        super(name, source);
        this.isOn = false;
        this.limit = limit;

    }
    /**
     * This method returns the limit
     */

    public int getLimit(){
        return limit;

    }
    /**
     * This method overrides changeDraw to disengage the load and reduce the draw
     */
    @Override
    protected void changeDraw(int additionalDraw){
        int old_draw = this.getDraw();
        this.draw = this.draw + additionalDraw;
        if (isOn){
            if (this.getDraw() > limit) {
                Reporter.report(this, Reporter.Msg.BLOWN, this.getDraw());
                isOn = false;
                Reporter.report(this, Reporter.Msg.SWITCHING_OFF);
                this.getSource().changeDraw(-old_draw);

                 //Might have to switch these report statements
                isEngaged = false;
                disengageLoads();
                return;
            }
            Reporter.report(this, Reporter.Msg.DRAW_CHANGE, additionalDraw);
            if (this.source != null) { //I changed this from this.getSource() -> this.source
                this.source.changeDraw(additionalDraw);

            }
        }

    }

    /**
     * This method sets the isEngaged flag to true
     */
    @Override
    public void engage(){
        isEngaged = true;
        Reporter.report(this, Reporter.Msg.ENGAGING);
    }
    /**
     * This method returns the status of the on switch
     */
    @Override
    public boolean isSwitchOn() {
        return isOn;
    }

    /**
     * This method turns on the component, and engages the loads before it.
     */
    @Override
    public void turnOn(){
        isOn = true;
        Reporter.report(this, Reporter.Msg.SWITCHING_ON);
        if (isOn){
            engageLoads();
        }
    }
    /**
     *This method turns off the component, and disengages the loads before it.
     */
    @Override
    public void turnOff() {
        isOn = false;
        Reporter.report(this, Reporter.Msg.SWITCHING_OFF);
        if (this.engaged()) {
            disengageLoads();
        }
    }
}

