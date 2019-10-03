package com;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SWAPIUtility {

    /**
     * @param characterPhrase search criteria given by the user to find the character,
     * @param homeworld       search criteria given by the user to find homeworld of character
     * @param reportId id of report
     * @return returns ReportEntity class
     */
    public static ReportEntity searchAndGenerateReport(String characterPhrase, String homeworld, int reportId) {
        HttpGet httpGetCharacter = new HttpGet("https://swapi.co/api/people/?search=" + characterPhrase);
        HttpGet httpGetPlanet = new HttpGet("https://swapi.co/api/planets/?search=" + homeworld);
        JsonObject character = null;
        JsonObject planet = null;
        ReportEntity report = new ReportEntity();
        report.setReportId(reportId);

        try {
            character = getRequest(httpGetCharacter);
            planet = getRequest(httpGetPlanet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert character != null;
        assert planet != null;

        JsonArray characterResults = character.get("results").getAsJsonArray();
        JsonArray planetResults = planet.get("results").getAsJsonArray();

        //search for a entity matching the requirements, that is character and home world
        boolean matchFound = false;
        for (int i = 0; i < characterResults.size(); i++) {
            String homeworldURL = characterResults.get(i).getAsJsonObject().get("homeworld").getAsString();
            String homeworldName = null;
            try {
                homeworldName = getRequest(new HttpGet(homeworldURL)).get("name").getAsString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert homeworldName != null;
            for (int j = 0; j < planetResults.size(); j++) {
                if (homeworldName.equals(planetResults.get(j).getAsJsonObject().get("name").getAsString())) {
                    matchFound = true;
                    report.setQueryCriteriaCharacterPhrase(characterPhrase);
                    report.setQueryCriteriaPlanetName(homeworld);
                    report.setPlanetName(homeworldName);
                    report.setCharacterName(characterResults.get(i).getAsJsonObject().get("name").getAsString());
                    report.setPlanetId(Integer.parseInt(homeworldURL.split("/")[5]));
                    report.setCharacterId(Integer.parseInt(characterResults.get(i).getAsJsonObject().get("url").getAsString().split("/")[5]));
                    report.setFilmId(Integer.parseInt(characterResults.get(i).getAsJsonObject().get("films").getAsJsonArray().get(0).getAsString().split("/")[5]));
                    try {
                        JsonObject film = getRequest(new HttpGet(characterResults.get(i).getAsJsonObject().get("films").getAsJsonArray().get(0).getAsString()));
                        report.setFilmName(film.get("title").getAsString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                } else {
                    report.setQueryCriteriaCharacterPhrase(characterPhrase);
                    report.setQueryCriteriaPlanetName(homeworld);
                }
            }
            if (matchFound) {
                break;
            }
        }
        return report;
    }

    private static JsonObject getRequest(HttpGet request) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        request.addHeader("accept", "application/json");
        HttpResponse response = httpClient.execute(request);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("HTTP error occured : " + response.getStatusLine().getStatusCode());
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }

        JsonObject jsonObject = deserialize(sb.toString());
        bufferedReader.close();

        return jsonObject;
    }

    private static JsonObject deserialize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, JsonObject.class);
    }
}
