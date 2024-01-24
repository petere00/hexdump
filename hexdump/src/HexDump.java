import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class HexDump {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Filename missing");
            return;
        }

        String filename = args[0];
        File file = new File(filename);

        if (!file.exists() || !file.isFile()) {
            System.out.println("File does not exist ");
            return;
        }

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            int byteData;
            int count = 0;
            int position = 0;
            System.out.printf("%08X  ", position);
            StringBuilder ascii = new StringBuilder();

            while ((byteData = fileInputStream.read()) != -1) {
                ascii.append(byteData >= 32 && byteData <= 126 ? (char) byteData : '.');
                String hexString = String.format("%02X ", byteData);
                System.out.print(hexString);
                count++;
                position++;

                if (count == 8) {
                    System.out.print(" ");
                }

                if (count == 16) {
                    System.out.println(" |" + ascii.toString()+"|");
                    ascii.setLength(0);
                    System.out.printf("%08X  ", position);
                    count = 0;
                }
            }

            if (count > 0) {
                int missingBytes = 16-count;
                if (missingBytes>8){
                    System.out.print(" ");
                }
                for (int i = 0; i < missingBytes ; i++) {
                    System.out.print("   ");
                }
                System.out.println(" |"+ascii.toString()+"|");
            }

        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
        }
    }
}
