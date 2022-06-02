package events;

public class MouseClickEvent extends Event {
    private final int button;
    private final int action;
    private final int mods;

    public MouseClickEvent(int button, int action, int mods){
        this.button = button;
        this.action = action;
        this.mods = mods;
    }

    public int getButton() {
        return button;
    }

    public int getAction() {
        return action;
    }

    public int getMods() {
        return mods;
    }
}
