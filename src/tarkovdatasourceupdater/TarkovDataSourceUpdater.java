package tarkovdatasourceupdater;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

public class TarkovDataSourceUpdater {
    //coloca aqui a ruta donde se generara el archivo json y sql
    final static String RUTA ="C:/Users/yassi/Desktop";
    
    public static void main(String[] args) throws IOException, InterruptedException {
        //System.out.println(dataDownload());
        dataToJSON();
        todb();
    }

    public static void todb() throws FileNotFoundException, IOException {
        ArrayList<Ammo> ammoAr = new ArrayList<>();
        String data = "";
        StringBuilder sb = new StringBuilder();

        try {
            Scanner input = new Scanner(new File(RUTA+"/data.json"));
            while (input.hasNextLine()) {
                sb.append(input.nextLine());
            }
            System.out.println(data);
            input.close();
        } catch (Exception ex) {
        }
        JSONArray jsonArray = new JSONArray(sb.toString());
        /*jsonArray.forEach((x) -> {
            System.out.println(x.toString());
        });*/
        int control = 0;
        while (control <= jsonArray.length() - 1) {
            JSONObject jsonObject = jsonArray.getJSONObject(control);
            System.out.println(jsonObject.getString("name"));
            int armorDamage = jsonObject.getInt("armorDamage");
            int damage= jsonObject.getInt("damage");
            String caliber = jsonObject.getString("caliber");
            float weight = jsonObject.getFloat("weight");
            String ammoType = jsonObject.getString("ammoType");
            float fragmentationChance= jsonObject.getFloat("fragmentationChance");
            float ricochetChance= jsonObject.getFloat("ricochetChance");
            float heavyBleedModifier= jsonObject.getFloat("heavyBleedModifier");
            float lightBleedModifier= jsonObject.getFloat("lightBleedModifier");
            boolean tracer= jsonObject.getBoolean("tracer");
            String tracerColor = jsonObject.getString("tracerColor");
            int avg24hPrice= jsonObject.getInt("avg24hPrice");
            System.out.println(jsonObject.getInt("lastLowPrice"));
            int lastLowPrice= jsonObject.getInt("lastLowPrice");
            String name= jsonObject.getString("name");
            String shortName= jsonObject.getString("shortName");
            String image512pxLink= jsonObject.getString("image512pxLink");
            
            Ammo ammo = new Ammo(armorDamage, damage, caliber, weight, ammoType, fragmentationChance, ricochetChance, heavyBleedModifier, lightBleedModifier, tracer, tracerColor, avg24hPrice, lastLowPrice, name, shortName, image512pxLink);
            ammoAr.add(ammo);
            control++;
        }
        exportToSQL(ammoAr);
    }

    public static void dataToJSON() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            FileWriter file = new FileWriter(RUTA+"/data.json");
            BufferedWriter output = new BufferedWriter(file);
            output.write(dataDownload());
            output.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String dataDownload() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        String query = "{\"query\": \"{ ammo {armorDamage damage armorDamage caliber weight ammoType fragmentationChance ricochetChance heavyBleedModifier lightBleedModifier tracer tracerColor item{avg24hPrice lastLowPrice name shortName image512pxLink}} }\"}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create("https://api.tarkov.dev/graphql"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(query))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //String jsonString = response.body();
        String datajson = response.body().toString();

        //adaptacion de los datos obtendos por graphQL eliminando elementos que la libreria jsonArray no admite
        datajson = datajson.replace("{\"data\":{\"ammo\":", "");
        datajson = datajson.replace("}}]}}", "}}]");
        datajson = datajson.replace(",\"item\":{", ",");
        datajson = datajson.replace("}},", "},");
        datajson = datajson.replace("}}]", "}]");
        datajson = datajson.replace("\"lastLowPrice\":null,", "\"lastLowPrice\":0,");

        return datajson;
    }
    public static void exportToSQL(ArrayList<Ammo> ammo){
        String sqldata="CREATE TABLE ammo(\n" +
"   armorDamage         INTEGER  NOT NULL\n" +
"  ,damage              INTEGER  NOT NULL\n" +
"  ,caliber             VARCHAR(17) NOT NULL\n" +
"  ,weight              NUMERIC(5,3) NOT NULL\n" +
"  ,ammoType            VARCHAR(9) NOT NULL\n" +
"  ,fragmentationChance NUMERIC(6,4) NOT NULL\n" +
"  ,ricochetChance      NUMERIC(5,3) NOT NULL\n" +
"  ,heavyBleedModifier  NUMERIC(4,2) NOT NULL\n" +
"  ,lightBleedModifier  NUMERIC(4,2) NOT NULL\n" +
"  ,tracer              VARCHAR(5) NOT NULL\n" +
"  ,tracerColor         VARCHAR(11) NOT NULL\n" +
"  ,avg24hPrice         INTEGER  NOT NULL\n" +
"  ,lastLowPrice        INTEGER  NOT NULL\n" +
"  ,name                VARCHAR(34) NOT NULL\n" +
"  ,shortName           VARCHAR(12) NOT NULL\n" +
"  ,image512pxLink      VARCHAR(59) NOT NULL\n" +
");";
        StringBuilder sqlData = new StringBuilder();
        sqlData.append(sqldata);
        ammo.forEach((c)->{
         sqlData.append("INSERT INTO ammo(armorDamage,damage,caliber,weight,ammoType,fragmentationChance,ricochetChance,heavyBleedModifier,lightBleedModifier,tracer,tracerColor,avg24hPrice,lastLowPrice,name,shortName,image512pxLink) VALUES ("+c.armorDamage+","+c.damage+",'"+c.caliber+"',"+c.weight+",'"+c.ammoType+"',"+c.fragmentationChance+","+c.ricochetChance+","+c.heavyBleedModifier+","+c.lightBleedModifier+",'"+c.tracer+"','"+c.tracerColor+"',"+c.avg24hPrice+","+c.lastLowPrice+",'"+c.name+"','"+c.shortName+"','"+c.image512pxLink+"');");
        });
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            FileWriter file = new FileWriter(RUTA+"/data.sql");
            BufferedWriter output = new BufferedWriter(file);
            output.write(sqlData.toString());
            output.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
