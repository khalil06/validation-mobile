package com.company.myapp.gui;
import com.codename1.charts.ChartComponent;
import com.codename1.components.SpanLabel;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.company.myapp.Components.ButtonComponent;
import com.company.myapp.Entities.Personality;
import com.company.myapp.api.Chart;
import com.company.myapp.api.Histogram;
import com.company.myapp.Components.RadioButtonComponent;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PersonalityTest {
    public static String finalAnswer = "";
    public static int[] extrovertVsIntrovertAnswersStorage = {2, 2, 2, 2, 2};//new int[5];// answer storing
    public static int[] sensingVsIntuitionsAnswersStorage = {2, 2, 2, 2, 2};// answer storing
    public static int[] thinkingVsFeelingAnswersStorage = {2, 2, 2, 2, 2};// answer storing
    public static int[] judgingVsPerceivingAnswersStorage = {2, 2, 2, 2, 2};// answer storing
    public static List<Personality> personalities;

    public static int sum(int[] intArrays) {
        int sum = 0;
        for (int number : intArrays)
            sum += number;
        return sum;
    }

    public static int countNumbers(int[] numArray, int number) {
        int count = 0;
        for (int num : numArray) {
            if (num == number)
                count++;
        }
        return count;
    }

    public static Form createPersonalityTestForm() {

        Form q1 = new Form("Personality Test", new BoxLayout(BoxLayout.Y_AXIS));
        Response<Map> jsonData = Rest.
                get("http://127.0.0.1:8000/mobile").
                acceptJson().
                getAsJsonMap();

        List<Map<String, Object>> res = (List<Map<String, Object>>) jsonData.getResponseData().get("root");

        personalities = res.stream().map(obj -> {

            String personalityId = (String) obj.get("personalityId");
            LinkedHashMap decisionMaking = (LinkedHashMap) obj.get("decisionMaking");
            LinkedHashMap interaction = (LinkedHashMap) obj.get("interaction");
            LinkedHashMap processing = (LinkedHashMap) obj.get("processing");
            LinkedHashMap social = (LinkedHashMap) obj.get("social");
            return new Personality(personalityId, social, processing, decisionMaking, interaction);
        }).collect(Collectors.toList());
        int questionNb = 1;

        String[] extroversionVsIntroversionTest = {
                "A. expend energy, enjoy groups. B. conserve energy, enjoy one-on-one :",
                "A. more outgoing, think out loud. B. more reserved, think to yourself :",
                "A. seek many tasks, public activities, interaction with others. :" +
                        "B. seek private solitary activities with quiet to concentrate :",
                "A. external, communicative,  express yourself. B. internal, reticent, keep to yourself :",
                "A. active, initiate. B. reflective, deliberate :"};
        Toolbar tb = q1.getToolbar();
        Container topBar = BorderLayout.east(new Label("Menu"));
        topBar.add(BorderLayout.SOUTH, new Label("Cool App Tagline...", "SidemenuTagline"));
        topBar.setUIID("SideCommand");
        tb.addComponentToSideMenu(topBar);

        tb.addMaterialCommandToSideMenu("Home", FontImage.MATERIAL_HOME, e -> {
        });
        tb.addMaterialCommandToSideMenu("Website", FontImage.MATERIAL_WEB, e -> {
        });
        tb.addMaterialCommandToSideMenu("Settings", FontImage.MATERIAL_SETTINGS, e -> {
        });
        tb.addMaterialCommandToSideMenu("About", FontImage.MATERIAL_INFO, e -> {
        });

        Style s = UIManager.getInstance().getComponentStyle("Title");
        for (String question : extroversionVsIntroversionTest
        ) {
            Label questionNumber = new Label("Question Number : " + questionNb);
            int finalQuestionNb = questionNb - 1;
            SpanLabel questionLabel = new SpanLabel(question);
            RadioButton A = new RadioButtonComponent().getRadioButtonComponent("A");
            A.addActionListener(evt -> extrovertVsIntrovertAnswersStorage[finalQuestionNb] = 1);
            RadioButton B = new RadioButtonComponent().getRadioButtonComponent("B");
            B.addActionListener(evt -> extrovertVsIntrovertAnswersStorage[finalQuestionNb] = 0);
            new ButtonGroup(A, B);
            q1.add(questionNumber);
            q1.add(questionLabel);
            q1.add(A);
            q1.add(B);

            questionNb++;
        }
        q1.getToolbar().setUnselectedStyle(s);
        Button next=new ButtonComponent().getButton("Next");
        next.addActionListener(evt -> {
            int sumOfAsInExtroversion = sum(extrovertVsIntrovertAnswersStorage);
            // append personality type accordingly
            System.out.println(" I OR E " + sumOfAsInExtroversion);
            if (sumOfAsInExtroversion < 3)
                finalAnswer = finalAnswer + "I";
            else {
                finalAnswer = finalAnswer + "E";
            }
            System.out.println("final answer" + finalAnswer);
            createPage2Form().show();
        });
        //    next.addActionListener();

        q1.add(next);
        return q1;
    }

    public static Form createPage2Form() {
        Form q2 = new Form("Personality Test", new BoxLayout(BoxLayout.Y_AXIS));
        int questionNb = 1;

        // sensing vs intuition questions
        String[] sensingVsIntuitionTest = {
                "A. interpret literally. B. look for meaning and possibilities",
                "A. practical, realistic, experiential. B. imaginative, innovative, theoretical",
                "A. standard, usual, conventional. B. different, novel, unique",
                "A. focus on here-and-now\" .B.look to the future, global perspective, \"big picture\"",
                "A. facts, things, \"what is\". B. ideas, dreams, \"what could be,\" philosophical"
        };
        for (String question : sensingVsIntuitionTest
        ) {
            Label questionNumber = new Label("Question Number : " + questionNb);

            SpanLabel questionLabel = new SpanLabel(question);
            int finalQuestionNb = questionNb - 1;
            RadioButton A = new RadioButtonComponent().getRadioButtonComponent("A");
            A.addActionListener(evt -> sensingVsIntuitionsAnswersStorage[finalQuestionNb] = 1);
            RadioButton B = new RadioButtonComponent().getRadioButtonComponent("B");
            B.addActionListener(evt -> sensingVsIntuitionsAnswersStorage[finalQuestionNb] = 0);
            new ButtonGroup(A, B);
            q2.add(questionNumber);
            q2.add(questionLabel);
            q2.add(A);
            q2.add(B);
            questionNb++;
        }
        Button next=new ButtonComponent().getButton("Next");
        next.addActionListener(evt -> {
            int sumOfAsInSensing = sum(sensingVsIntuitionsAnswersStorage);
            // append personality type accordingly
            if (sumOfAsInSensing < 3)
                finalAnswer = finalAnswer + "N";
            else {
                finalAnswer = finalAnswer + "S";
            }
            createPage3Form().show();
        });
        q2.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> q2.showBack());

        q2.add(next);
        return q2;
    }

    public static Form createPage3Form() {
        Form q3 = new Form("Personality Test", new BoxLayout(BoxLayout.Y_AXIS));
        String answer;
        int questionNb = 1;

        // sensing vs intuition questions
        String[] thinkingVsFeelingTest = {
                "A. logical, thinking, questioning. B. empathetic, feeling, accommodating",
                "B. candid, straight forward, frank. B.tactful, kind, encouraging",
                "A. firm, tend to criticize, hold the line. B. gentle, tend to appreciate, conciliate",
                "A. tough-minded, just B.tender-hearted, merciful",
                "A. matter of fact, issue-oriented B. sensitive, people-oriented, compassionate",
        };
        for (String question : thinkingVsFeelingTest
        ) {
            int finalQuestionNb = questionNb - 1;
            Label questionNumber = new Label("Question Number : " + questionNb);

            SpanLabel questionLabel = new SpanLabel(question);
            RadioButton A = new RadioButtonComponent().getRadioButtonComponent("A");
            A.addActionListener(evt -> thinkingVsFeelingAnswersStorage[finalQuestionNb] = 1);
            RadioButton B = new RadioButtonComponent().getRadioButtonComponent("B");
            B.addActionListener(evt -> thinkingVsFeelingAnswersStorage[finalQuestionNb] = 0);
            new ButtonGroup(A, B);

            q3.add(questionNumber);
            q3.add(questionLabel);
            q3.add(A);
            q3.add(B);
            questionNb++;

        }
        Button next=new ButtonComponent().getButton("Next");
        next.addActionListener(evt -> {
            int sumOfAsInThinking = sum(thinkingVsFeelingAnswersStorage);
            // append personality type accordingly
            if (sumOfAsInThinking < 3)
                finalAnswer = finalAnswer + "F";
            else {
                finalAnswer = finalAnswer + "T";
            }
            createPage4Form().show();
        });
        //    next.addActionListener();
        q3.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> q3.showBack());

        q3.add(next);
        return q3;
    }

    public static Form createPage4Form() {
        Form q4 = new Form("Personality Test", new BoxLayout(BoxLayout.Y_AXIS));
        String answer;
        int questionNb = 1;
        // sensing vs intuition questions
        String[] judgingVsPerceivingTest = {
                "A. organized, orderly. B. flexible, adaptable",
                "A. plan, schedule B. unplanned, spontaneous",
                "A. regulated, structured B. easygoing, “live\" and “let live\"",
                "A. preparation, plan ahead. B. go with the flow, adapt as you go",
                "A. control, govern B. latitude, freedom"};
        for (String question : judgingVsPerceivingTest
        ) {
            int finalQuestionNb = questionNb - 1;
            SpanLabel questionLabel = new SpanLabel(question);
            questionLabel.setGap(1);
            RadioButton A = new RadioButtonComponent().getRadioButtonComponent("A");
            A.addActionListener(evt -> judgingVsPerceivingAnswersStorage[finalQuestionNb] = 1);
            RadioButton B = new RadioButtonComponent().getRadioButtonComponent("B");
            B.addActionListener(evt -> judgingVsPerceivingAnswersStorage[finalQuestionNb] = 0);
            new ButtonGroup(A, B);
            q4.add(questionLabel);
            q4.add(A);
            q4.add(B);
            questionNb++;
        }
        Button next=new ButtonComponent().getButton("Next");
        next.addActionListener(evt -> {
            int sumOfAsInJudging = sum(judgingVsPerceivingAnswersStorage);
            // append personality type accordingly
            if (sumOfAsInJudging < 3)
                finalAnswer = finalAnswer + "P";
            else {
                finalAnswer = finalAnswer + "J";
            }
            result().show();
        });
        q4.add(next);
        q4.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> q4.showBack());

        return q4;

    }

    public static Form result() {
        //  body().
        //acceptJson().
        //getAsJsonMap();
        List<Personality> filterPersonalities = personalities.stream().filter(personality -> personality.getPersonalityId().startsWith(finalAnswer)).collect(Collectors.toList());
        Form personalityReport = new Form("Personality Test", new BoxLayout(BoxLayout.Y_AXIS));

        filterPersonalities.forEach(filterPersonality -> {
            Label personalityId = new Label(filterPersonality.getPersonalityId());
            SpanLabel sociacDetails = new SpanLabel(filterPersonality.getSocial().get("socialDetails").toString());
            SpanLabel processingDetails = new SpanLabel(filterPersonality.getProcessing().get("processingDetails").toString());
            SpanLabel interactionDetails = new SpanLabel(filterPersonality.getInteraction().get("interactionDetails").toString());
            SpanLabel thinkingDetails = new SpanLabel(filterPersonality.getDecisionMaking().get("decisionMakingDetails").toString());
            personalityReport.add(personalityId);
            personalityReport.add(sociacDetails);
            personalityReport.add(processingDetails);
            personalityReport.add(interactionDetails);
            personalityReport.add(thinkingDetails);
            ChartComponent chart = new Chart().createPieChartForm();
            personalityReport.add(chart);
            personalityReport.add(new Histogram().execute());
        });
        personalityReport.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> personalityReport.showBack());
    Button returnbtn=new Button("return to main");
    returnbtn.addActionListener(evt -> new HomeForm().show());
    personalityReport.add(returnbtn);
        return personalityReport;

    }

}
