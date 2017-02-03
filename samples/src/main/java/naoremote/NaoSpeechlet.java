/**
 * Author: flier@techfak.uni-bielefeld.de
 * <p>
 * Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use
 * this file except in compliance with the License. A copy of the License is located at
 * <p>
 * http://aws.amazon.com/apache2.0/
 * <p>
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 * for the specific language governing permissions and limitations under the License.
 */

package naoremote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.amazonaws.util.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;


/**
 * This class implements an Alexa Skill that finds available meals based on the
 * desired day and kind of food (vital, vegetarian, meal of the day ...)
 */
public class NaoSpeechlet implements Speechlet {

    private static final Logger log = LoggerFactory.getLogger(NaoSpeechlet.class);

    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session) throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
        return getNewWelcomeResponse();
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());

        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;
        String cmd = "";

        if ("GetNewNaoIntent".equals(intentName)) {
            if (intent.getSlot("Cmd") != null && intent.getSlot("Cmd").getValue() != null) {
                cmd = intent.getSlot("Cmd").getValue().toLowerCase();
            } else {
                cmd = "unknown";
            }
        }

        if ("GetNewNaoIntent".equals(intentName)) {
            return getNewNaoResponse(cmd);

        } else if ("AMAZON.HelpIntent".equals(intentName)) {
            return getHelpResponse();

        } else if ("AMAZON.StopIntent".equals(intentName)) {
            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText("Goodbye");
            return SpeechletResponse.newTellResponse(outputSpeech);
        } else if ("AMAZON.CancelIntent".equals(intentName)) {
            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText("Goodbye");

            return SpeechletResponse.newTellResponse(outputSpeech);
        } else {
            throw new SpeechletException("Invalid Intent");
        }
    }

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session) throws SpeechletException {
        log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
    }

    /**
     * Generate a String response based on the current slots.
     * The mensa plan is provided as JSON on the web.
     * @return String
     * @param day The desired day of the week
     * @param kind The desired menue, e.g., vital, vegetarian, etc.
     * @throws IOException
     * @see "https://raw.githubusercontent.com/warp1337/ubmensa2json/master/ub_mensa.json"
     */
//    private String getMensaPlan(String day, String kind) {
//
//        String answer = " ";
//        InputStreamReader inputStream = null;
//        BufferedReader bufferedReader = null;
//        StringBuilder builder = new StringBuilder();
//
//        if ("entwickler".equals(day)) {
//            return "Florian Lier. Er hat diese Applikation geschrieben. Netter Typ. Ehrlich.";
//        }
//
//        if ("sonntag".equals(day) || "samstag".equals(day) || "gibt es nicht".equals(day)) {
//            return "Easter eggs!";
//        }
//
//        if ("aktion".equals(kind)) {
//            kind = "aktions theke";
//        }
//
//        if ("vegetarisches".equals(kind)) {
//            kind = "vegetarisch";
//        }
//
//        if ("vital menue".equals(kind)) {
//            kind = "vital";
//        }
//
//        try {
//            String line;
//            URL url = new URL("https://raw.githubusercontent.com/warp1337/ubmensa2json/master/ub_mensa.json");
//            inputStream = new InputStreamReader(url.openStream(), Charset.forName("utf-8"));
//            bufferedReader = new BufferedReader(inputStream);
//            while ((line = bufferedReader.readLine()) != null) {
//                builder.append(line);
//            }
//        } catch (IOException e) {
//            builder.setLength(0);
//        } finally {
//            IOUtils.closeQuietly(inputStream);
//            IOUtils.closeQuietly(bufferedReader);
//        }
//
//        if (builder.length() == 0) {
//            answer = "Entschuldigung, ich konnte keine Informationen zu " + kind + " und " + day + " im Internet finden.";
//        } else {
//
//            try {
//                JSONObject MensaResponseObject = new JSONObject(new JSONTokener(builder.toString()));
//
//                if (MensaResponseObject != null) {
//
//                    JSONArray data = (JSONArray) MensaResponseObject.get(day);
//
//                    for (int i = 0; i < data.length(); i++) {
//                        JSONObject item = (JSONObject) data.get(i);
//                        if (item.has(kind)) {
//                            answer = item.get(kind).toString();
//                        }
//                    }
//                }
//            } catch (JSONException e) {
//                answer = "Entschuldigung, ich konnte keine Informationen im Mensa Plan zu " + kind + " und " + day + " finden.";
//            }
//        }
//
//        return answer;
//    }

    /**
     * Generate a response based on the current slots.
     * The mensa plan is provided as JSON on the web.
     * @return SpeechletResponse
     * @param day The desired day of the week
     * @param menue The desired menue, e.g., vital, vegetarian, etc.
     */
    private SpeechletResponse getNewNaoResponse(String cmd) {

        String speechText = cmd;

        SimpleCard card = new SimpleCard();
        card.setTitle("Nao");
        card.setContent(speechText);

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }

    /**
     * Generate a static help response
     * @return SpeechletResponse
     */
    private SpeechletResponse getHelpResponse() {
        String speechText = "Du kannst fragen welche Menues es heute in der Mensa der Universitaet Bielefeld gibt. Frage zum Beispiel: Mensa Bielefeld Heute Tagesmenue";

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    /**
     * Generate a static welcome response
     * @return SpeechletResponse
     */
    private SpeechletResponse getNewWelcomeResponse() {
        String speechText = "Hey!";

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt);
    }
}