package com.company.myapp.Entities;

import java.util.LinkedHashMap;

public class Personality {
    private final String personalityId;
    private final LinkedHashMap social;
    private final LinkedHashMap processing;
    private final LinkedHashMap decisionMaking;
    private final LinkedHashMap interaction;

    public Personality(String personalityId, LinkedHashMap social, LinkedHashMap processing, LinkedHashMap decisionMaking, LinkedHashMap interaction) {
        this.personalityId = personalityId;
        this.social = social;
        this.processing = processing;
        this.decisionMaking = decisionMaking;
        this.interaction = interaction;
    }

    public String getPersonalityId() {
        return personalityId;
    }

    public LinkedHashMap getSocial() {
        return social;
    }

    public LinkedHashMap getProcessing() {
        return processing;
    }

    public LinkedHashMap getDecisionMaking() {
        return decisionMaking;
    }

    public LinkedHashMap getInteraction() {
        return interaction;
    }

    @Override
    public String toString() {
        return "Personality{" +
                "personalityId='" + personalityId + '\'' +
                ", social='" + social + '\'' +
                ", processing='" + processing + '\'' +
                ", decisionMaking='" + decisionMaking + '\'' +
                ", interaction='" + interaction + '\'' +
                '}';
    }
}
