package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;

public class Debounce {
    public enum DynamicState {
        kPressed,
        kHeld,
        kReleased,
        kInactive
    }

    private GenericHID hid;
    private int buttonIndex;

    private boolean lastValue;
    private boolean currentValue;

    public Debounce(GenericHID hid, int buttonIndex) {
        this.hid = hid;
        this.buttonIndex = buttonIndex;

        lastValue = false;
        currentValue = false;
    }

    public DynamicState getCurrentState() {
        return getCurrentState(true);
    }

    public DynamicState getCurrentState(boolean triggerUpdate) {
        if(triggerUpdate) {
            updateInternal();
        }

        if(!lastValue && currentValue) {
            return DynamicState.kPressed;
        } else if(lastValue && currentValue) {
            return DynamicState.kHeld;
        } else if(lastValue && !currentValue) {
            return DynamicState.kReleased;
        } else {
            return DynamicState.kInactive;
        }
    }

    public boolean isPressed() {
        return isPressed(true);
    }

    public boolean isPressed(boolean triggerUpdate) {
        if(triggerUpdate) {
            updateInternal();
        }

        return !lastValue && currentValue;
    }

    public boolean isHeld() {
        return isHeld(true);
    }

    public boolean isHeld(boolean triggerUpdate) {
        if(triggerUpdate) {
            updateInternal();
        }

        return lastValue && currentValue;
    }

    public boolean isReleased() {
        return isReleased(true);
    }

    public boolean isReleased(boolean triggerUpdate) {
        if(triggerUpdate) {
            updateInternal();
        }

        return lastValue && !currentValue;
    }

    private void updateInternal() {
        lastValue = currentValue;

        currentValue = hid.getRawButton(buttonIndex);
    }
}