import org.firmata4j.I2CDevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.ssd1306.SSD1306;


static final int A0 = 14; // Potentiometer
static final int A2 = 16; // Sound
static final int D6 = 6; // Button
static final int D4 = 4; // LED
static final byte I2C0 = 0x3C; // OLED Display

void main()
        throws InterruptedException, IOException {

    // Initialize Arduino
    var device = new FirmataDevice("COM3");
    device.start();
    device.ensureInitializationIsDone();

    // Initialize Display
    I2CDevice i2cObject = device.getI2CDevice((byte) 0x3C);
    SSD1306 Disp = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64);
    Disp.init();

    //Initialize NFC Scanner
    var NFCScanner = device.getPin(A0);
    NFCScanner.setMode(Pin.Mode.OUTPUT);

    //Initialize Reset Button
    var ResetButton = device.getPin(D6);
    ResetButton.setMode(Pin.Mode.INPUT);

    // Change "ItemArray" to list of items you want to remember
    String[] ItemArray = {"Phone", "Wallet", "Keys"};

    // Only change if you want to break the program
    boolean Running = true;

    // List items on screen
    ItemReminderLogic.initialize(Disp, ItemArray);

    // Main running loop
    while (Running) {

        for (String Item : ItemArray) {

            if (ItemReminderLogic.ItemScan(NFCScanner) == Item) {
                String ScannedItem = ItemReminderLogic.ItemScan(NFCScanner);
                ItemArray = ItemReminderLogic.updateList(ItemArray, ScannedItem);
                ItemReminderLogic.updateDisp(Disp, ItemArray);
            }


            if (Integer.parseInt(ResetButton.toString()) == 1) {
                Running = false;
            }
        }


    }
}
