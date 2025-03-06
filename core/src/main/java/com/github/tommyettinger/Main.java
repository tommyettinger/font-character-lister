package com.github.tommyettinger;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static java.awt.Font.TRUETYPE_FONT;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public String[] args;

    public Main(String[] args){
        if(args == null || args.length == 0) this.args = new String[]{"Gentium.ttf"};
        else this.args = args;
    }

    @Override
    public void create() {
        String fontFileName = args[0];
        FileHandle cmap = Gdx.files.local(fontFileName + ".cmap.txt");
        System.out.println("Building character map...");
        StringBuilder sb = new StringBuilder(1024);
        sb.append("\"\\n");
        try {
            java.awt.Font af = java.awt.Font.createFont(TRUETYPE_FONT, new File(args[0]));
            int[] weirdChars = {0x200C, 0x200D, 0x200E, 0x200F, 0x2028, 0x2029, 0x202A, 0x202B, 0x202C, 0x202D, 0x202E, 0x206A, 0x206B, 0x206C, 0x206D, 0x206E, 0x206F};
            for (int i = 32; i < 65536; i++) {
                if (Arrays.binarySearch(weirdChars, i) < 0 && af.canDisplay(i)) {
                    if(i == '\\' || i == '"' )
                        sb.append('\\');
                    sb.append((char) i);
                }
            }
            sb.append("\"");
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        cmap.writeString(sb.toString(), false, "UTF-8");
        System.exit(0);
    }

    @Override
    public void render() {
    }

    @Override
    public void dispose() {
    }
}
