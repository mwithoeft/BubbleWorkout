package misc;

import de.jensd.fx.glyphs.GlyphIcons;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.octicons.OctIcon;
import javafx.scene.text.Text;

import java.util.TreeMap;

public class IconManager {
    private static IconManager ourInstance = new IconManager();

    public static IconManager getInstance() {
        return ourInstance;
    }

    private static final String ICON_SIZE = "4em";

    TreeMap<String, GlyphIcons> icons = new TreeMap<>();

    private IconManager() {
        //User Icons
        icons.put("addUser", MaterialDesignIcon.ACCOUNT_PLUS);
        icons.put("removeUser", MaterialDesignIcon.ACCOUNT_MINUS);
        icons.put("checkCircle", FontAwesomeIcon.CHECK_CIRCLE);
        icons.put("timesCircle", FontAwesomeIcon.TIMES_CIRCLE);
        icons.put("addCircle", MaterialIcon.CONTROL_POINT);
        icons.put("book", FontAwesomeIcon.BOOK);
        icons.put("gear", FontAwesomeIcon.GEAR);
        icons.put("archive", FontAwesomeIcon.ARCHIVE);
        icons.put("home", OctIcon.HOME);
        icons.put("signIn", OctIcon.SIGN_IN);
        icons.put("signOut", OctIcon.SIGN_OUT);
        icons.put("plus", MaterialDesignIcon.PLUS);
        icons.put("play", FontAwesomeIcon.PLAY);
        icons.put("pause", FontAwesomeIcon.PAUSE);
        icons.put("stop", FontAwesomeIcon.STOP);



        //Workout Icons
        icons.put("peace", FontAwesomeIcon.ANGELLIST);
        icons.put("scale", FontAwesomeIcon.BALANCE_SCALE);
        icons.put("bed", FontAwesomeIcon.BED);
        icons.put("bike", FontAwesomeIcon.BICYCLE);
        icons.put("child", FontAwesomeIcon.CHILD);
        icons.put("clock", FontAwesomeIcon.CLOCK_ALT);
        icons.put("cutlery", FontAwesomeIcon.CUTLERY);
        icons.put("basketball", FontAwesomeIcon.DRIBBBLE);
        icons.put("heartbeat", FontAwesomeIcon.HEARTBEAT);
        icons.put("football", FontAwesomeIcon.SOCCER_BALL_ALT);
        icons.put("trophy", FontAwesomeIcon.TROPHY);
        icons.put("partnerSkipping", FontAwesomeIcon.SLIDESHARE);
        icons.put("wheelchair", FontAwesomeIcon.WHEELCHAIR);
        icons.put("run", MaterialDesignIcon.RUN);
        icons.put("relax", MaterialDesignIcon.SEAT_RECLINE_NORMAL);
        icons.put("steeringWheel", MaterialDesignIcon.STEERING);
        icons.put("swim", MaterialDesignIcon.SWIM);
        icons.put("swimAlt", MaterialIcon.POOL);
        icons.put("americanFootball", MaterialDesignIcon.FOOTBALL);
        icons.put("americanFootballHelmet", MaterialDesignIcon.FOOTBALL_HELMET);
        icons.put("dumbbell", MaterialDesignIcon.DUMBBELL);
        icons.put("dumbbellAlt", MaterialIcon.FITNESS_CENTER);
        icons.put("golf", MaterialIcon.GOLF_COURSE);
        icons.put("rowing", MaterialIcon.ROWING);
        icons.put("flame", OctIcon.FLAME);
        icons.put("jersey", OctIcon.JERSEY);



        //Misc
        icons.put("airplane", MaterialDesignIcon.AIRPLANE);
        icons.put("car", FontAwesomeIcon.AUTOMOBILE);
        icons.put("motorbike", FontAwesomeIcon.MOTORCYCLE);
        icons.put("rubber", FontAwesomeIcon.ERASER);
        icons.put("jet", FontAwesomeIcon.FIGHTER_JET);
        icons.put("fireExtinguisher", FontAwesomeIcon.FIRE_EXTINGUISHER);
        icons.put("scissors", FontAwesomeIcon.CUT);
        icons.put("globe", FontAwesomeIcon.GLOBE);
        icons.put("headphones", FontAwesomeIcon.HEADPHONES);
        icons.put("lifesaver", FontAwesomeIcon.LIFE_SAVER);
        icons.put("music", FontAwesomeIcon.MUSIC);
        icons.put("plug", FontAwesomeIcon.PLUG);
        icons.put("recycle", FontAwesomeIcon.RECYCLE);
        icons.put("road", FontAwesomeIcon.ROAD);
        icons.put("ship", FontAwesomeIcon.SHIP);
        icons.put("train", FontAwesomeIcon.SUBWAY);
        icons.put("trolley", FontAwesomeIcon.SHOPPING_CART);
        icons.put("thumbsUp", FontAwesomeIcon.THUMBS_UP);
        icons.put("thumbsDown", FontAwesomeIcon.THUMBS_DOWN);
        icons.put("star", FontAwesomeIcon.STAR);
        icons.put("umbrella", FontAwesomeIcon.UMBRELLA);
        icons.put("rubbish", FontAwesomeIcon.TRASH);
        icons.put("group", FontAwesomeIcon.USERS);
        icons.put("cat", MaterialDesignIcon.CAT);
        icons.put("fingerprint", MaterialDesignIcon.FINGERPRINT);
        icons.put("poo", MaterialDesignIcon.EMOTICON_POOP);
        icons.put("school", MaterialDesignIcon.SCHOOL);
        icons.put("wrench", MaterialDesignIcon.WRENCH);
        icons.put("noGaming", MaterialDesignIcon.XBOX_CONTROLLER_OFF);
        icons.put("fish", MaterialDesignIcon.FISH);
        icons.put("pram", MaterialIcon.CHILD_FRIENDLY);
        icons.put("teePause", MaterialIcon.FREE_BREAKFAST);
        icons.put("hotTub", MaterialIcon.HOT_TUB);
        icons.put("pizza", MaterialIcon.LOCAL_PIZZA);
        icons.put("smokeFree", MaterialIcon.SMOKE_FREE);
        icons.put("smokeArea", MaterialIcon.SMOKING_ROOMS);
        icons.put("ventilator", MaterialIcon.TOYS);
        icons.put("dashboard", OctIcon.DASHBOARD);
        icons.put("camera", OctIcon.DEVICE_CAMERA);
        icons.put("paintCan", OctIcon.PAINTCAN);
        icons.put("package", OctIcon.PACKAGE);
        icons.put("key", OctIcon.KEY);
        icons.put("rocket", OctIcon.ROCKET);
        icons.put("lightning", OctIcon.ZAP);
        icons.put("picture", MaterialIcon.COLLECTIONS);


    }

    public TreeMap<String, GlyphIcons> getAll(){
        return icons;
    }
    public Text getIcon(String name, String size) {
        return GlyphsDude.createIcon(icons.get(name), size);
    }
    public Text getIcon(String name){
        return GlyphsDude.createIcon(icons.get(name), ICON_SIZE);
    }



}
