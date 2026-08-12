package ch.kmoes.steamdeckintegration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.codedisaster.steamworks.*;

public class SteamHelper {

    private static SteamUtils steamUtils;
    private static boolean steamAvailable;
    private static boolean onSteamDeck;

    public static void init() {
        System.out.println("=== Steam diagnostics ===");
        System.out.println("user.home = " + System.getProperty("user.home"));
        System.out.println("user.dir  = " + System.getProperty("user.dir"));
        System.out.println(
            "cwd      = " + Paths.get("")
                .toAbsolutePath());

        System.out.println(
            "steam_appid.txt = " + Paths.get("steam_appid.txt")
                .toAbsolutePath());

        System.out.println("steam_appid exists = " + Files.exists(Paths.get("steam_appid.txt")));
        try {
            SteamLibraryLoader loader = new SteamLoader();
            if (!SteamAPI.loadLibraries(loader)) {
                System.err.println("Failed to load Steam libraries!");
            }
            System.out.println("Loaded Steam API libraries!");
            makeAppIdFile();
             System.out.println("steam_appid after writing = " + Files.exists(Paths.get("steam_appid.txt")));
             System.out.println(
                "appid path = " + Paths.get("steam_appid.txt").toAbsolutePath());

            SteamAPI.InitResult initResult = SteamAPI.initEx();
            if (initResult != SteamAPI.InitResult.OK) {
                System.err.println("Failed to initialize Steam API libraries!");
                System.err.println(initResult.toString());
                return;
            }
            System.out.println("Initialized Steam API!");
            steamAvailable = true;
        } catch (SteamException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SteamUtilsCallback callback = new SteamUtilsCallback() {};
        steamUtils = new SteamUtils(callback);
        onSteamDeck = steamUtils.isSteamRunningOnSteamDeck();
        System.out.println("onSteamDeck: " + onSteamDeck);
    }

    public static void openKeyboard() {
        if (steamAvailable && onSteamDeck) {
            steamUtils
                .showFloatingGamepadTextInput(SteamUtils.FloatingGamepadTextInputMode.ModeSingleLine, 0, 0, 100, 10);
        }
    }

    private static void makeAppIdFile() throws IOException {
        Path path = Paths.get("steam_appid.txt");
        Files.write(path, "480".getBytes(StandardCharsets.UTF_8));
    }
}
