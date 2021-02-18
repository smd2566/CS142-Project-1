//Shane Doherty
package components;
/**
 * This interface adds functionality for the methods involving checking if the switch is on, and turning it on and off
 */
public interface Switchable {


    public abstract boolean isSwitchOn();
    public abstract void turnOn();
    public abstract void turnOff();

}
