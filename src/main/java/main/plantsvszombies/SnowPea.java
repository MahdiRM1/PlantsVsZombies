package main.plantsvszombies;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;

public class SnowPea extends PeaPlant{

    public static final int recharge = 10;

    public SnowPea(int row, int col){
        super(row, col);
        price = 175;
        HP = 100;
        gif.setEffect(iceEffect());
        bulletType = BulletType.ICE_BULLET;;
    }

    //effect to turn the snow bullets blue
    private Effect iceEffect(){
        ColorAdjust blueTone = new ColorAdjust();
        blueTone.setHue(0.6);
        blueTone.setSaturation(0.3);
        blueTone.setBrightness(0.2);
        blueTone.setContrast(0.1);

        DropShadow iceGlow = new DropShadow();
        iceGlow.setColor(Color.CORNFLOWERBLUE);
        iceGlow.setRadius(15);
        iceGlow.setInput(blueTone);

        return iceGlow;
    }
}
