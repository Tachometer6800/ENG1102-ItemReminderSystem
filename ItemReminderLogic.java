import org.firmata4j.Pin;
import org.firmata4j.ssd1306.SSD1306;

class ItemReminderLogic {
    private final SSD1306 display;
    private final String[] items;
    private final Pin pin;

    private ItemReminderLogic(SSD1306 display, String[] items, Pin pin) {
        this.display = display;
        this.items = items;
        this.pin = pin;
    }


    public static void initialize(SSD1306 display, String[] items) throws InterruptedException {
        int height = 0;
        System.out.println("Initializing...");
        display.getCanvas().drawString(0, 0, "Initializing...");
        Thread.sleep(1000);
        display.clear();
        for (int i = 0; i < items.length; i++) {
            if(items[i] != null) {
                display.getCanvas().drawString(0, 0, items[i]);
                height = height + 10;
            }

        }
    }

    public static String[] updateList(String[] items, String tag) {

        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(tag)) {
                items[i] = null;
            }
        }
        return items;
    }

    public static void updateDisp(SSD1306 display, String[] items) {
        int height = 0;
        display.clear();
        for (String item : items) {
            if(item != null) {
                display.getCanvas().drawString(0, height, item);
                height = height + 10;
            }
        }
    }

    public static String ItemScan(Pin pin) {
        var ReaderValue = pin.getValue();
        String ScannedItem = String.valueOf(ReaderValue);

        return ScannedItem;
    }

    public static Integer LightEnable(String[] items) {
        int nullValues = 0;
        for(int i = 0; i < items.length; i++) {
            if (items[i] == null){
                nullValues++;
            }
        }
        if(nullValues == items.length){
            return 1;
        }
        else{
            return 0;
        }
    }
}
