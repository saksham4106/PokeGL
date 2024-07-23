package events;


public class TypeEvent extends Event {
    private final int unicode;
    private final String text;

    public TypeEvent(int unicode){
        this.unicode = unicode;
        this.text = new String(Character.toChars(unicode));
    }

    public int getUnicode() {
        return unicode;
    }

    public String getText() {
        return text;
    }
}
