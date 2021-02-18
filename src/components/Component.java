//Shane Doherty


package components;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class creates various methods for each type of Component
 */
public abstract class Component {
    protected String name;
    protected Component source;
    protected ArrayList<Component> loadList = new ArrayList<>();
    protected int draw;
    protected boolean isEngaged;
    public boolean isOn;
    /**
     * This constructor initializes the name, the source, and the boolean value for isOn.
     */
    public Component(String name, Component source) {
        this.name = name;
        this.source = source;
        this.isOn = false;
        Reporter.report(this, Reporter.Msg.CREATING);
        if (this.source!=null){
            this.source.attach(this);
        }

    }


    /**
     * This method adds a new load to the loadlist
     */
    protected void addLoad(Component newLoad){
        loadList.add(newLoad);

    }
    /**
     * This method attaches this load to a specific load in the parameter.
     */

    protected void attach(Component load){
        addLoad(load);
        Reporter.report(this, load, Reporter.Msg.ATTACHING);
        if (this.engaged()){
            load.engage();
        }


    }

    /**
     * This method changes the draw on this by the given delta
     */
    protected void changeDraw(int delta){
        this.draw = this.draw + delta;
        Reporter.report(this,  Reporter.Msg.DRAW_CHANGE, delta);
        if (this.source != null) {
            this.source.changeDraw(delta);
        }


    }
    /**
     * This method disengages the load on "this"
     */
    public void disengage(){
        if (this.engaged()) {
            Reporter.report(this, Reporter.Msg.DISENGAGING);
            disengageLoads();
            this.isEngaged = false;

        }
    }
    /**
     * This method goes through each of the current loads and set engages false in the arraylist
     */
    protected void disengageLoads(){
        for (Component component : loadList) {
            component.disengage();
        }

    }
    /**
     * This method goes calls the display helper function to display every part of the component tree
     */
    public void display(){
        System.out.println(displayHelper(0, this));
    }

    /**
     * This method creates the printing to display the tree in proper format
     */
    public String displayHelper(int depth, Component component){
        String returnValue = "";
        String tabCount = "";
        returnValue+= "+";
        returnValue +=  " ";
        returnValue += component.toString();
        returnValue += "\n";
        for (int i=0; i<depth; i++){
            tabCount+= "    ";
        }
        for (Component value : loadList) {
            returnValue += tabCount;
            returnValue += value.displayHelper(depth+1, value);

        }
        return returnValue;
    }
    /**
     * This method engages the "this" component if it is not engaged
     */
    public void engage(){
        if (!this.engaged()) {
            this.isEngaged = true;
            Reporter.report(this, Reporter.Msg.ENGAGING);
            engageLoads();
        }
    }
    /**
     * This method returns the boolean isEngaged to check if "this" component is engaged
     */
    protected boolean engaged(){
        return this.isEngaged;


    }
    /**
     * This method goes through each of the current loads in the loadlist and engages them
     */
    protected void engageLoads(){
        for (Component component : loadList) {
            component.engage();
        }

    }
    /**
     * This method returns the draw of "this" component
     */
    protected int getDraw(){
        return this.draw;

    }
    /**
     * This method returns the loads in the loadlist
     */
    protected Collection<Component> getLoads(){
        return loadList;

    }
    /**
     * This method returns the name of "this" component
     */
    public String getName(){
        return name;

    }
    /**
     * This method returns the name of "this" source
     */
    protected Component getSource(){
        return this.source;

    }
    /**
     * This method sets the draw of "this" component
     */
    protected void setDraw(int draw){
        this.draw = draw;

    }
    /**
     *This method overrides toString() to allow the reporter to identify the "this" component
     */
    @Override
    public String toString(){
        return Reporter.identify(this);
    }

    /**
     *This method returns the status of the on switch, either true or false
     */
    public boolean isSwitchOn() {
        return isOn;
    }
}

