package ch.kmoes.steamdeckintegration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import com.codedisaster.steamworks.SteamAPI;
import com.codedisaster.steamworks.SteamLibraryLoader;

public class SteamLoader implements SteamLibraryLoader {

    private final Path nativesDir = Files.createTempDirectory("steamdeckintegrationnatives");

    public SteamLoader() throws IOException {}

    @Override
    public boolean loadLibrary(String libraryName) {
        boolean loaded = false;

        boolean steamApi = libraryName.equals("steam_api");
        String s = (steamApi ? "natives/linux64/libsteam_api.so" : "libsteamworks4j.so");
        try {
            String[] split = s.split("/");
            Path natives = nativesDir.resolve(split[split.length - 1]);
            System.out.println(natives);
            System.out.println(s);

            try (InputStream is = (!steamApi ? SteamLoader.class : SteamAPI.class).getResourceAsStream("/" + s)) {
                assert is != null;
                Files.copy(is, natives, StandardCopyOption.REPLACE_EXISTING);
            }
            System.out.println(natives.toString());
            System.load(natives.toString());
            loaded = true;
        } catch (Throwable t) {
            System.out.println("Failed to load " + libraryName + "!");
            throw new RuntimeException(t);
        }
        System.out.println("Native " + libraryName + " loaded: " + loaded);
        return loaded;
    }
}
